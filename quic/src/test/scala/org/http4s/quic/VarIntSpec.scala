/*
 * Copyright 2014-2020 http4s.org
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package org.http4s.quic

import org.http4s.quic.VarIntType.{VarInt16, VarInt32, VarInt64, VarInt8}
import scodec.bits._
import org.specs2.mutable.Specification
import scodec.Attempt._
import scodec.{Codec, DecodeResult}

class VarIntSpec extends Specification {

  // Values pulled from https://tools.ietf.org/html/draft-ietf-quic-transport-27#section-16

  val codec = Codec[VarInt]

  "VarInt" should {
    "decode an 8-bit value" in {
      val bits = hex"25".bits
      val result = codec.decode(bits)
      result must_== successful(DecodeResult(VarInt(VarInt8, 37), BitVector.empty))
    }

    "decode an 16-bit value" in {
      val bits = hex"7bbd".bits
      val result = codec.decode(bits)
      result must_== successful(DecodeResult(VarInt(VarInt16, 15293), BitVector.empty))
    }

    "decode an 32-bit value" in {
      val bits = hex"9d7f3e7d".bits
      val result = codec.decode(bits)
      result must_== successful(DecodeResult(VarInt(VarInt32, 494878333), BitVector.empty))
    }

    "decode an 64-bit value" in {
      val bits = hex"c2197c5eff14e88c".bits
      val result = codec.decode(bits)
      result must_== successful(DecodeResult(VarInt(VarInt64, 151288809941952652L), BitVector.empty))
    }
  }

}
