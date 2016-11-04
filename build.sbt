// ---------------------------------------------------------------------------
// Basic settings

name := "markwrap"

organization := "org.clapper"

version := "1.1.2"

licenses := Seq("BSD" -> url("http://software.clapper.org/markwrap/license.html"))

homepage := Some(url("http://software.clapper.org/markwrap/"))

description := "A unified API for converting various lightweight markup languages to HTML"

scalaVersion := "2.11.8"
crossScalaVersions := Seq("2.10.6", "2.11.8", "2.12.0")

ivyScala := ivyScala.value.map { _.copy(overrideScalaVersion = true) }

// ---------------------------------------------------------------------------
// Additional compiler options and plugins

autoCompilerPlugins := true

scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature")

bintrayPackageLabels := Seq("library", "markdown", "textile", "scala")

// ---------------------------------------------------------------------------
// ScalaTest dependendency

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.0" % "test"
)

// ---------------------------------------------------------------------------
// Other dependendencies

libraryDependencies ++= Seq(
    "org.fusesource.wikitext" % "textile-core" % "1.4",
    "org.pegdown" % "pegdown" % "1.6.0"
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
