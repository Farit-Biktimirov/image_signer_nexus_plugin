
package org.sonatype.nexus.plugins.image.signer.oci;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "type",
    "image",
    "identity"
})
public class Critical {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("type")
    private Critical.Type type;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("image")
    private Image image;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("identity")
    private Identity identity;

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("type")
    public Critical.Type getType() {
        return type;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("type")
    public void setType(Critical.Type type) {
        this.type = type;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("image")
    public Image getImage() {
        return image;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("image")
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("identity")
    public Identity getIdentity() {
        return identity;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("identity")
    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Critical.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("type");
        sb.append('=');
        sb.append(((this.type == null)?"<null>":this.type));
        sb.append(',');
        sb.append("image");
        sb.append('=');
        sb.append(((this.image == null)?"<null>":this.image));
        sb.append(',');
        sb.append("identity");
        sb.append('=');
        sb.append(((this.identity == null)?"<null>":this.identity));
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
        result = ((result* 31)+((this.type == null)? 0 :this.type.hashCode()));
        result = ((result* 31)+((this.image == null)? 0 :this.image.hashCode()));
        result = ((result* 31)+((this.identity == null)? 0 :this.identity.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Critical) == false) {
            return false;
        }
        Critical rhs = ((Critical) other);
        return ((((this.type == rhs.type)||((this.type!= null)&&this.type.equals(rhs.type)))&&((this.image == rhs.image)||((this.image!= null)&&this.image.equals(rhs.image))))&&((this.identity == rhs.identity)||((this.identity!= null)&&this.identity.equals(rhs.identity))));
    }

    public enum Type {

        ATOMIC_CONTAINER_SIGNATURE("atomic container signature");
        private final String value;
        private final static Map<String, Critical.Type> CONSTANTS = new HashMap<String, Critical.Type>();

        static {
            for (Critical.Type c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        Type(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static Critical.Type fromValue(String value) {
            Critical.Type constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
