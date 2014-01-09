package org.nemesis.irc.domain

import org.pircbotx.{Channel, User}
import java.net.InetAddress

/**
 * Created by nuru on 08.01.14.
 */
object Messages {
  implicit def userToIrcUser(user: User): IrcUser = IrcUser(user)
  implicit def userToOptionIrcUser(user: User): Option[IrcUser] = Option(user).map(IrcUser(_))
  implicit def channelToIrcChannel(channel: Channel): IrcChannel = IrcChannel(channel)
  implicit def channelToOptionIrcChannel(channel: Channel): Option[IrcChannel] = Option(channel).map(IrcChannel(_))
  implicit def channelListToIrcChannelList(channels: List[Channel]): List[IrcChannel] = channels.map(IrcChannel(_))
  implicit def userListToIrcUserList(channels: List[User]): List[IrcUser] = channels.map(IrcUser(_))

  case class ChannelMessage(user: IrcUser, channel: IrcChannel, message: String)
  case class Action(user: IrcUser, channel: IrcChannel, action: String)
  case class ChannelListEntry(name: String, users: Int, topic: String)
  case class ChannelList(list: List[ChannelListEntry])
  case class Connected(host: String, port: Int)
  case class Disconnected(host: String, port: Int)
  case class Finger(user: IrcUser, channel: Option[IrcChannel])
  case class HalfOp(channel: IrcChannel, user: IrcUser, recipient: IrcUser, halfOp: Boolean)
  case class IncomingChatRequest(user: IrcUser, chatAddress: InetAddress, chatPort: Int, chatToken: String, passive: Boolean)
  case class IncomingFileTransfer(user: IrcUser, rawFileName: String, safeFileName: String, address: InetAddress, port: Int,
                                  fileSize: Long, transferToken: String, passive: Boolean)
  case class Invite(byUser: String, channel: String)
  case class Joined(user: IrcUser, channel: IrcChannel)
  case class Kicked(channel: IrcChannel, user: IrcUser, recipient: IrcUser, reason: String)
  case class Mode(channel: IrcChannel, user: Option[IrcUser], mode: String, parsedModes: List[String])
  case class Motd(motd: String)
  case class NickAlreadyInUse(usedNick: String, autoGeneratedNick: String, nickAutoChange: Boolean)
  case class NickChanged(oldNick: String, newNick: String, user: IrcUser)
  case class Notice(user: IrcUser, channel: Option[IrcChannel], notice: String)
  case class Op(channel: IrcChannel, user: IrcUser, recipient: IrcUser, op: Boolean)
  case class Owner(channel: IrcChannel, user: IrcUser, recipient: IrcUser, owner: Boolean)
  case class Part(channel: IrcChannel, user: IrcUser, reason: String)
  case class Ping(user: IrcUser, channel: Option[IrcChannel], pingValue: String)
  case class PrivateMessage(user: IrcUser, message: String)
  case class Quit(user: IrcUser, reason: String)
  case class ChannelBanRemoved(channel: IrcChannel, user: IrcUser, hostMask: String)
  case class ChannelKeyRemoved(channel: IrcChannel, user: IrcUser, key: String)
  case class ChannelLimitRemoved(channel: IrcChannel, user: IrcUser)
  case class InviteOnlyRemoved(channel: IrcChannel, user: IrcUser)
  case class ModeratedRemoved(channel: IrcChannel, user: IrcUser)
  case class NoExternalMessagesRemoved(channel: IrcChannel, user: IrcUser)
  case class PrivateRemoved(channel: IrcChannel, user: IrcUser)
  case class SecretRemoved(channel: IrcChannel, user: IrcUser)
  case class TopicProtectionRemoved(channel: IrcChannel, user: IrcUser)
  case class ServerPing(response: String)
  case class ServerResponse(code: Int, rawLine: String, parsedResponse: List[String])
  case class ChannelBanSet(channel: IrcChannel, user: IrcUser, hostMask: String)
  case class ChannelKeySet(channel: IrcChannel, user: IrcUser, key: String)
  case class ChannelLimitSet(channel: IrcChannel, user: IrcUser, limit: Int)
  case class InviteOnlySet(channel: IrcChannel, user: IrcUser)
  case class ModeratedSet(channel: IrcChannel, user: IrcUser)
  case class NoExternalMessagesSet(channel: IrcChannel, user: IrcUser)
  case class PrivateSet(channel: IrcChannel, user: IrcUser)
  case class SecretSet(channel: IrcChannel, user: IrcUser)
  case class TopicProtectionSet(channel: IrcChannel, user: IrcUser)
  case class SocketConnected()
  case class SuperOp(channel: IrcChannel, user: IrcUser, recipient: IrcUser, superOp: Boolean)
  case class Time(channel: Option[IrcChannel], user: IrcUser)
  case class TopicSet(channel: IrcChannel, oldTopic: String, topic: String, user: IrcUser, changed: Boolean, date: Long)
  case class Unknown(line: String)
  case class UserList(channel: IrcChannel, users: List[IrcUser])
  case class UserMode(user: IrcUser, recipient: IrcUser, mode: String)
  case class Version(channel: Option[IrcChannel], user: IrcUser)
  case class Voice(channel: IrcChannel, user: IrcUser, recipient: IrcUser, hasVoice: Boolean)
  case class Whois(nick: String, login: String, hostName: String, realName: String, channels: List[String], server: String,
                   serverInfo: String, idleSeconds: Long, signedOnTime: Long, registeredAs: String)
  case class ChannelInfo(channel: IrcChannel, halfOps: List[IrcUser], normalUsers: List[IrcUser], ops: List[IrcUser],
                         owners: List[IrcUser], superOps: List[IrcUser], voices: List[IrcUser], allUsers: List[IrcUser])
  case class UserInfo(user: IrcUser, allChannels: List[IrcChannel], channelsHalfOpIn: List[IrcChannel],
                      channelsOpIn: List[IrcChannel], channelsOwnerIn: List[IrcChannel], channelsVoiceIn: List[IrcChannel],
                      channelsSuperOpIn: List[IrcChannel])
  case class EnabledCapabilities(list: List[String])
  case class ServerInfo(name: String, version: String, userModes: String, supportRaw: Map[String, String], prefixes: String,
                        channelTypes: String, channelModes: String, chanLimit: String, network: String, exceptBans: String,
                        exceptInvites: String, invites: String, statusMessage: String, caseMapping: String, eList: String,
                        channelIDLength: String, standard: String, language: String, motd: String, flags: ServerInfoFlags,
                        stats: ServerInfoStats)
  case class ServerInfoFlags(wallOps: Boolean, wallVoices: Boolean, RFC2812: Boolean, penalty: Boolean,
                             forcedNickChanges: Boolean, safeList: Boolean, noQuit: Boolean, userIPExists: Boolean,
                             cPrivMsgExists: Boolean, cNoticeExists: Boolean, knockExists: Boolean, vChannels: Boolean,
                             whoX: Boolean, callerID: Boolean, accept: Boolean)
  case class ServerInfoStats(maxModes: Int, maxChannels: Int, maxNickLength: Int, maxBans: Int, maxList: Map[String, Int],
                             topicLength: Int, kickLength: Int, channelLength: Int, silence: Int, awayLength: Int, maxTargets: Int,
                             watchMax: Int, highestConnections: Int, highestClients: Int, totalUsers: Int, totalInvisibleUsers: Int,
                             totalServers: Int, totalOperatorsOnline: Int, totalUnknownConnections: Int, totalChannelsFormed: Int,
                             serverUsers: Int, connectedServers: Int)
}
