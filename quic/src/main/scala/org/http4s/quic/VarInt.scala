/*
 * Copyright 2014-2020 http4s.org
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package org.http4s.quic

import scodec.Codec
import scodec.codecs._

final case class VarInt(encoding: VarIntType, value: Long)

object VarInt {

  implicit val codec: Codec[VarInt] = Codec[VarIntType].flatPrepend { vtype =>
    ulong(vtype.bits.toInt).hlist
  }.as[VarInt]

}
