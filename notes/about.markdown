The MarkWrap library (pronounced "mark wrap" or "more crap", depending on
your preference) is a Scala library that provides a unified API for using
various underlying lightweight markup APIs. Currently, it supports:

* [Markdown][], via the [PegDown][] parser.
* [Textile][], via the [FuseSource WikiText fork][] of then Eclipse
  [Mylyn][] *wikitext* parser API.
* An internal handler that wraps plain text in `<pre>` and `</pre>` tags.
* An internal handler that simply passes HTML straight through.

See the [MarkWrap web site][] for more details.

[PegDown]: http://pegdown.org
[Markdown]: http://daringfireball.net/projects/markdown/
[Textile]: http://textile.thresholdstate.com/
[FuseSource WikiText fork]: https://github.com/fusesource/wikitext
[Mylyn]: http://www.eclipse.org/mylyn/
[MarkWrap web site]: http://software.clapper.org/markwrap/
