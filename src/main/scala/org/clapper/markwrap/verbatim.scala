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
  * The `VerbatimHandler` type handles a file that is already HTML.
  */
private[markwrap] class VerbatimHandler extends MarkWrapParser {
  val markupType = MarkupType.XHTML

  /**
    * "Parse" a document that is assumed to be HTML already.
    *
    * @param source  The `Source` from which to read the lines of HTML
    *
    * @return the HTML
    */
  def parseToHTML(source: Source): String = source.getLines().mkString("\n")
}
