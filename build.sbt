// ---------------------------------------------------------------------------
// Basic settings

name := "markwrap"

organization := "org.clapper"

version := "1.0.0"

licenses := Seq("BSD" -> url("http://software.clapper.org/markwrap/license.html"))

homepage := Some(url("http://software.clapper.org/markwrap/"))

description := (
  "A unified API for converting various lightweight markup languages to HTML"
)

scalaVersion := "2.10.0-RC1"

// ---------------------------------------------------------------------------
// Additional compiler options and plugins

scalacOptions ++= Seq("-deprecation", "-unchecked")

crossScalaVersions := Seq("2.10.0-RC1")

seq(lsSettings :_*)

(LsKeys.tags in LsKeys.lsync) := Seq(
  "markdown", "textile", "markup", "html", "library", "crap"
)

(description in LsKeys.lsync) <<= description(d => d)

// ---------------------------------------------------------------------------
// ScalaTest dependendency

libraryDependencies <<= (scalaVersion, libraryDependencies) { (sv, deps) =>
    // Select ScalaTest version based on Scala version
    val scalatestVersionMap = Map(
      "2.10.0-RC1" -> ("scalatest_2.10.0-RC1", "2.0.M4-2.10.0-RC1-B1")
    )
    val (scalatestArtifact, scalatestVersion) = scalatestVersionMap.getOrElse(
        sv, error("Unsupported Scala version for ScalaTest: " + scalaVersion)
    )
    deps :+ "org.scalatest" % scalatestArtifact % scalatestVersion % "test"
}

libraryDependencies <<= (scalaVersion, libraryDependencies) { (sv, deps) =>
  // ScalaTest still uses the (deprecated) scala.actors API.
  deps :+ "org.scala-lang" % "scala-actors" % sv % "test"
}

// ---------------------------------------------------------------------------
// Other dependendencies

libraryDependencies ++= Seq(
    "org.fusesource.wikitext" % "textile-core" % "1.4",
    "org.pegdown" % "pegdown" % "1.2.1"
)

// ---------------------------------------------------------------------------
// Publishing criteria

publishTo <<= version { v: String =>
  val nexus = "https://oss.sonatype.org/"
  if (v.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

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
      <url>http://www.clapper.org/bmc</url>
    </developer>
  </developers>
)
