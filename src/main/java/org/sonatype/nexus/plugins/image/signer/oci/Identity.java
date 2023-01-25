
package org.sonatype.nexus.plugins.image.signer.oci;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "docker-reference"
})
public class Identity {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("docker-reference")
    private String dockerReference;

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("docker-reference")
    public String getDockerReference() {
        return dockerReference;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("docker-reference")
    public void setDockerReference(String dockerReference) {
        this.dockerReference = dockerReference;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Identity.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("dockerReference");
        sb.append('=');
        sb.append(((this.dockerReference == null)?"<null>":this.dockerReference));
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
        result = ((result* 31)+((this.dockerReference == null)? 0 :this.dockerReference.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Identity) == false) {
            return false;
        }
        Identity rhs = ((Identity) other);
        return ((this.dockerReference == rhs.dockerReference)||((this.dockerReference!= null)&&this.dockerReference.equals(rhs.dockerReference)));
    }

}
