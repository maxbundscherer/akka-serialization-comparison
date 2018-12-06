name          := "akka-serialization-comparision"
version       := "0.1"
scalaVersion  := "2.12.7"

// ~ Add Akka Actors and Akka Persistence
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-persistence" % "2.5.18"
)

// ~ Add LevelDB and Java Native Interface for LevelDB ~
libraryDependencies ++= Seq(
  "org.iq80.leveldb"            % "leveldb"          % "0.7",
  "org.fusesource.leveldbjni"   % "leveldbjni-all"   % "1.8"
)

// ~ Add Circe LevelDB ~
val circeVersion = "0.10.0"
libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)

// ~ Import-Bug (scalaPB) ~
libraryDependencies += "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion % "protobuf"

// ~ Trigger ScalaPB (see ./project/protoc.sbt) in compile step ~
PB.targets in Compile := Seq(
  scalapb.gen() -> (sourceManaged in Compile).value
)

// ~ Add ScalaMeter and trigger in test ~
resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
libraryDependencies += "com.storm-enroute" %% "scalameter" % "0.8.2"
testFrameworks += new TestFramework("org.scalameter.ScalaMeterFramework")
parallelExecution in Test := false
logBuffered := false

// ~ Define alias for sbt ~
addCommandAlias("mixedMode", "; clean; run; clean; test")