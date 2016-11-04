package org.clapper.markwrap

class HTMLTextSpec extends BaseSpec {
  "MarkWrap.HTML" should "properly render HTML" in {
    doType(MarkupType.HTML)
  }

  "MarkWrap.HTML" should "properly render XHTML" in {
    doType(MarkupType.XHTML)
  }

  private def doType(markupType: MarkupType): Unit = {

    val data = List(
      ("<h1>Test</h2>",      "<h1>Test</h2>"),
      ("<p>_Test_</p>",      "<p>_Test_</p>")
    )

    val parser = MarkWrap.parserFor(markupType)
    for ((input, expected) <- data) {
      parser.parseToHTML(input) shouldBe expected
    }
  }
}
