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

import scala.io.Source

/**
  * The `PreWrapHandler` type handles a file that is presumed to be plain text.
  * The text is wrapped in "pre" tags and emitted as-is.
  */
private[markwrap] class PreWrapHandler extends MarkWrapParser {
  val markupType = MarkupType.PlainText

  /**
    * "Parse" a document that is assumed to be plain text.
    *
    * @param source  The `Source` from which to read the lines of text
    *
    * @return the HTML
    */
  def parseToHTML(source: Source): String =
    "<pre>" + source.getLines().mkString("\n") + "</pre>"
}
