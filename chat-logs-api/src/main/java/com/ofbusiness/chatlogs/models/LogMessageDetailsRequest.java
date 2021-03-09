package com.ofbusiness.chatlogs.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value.Immutable;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;


@Immutable
@JsonSerialize
@JsonDeserialize(as = ImmutableLogMessageDetailsRequest.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface LogMessageDetailsRequest {

    @Valid
    @NotEmpty
    String getMessage();

    @Valid
    @NotEmpty
    Boolean getIsSender();

    @Valid
    Long getTimestamp();
}
