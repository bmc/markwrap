/*
  ---------------------------------------------------------------------------
  This software is released under a BSD license, adapted from
  http://opensource.org/licenses/bsd-license.php

  Copyright (c) 2010-2018, Brian M. Clapper
  All rights reserved.

  See the accompanying license file for details.
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

import scala.util.{Failure, Success, Try}

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

    // Don't use the XML Scala stuff. It can gag on HTML entities.
    // Just build an HTML string.

    val htmlBody = "<body>\n" + parseToHTML(markupSource) + "\n</body>\n"

    val contentType = "text/html; charset=" + encoding

    val style = cssSource.map { s =>
      "<style type='text/css'>" + s.getLines().mkString("\n") + "</style>\n"
    }.getOrElse("")

    """<html>
      |<head>
      |<title>""".stripMargin + title + "</title>" +
    style +
    "<meta http-equiv='Content-Type' content='" + contentType + "'/>\n" +
    "</head>\n" +
    htmlBody +
     "</html>\n"
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
    *
    * @return the parser
    */
  @deprecated("Use converterFor", "1.2.0")
  def parserFor(parserType: MarkupType): MarkWrapParser = {
    converterFor(parserType)
  }

  /** Get a converter (parser) for the specified type.
    *
    * @param parserType  the parser type
    */
  def converterFor(parserType: MarkupType): MarkWrapParser = parserType match {
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
  @deprecated("Use converterFor", "1.2.0")
  def parserFor(mimeType: String): MarkWrapParser = converterFor(mimeType).get

  /** Get a converter (parser) for the specified MIME type.
    *
    * @param mimeType  the MIME type
    *
    * @return a `Success` containing the appropriate object, or a `Failure`
    *         if the MIME type isn't supported.
    *
    * @throws IllegalArgumentException unsupported MIME type
    */
  def converterFor(mimeType: String): Try[MarkWrapParser] = {
    def BadMimeType =
      new IllegalArgumentException(s"""Unsupported MIME type $mimeType""")

    MimeTypes
      .get(mimeType)
      .map { p => Success(converterFor(p)) }
      .getOrElse(Failure(new IllegalArgumentException(BadMimeType)))
  }

  /** Get a parser for the specified file.
    *
    * @param f  the file
    *
    * @return the parser.
    *
    * @throws IllegalArgumentException unsupported MIME type
    */
  @deprecated("Use converterFor", "1.2.0")
  def parserFor(f: File): MarkWrapParser = converterFor(f).get

  /** Get a converter (parser) for the specified file.
    *
    * @param f  the file
    *
    * @return a `Success` containing the appropriate object, or a `Failure`
    *         if the file type isn't supported.
    */
  def converterFor(f: File): Try[MarkWrapParser] = {
    converterFor(MimeTypeMap.getContentType(f))
  }
}
