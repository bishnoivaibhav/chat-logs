## CouchBase Instance has been terminated as we have exhausted AWS free tier EC2 Resources.
```
Your AWS account 788014656349 has exceeded 85% of the usage limit for one or more AWS Free Tier-eligible services for the month of March.
```

# Chat Logs Service

Created by vbishnoi_be15@thapar.edu

This is logging service which will be used for logging messages, no authentication mechanism (token/ ApiKey) is
 integrated yet.

Note: This assumes that your environment prepared to use maven.

# IDE Support

Any IDE supports maven so feel free to use Netbeans, IntelliJ, Eclipse

# How to build locally
```
mvn package -Prelease
```
# How to check code coverage // TODO: Add Unit Test.
```

```
# How to run locally
```
cd chat-logs-service
java -jar target/chat-logs-service-1.0.0-SNAPSHOT.jar server configs/dev.yaml
```

# Data Store Details
Our Data Store couchbase is Deployed on AWS-EC2, Used Couchbase as DaaS(Data base as a Service).   
Couchbase is used as data store with a secondary index. kindly refer Index file with relative examples for usage.
```
chat_logs_idx.n1ql
``` 


# How to access API docs
All the commenting is followed by Java Style commenting, so you can build and use Java Docs generated for reference.

# Access Post Collections Via
[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/3620afea60844fa3d720)
