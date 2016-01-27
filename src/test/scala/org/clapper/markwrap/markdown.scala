/*
  ---------------------------------------------------------------------------
  This software is released under a BSD license, adapted from
  http://opensource.org/licenses/bsd-license.php

  Copyright (c) 2010 Brian M. Clapper. All rights reserved.

  Redistribution and use in source and binary forms, with or without
  modification, are permitted provided that the following conditions are
  met:

  * Redistributions of source code must retain the above copyright notice,
    this list of conditions and the following disclaimer.

  * Redistributions in binary form must reproduce the above copyright
    notice, this list of conditions and the following disclaimer in the
    documentation and/or other materials provided with the distribution.

  * Neither the names "clapper.org", "MarkWrap", nor the names of its
    contributors may be used to endorse or promote products derived from
    this software without specific prior written permission.

  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
  IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
  THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
  PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
  EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
  PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
  PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
  LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
  ---------------------------------------------------------------------------
*/

import org.scalatest.{Matchers, FlatSpec}
import org.clapper.markwrap._
import scala.io.Source;
/**
  * Tests the grizzled.parsing.markup Markdown functions.
  */
class MarkdownTest extends FlatSpec with Matchers {
  "MarkdownParser.parseToHTML" should "produce valid HTML" in {

    val data = List(
      ("*Test*",             "<p><em>Test</em></p>"),
      ("_Test_",             "<p><em>Test</em></p>"),
      ("**Test**",           "<p><strong>Test</strong></p>"),
      ("__Test__",           "<p><strong>Test</strong></p>"),
      ("___Test___",         "<p><strong><em>Test</em></strong></p>"),
      ("***Test***",         "<p><strong><em>Test</em></strong></p>"),
      ("abc\n===\n\ntest",   "<h1>abc</h1>\n<p>test</p>")
    )

    val parser = MarkWrap.parserFor(MarkupType.Markdown)
    assertResult(MarkupType.Markdown, "Markup type") {parser.markupType}

    for((input, expected) <- data) {
      parser.parseToHTML(input) shouldBe expected
    }
  }

  it should "not do anything special with hard line wraps" in {
    val data = List(
      ("test\n*test*",     "<p>test <em>test</em></p>"),

      ("""
         |* this is a list
         |  with line breaks in it.
         |* This is line two
         |  of the list.
       """.stripMargin,


       """<ul>
         |  <li>this is a list  with line breaks in it.</li>
         |  <li>This is line two  of the list.</li>
         |</ul>""".stripMargin),

      ("soft  \nwrap\n",   "<p>soft<br/>wrap</p>")
    )

    val parser = MarkWrap.parserFor(MarkupType.Markdown)
    for((input, expected) <- data) {
      parser.parseToHTML(input) shouldBe expected
    }
  }

  it should "not expand quotes into smart quote entities" in {
    val parser = MarkWrap.parserFor(MarkupType.Markdown)

    parser.parseToHTML("""This has "quotes" in it.""") shouldBe (
      """<p>This has "quotes" in it.</p>"""
    )
  }

  it should "not prettify em- and en-dashes" in {
    val parser = MarkWrap.parserFor(MarkupType.Markdown)

    parser.parseToHTML("An em-dash---and an en-dash--are here.") shouldBe (
      "<p>An em-dash---and an en-dash--are here.</p>"
    )
  }

  "MarkdownParser.parseToHTMLDocument" should "do title substitution" in {

    val data = List(
      ("test document", "test title",
       """<html>
         |<head>
         |<title>test title</title>""".stripMargin)
    )

    val parser = MarkWrap.parserFor(MarkupType.Markdown)
    assertResult(MarkupType.Markdown, "Markup type") {parser.markupType}

    for((input, title, expected) <- data) {
      val result = parser.parseToHTMLDocument(Source.fromString(input),
                                              title, None)
      result.startsWith(expected) shouldBe true
    }
  }

  it should "handle HTML entities" in {

    val data = List(
      // markdown            result contains
      ("&copy;",             "<p>&copy;</p>"),
      ("&emdash;",           "<p>&emdash;</p>")
    )

    val parser = MarkWrap.parserFor(MarkupType.Markdown)
    assertResult(MarkupType.Markdown, "Markup type") {parser.markupType}

    for((input, expected) <- data) {
      val result = parser.parseToHTMLDocument(Source.fromString(input),
                                              "", None)
      result.contains(expected) shouldBe true
    }
  }
}
