name          := "akka-serialization-comparision"
version       := "0.1"
scalaVersion  := "2.12.7"

// ~ Versions ~
val akkaVersion: String = "2.5.18"

// ~ Add Akka Actors and Akka Persistence
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  // "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test
)

// ~ Import-Bug (scalaPB) ~
libraryDependencies += "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion % "protobuf"

// ~ Trigger ScalaPB (see ./project/protoc.sbt) in compile step ~
PB.targets in Compile := Seq(
  scalapb.gen() -> (sourceManaged in Compile).value
)