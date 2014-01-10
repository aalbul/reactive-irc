package com.github.aalbul.irc.client.handlers

import org.pircbotx.{Channel, User, UserChannelDao, PircBotX}
import akka.actor.Actor

/**
 * Created by nuru on 06.01.14.
 */
trait Handlers extends Actor {
  val client: PircBotX
  val dao: UserChannelDao[User, Channel]
}
