---
title: Change log for MarkWrap
layout: default
---

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
  
[Grizzled Scala]: http://bmc.github.com/grizzled-scala/
