# Akka Serialization Comparision

[![shields.io](http://img.shields.io/badge/license-Apache2-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.txt)
[![Travis](https://img.shields.io/travis/rust-lang/rust.svg)](#)

Author: [Maximilian Bundscherer](https://bundscherer-online.de)

## Overview

This project **compares different serializers** can be used in akka projects (for example in **Akka Persistence** or **Akka Cluster**).

### Which serializer does this work compares?

- Java Serialization (should not be used in production)
- JSON Serialization (with Circe)
- Protobuf Serialization (with ScalaPB)

### Requirements

- [Java JDK](https://www.oracle.com/technetwork/java/javase/) (JDK 8)
- [Scala](https://www.scala-lang.org/) (v. 2.12.7 or higher - normally shipped with sbt)
- [Sbt](https://www.scala-sbt.org/) (v. 1.2.6 or higher)

### Included dependencies

- [Akka Actors](https://doc.akka.io/docs/akka/2.5/actors.html) (v. 2.5.18)
- [Akka Persistence](https://doc.akka.io/docs/akka/2.5/persistence.html) (v. 2.5.18)
- [Circe](https://circe.github.io/circe/) (v. 0.10.0)
- [LevelDB Java Native Interface](https://github.com/fusesource/leveldbjni) (v. 1.8)
- [Port of LevelDB to Java](https://github.com/dain/leveldb) (v. 0.7)
- [ScalaMeter](https://scalameter.github.io/) (v. 0.8.2)
- [ScalaPB](https://github.com/scalapb/ScalaPB) (v. 0.8.1)

## Let´s get started

You can run this project in two modes:

- ``ExperimentMode``: Test different serializers by timekeeping with complete Akka Actors System etc.
- ``BenchmarkMode``: Only test different serializers by ScalaMeter

First of all you should read and understand the test params. They are located in ``./src/main/resources/params.conf``

### Start ``ExperimentMode``

1. Checkout project
2. Go to ``./`` (project root folder)
3. Run command ``sbt clean run`` (clean database)
4. Or run command ``sbt run`` (non clean database)

**Important**: There are thrown RuntimeExceptions (Simulate crash): This behavior is desirable.

### Start ``BenchmarkMode``

1. Checkout project
2. Go to ``./`` (project root folder)
3. Run command ``sbt clean test``

## Advanced

To run both modes after each other you can run command ``sbt mixedMode``.

To run both modes after each other and pipe outputs to files you can run command ``./autoRunner.sh`` (check script before you do that).

### Increase default sbt memory-params

To get better results increase your default sbt memory-params with: ``export SBT_OPTS="-Xms1G -Xmx8G"``

- ``Xms`` specifies the initial memory allocation pool.
- ``Xmx`` specifies the maximum memory allocation pool.