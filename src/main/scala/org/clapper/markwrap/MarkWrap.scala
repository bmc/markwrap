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

/**
  * Defines a common API for various simple markup parsers. Currently, this
  * API supports Markdown and Textile, using different parsers behind a common
  * interface.
  */
package org.clapper.markwrap

import scala.io.Source

import java.io.File

/**
  * The common parser interface.
  */
trait MarkWrapParser {

  /** The markup type associated with the parser.
    *
    * @return the markup type.
    */
  val markupType: MarkupType

  /** Convert the specified markup to an HTML fragment, without
    * `html` or `body` tags. The resulting HTML fragment can then be
    * included within an existing HTML document.
    *
    * @param source  the source containing the markup
    *
    * @return the HTML
    */
  def parseToHTML(source: Source): String

  /** Convert the specified markup to an HTML fragment, without
    * `html` or `body` tags. The resulting HTML fragment can then be
    * included within an existing HTML document.
    *
    * @param file  the file containing the markup
    *
    * @return the HTML
    */
  def parseToHTML(file: File): String =
    parseToHTML(Source.fromFile(file))

  /** Convert the specified markup to an HTML fragment, without
    * `html` or `body` tags. The resulting HTML fragment can then be
    * included within an existing HTML document.
    *
    * @param markup  the string containing the markup
    *
    * @return the HTML
    */
  def parseToHTML(markup: String): String =
    parseToHTML(Source.fromString(markup))

  /** Simple wrapper function that produces an XHTML-compliant document,
    * complete with HTML, HEAD and BODY tags, from a markup document.
    *
    * @param markupSource  The `Source` from which to read the
    *                      lines of markup
    * @param title         The document title
    * @param cssSource     Source for any CSS to be included, or None
    * @param encoding      The encoding to use. Defaults to "UTF-8".
    *
    * @return the formatted HTML document.
    */
  def parseToHTMLDocument(markupSource: Source, 
                          title: String,
                          cssSource: Option[Source] = None,
                          encoding: String  = "UTF-8"): String = {
    import scala.xml.parsing.XhtmlParser

    val css = cssSource.map(src => src.getLines() mkString "\n").getOrElse("")

    // Inserting raw HTML in the body will cause it to be escaped. So,
    // parse the HTML into a NodeSeq first. Note the the whole thing
    // has to be wrapped in a single element, so it might as well
    // be the <body> element.

    val htmlBody = "<body>" + parseToHTML(markupSource) + "</body>"
    val htmlBodyNode = XhtmlParser(Source.fromString(htmlBody))

    val contentType = "text/html; charset=" + encoding
    val htmlTemplate =
      <html>
        <head>
        <title>{title}</title>
        <style type="text/css">{css}</style>
        <meta http-equiv="Content-Type" content={contentType}/>
        </head>
        {htmlBodyNode}
      </html>

    htmlTemplate.toString
  }
}

/**
 * Supported markup types.
 */
sealed abstract class MarkupType(val name: String, val mimeType: String)

/**
 * Defines the various supported lightweight markup types.
 */
object MarkupType {
  /**
   * Markup type for Markdown. Text is transformed to HTML via Markdown
   * parser.
   */
  case object Markdown extends MarkupType("markdown", "text/markdown")

  /**
   * Markup type for Textile. Text is transformed to HTML via Textile
   * parser.
   */
  case object Textile extends MarkupType("textile", "text/textile")

  /**
   * Markup type for HTML. Text is emitted as-is.
   */
  case object HTML extends MarkupType("html", "text/html")

  /**
   * Markup type for XHTML. Text is emitted as-is.
   */
  case object XHTML extends MarkupType("xhtml", "text/xhtml")

  /**
   * Markup type for plain text. Text is wrapped in "pre" tags
   * and emitted as-is.
   */
  case object PlainText extends MarkupType("plaintext", "text/plain")
}

/**
 * Factory object to produce parsers for specific markup document types.
 */
object MarkWrap {
  import javax.activation.MimetypesFileTypeMap

  private lazy val MimeTypeMap = new MimetypesFileTypeMap
  MimeTypeMap.addMimeTypes(
    """text/markdown md markdown
    |text/textile textile
    |text/html htm html
    |text/xhtml xhtml xhtm
    |text/plain txt TXT text TEXT cfg conf properties
    """.stripMargin
  )

  /** MIME type to MarkupType mapping.
    */
  private val MimeTypes = Map(
    MarkupType.Markdown.mimeType  -> MarkupType.Markdown,
    MarkupType.Textile.mimeType   -> MarkupType.Textile,
    MarkupType.HTML.mimeType      -> MarkupType.HTML,
    MarkupType.XHTML.mimeType     -> MarkupType.XHTML,
    MarkupType.PlainText.mimeType -> MarkupType.PlainText
  )

  /** Get a parser for the specified type.
    *
    * @param parserType  the parser type
    */
  def parserFor(parserType: MarkupType): MarkWrapParser = parserType match {
    case MarkupType.Markdown  => new MarkdownParser
    case MarkupType.Textile   => new TextileParser
    case MarkupType.HTML      => new VerbatimHandler
    case MarkupType.XHTML     => new VerbatimHandler
    case MarkupType.PlainText => new PreWrapHandler
  }

  /** Get a parser for the specified MIME type.
    *
    * @param mimeType  the MIME type
    *
    * @return the parser.
    *
    * @throws IllegalArgumentException unsupported MIME type
    */
  def parserFor(mimeType: String): MarkWrapParser = {
    def BadMimeType = 
      throw new IllegalArgumentException("Unknown MIME type: " + mimeType)

    MimeTypes.get(mimeType).map(p => parserFor(p)).getOrElse(BadMimeType)
  }

  /** Get a parser for the specified file.
    *
    * @param f  the file
    *
    * @return the parser.
    *
    * @throws IllegalArgumentException unsupported MIME type
    */
  def parserFor(f: File): MarkWrapParser =
    parserFor(MimeTypeMap.getContentType(f))
}
