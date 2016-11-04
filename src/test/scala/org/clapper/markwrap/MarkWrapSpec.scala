package org.clapper.markwrap

import java.io.File

/**
  * Tests the MarkWrap functions.
  */
class MarkWrapSpec extends BaseSpec {
  "MarkWrap.parserFor" should "return the right type for file extensions" in {
    val fData = List(
      (MarkupType.Markdown,  "foo.md"),
      (MarkupType.Markdown,  "foo.markdown"),
      (MarkupType.Textile,   "foo.textile"),
      (MarkupType.XHTML,     "foo.xhtml"),
      (MarkupType.XHTML,     "foo.xhtm"),
      (MarkupType.XHTML,     "foo.htm"),
      (MarkupType.XHTML,     "foo.html"),
      (MarkupType.PlainText, "foo.text"),
      (MarkupType.PlainText, "foo.txt"),
      (MarkupType.PlainText, "foo.properties"),
      (MarkupType.PlainText, "foo.cfg"),
      (MarkupType.PlainText, "foo.conf")
    )

    val mtData = List(
      (MarkupType.Markdown,  "text/markdown"),
      (MarkupType.Textile,   "text/textile"),
      (MarkupType.XHTML,     "text/html"),
      (MarkupType.XHTML,     "text/xhtml"),
      (MarkupType.PlainText, "text/plain")
    )

    val typeData = List(
      MarkupType.Markdown,
      MarkupType.Textile,
      MarkupType.XHTML,
      MarkupType.XHTML,
      MarkupType.PlainText
    )

    for ((expected, fn) <- fData) {
      MarkWrap.parserFor(new File(fn)).markupType shouldBe expected
    }

    for ((expected, mimeType) <- mtData) {
      MarkWrap.parserFor(mimeType).markupType shouldBe expected
    }

    for (parserType <- typeData) {
      MarkWrap.parserFor(parserType).markupType shouldBe parserType
    }
  }
}
