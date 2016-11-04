package org.clapper.markwrap

/**
  * Tests the Textile functions.
  */
class TextileSpec extends BaseSpec {
  "TextileParser.parseToHTML" should "properly render HTML" in {
    import scala.io.Source

    val data = List(
      ("*Test*",             "<p><strong>Test</strong></p>"),
      ("_Test_",             "<p><em>Test</em></p>"),
      ("**Test**",           "<p><b>Test</b></p>"),
      ("__Test__",           "<p><i>Test</i></p>"),
      ("-Test-",             "<p><del>Test</del></p>"),
      ("^Test^",             "<p><sup>Test</sup></p>"),
      ("~Test~",             "<p><sub>Test</sub></p>"),
      ("@code block@",       "<p><code>code block</code></p>"),
      ("h1. Test Header",    "<h1 id=\"TestHeader\">Test Header</h1>"),
      ("\"link\":#link",     "<p><a href=\"#link\">link</a></p>"),
      ("Trademark(tm)",      "<p>Trademark&#8482;</p>"),
      ("Registered(r)",      "<p>Registered&#174;</p>"),
      ("Copyright(c)",       "<p>Copyright&#169;</p>"),
      ("bc. foo",            "<pre><pre>foo\n</pre></pre>")
    )

    val parser = MarkWrap.parserFor(MarkupType.Textile)
    parser.markupType shouldBe MarkupType.Textile

    for((input, expected) <- data) {
      parser.parseToHTML(input) shouldBe expected
    }
  }
}
