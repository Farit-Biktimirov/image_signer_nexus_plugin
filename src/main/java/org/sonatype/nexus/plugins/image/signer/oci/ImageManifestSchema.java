
package org.sonatype.nexus.plugins.image.signer.oci;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;


/**
 * OpenContainer Image Manifest Specification
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "schemaVersion",
    "mediaType",
    "config",
    "layers",
    "annotations"
})
public class ImageManifestSchema {

    /**
     * This field specifies the image manifest schema version as an integer
     * (Required)
     * 
     */
    @JsonProperty("schemaVersion")
    @JsonPropertyDescription("This field specifies the image manifest schema version as an integer")
    private Integer schemaVersion;
    @JsonProperty("mediaType")
    private String mediaType;
    /**
     * OpenContainer Content Descriptor Specification
     * (Required)
     * 
     */
    @JsonProperty("config")
    @JsonPropertyDescription("OpenContainer Content Descriptor Specification")
    private ContentDescriptor config;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("layers")
    private List<ContentDescriptor> layers = new ArrayList<ContentDescriptor>();
    @JsonProperty("annotations")
    private Map<String, String> annotations;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * This field specifies the image manifest schema version as an integer
     * (Required)
     * 
     */
    @JsonProperty("schemaVersion")
    public Integer getSchemaVersion() {
        return schemaVersion;
    }

    /**
     * This field specifies the image manifest schema version as an integer
     * (Required)
     * 
     */
    @JsonProperty("schemaVersion")
    public void setSchemaVersion(Integer schemaVersion) {
        this.schemaVersion = schemaVersion;
    }

    @JsonProperty("mediaType")
    public String getMediaType() {
        return mediaType;
    }

    @JsonProperty("mediaType")
    public void setMediaType(String mediaType) {
        Pattern p = Pattern.compile("^[A-Za-z0-9][A-Za-z0-9!#$&-^_.+]{0,126}/[A-Za-z0-9][A-Za-z0-9!#$&-^_.+]{0,126}$");
        if (p.matcher(mediaType).matches()) {
            this.mediaType = mediaType;
        } else {
            throw new IllegalArgumentException("argument does not match");
        }
    }

    /**
     * OpenContainer Content Descriptor Specification
     * (Required)
     * 
     */
    @JsonProperty("config")
    public ContentDescriptor getConfig() {
        return config;
    }

    /**
     * OpenContainer Content Descriptor Specification
     * (Required)
     * 
     */
    @JsonProperty("config")
    public void setConfig(ContentDescriptor config) {
        this.config = config;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("layers")
    public List<ContentDescriptor> getLayers() {
        return layers;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("layers")
    public void setLayers(List<ContentDescriptor> layers) {
        this.layers = layers;
    }

    @JsonProperty("annotations")
    public Map<String,String> getAnnotations() {
        return annotations;
    }

    @JsonProperty("annotations")
    public void setAnnotations(Map<String,String> annotations) {
        this.annotations = annotations;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        JsonMapper mapper = new JsonMapper();
        String result = "";
        try {
            result = mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            //TODO: logger throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.layers == null)? 0 :this.layers.hashCode()));
        result = ((result* 31)+((this.annotations == null)? 0 :this.annotations.hashCode()));
        result = ((result* 31)+((this.mediaType == null)? 0 :this.mediaType.hashCode()));
        result = ((result* 31)+((this.schemaVersion == null)? 0 :this.schemaVersion.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.config == null)? 0 :this.config.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ImageManifestSchema) == false) {
            return false;
        }
        ImageManifestSchema rhs = ((ImageManifestSchema) other);
        return (((((((this.layers == rhs.layers)||((this.layers!= null)&&this.layers.equals(rhs.layers)))&&((this.annotations == rhs.annotations)||((this.annotations!= null)&&this.annotations.equals(rhs.annotations))))&&((this.mediaType == rhs.mediaType)||((this.mediaType!= null)&&this.mediaType.equals(rhs.mediaType))))&&((this.schemaVersion == rhs.schemaVersion)||((this.schemaVersion!= null)&&this.schemaVersion.equals(rhs.schemaVersion))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.config == rhs.config)||((this.config!= null)&&this.config.equals(rhs.config))));
    }

}
