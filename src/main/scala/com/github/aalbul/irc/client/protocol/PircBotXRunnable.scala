package com.github.aalbul.irc.client.protocol

import org.pircbotx.PircBotX

/**
 * Created by nuru on 06.01.14.
 *
 * Thread that starts IRC client and listen for messages
 */
class PircBotXRunnable(client: PircBotX) extends Runnable {
  def run(): Unit = {
    try {
      client.startBot()
    } catch {
      case _:InterruptedException =>
        client.stopBotReconnect()
        Thread.currentThread().interrupt()
    }
  }
}
