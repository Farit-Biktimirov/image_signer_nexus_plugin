package org.sonatype.nexus.plugins.image.signer.listener.content;


import com.fasterxml.jackson.databind.json.JsonMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonatype.nexus.common.event.EventAware;
import org.sonatype.nexus.common.hash.HashAlgorithm;
import org.sonatype.nexus.plugins.image.signer.oci.*;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.content.event.asset.AssetCreatedEvent;
import org.sonatype.nexus.repository.content.Asset;
import org.sonatype.nexus.repository.content.Component;
import org.sonatype.nexus.repository.content.facet.*;
import org.sonatype.nexus.repository.view.payloads.TempBlob;

import javax.inject.Named;
import javax.inject.Singleton;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import static org.sonatype.nexus.plugins.image.signer.util.ListenerUtils.*;

@Named
@Singleton
public class ImageManifestListener implements EventAware {

    private final Logger log = LoggerFactory.getLogger(org.sonatype.nexus.plugins.image.signer.listener.orient.ImageManifestListener.class);

    public ImageManifestListener() {

    }

    @Subscribe
    @AllowConcurrentEvents
    public void on(AssetCreatedEvent assetCreatedEvent) throws Exception {
        log.info("LISTENER: got event {}", assetCreatedEvent);

        Asset asset = assetCreatedEvent.getAsset();
        Repository repository = assetCreatedEvent.getRepository().get();


        if (asset.component().isPresent()) {
            return;
        }

        if (asset.kind().equals(ASSET_KIND_MANIFEST)
                && repository.getFormat().getValue().equals(DOCKER)) {

            String componentName = asset.path().substring(asset.path().indexOf("v2/") + 3,asset.path().indexOf("/manifests"));
            String assetContentDigest =  asset.attributes().contains(CONTENT_DIGEST) ? (String) asset.attributes().get(CONTENT_DIGEST) :
                    asset.path().substring(asset.path().indexOf(SHA_256));
            ContentFacet contentFacet = repository.facet(ContentFacet.class);

            String simpleSignatureJson = generateSignatureLayer(componentName, assetContentDigest);
            String simpleSignatureDigest = getSHA256Digest(simpleSignatureJson);

            String configSchemaJson = generateConfig(simpleSignatureDigest);
            String configSchemaDigest = getSHA256Digest(configSchemaJson);

            //TODO: add signature
            String imageManifestSchemaJson = generateManifest(configSchemaJson, configSchemaDigest, simpleSignatureJson,
                    simpleSignatureDigest, "bla-bla-bla");
            String imageManifestSchemaDigest = getSHA256Digest(imageManifestSchemaJson);

            try {


                try (InputStream io = contentFacet.blobs().blob(asset.blob().get().blobRef()).get().getInputStream()) {
                    JsonMapper mapper = new JsonMapper();
                    ImageManifestSchema assetOriginalManifest = mapper.readValue(io, ImageManifestSchema.class);
                    if (assetOriginalManifest.getLayers().stream()
                            .filter( p -> p.getMediaType().equals(APPLICATION_VND_DEV_COSIGN_SIMPLESIGNING_V_1_JSON))
                            .count() > 0 ) {
                        log.info("LISTENER: manifest relates to signer");
                        return;
                    }
                }

                createAsset(contentFacet, simpleSignatureJson.getBytes(StandardCharsets.UTF_8), getBlobName(simpleSignatureDigest),
                        APPLICATION_VND_DOCKER_CONTAINER_IMAGE_V_1_JSON, ASSET_KIND_BLOB);

                createAsset(contentFacet, configSchemaJson.getBytes(StandardCharsets.UTF_8), getBlobName(configSchemaDigest),
                        APPLICATION_VND_DOCKER_CONTAINER_IMAGE_V_1_JSON, ASSET_KIND_BLOB);

                String assetManifestName = asset.path().substring(0, asset.path().indexOf(SHA_256)) + imageManifestSchemaDigest;

                createAsset(contentFacet, imageManifestSchemaJson.getBytes(StandardCharsets.UTF_8), assetManifestName,
                        APPLICATION_VND_OCI_IMAGE_MANIFEST_V_1_JSON, ASSET_KIND_MANIFEST);
                String assetName = (asset.path().replace(":","-") + SIG_EXTENSION);
                createAssetWithComponent(contentFacet, imageManifestSchemaJson.getBytes(StandardCharsets.UTF_8),
                        assetName,componentName, APPLICATION_VND_OCI_IMAGE_MANIFEST_V_1_JSON,
                        getSHA256Digest(imageManifestSchemaJson, false),  ASSET_KIND_MANIFEST);
            } catch(Exception ex) {
                log.error(ex.getMessage(), ex);
            }
        }
    }


    private void createAsset(ContentFacet contentFacet, byte[] inputByteArray,
                             String assetName, String contentType, String assetKind) throws Exception {
        try(ByteArrayInputStream bais = new ByteArrayInputStream(inputByteArray);
            TempBlob blob = contentFacet.blobs().ingest(bais, contentType, ImmutableList.of(HashAlgorithm.SHA1, HashAlgorithm.SHA256))) {
            contentFacet.assets().path(assetName)
                    .blob(blob)
                    .kind(assetKind)
                    .save();
        }
    }

    private void createAssetWithComponent(ContentFacet contentFacet, byte[] inputByteArray,
                                          String assetName, String componentName, String contentType, String contentDigest, String assetKind) throws Exception {
        try(ByteArrayInputStream bais = new ByteArrayInputStream(inputByteArray);
            TempBlob blob = contentFacet.blobs().ingest(bais, contentType,  ImmutableList.of(HashAlgorithm.SHA1, HashAlgorithm.SHA256))) {
            Component component = contentFacet.components()
                                              .name(componentName)
                                              .version(assetName.substring(assetName.indexOf(SHA_256)))
                                              .getOrCreate();
            HashMap<String, Object> attributes = new HashMap<>();
            attributes.put(CONTENT_DIGEST, contentDigest);
            contentFacet.assets().path(assetName)
                    .blob(blob)
                    .kind(assetKind)
                    .attributes(DOCKER, attributes)
                    .component(component)
                    .save();
        }
    }
}
