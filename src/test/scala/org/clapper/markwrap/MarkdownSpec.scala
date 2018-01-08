/*
  ---------------------------------------------------------------------------
  This software is released under a BSD license, adapted from
  http://opensource.org/licenses/bsd-license.php

  Copyright (c) 2010-2018, Brian M. Clapper
  All rights reserved.

  See the accompanying license file for details.
  ---------------------------------------------------------------------------
*/

import org.clapper.markwrap._
import scala.io.Source

/**
  * Tests the Markdown functions.
  */
class MarkdownSpec extends BaseFixtureSpec {

  case class FixtureParam(parser: MarkWrapParser)

  override def withFixture(test: OneArgTest) = {
    val parser = MarkWrap.converterFor(MarkupType.Markdown)
    parser.markupType shouldBe MarkupType.Markdown
    withFixture(test.toNoArgTest(FixtureParam(parser)))
  }

  "MarkdownParser.parseToHTML" should "produce valid HTML" in { f =>

    val data = List(
      ("*Test*",             "<p><em>Test</em></p>\n"),
      ("_Test_",             "<p><em>Test</em></p>\n"),
      ("**Test**",           "<p><strong>Test</strong></p>\n"),
      ("__Test__",           "<p><strong>Test</strong></p>\n"),
      ("___Test___",         "<p><em><strong>Test</strong></em></p>\n"),
      ("***Test***",         "<p><em><strong>Test</strong></em></p>\n"),
      ("abc\n===\n\ntest",   "<h1>abc</h1>\n<p>test</p>\n")
    )

    for((input, expected) <- data) {
      f.parser.parseToHTML(input) shouldBe expected
    }
  }

  it should "handle soft line breaks" in { f =>
    val data = List(
      ("test\n*test*",     "<p>test\n<em>test</em></p>\n"),

      ("""
         |* this is a list
         |  with line breaks in it.
         |* This is line two
         |  of the list.
       """.stripMargin,


       """<ul>
         |<li>this is a list
         |with line breaks in it.</li>
         |<li>This is line two
         |of the list.</li>
         |</ul>
         |""".stripMargin)
    )

    for((input, expected) <- data) {
      f.parser.parseToHTML(input) shouldBe expected
    }
  }

  it should "handle hard line breaks" in { f =>
    val data = List(
      ("soft  \nwrap\n",   "<p>soft<br />\nwrap</p>\n")
    )

    for((input, expected) <- data) {
      f.parser.parseToHTML(input) shouldBe expected
    }
  }

  it should "expand quotes into smart quote entities" in { f =>
    val parser = MarkWrap.converterFor(MarkupType.Markdown)

    f.parser.parseToHTML("""This has "quotes" in it.""") shouldBe
      "<p>This has &quot;quotes&quot; in it.</p>\n"
  }

  it should "not prettify em- and en-dashes" in { f =>
    f.parser.parseToHTML("An em-dash---and an en-dash--are here.") shouldBe
      "<p>An em-dash---and an en-dash--are here.</p>\n"
  }

  "MarkdownParser.parseToHTMLDocument" should "do title substitution" in { f =>

    val data = List(
      ("test document", "test title",
       """<html>
         |<head>
         |<title>test title</title>""".stripMargin)
    )

    for((input, title, expected) <- data) {
      val result = f.parser.parseToHTMLDocument(Source.fromString(input),
                                                title, None)
      result.startsWith(expected) shouldBe true
    }
  }

  it should "expand HTML entities into their Unicode counterparts" in { f =>
    val data = List(
      // markdown            result contains
      ("&copy;",             "<p>\u00a9</p>"),
      ("&#x2122;",           "<p>\u2122</p>"),
      ("&#8482;",            "<p>\u2122</p>"),
      ("&Hat;",              "<p>^</p>"),
      ("&cent;",             "<p>\u00a2</p>"),
      ("&ugrave;",           "<p>\u00f9</p>"),
      ("&reg;",              "<p>\u00ae</p>"),
      ("&half;",             "<p>\u00bd</p>"),
      ("&frac12;",           "<p>\u00bd</p>"),
      ("&frac14;",           "<p>\u00bc</p>")
    )

    for((input, expected) <- data) {
      val result = f.parser.parseToHTMLDocument(Source.fromString(input),
                                                "", None)
      result should include (expected)
    }
  }

  it should "escape unknown HTML entities" in { f =>
    val data = List(
      // markdown            result contains
      ("&copyr;",            "<p>&amp;copyr;</p>"),
      ("&Hart;",             "<p>&amp;Hart;</p>"),
      ("&quarter;",          "<p>&amp;quarter;</p>")
    )

    for((input, expected) <- data) {
      val result = f.parser.parseToHTMLDocument(Source.fromString(input),
                                                "", None)
      result should include (expected)
    }
  }

  it should "render tables" in { f =>
    val markdown1 =
      """
        >| head1 | head2 |
        >| ----- | ----- |
        >| col 1 | col 2 |
        >| col 3 | col 3 |
      """.stripMargin('>')
    val expected1 =
      """|<table>
         |<thead>
         |<tr><th>head1</th><th>head2</th></tr>
         |</thead>
         |<tbody>
         |<tr><td>col 1</td><td>col 2</td></tr>
         |<tr><td>col 3</td><td>col 3</td></tr>
         |</tbody>
         |</table>
         |""".stripMargin
    f.parser.parseToHTML(markdown1) shouldBe expected1

    val markdown2 =
      """
        >| Left-aligned | Center-aligned | Right-aligned |
        >| :---         |      :---:     |          ---: |
        >| git status   | git status     | git status    |
        >| git status   | git status     | git status    |
       """.stripMargin('>')

    val expected2 =
      """|<table>
         |<thead>
         |<tr><th align="left">Left-aligned</th><th align="center">Center-aligned</th><th align="right">Right-aligned</th></tr>
         |</thead>
         |<tbody>
         |<tr><td align="left">git status</td><td align="center">git status</td><td align="right">git status</td></tr>
         |<tr><td align="left">git status</td><td align="center">git status</td><td align="right">git status</td></tr>
         |</tbody>
         |</table>
         |""".stripMargin
    f.parser.parseToHTML(markdown2) shouldBe expected2
  }

  it should "render strikethrough" in { f =>
    f.parser.parseToHTML("~~hello~~") shouldBe "<p><del>hello</del></p>\n"
  }

  it should "render subscript" in { f =>
    f.parser.parseToHTML("~hello~") shouldBe "<p><sub>hello</sub></p>\n"
  }

  it should "render a definition list" in { f =>
    val markdown1 =
      """|Orange
         |:   The fruit of an evergreen tree of the genus Citrus.
      """.stripMargin
    val expected1 =
      """|<dl>
         |<dt>Orange</dt>
         |<dd>The fruit of an evergreen tree of the genus Citrus.</dd>
         |</dl>
         |""".stripMargin
    f.parser.parseToHTML(markdown1) shouldBe expected1

    val markdown2 =
      """|Orange
         |:   The fruit of an evergreen tree of the genus Citrus.
         |Apple
         |:   Pomaceous fruit of plants of the genus Malus in
         |    the family Rosaceae.
         |""".stripMargin
    val expected2 =
      """|<dl>
         |<dt>Orange</dt>
         |<dd>The fruit of an evergreen tree of the genus Citrus.</dd>
         |<dt>Apple</dt>
         |<dd>Pomaceous fruit of plants of the genus Malus in
         |the family Rosaceae.</dd>
         |</dl>
         |""".stripMargin
    f.parser.parseToHTML(markdown1) shouldBe expected1
  }
}
