package org.sonatype.nexus.plugins.image.signer.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
    public static final String SHA265 = "sha256";
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
        return "v2/-/blobs/" + assetDigest;
    }

    public static String getSHA256Digest(String importJson) throws NoSuchAlgorithmException {
        return SHA265 + ":" + new String(toHexView(MessageDigest.getInstance("SHA256")
                .digest(importJson.getBytes(StandardCharsets.UTF_8))));
    }
}
