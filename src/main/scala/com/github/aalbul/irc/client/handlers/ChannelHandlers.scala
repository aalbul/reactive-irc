package com.github.aalbul.irc.client.handlers

import akka.actor.Actor
import com.github.aalbul.irc.domain.Messages._
import scala.collection.JavaConversions._
import com.github.aalbul.irc.client.IrcClient._
import com.github.aalbul.irc.client.IrcClient
/**
 * Created by nuru on 06.01.14.
 *
 * Channel-related message handlers
 */
trait ChannelHandlers { self: IrcClient =>

  def channelHandlers: Actor.Receive = {
    case SendChannelMessage(channel, message, recipient) => recipient match {
      case Some(user) => dao.getChannel(channel).send().message(dao.getUser(user), message)
      case None => dao.getChannel(channel).send().message(message)
    }
    case ListChannels(min, max) => client.sendIRC().listChannels(List(min.map("-min " + _), max.map("-max " + _)).flatten.mkString(" "))
    case SendInvite(channel, user) => dao.getUser(user).send().invite(channel)
    case Ban(channel, hostMask) => dao.getChannel(channel).send().ban(hostMask)
    case UnBan(channel, hostMask) => dao.getChannel(channel).send().unBan(hostMask)
    case GiveOp(channel, user) => dao.getChannel(channel).send().op(dao.getUser(user))
    case DeOp(channel, user) => dao.getChannel(channel).send().deOp(dao.getUser(user))
    case SetPrivateChannel(channel) => dao.getChannel(channel).send().setChannelPrivate(null)
    case UnSetPrivateChannel(channel) => dao.getChannel(channel).send().removeChannelPrivate(null)
    case SetSecretChannel(channel) => dao.getChannel(channel).send().setSecret(null)
    case UnSetSecretChannel(channel) => dao.getChannel(channel).send().removeSecret(null)
    case SetInviteOnly(channel) => dao.getChannel(channel).send().setInviteOnly(null)
    case UnSetInviteOnly(channel) => dao.getChannel(channel).send().removeInviteOnly(null)
    case SetTopicProtection(channel) => dao.getChannel(channel).send().setTopicProtection(null)
    case UnSetTopicProtection(channel) => dao.getChannel(channel).send().removeTopicProtection(null)
    case SetNoExternalMessages(channel) => dao.getChannel(channel).send().setNoExternalMessages(null)
    case UnSetNoExternalMessages(channel) => dao.getChannel(channel).send().removeNoExternalMessages(null)
    case SetModerated(channel) => dao.getChannel(channel).send().setModerated(null)
    case UnSetModerated(channel) => dao.getChannel(channel).send().removeModerated(null)
    case SetChannelLimit(channel, limit) => dao.getChannel(channel).send().setChannelLimit(limit)
    case UnSetChannelLimit(channel) => dao.getChannel(channel).send().removeChannelLimit(null)
    case GiveVoice(channel, user) => dao.getChannel(channel).send().voice(dao.getUser(user))
    case DeVoice(channel, user) => dao.getChannel(channel).send().deVoice(dao.getUser(user))
    case SetChannelKey(channel, key) => dao.getChannel(channel).send().setChannelKey(key)
    case RemoveChannelKey(channel, key) => dao.getChannel(channel).send().removeChannelKey(key)
    case ReJoin(channel, key) => dao.getChannel(channel).send().cycle(key.getOrElse(""))
    case GiveHalfOp(channel, user) => dao.getChannel(channel).send().halfOp(dao.getUser(user))
    case DeHalfOp(channel, user) => dao.getChannel(channel).send().deHalfOp(dao.getUser(user))
    case GiveOwner(channel, user) => dao.getChannel(channel).send().owner(dao.getUser(user))
    case DeOwner(channel, user) => dao.getChannel(channel).send().deOwner(dao.getUser(user))
    case GiveSuperOp(channel, user) => dao.getChannel(channel).send().superOp(dao.getUser(user))
    case DeSuperOp(channel, user) => dao.getChannel(channel).send().deSuperOp(dao.getUser(user))
    case GetChannelMode(channel) => dao.getChannel(channel).send().getMode()
    case Kick(channel, user, reason) => dao.getChannel(channel).send().kick(dao.getUser(user), reason.getOrElse(""))
    case SendChannelNotice(channel, notice) => dao.getChannel(channel).send().notice(notice)
    case LeaveChannel(channel, reason) => reason match {
      case Some(string) => dao.getChannel(channel).send().part(string)
      case None => dao.getChannel(channel).send().part()
    }
    case SetChannelMode(channel, mode) => dao.getChannel(channel).send().setMode(mode)
    case SetTopic(channel, topic) => dao.getChannel(channel).send().setTopic(topic)
    case SendWho(channel) => dao.getChannel(channel).send().who()
    case SendChannelCtcp(channel, command) => dao.getChannel(channel).send().ctcpCommand(command)
    case GetChannelInfo(channel) =>
      val chan = dao.getChannel(channel)
      sender ! ChannelInfo(chan, chan.getHalfOps.toList, chan.getNormalUsers.toList, chan.getOps.toList,
        chan.getOwners.toList, chan.getSuperOps.toList, chan.getVoices.toList, chan.getUsers.toList)
    case JoinChannel(channel, key) => key match {
      case Some(string) => client.sendIRC().joinChannel(string, string)
      case None => client.sendIRC().joinChannel(channel)
    }
    case SendChannelAction(channel, action) => dao.getChannel(channel).send().action(action)
  }
}
