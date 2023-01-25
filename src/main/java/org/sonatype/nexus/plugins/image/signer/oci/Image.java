
package org.sonatype.nexus.plugins.image.signer.oci;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "docker-manifest-digest"
})
public class Image {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("docker-manifest-digest")
    private String dockerManifestDigest;

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("docker-manifest-digest")
    public String getDockerManifestDigest() {
        return dockerManifestDigest;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("docker-manifest-digest")
    public void setDockerManifestDigest(String dockerManifestDigest) {
        this.dockerManifestDigest = dockerManifestDigest;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Image.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("dockerManifestDigest");
        sb.append('=');
        sb.append(((this.dockerManifestDigest == null)?"<null>":this.dockerManifestDigest));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.dockerManifestDigest == null)? 0 :this.dockerManifestDigest.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Image) == false) {
            return false;
        }
        Image rhs = ((Image) other);
        return ((this.dockerManifestDigest == rhs.dockerManifestDigest)||((this.dockerManifestDigest!= null)&&this.dockerManifestDigest.equals(rhs.dockerManifestDigest)));
    }

}
