/*
 * Copyright 2014-2020 http4s.org
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package org.http4s.quic

import scodec.Codec
import scodec.codecs._

sealed abstract class VarIntType(val bits: Long)

object VarIntType {
  case object VarInt8 extends VarIntType(6)
  case object VarInt16 extends VarIntType(14)
  case object VarInt32 extends VarIntType(30)
  case object VarInt64 extends VarIntType(62)

  implicit val codec: Codec[VarIntType] = discriminated[VarIntType].by(uint2L)
    .typecase(0, provide(VarInt8))
    .typecase(1, provide(VarInt16))
    .typecase(2, provide(VarInt32))
    .typecase(3, provide(VarInt64))
}
