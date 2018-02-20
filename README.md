# micro-java-service
A simple project which uses two applications written in Java to process and store data entries from a TCP stream.

The project consists of two applications communicating over RabbitMQ:

  - stream-processor -> Used for reading the TCP stram and transforming the entries into JSON before publishing them on RabbitMQ.
  - stream-archver -> Consumes the JSON entries from RabbitMQ and stores them to MongoDB.

## Setup
### Clone project
```
$ cd ~/projects

$ git clone https://github.com/opreaalex/micro-java-service
```

## How to run
### Build the project

For building the project's components you will need to have <b>maven</b> installed.

In the project directory you will find 3 sub-directories:

  - stream-archiver
  - stream-common
  - stream-processor

We need to first build the <i>stream-common</i> as this will be used by both applications.

```
$ cd ~/projects/micro-java-service/stream-common
$ mvn install
```

Now we build and start the individual applications.
<b>NOTE</b> Make sure the TCP provider is running (as well as RabbitMQ and MongoDB).

Build and start the processor:
```
$ cd ~/projects/micro-java-service/stream-processor
$ mvn package exec:java -Dexec.mainClass="com.opreaalex.processor.Application"
```

Build and start the archiver:
```
$ cd ~/projects/micro-java-service/stream-archiver
$ mvn package exec:java -Dexec.mainClass="com.opreaalex.archiver.Application"
```

## What to expect
Messages being transformed and persisted are displayed to the standard output for each of the two applications.

