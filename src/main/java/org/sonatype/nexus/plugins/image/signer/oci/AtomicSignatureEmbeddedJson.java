
package org.sonatype.nexus.plugins.image.signer.oci;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * JSON embedded in an atomic container signature
 * <p>
 * This schema is a supplement to atomic-signature.md in this directory.
 * 
 * Consumers of the JSON MUST use the processing rules documented in atomic-signature.md, especially the requirements for the 'critical' subobject.
 * 
 * Whenever this schema and atomic-signature.md, or the github.com/containers/image/signature implementation, differ,
 * it is the atomic-signature.md document, or the github.com/containers/image/signature implementation, which governs.
 * 
 * Users are STRONGLY RECOMMENDED to use the github.com/containers/image/signature implementation instead of writing
 * their own, ESPECIALLY when consuming signatures, so that the policy.json format can be shared by all image consumers.
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "critical",
    "optional"
})
public class AtomicSignatureEmbeddedJson {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("critical")
    private Critical critical;
    /**
     * All members are optional, but if they are included, they must be valid.
     * (Required)
     * 
     */
    @JsonProperty("optional")
    @JsonPropertyDescription("All members are optional, but if they are included, they must be valid.")
    private Optional optional;

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("critical")
    public Critical getCritical() {
        return critical;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("critical")
    public void setCritical(Critical critical) {
        this.critical = critical;
    }

    /**
     * All members are optional, but if they are included, they must be valid.
     * (Required)
     * 
     */
    @JsonProperty("optional")
    public Optional getOptional() {
        return optional;
    }

    /**
     * All members are optional, but if they are included, they must be valid.
     * (Required)
     * 
     */
    @JsonProperty("optional")
    public void setOptional(Optional optional) {
        this.optional = optional;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(AtomicSignatureEmbeddedJson.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("critical");
        sb.append('=');
        sb.append(((this.critical == null)?"<null>":this.critical));
        sb.append(',');
        sb.append("optional");
        sb.append('=');
        sb.append(((this.optional == null)?"<null>":this.optional));
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
        result = ((result* 31)+((this.critical == null)? 0 :this.critical.hashCode()));
        result = ((result* 31)+((this.optional == null)? 0 :this.optional.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof AtomicSignatureEmbeddedJson) == false) {
            return false;
        }
        AtomicSignatureEmbeddedJson rhs = ((AtomicSignatureEmbeddedJson) other);
        return (((this.critical == rhs.critical)||((this.critical!= null)&&this.critical.equals(rhs.critical)))&&((this.optional == rhs.optional)||((this.optional!= null)&&this.optional.equals(rhs.optional))));
    }

}
