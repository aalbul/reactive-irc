package com.github.aalbul.irc.client.protocol

/**
 * Created by nuru on 08.01.14.
 *
 * Control statement case classes
 */
trait ControlStatements {
  case class SendPrivateMessage(user: String, message: String)
  case class SendChannelMessage(channel: String, message: String, recipient: Option[String] = None)
  case class ListChannels(min: Option[Int] = None, max: Option[Int] = None)
  case class SendInvite(channel: String, user: String)
  case class ChangeNick(newNick: String)
  case class Ban(channel: String, hostMask: String)
  case class UnBan(channel: String, hostMask: String)
  case class GiveOp(channel: String, user: String)
  case class DeOp(channel: String, user: String)
  case class SetPrivateChannel(channel: String)
  case class UnSetPrivateChannel(channel: String)
  case class SetSecretChannel(channel: String)
  case class UnSetSecretChannel(channel: String)
  case class SetInviteOnly(channel: String)
  case class UnSetInviteOnly(channel: String)
  case class SetTopicProtection(channel: String)
  case class UnSetTopicProtection(channel: String)
  case class SetNoExternalMessages(channel: String)
  case class UnSetNoExternalMessages(channel: String)
  case class SetModerated(channel: String)
  case class UnSetModerated(channel: String)
  case class SetChannelLimit(channel: String, limit: Int)
  case class UnSetChannelLimit(channel: String)
  case class GiveVoice(channel: String, user: String)
  case class DeVoice(channel: String, user: String)
  case class SetChannelKey(channel: String, key: String)
  case class RemoveChannelKey(channel: String, key: String)
  case class ReJoin(channel: String, key: Option[String])
  case class GiveHalfOp(channel: String, user: String)
  case class DeHalfOp(channel: String, user: String)
  case class GiveOwner(channel: String, user: String)
  case class DeOwner(channel: String, user: String)
  case class GiveSuperOp(channel: String, user: String)
  case class DeSuperOp(channel: String, user: String)
  case class GetChannelMode(channel: String)
  case class Kick(channel: String, user: String, reason: Option[String])
  case class SendChannelNotice(channel: String, notice: String)
  case class LeaveChannel(channel: String, reason: Option[String])
  case class SetChannelMode(channel: String, mode: String)
  case class SetTopic(channel: String, topic: String)
  case class SendWho(channel: String)
  case class SendChannelCtcp(channel: String, command: String)
  case class GetChannelInfo(channel: String)
  case class JoinChannel(channel: String, key: Option[String])
  case class GetUserInfo(user: String)
  case class SendChannelAction(channel: String, action: String)
  case class SendUserAction(user: String, action: String)
  case class SendUserCtcp(user: String, command: String)
  case class SendUserMode(user: String, mode: String)
  case class SendUserNotice(user: String, notice: String)
  case class GetEnabledCapabilities()
  case class GetServerInfo()
  case class Disconnect(message: Option[String])
  case class SendFinger(user: String, message: String)
  case class SendPing(user: String, value: String)
  case class SendServerPong(response: String)
  case class SendTime(user: String, time: String)
  case class SendVersion(user: String, version: String)
}
