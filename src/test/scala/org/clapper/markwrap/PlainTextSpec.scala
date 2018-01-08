/*
  ---------------------------------------------------------------------------
  This software is released under a BSD license, adapted from
  http://opensource.org/licenses/bsd-license.php

  Copyright (c) 2010-2018, Brian M. Clapper
  All rights reserved.

  See the accompanying license file for details.
  ---------------------------------------------------------------------------
*/

package org.clapper.markwrap

/** Test the plain text functions.
  */
class PlainTextSpec extends BaseFixtureSpec {

  case class FixtureParam(parser: MarkWrapParser)

  override def withFixture(test: OneArgTest) = {
    val parser = MarkWrap.converterFor(MarkupType.PlainText)
    parser.markupType shouldBe MarkupType.PlainText
    withFixture(test.toNoArgTest(FixtureParam(parser)))
  }

  "MarkWrap.PlainText" should "properly wrap text in <pre></pre>" in { f =>
    val data = List(
      ("Test",        "<pre>Test</pre>"),
      ("_Test_",      "<pre>_Test_</pre>"),
      ("foo\nbar\n",  "<pre>foo\nbar</pre>")
    )

    for ((input, expected) <- data) {
      f.parser.parseToHTML(input) shouldBe expected
    }
  }
}
