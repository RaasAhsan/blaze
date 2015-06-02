package org.http4s.blaze.channel.nio1

import java.nio.channels._
import java.net.SocketAddress

import org.http4s.blaze.channel._

import scala.util.control.NonFatal

abstract class NIO1ServerChannelFactory[Channel <: NetworkChannel](pool: SelectorLoopPool)
                extends ServerChannelFactory[Channel] {

  protected def doBind(address: SocketAddress): Channel

  protected def completeConnection(ch: Channel, loop: SelectorLoop): Boolean

  final protected def makeSelector: Selector = Selector.open()

  final protected def createServerChannel(channel: Channel): ServerChannel =
    new NIO1ServerChannel(channel, pool)

  override def bind(localAddress: SocketAddress): ServerChannel = {
    val c = doBind(localAddress)
    createServerChannel(c)
  }

  private class NIO1ServerChannel(val channel: Channel, pool: SelectorLoopPool) extends ServerChannel {

    type C = Channel

    override def close(): Unit = {
      pool.shutdown()
      super.close()
    }

    // The accept thread just accepts connections and pawns them off on the SelectorLoop pool
    final def run(): Unit = {
      while (channel.isOpen) {
        try {
          val p = pool.nextLoop()
          completeConnection(channel, p)
        } catch {
          case NonFatal(t) => logger.error(t)("Error accepting connection")
        }
      }
    }

  }
}
