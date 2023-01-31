package org.sonatype.nexus.plugins.image.signer.util;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.joda.time.LocalDateTime;
import org.sonatype.nexus.plugins.image.signer.oci.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;

public class ListenerUtils {
    public static final String APPLICATION_VND_OCI_IMAGE_MANIFEST_V_1_JSON = "application/vnd.oci.image.manifest.v1+json";
    public static final String APPLICATION_VND_OCI_IMAGE_CONFIG_V_1_JSON = "application/vnd.oci.image.config.v1+json";
    public static final String APPLICATION_VND_DOCKER_CONTAINER_IMAGE_V_1_JSON = "application/vnd.docker.container.image.v1+json";
    public static final String APPLICATION_VND_DEV_COSIGN_SIMPLESIGNING_V_1_JSON = "application/vnd.dev.cosign.simplesigning.v1+json";
    public static final String SYSTEM = "system";
    public static final String DOCKER = "docker";
    public static final String CONTENT_DIGEST = "content_digest";
    public static final String DATE_TIME_PATTERN = "yyyy-mm-dd'T'hh:mm:ss'Z'";
    public static final String DEV_COSIGNPROJECT_COSIGN_SIGNATURE = "dev.cosignproject.cosign/signature";
    public static final String SIG_EXTENSION = ".sig";
    public static final String SHA_256 = "sha256";
    public static final String ASSET_KIND_MANIFEST = "MANIFEST";
    public static final String ASSET_KIND_BLOB = "BLOB";
    public static final String ASSET_KIND_PROPERTY = "asset_kind";
    public static  byte[] toHexView(byte[] byteArray) {
        if ( byteArray != null) {
            StringBuilder sb = new StringBuilder();
            for (byte digit : byteArray){
                String hexDigit = String.format("%02x",digit);
                sb.append(hexDigit);
            }
            return sb.toString().getBytes(StandardCharsets.UTF_8);
        }
        return null;
    }

    public static String getBlobName(String assetDigest) {
        return "/v2/-/blobs/" + assetDigest;
    }

    public static String getSHA256Digest(String importJson) throws NoSuchAlgorithmException {
        return getSHA256Digest(importJson, true);
    }

    public static String getSHA256Digest(String importJson, boolean withoutAlgorithmName) throws NoSuchAlgorithmException {
        StringBuilder sb = new StringBuilder();
        if (withoutAlgorithmName) {
            sb.append(SHA_256).append(":");
        }
        sb.append(new String(toHexView(MessageDigest.getInstance("SHA256")
                .digest(importJson.getBytes(StandardCharsets.UTF_8)))));
        return sb.toString();
    }

    public static String generateSignatureLayer(String componentName, String contentDigest) throws Exception {
        AtomicSignatureEmbeddedJson simpleSignature = new AtomicSignatureEmbeddedJson();
        Critical critical = new Critical();
        Image image = new Image();
        image.setDockerManifestDigest(contentDigest);
        critical.setImage(image);
        Identity identity = new Identity();
        identity.setDockerReference(componentName);
        critical.setIdentity(identity);
        critical.setType(Critical.Type.ATOMIC_CONTAINER_SIGNATURE);
        Optional optional = new Optional();
        optional.setCreator(SYSTEM);
        simpleSignature.setCritical(critical);
        simpleSignature.setOptional(optional);
        JsonMapper jsonMapper = new JsonMapper();
        String simpleSignatureJson = jsonMapper.writeValueAsString(simpleSignature);
        return simpleSignatureJson;
    }

    public static String generateConfig(String simpleSignatureDigest) throws Exception {
        ConfigSchema configSchema = new ConfigSchema();
        configSchema.setArchitecture("");
        configSchema.setCreated(LocalDateTime.now().toDateTime().toString(DATE_TIME_PATTERN));
        configSchema.setOs("");
        Rootfs rootfs = new Rootfs();
        rootfs.setType(Rootfs.Type.LAYERS);
        rootfs.setDiffIds(Arrays.asList(simpleSignatureDigest));
        JsonMapper jsonMapper = new JsonMapper();
        String configSchemaJson = jsonMapper.writeValueAsString(configSchema);
        return configSchemaJson;
    }

    public static String generateManifest(String configSchemaJson, String configSchemaDigest, String simpleSignatureJson,
                                    String simpleSignatureDigest, String signature) throws Exception {
        ImageManifestSchema imageManifestSchema = new ImageManifestSchema();
        imageManifestSchema.setSchemaVersion(2);
        imageManifestSchema.setMediaType(APPLICATION_VND_OCI_IMAGE_MANIFEST_V_1_JSON);
        ContentDescriptor config = new ContentDescriptor();
        config.setMediaType(APPLICATION_VND_OCI_IMAGE_CONFIG_V_1_JSON);
        config.setSize(configSchemaJson.getBytes(StandardCharsets.UTF_8).length);
        config.setDigest(configSchemaDigest);
        imageManifestSchema.setConfig(config);
        ContentDescriptor layerWithSignature = new ContentDescriptor();
        layerWithSignature.setMediaType(APPLICATION_VND_DEV_COSIGN_SIMPLESIGNING_V_1_JSON);
        layerWithSignature.setDigest(simpleSignatureDigest);
        layerWithSignature.setSize(simpleSignatureJson.getBytes(StandardCharsets.UTF_8).length);
        HashMap<String, String> annotations = new HashMap<>();
        annotations.put(DEV_COSIGNPROJECT_COSIGN_SIGNATURE, signature);
        layerWithSignature.setAnnotations(annotations);
        imageManifestSchema.setLayers(Arrays.asList(layerWithSignature));
        JsonMapper jsonMapper = new JsonMapper();
        String imageManifestSchemaJson = jsonMapper.writeValueAsString(imageManifestSchema);
        return imageManifestSchemaJson;
    }
}
