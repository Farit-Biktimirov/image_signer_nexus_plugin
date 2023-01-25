
package org.sonatype.nexus.plugins.image.signer.oci;

import java.net.URI;
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


/**
 * OpenContainer Content Descriptor Specification
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "mediaType",
    "size",
    "digest",
    "urls",
    "annotations"
})
public class ContentDescriptor {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("mediaType")
    private String mediaType;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("size")
    private Integer size;
    /**
     * the cryptographic checksum digest of the object, in the pattern '<algorithm>:<encoded>'
     * (Required)
     * 
     */
    @JsonProperty("digest")
    @JsonPropertyDescription("the cryptographic checksum digest of the object, in the pattern '<algorithm>:<encoded>'")
    private String digest;
    /**
     * a list of urls from which this object may be downloaded
     * 
     */
    @JsonProperty("urls")
    @JsonPropertyDescription("a list of urls from which this object may be downloaded")
    private List<URI> urls;
    @JsonProperty("annotations")
    private Map<String, String> annotations;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("mediaType")
    public String getMediaType() {
        return mediaType;
    }

    /**
     * 
     * (Required)
     * 
     */
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
     * 
     * (Required)
     * 
     */
    @JsonProperty("size")
    public Integer getSize() {
        return size;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("size")
    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     * the cryptographic checksum digest of the object, in the pattern '<algorithm>:<encoded>'
     * (Required)
     * 
     */
    @JsonProperty("digest")
    public String getDigest() {
        return digest;
    }

    /**
     * the cryptographic checksum digest of the object, in the pattern '<algorithm>:<encoded>'
     * (Required)
     * 
     */
    @JsonProperty("digest")
    public void setDigest(String digest) {
        Pattern p = Pattern.compile("^[a-z0-9]+(?:[+._-][a-z0-9]+)*:[a-zA-Z0-9=_-]+$");
        if (p.matcher(digest).matches()) {
            this.digest = digest;
        } else {
            throw new IllegalArgumentException("argument does not match");
        }
    }

    /**
     * a list of urls from which this object may be downloaded
     * 
     */
    @JsonProperty("urls")
    public List<URI> getUrls() {
        return urls;
    }

    /**
     * a list of urls from which this object may be downloaded
     * 
     */
    @JsonProperty("urls")
    public void setUrls(List<URI> urls) {
        this.urls = urls;
    }

    @JsonProperty("annotations")
    public Map<String, String> getAnnotations() {
        return annotations;
    }

    @JsonProperty("annotations")
    public void setAnnotations(Map<String, String> annotations) {
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
        StringBuilder sb = new StringBuilder();
        sb.append(ContentDescriptor.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("mediaType");
        sb.append('=');
        sb.append(((this.mediaType == null)?"<null>":this.mediaType));
        sb.append(',');
        sb.append("size");
        sb.append('=');
        sb.append(((this.size == null)?"<null>":this.size));
        sb.append(',');
        sb.append("digest");
        sb.append('=');
        sb.append(((this.digest == null)?"<null>":this.digest));
        sb.append(',');
        sb.append("urls");
        sb.append('=');
        sb.append(((this.urls == null)?"<null>":this.urls));
        sb.append(',');
        sb.append("annotations");
        sb.append('=');
        sb.append(((this.annotations == null)?"<null>":this.annotations));
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null)?"<null>":this.additionalProperties));
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
        result = ((result* 31)+((this.digest == null)? 0 :this.digest.hashCode()));
        result = ((result* 31)+((this.annotations == null)? 0 :this.annotations.hashCode()));
        result = ((result* 31)+((this.mediaType == null)? 0 :this.mediaType.hashCode()));
        result = ((result* 31)+((this.urls == null)? 0 :this.urls.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.size == null)? 0 :this.size.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ContentDescriptor) == false) {
            return false;
        }
        ContentDescriptor rhs = ((ContentDescriptor) other);
        return (((((((this.digest == rhs.digest)||((this.digest!= null)&&this.digest.equals(rhs.digest)))&&((this.annotations == rhs.annotations)||((this.annotations!= null)&&this.annotations.equals(rhs.annotations))))&&((this.mediaType == rhs.mediaType)||((this.mediaType!= null)&&this.mediaType.equals(rhs.mediaType))))&&((this.urls == rhs.urls)||((this.urls!= null)&&this.urls.equals(rhs.urls))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.size == rhs.size)||((this.size!= null)&&this.size.equals(rhs.size))));
    }

}
