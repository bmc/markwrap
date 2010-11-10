MarkWrap: Unified Scala wrapper API for various lightweight markup APIs
=======================================================================

## Introduction

The MarkWrap library (pronounced "mark wrap" or "more crap", depending on
your preference) is a Scala library that provides a unified API for using
various underlying lightweight markup APIs. Currently, it supports:

* [Markdown][], via the [Knockoff][] parser.
* [Textile][], via the Eclipse [Mylyn][] *wikitext* parser API.
* An internal handler that wraps plain text in `<pre>` and `</pre>` tags.
* An internal handler that simply passes HTML straight through.

See the [MarkWrap web site][] for more details.

[Knockoff]: http://tristanhunt.com/projects/knockoff/
[Markdown]: http://daringfireball.net/projects/markdown/
[Textile]: http://textile.thresholdstate.com/
[Mylyn]: http://www.eclipse.org/mylyn/
[MarkWrap web site]: http://bmc.github.com/markwrap/

MarkWrap is copyright &copy; 2010 [Brian M. Clapper][] and is released
under a new BSD license.

[Brian M. Clapper]: mailto:bmc@clapper.org
