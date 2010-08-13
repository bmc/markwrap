---
title: MarkWrap -- Unified Scala wrapper API for various lightweight markup APIs
layout: withTOC
---

## Introduction

The MarkWrap library (pronounced "mark wrap" or "more crap", depending on
your preference) is a Scala library that provides a unified API for using
various underlying lightweight markup APIs. Currently, it supports:

* [Markdown][], via the [Knockoff][] parser.
* [Textile][], via the Eclipse [Mylyn] *wikitext* parser API.
* An internal handler that wraps plain text in `<pre>` and `</pre>` tags.
* An internal handler that simply passes HTML straight through.

See the [MarkWrap web site] for more details.

## Author

[Brian M. Clapper][], [bmc@clapper.org][]

## Copyright and License

The Grizzled Scala Library is copyright &copy; 2009-2010 Brian M. Clapper
and is released under a [BSD License][].

## Patches

I gladly accept patches from their original authors. Feel free to email
patches to me or to fork the [GitHub repository][] and send me a pull
request. Along with any patch you send:

* Please state that the patch is your original work.
* Please indicate that you license the work to the MarkWrap project
  under a [BSD License][].

[BSD License]: license.html
[GitHub repository]: http://github.com/bmc/PROJECT
[GitHub]: http://github.com/bmc/
[downloads area]: http://github.com/bmc/PROJECT/downloads
[*clapper.org* Maven repository]: http://maven.clapper.org/org/clapper/
[Maven]: http://maven.apache.org/
[Knockoff]: http://tristanhunt.com/projects/knockoff/
[Markdown]: http://daringfireball.net/projects/markdown/
[Textile]: http://textile.thresholdstate.com/
[Mylyn]: http://www.eclipse.org/mylyn/
[MarkWrap web site]: http://bmc.github.com/markwrap/
[bmc@clapper.org]: mailto:bmc@clapper.org
[Brian M. Clapper]: mailto:bmc@clapper.org
