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

import org.scalatest.{FlatSpec, Matchers, fixture}

trait BaseFixtureSpec extends fixture.FlatSpec with Matchers
trait BaseSpec extends FlatSpec with Matchers
