package com.github.aalbul.irc.bot

import akka.actor.Actor
import java.util.Date
import com.github.aalbul.irc.domain.Messages._
import grizzled.slf4j.Logging
import com.github.aalbul.irc.client.IrcClient._

/**
 * Created by nuru on 08.01.14.
 *
 * Bot that contains core reactive functionality
 */
trait ReactiveBot extends Actor with Logging {

  /**
   * Contains function set to handle hooks like finger, ping, version, time, server ping e.t.c.
   * @param config - hooks config
   * @return configured hooks function
   */
  protected def coreHooks(config: CoreHooksConfig = CoreHooksConfig()): Receive = {
    finger(config.finger) orElse ping orElse serverPing orElse time orElse version(config.version)
  }

  protected def finger(message: String): Receive = {
    case Finger(user, _) =>
      trace("Sending 'FINGER' response to user: " + user.nick)
      sender ! SendFinger(user.nick, message)
  }

  protected def ping: Receive = {
    case Ping(user, _, value) =>
      trace("Sending 'PING' response to user: " + user.nick)
      sender ! SendPing(user.nick, value)
  }

  protected def serverPing: Receive = {
    case ServerPing(response) =>
      trace("Sending 'PONG' response to server")
      sender ! SendServerPong(response)
  }

  protected def time: Receive = {
    case Time(_, user) =>
      trace("Sending 'TIME' response to user: " + user.nick)
      sender ! SendTime(user.nick, new Date().toString)
  }

  protected def version(version: String): Receive = {
    case Version(_, user) =>
      trace("Sending 'VERSION' response to user: " + user.nick)
      sender ! SendVersion(user.nick, version)
  }

  case class CoreHooksConfig(finger: String = "", version: String = "1.0")
}
