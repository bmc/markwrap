# Change log for MarkWrap library

**Version 1.2.0**

* No longer uses Pegdown to render Markdown, as Pegdown is officially
  at end-of-life. Now uses [flexmark-java][] and the 
  [CommonMark](http://commonmark.org/) dialect, with some additional
  extensions. For the list of enabled extensions, see the [MarkWrap][] web 
  site.
* Deprecated the `MarkWrap.parserFor()` functions, because they throw
  exceptions. Added new `MarkWrap.converterFor()` functions that return
  `Try` values, instead.
* Added more unit tests.

**Version 1.1.2**

* Updated to cross-build against Scala 2.12.0-final.
* Updated to ScalaTest 3.0.0, and converted all tests to `FlatSpec` style.

**Version 1.1.1**

* Added Lightbend Activator for auto-downloading build.
* Integrated with Travis CI.
* Now builds with Scala 2.12, as well as 2.10 and 2.11.

**Version 1.1.0**

* Disabled conversion of "---", "--", and quotes to HTML entities (which mean
  the resulting HTML can be more reliably parsed by `scala.xml`).
* Updated ScalaTest and Pegdown versions.
* Removed `ls` from build.

**Version 1.0.2**

* Built for Scala 2.10 and 2.11.
* Updated to latest version of ScalaTest.

**Version 1.0.1**

* Bumped the WikiText `textile-core` version to 1.4 and the `pegdown`
  version to 1.2.1. Thanks to Ricky Elrod, _ricky.gh_ at _elrod.me_, for
  the update.
* Compiles against Scala 2.10.1 now (which should be bytecode-compatible with
  prior Scala 2.10 releases).
* Bumped ScalaTest version to 2.0.M5b.

**Version 1.0.0:**

* Cross-compiled and published for Scala 2.10.0-RC1.
* Converted to use ScalaTest 2.0, which changes `expect` to `expectResult`.

**Version 0.5.5:**

* Added Scala 2.10.0-M7 to the list of cross-built versions.
* The project now explicitly specifies the SBT [ls][] plugin, instead of
  assuming that it's globally specified, to permit others to build the
  project more easily.
* Build converted to use SBT 0.12.0.

**Version 0.5.4:**

* Added Scala 2.9.2 to the list of cross-built versions.

**Version 0.5.3:**

* Added Scala 2.9.1-1 to the list of cross-built versions.

**Version 0.5.2:**

* Converted to build with SBT 0.11.2.
* Added support for `ls.implicit.ly` metadata.
* Now publishes to `oss.sonatype.org` (and, thence, to the Maven central repo).

**Version 0.5.1:**

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

**Version 0.5:**

* `MarkWrapParser.parseToHTMLDocument()` no longer uses `scala.xml` to
  compose the document. First, there's no need; building the resulting
  string directly is more efficient. Second, HTML supports entities
  that XML does not, so using `scala.xml` can result in those entities
  being improperly converted.


**Version 0.4.3:**

* Now builds for [Scala][] 2.9.1, as well as 2.9.0-1, 2.9.0, 2.8.1, and 2.8.0.

[Scala]: http://www.scala-lang.org/

**Version 0.4.2:**

* Converted code to conform with standard Scala coding style.

**Version 0.4.1:**

* For some reason, "publish" with Scala 2.9.0 causes some JSON error while
  attempting to generate the documentation jar. Disabled publishing
  documentation jar for now.

**Version 0.4:**

* Now builds against Scala 2.9.0.1, as well as Scala 2.9.0, 2.8.1 and 2.8.0.
* Converted to build with [SBT][] 0.10.1

[SBT]: http://code.google.com/p/simple-build-tool/

**Version 0.3:**

* Now uses [Pegdown][] to parse [Markdown][], instead of [Knockoff][].
* Now uses [FuseSource WikiText fork][] of Eclipse Mylyn Wikitext.
* Now available for Scala 2.9.0, as well as 2.8.1 and 2.8.0.

**Version 0.2.1:**

* Now compiles against [Scala][] 2.8.1, as well as 2.8.0.

**Version 0.2:**

* Added tests for remaining parser and document types.
* Now published to the [Scala Tools Maven repository][], which [SBT][]
  includes by default. Thus, if you're using SBT, it's longer necessary to
  specify a custom repository to find this artifact.

**Version 0.1:**

* Extracted from the [Grizzled Scala][] library and repackaged, to reduce
  transitive dependencies in [Grizzled Scala][].

[flexmark-java]: https://github.com/vsch/flexmark-java
[MarkWrap]: http://software.clapper.org/markwrap
[Pegdown]: http://pegdown.org
[Markdown]: http://daringfireball.net/projects/markdown/
[Knockoff]: http://tristanhunt.com/projects/knockoff/
[FuseSource WikiText fork]: https://github.com/fusesource/wikitext
[Scala]: http://www.scala-lang.org/
[Scala Tools Maven repository]: http://www.scala-tools.org/repo-releases/
[SBT]: http://code.google.com/p/simple-build-tool/
[Grizzled Scala]: http://software.clapper.org/grizzled-scala/
[ls]: https://github.com/softprops/ls
