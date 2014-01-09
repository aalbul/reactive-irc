import scala.collection.JavaConverters._
import scala.Some

name := "irc-actor"

organization := "org.nemesis"

version := "0.6"

scalaVersion := "2.10.2"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies += "org.pircbotx" % "pircbotx" % "2.0.1"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.2.3"

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.0.13"

libraryDependencies += "org.clapper" %% "grizzled-slf4j" % "1.0.1"

libraryDependencies += "com.google.code.findbugs" % "jsr305" % "2.0.2"

publishMavenStyle := true

publishTo <<= version { (v: String) =>
  val nexus = "https://oss.sonatype.org/"
  if (v.trim.endsWith("SNAPSHOT")) {
    Some("snapshots" at nexus + "content/repositories/snapshots")
  } else {
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
  }
}

pomIncludeRepository := { _ => false }

pomExtra :=
  <scm>
    <url>git@github.com:aalbul/reactive-irc.git</url>
    <connection>scm:git:git@github.com:aalbul/reactive-irc.git</connection>
  </scm>
    <developers>
      <developer>
        <id>aalbul</id>
        <name>Alexander Albul</name>
        <url>https://github.com/aalbul</url>
      </developer>
    </developers>