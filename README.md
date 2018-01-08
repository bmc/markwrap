MarkWrap: Unified Scala wrapper API for various lightweight markup APIs
=======================================================================

[![Build Status](https://travis-ci.org/bmc/markwrap.svg?branch=master)](https://travis-ci.org/bmc/markwrap)

## Introduction

The MarkWrap library (pronounced "mark wrap" or "more crap", depending on
your preference) is a Scala library that provides a unified API for using
various underlying lightweight markup APIs. Currently, it supports:

* [Markdown][], via the [flexmark-java][] parser.
* [Textile][], via the [FuseSource WikiText fork][] of the Eclipse
  [Mylyn][] *wikitext* parser API.
* An internal handler that wraps plain text in `<pre>` and `</pre>` tags.
* An internal handler that simply passes HTML straight through.

See the [MarkWrap web site][] for more details.

[flexmark-java]: https://github.com/vsch/flexmark-java
[Markdown]: http://daringfireball.net/projects/markdown/
[Textile]: http://textile.thresholdstate.com/
[Mylyn]: http://www.eclipse.org/mylyn/
[FuseSource WikiText fork]: https://github.com/fusesource/wikitext
[MarkWrap web site]: http://software.clapper.org/markwrap/

MarkWrap is copyright &copy; 2010-2016 [Brian M. Clapper][] and is released
under a new BSD license.

[Brian M. Clapper]: mailto:bmc@clapper.org
