package com.ofbusiness.chatlogs.util;

import com.couchbase.client.core.env.SecurityConfig;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.ClusterOptions;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.env.ClusterEnvironment;
import com.ofbusiness.chatlogs.models.LogMessageDetailsRequest;

import java.nio.file.Paths;
import java.time.Duration;
import java.util.Objects;

public class CouchBaseCloud {
    private static Cluster shared1Cluster = null;
    private static Collection messageDetailsCollection = null;

    private CouchBaseCloud() {}

    private static void initializeCouchBaseCloud() {
        String endpoint = "37b3ab90-f7c1-4640-ab0b-0b24c2505b98.dp.cloud.couchbase.com";
        String bucketName = "couchbasecloudbucket";
        String username = "chat-logs-api";
        String password = "Qweasdzxc@123";

        // Initialize a Couchbase cluster environment.
        ClusterEnvironment env =
                ClusterEnvironment
                        .builder()
                        .securityConfig(SecurityConfig.enableTls(true)
                                                      .trustCertificate(Paths.get(
                                                              "configs/chat-logs-shared1-root-certificate.pem")))
                        .build();

        // Initialize the Connection, Cluster.
        Cluster cluster = Cluster.connect(endpoint,
                ClusterOptions.clusterOptions(username, password).environment(env));
        // Initialize Bucket Object.
        Bucket bucket = cluster.bucket(bucketName);
        bucket.waitUntilReady(Duration.parse("PT10S"));

        Collection messageDetailsCollection = bucket.collection(LogMessageDetailsRequest.class.getName());

        CouchBaseCloud.shared1Cluster = cluster;
        CouchBaseCloud.messageDetailsCollection = messageDetailsCollection;
    }

    public static synchronized void verifyCouchBaseCloudInstance() {
        if (Objects.isNull(shared1Cluster)) {
            initializeCouchBaseCloud();
        }
    }


    public static Cluster getCouchBaseCluster() {
        verifyCouchBaseCloudInstance();
        return CouchBaseCloud.shared1Cluster;
    }

    public static Collection getCouchBaseChatLogsDefaultCollection() {
        verifyCouchBaseCloudInstance();
        return CouchBaseCloud.messageDetailsCollection;
    }

}
