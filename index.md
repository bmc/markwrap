---
title: MarkWrap -- Unified Scala wrapper API for various lightweight markup APIs
layout: withTOC
---

# Introduction

The MarkWrap library (pronounced "mark wrap" or "more crap", depending on
your preference) is a unified Scala API for various underlying lightweight
markup APIs. Currently, it supports:

* [Markdown][], via the [Knockoff][] parser.
* [Textile][], via the Eclipse [Mylyn][] *wikitext* parser API.
* An internal handler that wraps plain text in `<pre>` and `</pre>` tags.
* An internal handler that simply passes HTML straight through.

# Installation

The easiest way to install the MarkWrap library is to download a
pre-compiled jar from the [Scala Tools Maven repository][]. However, you
can also get certain build tools to download it for you automatically.

## Installing for Maven

If you're using [Maven][], you can simply tell Maven to get MarkWrap
from the [Scala Tools Maven repository][]. The relevant pieces of
information are:

* Group ID: `org.clapper`
* Artifact ID: `markwrap_2.8.1`
* Version: `0.2.1`
* Type: `jar`
* Repository: `http://www.scala-tools.org/repo-releases/`

For example:

    <repositories>
      <repository>
        <id>scala-tools.org</id>
          <name>Scala-tools Maven2 Repository</name>
          <url>http://scala-tools.org/repo-releases</url>
      </repository>
    </repositories>

    <dependency>
      <groupId>org.clapper</groupId>
      <artifactId>markwrap_2.8.1</artifactId>
      <version>0.2.1</version>
    </dependency>

Version 0.2.1 is available for Scala 2.8.0 and 2.8.1.

For more information on using Maven and Scala, see Josh Suereth's
[Scala Maven Guide][].

## Using with SBT

If you're using [SBT][] (the Simple Build Tool) to compile your code, you
can place the following lines in your project file (i.e., the Scala file in
your `project/build/` directory):

    val t_repo = "t_repo" at
        "http://tristanhunt.com:8081/content/groups/public/"
    val newReleaseToolsRepository = ScalaToolsSnapshots
    val orgClapper = "org.clapper Maven repo" at "http://maven.clapper.org/

    val wikitext = "org.eclipse.mylyn.wikitext" % "wikitext.textile" %
                   "0.9.4.I20090220-1600-e3x"
    val knockoff = "com.tristanhunt" %% "knockoff" % "0.7.3-14"
    val markwrap = "org.clapper" %% "markwrap" % "0.2.1"

**NOTES**

1. The first doubled percent is *not* a typo. It tells SBT to treat the
   artifact as a cross-built library and automatically inserts the Scala
   version you're using into the artifact ID. It will *only* work if you
   are building with Scala 2.8.0 or 2.8.1. See the [SBT cross-building][]
   page for details.
  
2. You *must* specify the `tristanhunt.com` and `ScalaToolsSnapshots`.
   Similarly, you must provide the dependencies on Knockoff and Mylyn. Even
   though those additional repositories and artifacts are in the published
   MarkWrap Maven `pom.xml`, SBT will not read them. Under the covers, SBT
   uses [Apache Ivy][] for dependency management, and Ivy doesn't extract
   repositories from Maven POM files. See
   [Library Management Maven/Ivy section][] in the [SBT Manual][] for
   details. Also see this [email thread][SBT-repo-email-thread].

# Building from Source

## Source Code Repository

The source code for the MarkWrap library is maintained on [GitHub][]. To
clone the repository, run this command:

    git clone git://github.com/bmc/markwrap.git

## Build Requirements

Building the MarkWrap library requires [SBT][] and Scala 2.8.0. Install
SBT, as described at the SBT web site.

## Building MarkWrap

Assuming you have an `sbt` shell script (or .BAT file, for Windows), run:

    sbt update

That command will pull down the external jars on which the Grizzled Scala
Library depends. After that step, build the library with:

    sbt compile test package

The resulting jar file will be in the top-level `target` directory.

# Using MarkWrap

MarkWrap provides a simple, unified API for various lightweight markup
languages. There are two steps to use the API:

* Get the parser for the desired lightweight markup language.
* Call the parser's `parseToHTML()` method with the content

## Getting the Desired Parser

To obtain a parser, use the `org.clapper.markwrap.MarkWrap` object's
`parserFor()` method, passing it either:

* a MIME type
* a markup language constant
* a `java.io.File` object, whose extension is used to determine the MIME type.

The following values are supported:

* `MarkupType.Markdown`: The [Markdown][] markup language, as parsed by the
  [Knockoff][] API.

  - **Corresponding MIME type:** text/markdown
  - **Corresponding file extensions**: `.md`, `.markdown`

* `MarkupType.Textile`: The [Textile][] markup language,
  as parsed by the Eclipse [Mylyn][] *wikitext* parser API.

  - **Corresponding MIME type:** text/textile
  - **Corresponding file extension**: `.textile`

* `MarkupType.HTML` and `MarkupType.XHTML`: Pass-through HTML and XHTML.

  - **Corresponding MIME types:** text/html, text/xhtml
  - **Corresponding file extensions**: `.htm, `.html`, `.xhtm`, `.xhtml`

* `MarkupType.PlainText`: Plain text, which is simply wrapped in `<pre>`
  and `</pre>` tags.

  - **Corresponding MIME types:** text/plain
  - **Corresponding file extensions**: `.htm, `.html`, `.xhtm`, `.xhtml`

### Examples

