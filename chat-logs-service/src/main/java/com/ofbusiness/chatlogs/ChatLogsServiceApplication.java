package com.ofbusiness.chatlogs;

import com.ofbusiness.chatlogs.dao.ChatLogsDao;
import com.ofbusiness.chatlogs.health.ChatLogsHealthCheck;
import com.ofbusiness.chatlogs.resources.ChatLogsResource;
import com.ofbusiness.chatlogs.service.ChatLogsService;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;


/**
 * A class to setup and initialize the service.
 */
public class ChatLogsServiceApplication
        extends Application<ChatLogsServiceConfiguration> {

    public static final String APPLICATION_NAME = "chat-logs-service";

    @Override
    public String getName() {
        return APPLICATION_NAME;
    }

    @Override
    public void initialize(Bootstrap<ChatLogsServiceConfiguration> bootstrap) {
        super.initialize(bootstrap);
    }

    @Override
    public void run(ChatLogsServiceConfiguration config, Environment environment) {

        // setup

        // setup resources
        ChatLogsDao chatLogsDao = new ChatLogsDao();
        ChatLogsService chatLogsService = new ChatLogsService(chatLogsDao);
        environment.jersey().register(new ChatLogsResource(chatLogsService));


        // setup health checks
        environment.healthChecks().register(this.getName(), new ChatLogsHealthCheck());
    }

    /**
     * Launch ChatLogsServiceApplication
     *
     * @param args args passed to this service
     *
     * @throws Exception thrown if there are errors launching service
     */
    public static void main(String[] args) throws Exception {
        new ChatLogsServiceApplication().run(args);
    }


}
