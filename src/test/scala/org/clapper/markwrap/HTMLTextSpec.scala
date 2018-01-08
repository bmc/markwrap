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

    val parser = MarkWrap.converterFor(markupType)
    for ((input, expected) <- data) {
      parser.parseToHTML(input) shouldBe expected
    }
  }
}
