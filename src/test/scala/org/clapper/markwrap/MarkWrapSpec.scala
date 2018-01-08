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

import java.io.File

/**
  * Tests the MarkWrap functions.
  */
class MarkWrapSpec extends BaseSpec {
  "MarkWrap.converterFor" should "return the right type for file extensions" in {
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

    for ((expected, fn) <- fData) {
      val t = MarkWrap.converterFor(new File(fn))
      t shouldBe 'success
      t.get.markupType shouldBe expected
    }
  }

  it should "return the right parser for a valid MIME type" in {

    val mtData = List(
      (MarkupType.Markdown,  "text/markdown"),
      (MarkupType.Textile,   "text/textile"),
      (MarkupType.XHTML,     "text/html"),
      (MarkupType.XHTML,     "text/xhtml"),
      (MarkupType.PlainText, "text/plain")
    )

    for ((expected, mimeType) <- mtData) {
      val t = MarkWrap.converterFor(mimeType)
      t shouldBe 'success
      t.get.markupType shouldBe expected
    }
  }

  it should "return the right parser for a MarkWrap parser type" in {
    val typeData = List(
      MarkupType.Markdown,
      MarkupType.Textile,
      MarkupType.XHTML,
      MarkupType.XHTML,
      MarkupType.PlainText
    )

    for (parserType <- typeData) {
      val parser = MarkWrap.converterFor(parserType)
      parser.markupType shouldBe parserType
    }
  }

  it should "return a Failure for a bad MIME type" in {
    MarkWrap.converterFor("application/json") shouldBe 'failure
  }

  it should "return a Failure for an unknown extension" in {
    MarkWrap.converterFor(new File("foo.c")) shouldBe 'failure
  }
}
