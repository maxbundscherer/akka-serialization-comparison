# Akka Serialization Comparision

[![shields.io](http://img.shields.io/badge/license-Apache2-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.txt)
[![Travis](https://img.shields.io/travis/rust-lang/rust.svg)](#)

Author: [Maximilian Bundscherer](https://bundscherer-online.de)

## Overview

This project **compares different serializers** can be used in akka projects (for example in **Akka Persistence** or **Akka Cluster**).

### Requirements

- [sbt](https://www.scala-sbt.org/) (v. 1.2.6 or higher)

### Included tools

- [Scala](https://www.scala-lang.org/) (v. 2.12.7)
- [ScalaPB](https://github.com/scalapb/ScalaPB) (v. 0.8.1)
- [Akka Actors](https://doc.akka.io/docs/akka/2.5/actors.html) (v. 2.5.18)
- [Akka Persistence](https://doc.akka.io/docs/akka/2.5/persistence.html) (v. 2.5.18)
- [Port of LevelDB to Java](https://github.com/dain/leveldb) (v. 0.7)
- [LevelDB Java Native Interface](https://github.com/fusesource/leveldbjni) (v. 1.8)

### Let´s get started

1. Checkout project
2. Go to ``./`` (project root folder)
3. Run command ``sbt clean run`` (clean database)
4. Or run command ``sbt run`` (non clean database)