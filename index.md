---
title: MarkWrap -- Unified Scala wrapper API for various lightweight markup APIs
layout: withTOC
---

# Introduction

The MarkWrap library (pronounced "mark wrap" or "more crap", depending on
your preference) is a unified Scala API for various underlying lightweight
markup APIs. Currently, it supports:

* [Markdown][], via the [PegDown][] parser.
* [Textile][], via the [FuseSource WikiText fork][] of then Eclipse
  [Mylyn][] *wikitext* parser API.
* An internal handler that wraps plain text in `<pre>` and `</pre>` tags.
* An internal handler that simply passes HTML straight through.

# Installation

MarkWrap is published to the
[Bintray Maven repository](https://bintray.com/bmc/maven), which is
automatically linked to Bintray's [JCenter](https://bintray.com/bintray/jcenter)
repository. (From JCenter, it's eventually pushed to the
automatically sync'd with the [Maven Central Repository][].

* Version 1.1.2 supports Scala 2.12, Scala 2.11 and 2.10.
* Version 1.0.2 supports Scala 2.11 and 2.10.
* Version 1.0 supports Scala 2.10.
* Version 0.5.5 supports Scala 2.10.0-M7, 2.9.2, 2.9.1-1, 2.9.1, 2.9.0-1,
  2.9.0, 2.8.2, 2.8.1 and 2.8.0.

## Installing for Maven

If you're using [Maven][], just specify the artifact, and Maven will do the
rest for you:

* Group ID: `org.clapper`
* Artifact ID: `markwrap_SCALA_VERSION` (`markwrap_2.11`, for example)
* Version: `1.1.2`
* Type: `jar`

For example:

    <dependency>
      <groupId>org.clapper</groupId>
      <artifactId>markwrap_2.10</artifactId>
      <version>1.1.2</version>
    </dependency>

If you cannot resolve the artifact, then add the JCenter repository:

    <repositories>
      <repository>
        <snapshots>
          <enabled>false</enabled>
        </snapshots>
        <id>central</id>
        <name>bintray</name>
        <url>http://jcenter.bintray.com</url>
      </repository>
      ...
    </repositories>

For more information on using Maven and Scala, see Josh Suereth's
[Scala Maven Guide][].

## Using with SBT

### Using with SBT

Just add the following line to your `build.sbt`:

    libraryDependencies += "org.clapper" %% "markwrap" % "1.1.2"

# Building from Source

## Source Code Repository

The source code for the MarkWrap library is maintained on [GitHub][]. To
clone the repository, run this command:

    git clone git://github.com/bmc/markwrap.git

## Build Requirements

Building the MarkWrap library requires [SBT][] 0.13.x. Install
SBT, as described at the SBT web site.

## Building MarkWrap

Assuming you have an `sbt` shell script (or .BAT file, for Windows), run:

    sbt compile test package

The resulting jar file will be in the top-level `target` directory. If you're
on a Unix-like system (including Mac OS), you can just use the `activator`
script that's bundled with the code:

    bin/activator compile test package

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

### Parsing Markdown

To get a [Markdown][] parser, call `MarkWrap.parserFor()` with:

* the `MarkupType.Markdown` constant
* the MIME type string "text/markdown"
* A `java.io.File` object specifying a file with either a `.md` or `.markdown`
  extension.

[Markdown][] is parsed with [Pegdown][], instantiated with all of Pegdown's
extension options *except* `HARDWRAPS`. This means that hard line wraps in
your Markdown source are *not* passed through as `<br/>` elements in the
HTML. To force a `<br/>` (a hard line wrap), use two or more spaces at the
end of a Markdown line, which is how hard line wraps are handled in
standard Markdown.

### Parsing Textile

To get a [Textile][] parser, call `MarkWrap.parserFor()` with:

* the `MarkupType.Textile` constant
* the MIME type string "text/textile"
* A `java.io.File` object specifying a file with a `.textile` extension.

[Textile][] is parsed with the Eclipse [Mylyn][] *wikitext* parser API.

### Parsing raw HTML

To get a passthrough parser for HTML or XHTML, call `MarkWrap.parserFor()`
with:

* the `MarkupType.HTML` or `Markup.XHTML` constants
* one of the MIME type strings "text/html" or "text/xhtml"
* A `java.io.File` object specifying a file with one of these extensions:
  `.htm`, `.html`, `.xhtm`, `.xhtml`

A passthrough HTML parser simply passing the HTML straight through,
unmodified.

### Handling plain text

To get a plaintext-to-HTML parser, call `MarkWrap.parserFor()` with:

* the `MarkupType.PlainText` constant
* the MIME type string "text/plain"
* A `java.io.File` object specifying a file with one of these extensions:
  `.txt`, `.text,` `.cfg`, `.conf`, `.properties`

The resulting plain text is simply wrapped in `<pre>` and `</pre>` tags.

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
    val html = parser.parseToHTML(Source.fromFile(file))

Example 2:

    import org.clapper.markwrap._
    import scala.io.Source

    val markup = """This is some *Markdown* text"""
    val parser = MarkWrap.parserFor(MarkupType.Markdown)
    val html = parser.parseToHTML(Source.fromString(markup))

#### Parsing from a file or a string

If you're parsing from a file or a string, there are some shortcut methods:

From a `java.io.File`:

    import org.clapper.markwrap._
    import java.io.File

    val file = new File("/path/to/markup.md")
    val parser = MarkWrap.parserFor(file)
    val html = parser.parseToHTML(file)

From a string:

    import org.clapper.markwrap._

    val parser = MarkWrap.parserFor(MarkupType.Markdown)
    val html = parser.parseToHTML("""This is some *Markdown* text""")

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

MarkWrap is copyright &copy; 2009-2011 Brian M. Clapper and is released
under a [BSD License][].

# Patches

I gladly accept patches from their original authors. Feel free to email
patches to me or to fork the [GitHub repository][] and send me a pull
request. Along with any patch you send:

* Please state that the patch is your original work.
* Please indicate that you license the work to the MarkWrap project
  under a [BSD License][].

[BSD License]: https://github.com/bmc/markwrap/blob/master/LICENSE.md
[GitHub repository]: http://github.com/bmc/markwrap
[GitHub]: http://github.com/bmc/
[downloads area]: http://github.com/bmc/markwrap/downloads
[API Documentation]: api/
[Maven]: http://maven.apache.org/
[Scala Maven Guide]: http://www.scala-lang.org/node/345
[PegDown]: http://pegdown.org
[Markdown]: http://daringfireball.net/projects/markdown/
[Textile]: http://textile.thresholdstate.com/
[Mylyn]: http://www.eclipse.org/mylyn/
[FuseSource WikiText fork]: https://github.com/fusesource/wikitext
[MarkWrap web site]: http://software.clapper.org/markwrap/
[bmc@clapper.org]: mailto:bmc@clapper.org
[Brian M. Clapper]: mailto:bmc@clapper.org
[changelog]: https://github.com/bmc/markwrap/blob/master/CHANGELOG.md
[SBT]: http://code.google.com/p/simple-build-tool
[SBT cross-building]: http://code.google.com/p/simple-build-tool/wiki/CrossBuild
[Apache Ivy]: http://ant.apache.org/ivy/
[Library Management Maven/Ivy section]: http://code.google.com/p/simple-build-tool/wiki/LibraryManagement#Maven/Ivy
[SBT Manual]: http://code.google.com/p/simple-build-tool/wiki/DocumentationHome
[SBT-repo-email-thread]: http://groups.google.com/group/simple-build-tool/browse_thread/thread/470bba921252a167
[ls.implicit.ly]: http://ls.implicit.ly
[Maven central repository]: http://search.maven.org/
