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
  * The `TextileParser` class parses the Textile markup language, producing
  * HTML. The current implementation uses the Textile parser API from the
  * Eclipse Mylyn WikiText library.
  */
private[markwrap] class TextileParser extends MarkWrapParser {
  import org.eclipse.mylyn.wikitext.core.parser.{MarkupParser => WTParser}
  import org.eclipse.mylyn.wikitext.core.parser.builder.HtmlDocumentBuilder
  import org.eclipse.mylyn.wikitext.textile.core.TextileLanguage
  import java.io.StringWriter

  val markupType = MarkupType.Textile

  /**
    * Parse a Textile document, producing HTML. The generated HTML markup
    * does not contain HTML or BODY tags, so it is suitable for embedding in
    * existing HTML documents.
    *
    * @param source  source from which to read the lines of Textile
    *
    * @return the formatted HTML
    */
  def parseToHTML(source: Source): String = {
    val buf = new StringWriter
    val builder = new HtmlDocumentBuilder(buf)

    builder.setEmitAsDocument(false)

    val parser = new WTParser(new TextileLanguage)
    parser.setBuilder(builder)
    parser.parse(source.getLines() mkString "\n")
    buf.toString
  }
}
