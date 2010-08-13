/*
  ---------------------------------------------------------------------------
  This software is released under a BSD license, adapted from
  http://opensource.org/licenses/bsd-license.php

  Copyright (c) 2010 Brian M. Clapper. All rights reserved.

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

import org.scalatest.FunSuite
import org.clapper.markwrap._
import java.io.File

/**
 * Tests the grizzled.parsing.markup Markdown functions.
 */
class MarkWrapTest extends FunSuite
{
    test("MarkWrap.parserFor")
    {
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

        val mtData = List(
            (MarkupType.Markdown,  "text/markdown"),
            (MarkupType.Textile,   "text/textile"),
            (MarkupType.XHTML,     "text/html"),
            (MarkupType.XHTML,     "text/xhtml"),
            (MarkupType.PlainText, "text/plain")
        )

        val typeData = List(
            MarkupType.Markdown,
            MarkupType.Textile,
            MarkupType.XHTML,
            MarkupType.XHTML,
            MarkupType.PlainText
        )

        for ((expected, fn) <- fData)
        {
            expect(expected, "MarkWrap.parserFor(new File(\"" + fn + "\"))")
            {
                MarkWrap.parserFor(new File(fn)).markupType
            }
        }

        for ((expected, mimeType) <- mtData)
        {
            expect(expected, "MarkWrap.parserFor(\"" + mimeType + "\")")
            {
                MarkWrap.parserFor(mimeType).markupType
            }
        }

        for (parserType <- typeData)
        {
            expect(parserType, "MarkWrap.parserFor(" + parserType + ")")
            {
                MarkWrap.parserFor(parserType).markupType
            }
        }
    }
}
