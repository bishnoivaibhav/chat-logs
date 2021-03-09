package com.ofbusiness.chatlogs.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;


@Value.Immutable
@JsonSerialize
@JsonDeserialize(as = ImmutableLoggedMessageResponse.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface LoggedMessageResponse {

    @NotNull
    @NotEmpty
    UUID getMessageId();

    @Valid
    @NotEmpty
    String getMessage();

    @Valid
    @NotEmpty
    Boolean getIsSender();

    @Valid
    @NotEmpty
    Long getTimestamp();
}
