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

import java.util

import com.vladsch.flexmark.ext.definition.DefinitionExtension
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughSubscriptExtension

import scala.io.Source
import scala.collection.JavaConverters._
import com.vladsch.flexmark.ext.tables.TablesExtension
import com.vladsch.flexmark.html.HtmlRenderer
import com.vladsch.flexmark.parser.Parser
import com.vladsch.flexmark.util.options.MutableDataSet
import grizzled.io.SourceReader

/**
  * The `MarkdownParser` class parses the Markdown markup language,
  * producing HTML. The current implementation uses the Java-based Pegdown
  * parser with the following extensions enabled:
  *
  * - Definition lists <https://michelf.ca/projects/php-markdown/extra/#def-list>
  * - Fenced code blocks <https://michelf.ca/projects/php-markdown/extra/#fenced-code-blocks>
  * - Tables <http://fletcher.github.io/MultiMarkdown-5/tables.html>
  */
private[markwrap] class MarkdownParser extends MarkWrapParser {
  val markupType = MarkupType.Markdown

  type JBool = java.lang.Boolean

  private def jBool(v: Boolean) = new JBool(v)

  private val Extensions = util.Arrays.asList(
    TablesExtension.create,
    DefinitionExtension.create,
    StrikethroughSubscriptExtension.create
  )

  private val (parser, renderer) = {
    val options = new MutableDataSet()
      .set(TablesExtension.COLUMN_SPANS, jBool(false))
      .set(TablesExtension.APPEND_MISSING_COLUMNS, jBool(true))
      .set(TablesExtension.DISCARD_EXTRA_COLUMNS, jBool(true))
      .set(TablesExtension.HEADER_SEPARATOR_COLUMN_MATCH, jBool(true))
      .set(Parser.EXTENSIONS, Extensions)

    (Parser.builder(options).build, HtmlRenderer.builder(options).build)
  }

  /** Parse a Markdown document, producing HTML. The generated HTML markup
    * does not contain HTML or BODY tags, so it is suitable for embedding in
    * existing HTML documents.
    *
    * @param source  The `Source` from which to read the lines of Markdown
    *
    * @return the formatted HTML
    */
  def parseToHTML(source: Source): String = {
    val document = parser.parseReader(SourceReader(source))
    renderer.render(document)
  }
}
