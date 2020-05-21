/*
 * Copyright 2014-2020 http4s.org
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package org.http4s.quic

//import scodec.Codec
//import scodec.codecs._

final case class Frame(frameType: VarInt)

object Frame {
//  implicit val codec: Codec[Frame] = {
//    ("frameType" | int8)
//  }.as[Frame]
}
