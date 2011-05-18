
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
