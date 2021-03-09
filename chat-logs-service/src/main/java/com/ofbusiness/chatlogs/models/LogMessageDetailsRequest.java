package com.ofbusiness.chatlogs.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value.Immutable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Immutable
@JsonSerialize
@JsonDeserialize(as = ImmutableLogMessageDetailsRequest.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface LogMessageDetailsRequest {

    @Valid
    @NotNull(message = "Please enter message ")
    String getMessage();

    @Valid
    @NotNull(message = "Please provide sender details 'isSender' ")
    Boolean getIsSender();

    @Valid
    @NotNull(message = "Please enter timestamp ")
    Timestamp getTimestamp();
}
