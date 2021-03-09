package com.ofbusiness.chatlogs.exceptions;

import com.couchbase.client.core.msg.kv.ResponseStatusDetails;

public class CouchbaseException extends RuntimeException {

    private volatile ResponseStatusDetails responseStatusDetails;

    public CouchbaseException() {
        super();
    }

    public CouchbaseException(String message) {
        super(message);
    }

    public CouchbaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public CouchbaseException(Throwable cause) {
        super(cause);
    }

    public ResponseStatusDetails details() {
        return responseStatusDetails;
    }

    public void details(final ResponseStatusDetails responseStatusDetails) {
        this.responseStatusDetails = responseStatusDetails;
    }

    @Override
    public String getMessage() {
        String message = super.getMessage();

        if (responseStatusDetails != null) {
            message +=  " (Context: " + responseStatusDetails.context() + ", Reference: "
                                + responseStatusDetails.reference() + ")";
        }
        return message;
    }
}
