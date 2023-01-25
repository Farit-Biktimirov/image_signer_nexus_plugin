
package org.sonatype.nexus.plugins.image.signer.oci;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "User",
    "ExposedPorts",
    "Env",
    "Entrypoint",
    "Cmd",
    "Volumes",
    "WorkingDir",
    "Labels",
    "StopSignal",
    "ArgsEscaped"
})
public class Config {

    @JsonProperty("User")
    private String user;
    @JsonProperty("ExposedPorts")
    private Map<String,Object> exposedPorts;
    @JsonProperty("Env")
    private List<String> env = new ArrayList<String>();
    @JsonProperty("Entrypoint")
    private Object entrypoint;
    @JsonProperty("Cmd")
    private Object cmd;
    @JsonProperty("Volumes")
    private Object volumes;
    @JsonProperty("WorkingDir")
    private String workingDir;
    @JsonProperty("Labels")
    private Object labels;
    @JsonProperty("StopSignal")
    private String stopSignal;
    @JsonProperty("ArgsEscaped")
    private Boolean argsEscaped;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("User")
    public String getUser() {
        return user;
    }

    @JsonProperty("User")
    public void setUser(String user) {
        this.user = user;
    }

    @JsonProperty("ExposedPorts")
    public Map<String, Object> getExposedPorts() {
        return exposedPorts;
    }

    @JsonProperty("ExposedPorts")
    public void setExposedPorts(Map<String, Object> exposedPorts) {
        this.exposedPorts = exposedPorts;
    }

    @JsonProperty("Env")
    public List<String> getEnv() {
        return env;
    }

    @JsonProperty("Env")
    public void setEnv(List<String> env) {
        this.env = env;
    }

    @JsonProperty("Entrypoint")
    public Object getEntrypoint() {
        return entrypoint;
    }

    @JsonProperty("Entrypoint")
    public void setEntrypoint(Object entrypoint) {
        this.entrypoint = entrypoint;
    }

    @JsonProperty("Cmd")
    public Object getCmd() {
        return cmd;
    }

    @JsonProperty("Cmd")
    public void setCmd(Object cmd) {
        this.cmd = cmd;
    }

    @JsonProperty("Volumes")
    public Object getVolumes() {
        return volumes;
    }

    @JsonProperty("Volumes")
    public void setVolumes(Object volumes) {
        this.volumes = volumes;
    }

    @JsonProperty("WorkingDir")
    public String getWorkingDir() {
        return workingDir;
    }

    @JsonProperty("WorkingDir")
    public void setWorkingDir(String workingDir) {
        this.workingDir = workingDir;
    }

    @JsonProperty("Labels")
    public Object getLabels() {
        return labels;
    }

    @JsonProperty("Labels")
    public void setLabels(Object labels) {
        this.labels = labels;
    }

    @JsonProperty("StopSignal")
    public String getStopSignal() {
        return stopSignal;
    }

    @JsonProperty("StopSignal")
    public void setStopSignal(String stopSignal) {
        this.stopSignal = stopSignal;
    }

    @JsonProperty("ArgsEscaped")
    public Boolean getArgsEscaped() {
        return argsEscaped;
    }

    @JsonProperty("ArgsEscaped")
    public void setArgsEscaped(Boolean argsEscaped) {
        this.argsEscaped = argsEscaped;
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
        sb.append(Config.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("user");
        sb.append('=');
        sb.append(((this.user == null)?"<null>":this.user));
        sb.append(',');
        sb.append("exposedPorts");
        sb.append('=');
        sb.append(((this.exposedPorts == null)?"<null>":this.exposedPorts));
        sb.append(',');
        sb.append("env");
        sb.append('=');
        sb.append(((this.env == null)?"<null>":this.env));
        sb.append(',');
        sb.append("entrypoint");
        sb.append('=');
        sb.append(((this.entrypoint == null)?"<null>":this.entrypoint));
        sb.append(',');
        sb.append("cmd");
        sb.append('=');
        sb.append(((this.cmd == null)?"<null>":this.cmd));
        sb.append(',');
        sb.append("volumes");
        sb.append('=');
        sb.append(((this.volumes == null)?"<null>":this.volumes));
        sb.append(',');
        sb.append("workingDir");
        sb.append('=');
        sb.append(((this.workingDir == null)?"<null>":this.workingDir));
        sb.append(',');
        sb.append("labels");
        sb.append('=');
        sb.append(((this.labels == null)?"<null>":this.labels));
        sb.append(',');
        sb.append("stopSignal");
        sb.append('=');
        sb.append(((this.stopSignal == null)?"<null>":this.stopSignal));
        sb.append(',');
        sb.append("argsEscaped");
        sb.append('=');
        sb.append(((this.argsEscaped == null)?"<null>":this.argsEscaped));
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
        result = ((result* 31)+((this.exposedPorts == null)? 0 :this.exposedPorts.hashCode()));
        result = ((result* 31)+((this.entrypoint == null)? 0 :this.entrypoint.hashCode()));
        result = ((result* 31)+((this.workingDir == null)? 0 :this.workingDir.hashCode()));
        result = ((result* 31)+((this.volumes == null)? 0 :this.volumes.hashCode()));
        result = ((result* 31)+((this.cmd == null)? 0 :this.cmd.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.env == null)? 0 :this.env.hashCode()));
        result = ((result* 31)+((this.stopSignal == null)? 0 :this.stopSignal.hashCode()));
        result = ((result* 31)+((this.user == null)? 0 :this.user.hashCode()));
        result = ((result* 31)+((this.argsEscaped == null)? 0 :this.argsEscaped.hashCode()));
        result = ((result* 31)+((this.labels == null)? 0 :this.labels.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Config) == false) {
            return false;
        }
        Config rhs = ((Config) other);
        return ((((((((((((this.exposedPorts == rhs.exposedPorts)||((this.exposedPorts!= null)&&this.exposedPorts.equals(rhs.exposedPorts)))&&((this.entrypoint == rhs.entrypoint)||((this.entrypoint!= null)&&this.entrypoint.equals(rhs.entrypoint))))&&((this.workingDir == rhs.workingDir)||((this.workingDir!= null)&&this.workingDir.equals(rhs.workingDir))))&&((this.volumes == rhs.volumes)||((this.volumes!= null)&&this.volumes.equals(rhs.volumes))))&&((this.cmd == rhs.cmd)||((this.cmd!= null)&&this.cmd.equals(rhs.cmd))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.env == rhs.env)||((this.env!= null)&&this.env.equals(rhs.env))))&&((this.stopSignal == rhs.stopSignal)||((this.stopSignal!= null)&&this.stopSignal.equals(rhs.stopSignal))))&&((this.user == rhs.user)||((this.user!= null)&&this.user.equals(rhs.user))))&&((this.argsEscaped == rhs.argsEscaped)||((this.argsEscaped!= null)&&this.argsEscaped.equals(rhs.argsEscaped))))&&((this.labels == rhs.labels)||((this.labels!= null)&&this.labels.equals(rhs.labels))));
    }

}
