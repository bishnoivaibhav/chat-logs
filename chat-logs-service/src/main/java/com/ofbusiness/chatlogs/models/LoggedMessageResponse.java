package com.ofbusiness.chatlogs.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.sql.Timestamp;
import java.util.UUID;


@Value.Immutable
@JsonSerialize
@JsonDeserialize(as = ImmutableLoggedMessageResponse.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface LoggedMessageResponse {

    UUID getMessageId();

    String getMessage();

    Boolean getIsSender();

    Timestamp getTimestamp();
}
