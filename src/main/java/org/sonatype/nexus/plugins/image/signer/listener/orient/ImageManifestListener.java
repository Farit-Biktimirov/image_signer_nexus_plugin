package org.sonatype.nexus.plugins.image.signer.listener.orient;


import com.fasterxml.jackson.databind.json.JsonMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonatype.nexus.common.event.EventAware;
import org.sonatype.nexus.common.hash.HashAlgorithm;
import org.sonatype.nexus.plugins.image.signer.oci.ImageManifestSchema;
import org.sonatype.nexus.repository.Format;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.manager.RepositoryManager;
import org.sonatype.nexus.repository.storage.*;
import org.sonatype.nexus.repository.view.payloads.TempBlob;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.sonatype.nexus.plugins.image.signer.util.ListenerUtils.*;


@Named
@Singleton
public class ImageManifestListener implements EventAware {

    private final Logger log = LoggerFactory.getLogger(ImageManifestListener.class);

    private final RepositoryManager repositoryManager;

    @Inject
    public ImageManifestListener(RepositoryManager repositoryManager) {
        this.repositoryManager = repositoryManager;
    }

    @Subscribe
    @AllowConcurrentEvents
    public void on(AssetCreatedEvent assetCreatedEvent) throws Exception {
        log.info("LISTENER: got event {}", assetCreatedEvent);

        String repositoryName = assetCreatedEvent.getRepositoryName();
        Asset asset = assetCreatedEvent.getAsset();
        asset.format();
        Repository repository = repositoryManager.get(repositoryName);

        if (asset.componentId() != null) {
            return;
        }

        if (asset.formatAttributes().contains(ASSET_KIND_PROPERTY)
                && asset.formatAttributes().get(ASSET_KIND_PROPERTY).equals(ASSET_KIND_MANIFEST)
                && asset.format().equals(DOCKER)) {

            String componentName = asset.name().substring(asset.name().indexOf("v2/") + 3,asset.name().indexOf("/manifests"));
            String assetContentDigest = (String) asset.formatAttributes().get(CONTENT_DIGEST);
            StorageFacet storageFacet = repository.facet(StorageFacet.class);
            StorageTx sTx = storageFacet.txSupplier().get();

            String simpleSignatureJson = generateSignatureLayer(componentName, assetContentDigest);
            String simpleSignatureDigest = getSHA256Digest(simpleSignatureJson);

            String configSchemaJson = generateConfig(simpleSignatureDigest);
            String configSchemaDigest = getSHA256Digest(configSchemaJson);

            //TODO: add signature
            String imageManifestSchemaJson = generateManifest(configSchemaJson, configSchemaDigest, simpleSignatureJson,
                    simpleSignatureDigest, "bla-bla-bla");
            String imageManifestSchemaDigest = getSHA256Digest(imageManifestSchemaJson);

            try {
                sTx.begin();
                Bucket bucket = sTx.findBucket(repository);

                try (InputStream io = sTx.getBlob(asset.blobRef()).getInputStream()) {
                    JsonMapper mapper = new JsonMapper();

                    ImageManifestSchema assetOriginalManifest = mapper.readValue(io, ImageManifestSchema.class);

                    if (assetOriginalManifest.getLayers().stream()
                            .filter( p -> p.getMediaType().equals(APPLICATION_VND_DEV_COSIGN_SIMPLESIGNING_V_1_JSON))
                            .count() > 0 ) {
                        log.info("LISTENER: manifest relates to signer");
                        return;
                    }

                }

                Map<String, String> attributes = new HashMap<>();
                attributes.put(ASSET_KIND_PROPERTY, ASSET_KIND_BLOB);

                createAsset(sTx,storageFacet,bucket, simpleSignatureJson.getBytes(StandardCharsets.UTF_8), getBlobName(simpleSignatureDigest),
                        APPLICATION_VND_DOCKER_CONTAINER_IMAGE_V_1_JSON, attributes);

                createAsset(sTx,storageFacet,bucket, configSchemaJson.getBytes(StandardCharsets.UTF_8), getBlobName(configSchemaDigest),
                        APPLICATION_VND_DOCKER_CONTAINER_IMAGE_V_1_JSON, attributes);

                String assetManifestName = asset.name().substring(0, asset.name().indexOf(SHA_256)) + imageManifestSchemaDigest;
                attributes.put(ASSET_KIND_PROPERTY, ASSET_KIND_MANIFEST);
                attributes.put(CONTENT_DIGEST, imageManifestSchemaDigest);
                createAsset(sTx,storageFacet,bucket, imageManifestSchemaJson.getBytes(StandardCharsets.UTF_8), assetManifestName,
                        APPLICATION_VND_OCI_IMAGE_MANIFEST_V_1_JSON, attributes);
                String assetName = (asset.name().replace(":","-") + SIG_EXTENSION);
                createAssetWithComponent(sTx,storageFacet,bucket, imageManifestSchemaJson.getBytes(StandardCharsets.UTF_8),
                        assetName,componentName, APPLICATION_VND_OCI_IMAGE_MANIFEST_V_1_JSON, attributes);

                sTx.saveAsset(asset);
                sTx.commit();
            } catch(Exception ex) {
                log.error(ex.getMessage(), ex);
            } finally {
                sTx.close();
            }
        }
    }

    private void createAsset(StorageTx sTx, StorageFacet storageFacet, Bucket bucket, byte[] inputByteArray,
                                String assetName, String contentType, Map<String, String> attributes) throws Exception {
        try(ByteArrayInputStream bais = new ByteArrayInputStream(inputByteArray);
            TempBlob blob = storageFacet.createTempBlob(bais, ImmutableList.of(HashAlgorithm.SHA1, HashAlgorithm.SHA256))) {
            Asset simpleSignatureAsset = sTx.createAsset(bucket, new Format(DOCKER) {});
            sTx.attachBlob(simpleSignatureAsset, sTx.createBlob(assetName, blob, null, contentType, true));
            simpleSignatureAsset.formatAttributes().backing().putAll(attributes);
            simpleSignatureAsset.name(assetName);
            sTx.saveAsset(simpleSignatureAsset);
        }
    }

    private void createAssetWithComponent(StorageTx sTx, StorageFacet storageFacet, Bucket bucket, byte[] inputByteArray,
                                             String assetName, String componentName, String contentType, Map<String, String> attributes) throws Exception {
        try(ByteArrayInputStream bais = new ByteArrayInputStream(inputByteArray);
            TempBlob blob = storageFacet.createTempBlob(bais, ImmutableList.of(HashAlgorithm.SHA1, HashAlgorithm.SHA256))) {
            Asset manifestAsset = sTx.createAsset(bucket, new Format(DOCKER) {});
            manifestAsset.name(assetName);
            sTx.attachBlob(manifestAsset, sTx.createBlob(assetName, blob, null, contentType, true));
            manifestAsset.formatAttributes().backing().putAll(attributes);
            Component newComponent = sTx.createComponent(bucket, new Format(DOCKER) {});
            newComponent.version(assetName.substring(assetName.indexOf(SHA_256)));
            newComponent.name(componentName);
            sTx.saveComponent(newComponent);
            manifestAsset.componentId(newComponent.getEntityMetadata().getId());
            sTx.saveAsset(manifestAsset);
        }
    }
}
