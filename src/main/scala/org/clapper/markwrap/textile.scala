/*
  ---------------------------------------------------------------------------
  This software is released under a BSD license, adapted from
  http://opensource.org/licenses/bsd-license.php

  Copyright (c) 2010, Brian M. Clapper
  All rights reserved.

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

package org.clapper.markwrap

import scala.io.Source

/**
 * The `TextileParser` class parses the Textile markup language, producing
 * HTML. The current implementation uses the Textile parser API from the
 * Eclipse Mylyn WikiText library.
 */
private[markwrap] class TextileParser extends MarkWrapParser
{
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
    def parseToHTML(source: Source): String =
    {
        val buf = new StringWriter
        val builder = new HtmlDocumentBuilder(buf)

        builder.setEmitAsDocument(false)

        val parser = new WTParser(new TextileLanguage)
        parser.setBuilder(builder)
        parser.parse(source.getLines() mkString "\n")
        buf.toString
    }
}
