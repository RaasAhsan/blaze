/*
 * Copyright 2014-2020 http4s.org
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package org.http4s.quic

import scodec.Codec
import scodec.bits._

object Main extends App {

  val bytes = hex"25"
  println(bytes.bits.toBin)

  val decoded = Codec[VarInt].decode(bytes.bits)

  println(decoded)

}
