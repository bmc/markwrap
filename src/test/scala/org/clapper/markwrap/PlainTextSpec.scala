package org.clapper.markwrap

/** Test the plain text functions.
  */
class PlainTextSpec extends BaseSpec {
  "MarkWrap.PlainText" should "properly wrap text in <pre></pre>" in {
    val data = List(
      ("Test",        "<pre>Test</pre>"),
      ("_Test_",      "<pre>_Test_</pre>"),
      ("foo\nbar\n",  "<pre>foo\nbar</pre>")
    )

    val parser = MarkWrap.parserFor(MarkupType.PlainText)
    for ((input, expected) <- data) {
      parser.parseToHTML(input) shouldBe expected
    }
  }
}
