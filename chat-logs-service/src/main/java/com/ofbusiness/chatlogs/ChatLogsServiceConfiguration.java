package com.ofbusiness.chatlogs;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * The configuration object for the service
 */
public class ChatLogsServiceConfiguration extends Configuration {
    @NotNull
    @Valid
    @JsonProperty
    private String couchBaseCloudEndPoint;

    public String getCouchBaseCloudEndPoint() {
        return this.couchBaseCloudEndPoint;
    }

    @NotNull
    @Valid
    @JsonProperty
    private String couchBaseCloudUserName;

    public String getCouchBaseCloudUserName() {
        return this.couchBaseCloudUserName;
    }


    @NotNull
    @Valid
    @JsonProperty
    private String couchBaseCloudUserPassword;

    public String getCouchBaseCloudUserPassword() {
        return this.couchBaseCloudUserPassword;
    }

    @NotNull
    @Valid
    @JsonProperty
    private String bucketName;

    public String getucketName() {
        return this.bucketName;
    }

}
