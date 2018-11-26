name := "akka-serialization-comparision"
version := "0.1"
scalaVersion := "2.12.7"

// ~ Versions ~
val akkaVersion= "2.5.18"

// ~ Add Akka Actors
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test
)

// ~ Add Akka Persistence ~
libraryDependencies += "com.typesafe.akka" %% akkaVersion % "2.5.18"