#### Getting a Markdown parser

    import org.clapper.markwrap._
    
    // Using the constant
    val parser1 = MarkWrap.parserFor(MarkupType.Markdown)
    
    // Using the MIME type
    val parser2 = MarkWrap.parserFor("text/markdown")

    // Using a File object
    val parser3 = MarkWrap.parserFor(new java.io.File("foo.md"))

#### Getting a Textile parser

    import org.clapper.markwrap._
    
    // Using the constant
    val parser1 = MarkWrap.parserFor(MarkupType.Textile)
    
    // Using the MIME type
    val parser2 = MarkWrap.parserFor("text/textile")

    // Using a File object
    val parser3 = MarkWrap.parserFor(new java.io.File("foo.textile"))

#### Getting a pass-through HTML "parser"

    import org.clapper.markwrap._
    
    // Using the constant
    val parser1 = MarkWrap.parserFor(MarkupType.XHTML)
    
    // Using the MIME type
    val parser2 = MarkWrap.parserFor("text/xhtml")

    // Using a File object
    val parser3 = MarkWrap.parserFor(new java.io.File("foo.html"))

#### Getting a plain text "parser"

    import org.clapper.markwrap._
    
    // Using the constant
    val parser1 = MarkWrap.parserFor(MarkupType.PlainText)
    
    // Using the MIME type
    val parser2 = MarkWrap.parserFor("text/plain")

    // Using a File object
    val parser3 = MarkWrap.parserFor(new java.io.File("foo.txt"))

## Parsing the Markup

Once you have the correct parser, you can parse the markup using one of the
parsing methods.

### Parsing to an HTML fragment

The `parseToHTML()` methods produce HTML fragments, not complete HTML
documents. (There's another `parseToHTMLDocument()` method that produces a
complete document; see below for details.)

#### Parsing from a Scala `Source`

Example 1:

    import org.clapper.markwrap._
    import scala.io.Source
    import java.io.File

    val file = new File("/path/to/markup.md")
    val parser = MarkWrap.parserFor(file)
    val html = parser.toHTML(Source.fromFile(file))

Example 2:

    import org.clapper.markwrap._
    import scala.io.Source

    val markup = """This is some *Markdown* text"""
    val parser = MarkWrap.parserFor(MarkupType.Markdown)
    val html = parser.toHTML(Source.fromString(markup))

#### Parsing from a file or a string

If you're parsing from a file or a string, there are some shortcut methods:

From a `java.io.File`:

    import org.clapper.markwrap._
    import java.io.File
    
    val file = new File("/path/to/markup.md")
    val parser = MarkWrap.parserFor(file)
    val html = parser.toHTML(file)
    
From a string:

    import org.clapper.markwrap._

    val parser = MarkWrap.parserFor(MarkupType.Markdown)
    val html = parser.toHTML("""This is some *Markdown* text""")
    
### Producing a complete HTML document

The `parseToHTMLDocument()` method produces a complete HTML document,
with `<html>`, `<head>`, and `<body>` sections, as well as an optional
cascading style sheet. The method's signature looks like this:

    def parseToHTMLDocument(markupSource: Source, 
                            title: String,
                            cssSource: Option[Source] = None,
                            encoding: String  = "UTF-8"): String =

* `markupSource` is the markup to be rendered.
* `title` is the title to be put in the document's `<title>` section.
* `cssSource` is an optional `Source` specifying a file containing
  a cascading style sheet. The contents of the file are pulled inline.
* `encoding` is the encoding of the document.

This method is just a simple convenience method; there are features it does
not support (for instance, `<link>` tags, for external style sheets or
Javascript files). You're certainly free to use your own replacement for
it.

# API Documentation

The Scaladoc-generated the [API documentation][] is available locally.
In addition, you can generate your own version with:

    sbt doc

# Change log

The change log for all releases is [here][changelog].

# Author

[Brian M. Clapper][], [bmc@clapper.org][]

# Copyright and License

MarkWrap is copyright &copy; 2009-2010 Brian M. Clapper and is released
under a [BSD License][].

# Patches

I gladly accept patches from their original authors. Feel free to email
patches to me or to fork the [GitHub repository][] and send me a pull
request. Along with any patch you send:

* Please state that the patch is your original work.
* Please indicate that you license the work to the MarkWrap project
  under a [BSD License][].

[BSD License]: license.html
[GitHub repository]: http://github.com/bmc/markwrap
[GitHub]: http://github.com/bmc/
[downloads area]: http://github.com/bmc/markwrap/downloads
[API Documentation]: api/
[Maven]: http://maven.apache.org/
[Scala Tools Maven repository]: http://www.scala-tools.org/repo-releases/
[Scala Maven Guide]: http://www.scala-lang.org/node/345
[Knockoff]: http://tristanhunt.com/projects/knockoff/
[Markdown]: http://daringfireball.net/projects/markdown/
[Textile]: http://textile.thresholdstate.com/
[Mylyn]: http://www.eclipse.org/mylyn/
[MarkWrap web site]: http://software.clapper.org/markwrap/
[bmc@clapper.org]: mailto:bmc@clapper.org
[Brian M. Clapper]: mailto:bmc@clapper.org
[changelog]: CHANGELOG.html
[SBT]: http://code.google.com/p/simple-build-tool
[SBT cross-building]: http://code.google.com/p/simple-build-tool/wiki/CrossBuild
[Apache Ivy]: http://ant.apache.org/ivy/
[Library Management Maven/Ivy section]: http://code.google.com/p/simple-build-tool/wiki/LibraryManagement#Maven/Ivy
[SBT Manual]: http://code.google.com/p/simple-build-tool/wiki/DocumentationHome
[SBT-repo-email-thread]: http://groups.google.com/group/simple-build-tool/browse_thread/thread/470bba921252a167
