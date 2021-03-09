package com.ofbusiness.chatlogs.health;

import com.codahale.metrics.health.HealthCheck;

/**
 * Simple service check, triggered by GET request to http://{host}:{adminPort}/health check.
 */
public class  ChatLogsHealthCheck extends HealthCheck {

    /**
     * Create an instance of this health check
     */
    public  ChatLogsHealthCheck() {
        super();
    }

    @Override
    protected Result check() {
        return Result.healthy();
    }
}
