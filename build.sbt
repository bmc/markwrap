// ---------------------------------------------------------------------------
// Basic settings

name := "markwrap"

organization := "org.clapper"

version := "1.0.2"

licenses := Seq("BSD" -> url("http://software.clapper.org/markwrap/license.html"))

homepage := Some(url("http://software.clapper.org/markwrap/"))

description := (
  "A unified API for converting various lightweight markup languages to HTML"
)

crossScalaVersions := Seq("2.10.4", "2.11.0")

scalaVersion := crossScalaVersions.value.head

// ---------------------------------------------------------------------------
// Additional compiler options and plugins

scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature")

lsSettings 

(LsKeys.tags in LsKeys.lsync) := Seq(
  "markdown", "textile", "markup", "html", "library", "crap"
)

(description in LsKeys.lsync) <<= description(d => d)

bintraySettings

bintray.Keys.packageLabels in bintray.Keys.bintray := (
  LsKeys.tags in LsKeys.lsync
).value

// ---------------------------------------------------------------------------
// ScalaTest dependendency

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.1.3" % "test"
)

// ---------------------------------------------------------------------------
// Other dependendencies

libraryDependencies ++= Seq(
    "org.fusesource.wikitext" % "textile-core" % "1.4",
    "org.pegdown" % "pegdown" % "1.4.2"
)

// ---------------------------------------------------------------------------
// Publishing criteria

// Don't set publishTo. The Bintray plugin does that automatically.

publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

pomExtra := (
  <scm>
    <url>git@github.com:bmc/markwrap.git/</url>
    <connection>scm:git:git@github.com:bmc/markwrap.git</connection>
  </scm>
  <developers>
    <developer>
      <id>bmc</id>
      <name>Brian Clapper</name>
      <url>https://github.com/bmc</url>
    </developer>
  </developers>
)
