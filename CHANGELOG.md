---
title: "Change Log: MarkWrap, a unified API wrapper for lightweight markup APIs"
layout: default
---

Version 0.5.3:

* Added Scala 2.9.1-1 to the list of cross-built versions.

Version 0.5.2:

* Converted to build with SBT 0.11.2.
* Added support for `ls.implicit.ly` metadata.
* Now publishes to `oss.sonatype.org` (and, thence, to the Maven central repo).

Version 0.5.1:

* Now instantiates the underlying [Pegdown][] parser with the `HARDWRAPS`
  option disabled, ensuring that hard linewraps in the Markdown source are
  _not_ propagated automatically to the generated HTML. This is more
  consistent with common usage. To force a newline, use two or more spaces
  at the end of a line.
* When calling `MarkWrap.parserFor()` on Markdown, the underlying code
  returned an object that allocated a new Pegdown processor each time
  `parseToHTML()` was called. That's silly, so the object now allocates a
  Pegdown processor when it's allocated and re-uses it. Note that this
  change makes the returned Markdown parser object *not* thread-safe, since
  Pegdown is not thread-safe. (Just allocate one parser per thread, if
  you're threading.)

[Pegdown]: https://github.com/sirthias/pegdown

Version 0.5:

* `MarkWrapParser.parseToHTMLDocument()` no longer uses `scala.xml` to
  compose the document. First, there's no need; building the resulting
  string directly is more efficient. Second, HTML supports entities
  that XML does not, so using `scala.xml` can result in those entities
  being improperly converted.


Version 0.4.3:

* Now builds for [Scala][] 2.9.1, as well as 2.9.0-1, 2.9.0, 2.8.1, and 2.8.0.

[Scala]: http://www.scala-lang.org/

Version 0.4.2:

* Converted code to conform with standard Scala coding style.

Version 0.4.1:

* For some reason, "publish" with Scala 2.9.0 causes some JSON error while
  attempting to generate the documentation jar. Disabled publishing
  documentation jar for now.

Version 0.4:

* Now builds against Scala 2.9.0.1, as well as Scala 2.9.0, 2.8.1 and 2.8.0.
* Converted to build with [SBT][] 0.10.1

[SBT]: http://code.google.com/p/simple-build-tool/

Version 0.3:

* Now uses [PegDown][] to parse [Markdown][], instead of [Knockoff][].
* Now uses [FuseSource WikiText fork][] of Eclipse Mylyn Wikitext.
* Now available for Scala 2.9.0, as well as 2.8.1 and 2.8.0.

[PegDown]: http://pegdown.org
[Markdown]: http://daringfireball.net/projects/markdown/
[Knockoff]: http://tristanhunt.com/projects/knockoff/
[FuseSource WikiText fork]: https://github.com/fusesource/wikitext

Version 0.2.1:

* Now compiles against [Scala][] 2.8.1, as well as 2.8.0.

[Scala]: http://www.scala-lang.org/

Version 0.2:

* Added tests for remaining parser and document types.
* Now published to the [Scala Tools Maven repository][], which [SBT][]
  includes by default. Thus, if you're using SBT, it's longer necessary to
  specify a custom repository to find this artifact.

[Scala Tools Maven repository]: http://www.scala-tools.org/repo-releases/
[SBT]: http://code.google.com/p/simple-build-tool/

Version 0.1:

* Extracted from the [Grizzled Scala][] library and repackaged, to reduce
  transitive dependencies in [Grizzled Scala][].
  
[Grizzled Scala]: http://software.clapper.org/grizzled-scala/
