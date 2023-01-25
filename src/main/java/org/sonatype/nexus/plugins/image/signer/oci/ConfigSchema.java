
package org.sonatype.nexus.plugins.image.signer.oci;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * OpenContainer Config Specification
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "created",
    "author",
    "architecture",
    "variant",
    "os",
    "os.version",
    "os.features",
    "config",
    "rootfs",
    "history"
})
public class ConfigSchema {

    @JsonProperty("created")
    private String created;
    @JsonProperty("author")
    private String author;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("architecture")
    private String architecture;
    @JsonProperty("variant")
    private String variant;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("os")
    private String os;
    @JsonProperty("os.version")
    private String osversion;
    @JsonProperty("os.features")
    private List<String> osfeatures;
    @JsonProperty("config")
    private Config config;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("rootfs")
    private Rootfs rootfs;
    @JsonProperty("history")
    private List<History> history;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("created")
    public String getCreated() {
        return created;
    }

    @JsonProperty("created")
    public void setCreated(String created) {
        Pattern p = Pattern.compile("\\d{4}-\\d{1,2}-\\d{1,2}T\\d{2}:\\d{2}:\\d{2}Z[\\+\\-]*\\d*");
        if (p.matcher(created).matches()) {
            this.created = created;
        } else {
            throw new IllegalArgumentException("unsupported regEx");
        }
    }

    @JsonProperty("author")
    public String getAuthor() {
        return author;
    }

    @JsonProperty("author")
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("architecture")
    public String getArchitecture() {
        return architecture;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("architecture")
    public void setArchitecture(String architecture) {
        this.architecture = architecture;
    }

    @JsonProperty("variant")
    public String getVariant() {
        return variant;
    }

    @JsonProperty("variant")
    public void setVariant(String variant) {
        this.variant = variant;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("os")
    public String getOs() {
        return os;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("os")
    public void setOs(String os) {
        this.os = os;
    }

    @JsonProperty("os.version")
    public String getOsversion() {
        return osversion;
    }

    @JsonProperty("os.version")
    public void setOsversion(String osversion) {
        this.osversion = osversion;
    }

    @JsonProperty("os.features")
    public List<String> getOsfeatures() {
        return osfeatures;
    }

    @JsonProperty("os.features")
    public void setOsfeatures(List<String> osfeatures) {
        this.osfeatures = osfeatures;
    }

    @JsonProperty("config")
    public Config getConfig() {
        return config;
    }

    @JsonProperty("config")
    public void setConfig(Config config) {
        this.config = config;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("rootfs")
    public Rootfs getRootfs() {
        return rootfs;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("rootfs")
    public void setRootfs(Rootfs rootfs) {
        this.rootfs = rootfs;
    }

    @JsonProperty("history")
    public List<History> getHistory() {
        return history;
    }

    @JsonProperty("history")
    public void setHistory(List<History> history) {
        this.history = history;
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
        sb.append(ConfigSchema.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("created");
        sb.append('=');
        sb.append(((this.created == null)?"<null>":this.created));
        sb.append(',');
        sb.append("author");
        sb.append('=');
        sb.append(((this.author == null)?"<null>":this.author));
        sb.append(',');
        sb.append("architecture");
        sb.append('=');
        sb.append(((this.architecture == null)?"<null>":this.architecture));
        sb.append(',');
        sb.append("variant");
        sb.append('=');
        sb.append(((this.variant == null)?"<null>":this.variant));
        sb.append(',');
        sb.append("os");
        sb.append('=');
        sb.append(((this.os == null)?"<null>":this.os));
        sb.append(',');
        sb.append("os.version");
        sb.append('=');
        sb.append(((this.osversion == null)?"<null>":this.osversion));
        sb.append(',');
        sb.append("os.features");
        sb.append('=');
        sb.append(((this.osfeatures == null)?"<null>":this.osfeatures));
        sb.append(',');
        sb.append("config");
        sb.append('=');
        sb.append(((this.config == null)?"<null>":this.config));
        sb.append(',');
        sb.append("rootfs");
        sb.append('=');
        sb.append(((this.rootfs == null)?"<null>":this.rootfs));
        sb.append(',');
        sb.append("history");
        sb.append('=');
        sb.append(((this.history == null)?"<null>":this.history));
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
        result = ((result* 31)+((this.osfeatures == null)? 0 :this.osfeatures.hashCode()));
        result = ((result* 31)+((this.os == null)? 0 :this.os.hashCode()));
        result = ((result* 31)+((this.created == null)? 0 :this.created.hashCode()));
        result = ((result* 31)+((this.author == null)? 0 :this.author.hashCode()));
        result = ((result* 31)+((this.rootfs == null)? 0 :this.rootfs.hashCode()));
        result = ((result* 31)+((this.variant == null)? 0 :this.variant.hashCode()));
        result = ((result* 31)+((this.history == null)? 0 :this.history.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.osversion == null)? 0 :this.osversion.hashCode()));
        result = ((result* 31)+((this.config == null)? 0 :this.config.hashCode()));
        result = ((result* 31)+((this.architecture == null)? 0 :this.architecture.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ConfigSchema) == false) {
            return false;
        }
        ConfigSchema rhs = ((ConfigSchema) other);
        return ((((((((((((this.osfeatures == rhs.osfeatures)||((this.osfeatures!= null)&&this.osfeatures.equals(rhs.osfeatures)))&&((this.os == rhs.os)||((this.os!= null)&&this.os.equals(rhs.os))))&&((this.created == rhs.created)||((this.created!= null)&&this.created.equals(rhs.created))))&&((this.author == rhs.author)||((this.author!= null)&&this.author.equals(rhs.author))))&&((this.rootfs == rhs.rootfs)||((this.rootfs!= null)&&this.rootfs.equals(rhs.rootfs))))&&((this.variant == rhs.variant)||((this.variant!= null)&&this.variant.equals(rhs.variant))))&&((this.history == rhs.history)||((this.history!= null)&&this.history.equals(rhs.history))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.osversion == rhs.osversion)||((this.osversion!= null)&&this.osversion.equals(rhs.osversion))))&&((this.config == rhs.config)||((this.config!= null)&&this.config.equals(rhs.config))))&&((this.architecture == rhs.architecture)||((this.architecture!= null)&&this.architecture.equals(rhs.architecture))));
    }

}
