package org.nemesis.irc.client.handlers

import akka.actor.Actor
import org.nemesis.irc.client.protocol.ControlStatements._
import org.nemesis.irc.domain.Messages._
import scala.collection.JavaConversions._

/**
 * Created by nuru on 06.01.14.
 *
 * User-related message handlers
 */
trait UserHandlers extends Handlers {

  def userHandlers: Actor.Receive = {
    case SendPrivateMessage(user, message) => dao.getUser(user).send().message(message)
    case ChangeNick(newNick) => client.sendIRC().changeNick(newNick)
    case GetUserInfo(user) =>
      val u = dao.getUser(user)
      sender ! UserInfo(u, u.getChannels.toList, u.getChannelsHalfOpIn.toList, u.getChannelsOpIn.toList,
        u.getChannelsOwnerIn.toList, u.getChannelsVoiceIn.toList, u.getChannelsSuperOpIn.toList)
    case SendUserAction(user, action) => dao.getUser(user).send().action(action)
    case SendUserCtcp(user, command) => dao.getUser(user).send().ctcpCommand(command)
    case SendUserMode(user, mode) => dao.getUser(user).send().mode(mode)
    case SendUserNotice(user, notice) => dao.getUser(user).send().notice(notice)
    case SendFinger(user, message) => dao.getUser(user).send().ctcpResponse("FINGER " + message)
    case SendPing(user, value) => dao.getUser(user).send().ctcpResponse("PING " + value)
    case SendTime(user, time) => dao.getUser(user).send().ctcpResponse("TIME " + time)
    case SendVersion(user, version) => dao.getUser(user).send().ctcpResponse("VERSION " + version)
  }
}
