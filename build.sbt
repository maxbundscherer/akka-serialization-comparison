name          := "akka-serialization-comparision"
version       := "0.1"
scalaVersion  := "2.12.7"

// ~ Add Akka Actors and Akka Persistence
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-persistence" % "2.5.18"
)

// ~ Add LevelDB and Java Native Interface ~
libraryDependencies ++= Seq(
  "org.iq80.leveldb"            % "leveldb"          % "0.7",
  "org.fusesource.leveldbjni"   % "leveldbjni-all"   % "1.8"
)

// ~ Import-Bug (scalaPB) ~
libraryDependencies += "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion % "protobuf"

// ~ Trigger ScalaPB (see ./project/protoc.sbt) in compile step ~
PB.targets in Compile := Seq(
  scalapb.gen() -> (sourceManaged in Compile).value
)