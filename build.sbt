import sbtprotobuf.{ProtobufPlugin => PB}

organization := "com.sap.pdms"

name := """akka-streams-experiments"""

version := "0.1-SNAPSHOT"

lazy val root = project in file(".")

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream" % "2.4.2",
  "com.typesafe.akka" %% "akka-http-experimental" % "2.4.2",
  "com.google.code.gson" % "gson" % "2.6.2",
  "com.typesafe.akka" %% "akka-stream-testkit" % "2.4.2" % Test,
  "org.scalatest" %% "scalatest" % "2.2.6" % Test,
  "org.scalamock" %% "scalamock-scalatest-support" % "3.2.2" % Test
)

PB.protobufSettings