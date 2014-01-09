package org.nemesis.irc.client.protocol

import org.pircbotx.hooks.ListenerAdapter
import org.pircbotx.PircBotX
import org.pircbotx.hooks.events._
import scala.collection.JavaConversions._
import org.nemesis.irc.domain.Messages._
import org.nemesis.irc.domain._

/**
 * Created by nuru on 04.01.14.
 * Listener that converts events to Scala-friendly messages and delegates them to the underlying client.
 */
class DelegatingListener(callback: AnyRef => Unit) extends ListenerAdapter[PircBotX] {
  override def onMessage(event: MessageEvent[PircBotX]): Unit = {
    callback(ChannelMessage(event.getUser, event.getChannel, event.getMessage))
  }

  override def onAction(event: ActionEvent[PircBotX]): Unit = {
    callback(Action(event.getUser, event.getChannel, event.getAction))
  }

  override def onChannelInfo(event: ChannelInfoEvent[PircBotX]): Unit = {
    val entries = event.getList.map(e => ChannelListEntry(e.getName, e.getUsers, e.getTopic)).toList
    callback(ChannelList(entries))
  }

  override def onConnect(event: ConnectEvent[PircBotX]): Unit = {
    val botConfig = event.getBot.getConfiguration
    callback(Connected(botConfig.getServerHostname, botConfig.getServerPort))
  }

  override def onDisconnect(event: DisconnectEvent[PircBotX]): Unit = {
    val botConfig = event.getBot.getConfiguration
    callback(Disconnected(botConfig.getServerHostname, botConfig.getServerPort))
  }

  override def onFinger(event: FingerEvent[PircBotX]): Unit = {
    callback(Finger(event.getUser, event.getChannel))
  }

  override def onHalfOp(event: HalfOpEvent[PircBotX]): Unit = {
    callback(HalfOp(event.getChannel, event.getUser, event.getRecipient, event.isHalfOp))
  }

  override def onIncomingChatRequest(event: IncomingChatRequestEvent[PircBotX]): Unit = {
    callback(
      IncomingChatRequest(
        event.getUser, event.getChatAddress, event.getChatPort, event.getChatToken, event.isPassive
      )
    )
  }

  override def onIncomingFileTransfer(event: IncomingFileTransferEvent[PircBotX]): Unit = {
    callback(
      IncomingFileTransfer(
        event.getUser, event.getRawFilename, event.getSafeFilename, event.getAddress, event.getPort,
          event.getFilesize, event.getTransferToken, event.isPassive
      )
    )
  }

  override def onInvite(event: InviteEvent[PircBotX]): Unit = {
    callback(Invite(event.getUser, event.getChannel))
  }

  override def onJoin(event: JoinEvent[PircBotX]): Unit = {
    callback(Joined(event.getUser, event.getChannel))
  }

  override def onKick(event: KickEvent[PircBotX]): Unit = {
    callback(Kicked(event.getChannel, event.getUser, event.getRecipient, event.getReason))
  }

  override def onMode(event: ModeEvent[PircBotX]): Unit = {
    callback(Mode(event.getChannel, event.getUser, event.getMode, event.getModeParsed.toList))
  }

  override def onMotd(event: MotdEvent[PircBotX]): Unit = {
    callback(Motd(event.getMotd))
  }

  override def onNickAlreadyInUse(event: NickAlreadyInUseEvent[PircBotX]): Unit = {
    callback(NickAlreadyInUse(event.getUsedNick, event.getAutoNewNick, event.isAutoNickChange))
  }

  override def onNickChange(event: NickChangeEvent[PircBotX]): Unit = {
    callback(NickChanged(event.getOldNick, event.getNewNick, event.getUser))
  }

  override def onNotice(event: NoticeEvent[PircBotX]): Unit = {
    callback(Notice(event.getUser, event.getChannel, event.getNotice))
  }

  override def onOp(event: OpEvent[PircBotX]): Unit = {
    callback(Op(event.getChannel, event.getUser, event.getRecipient, event.isOp))
  }

  override def onOwner(event: OwnerEvent[PircBotX]): Unit = {
    callback(Owner(event.getChannel, event.getUser, event.getRecipient, event.isOwner))
  }

  override def onPart(event: PartEvent[PircBotX]): Unit = {
    callback(Part(event.getChannel.getGeneratedFrom, event.getUser.getGeneratedFrom, event.getReason))
  }

  override def onPing(event: PingEvent[PircBotX]): Unit = {
    callback(Ping(event.getUser, event.getChannel, event.getPingValue))
  }

  override def onPrivateMessage(event: PrivateMessageEvent[PircBotX]): Unit = {
    callback(PrivateMessage(event.getUser, event.getMessage))
  }

  override def onQuit(event: QuitEvent[PircBotX]): Unit = {
    callback(Quit(event.getUser, event.getReason))
  }

  override def onRemoveChannelBan(event: RemoveChannelBanEvent[PircBotX]): Unit = {
    callback(ChannelBanRemoved(event.getChannel, event.getUser, event.getHostmask))
  }

  override def onRemoveChannelKey(event: RemoveChannelKeyEvent[PircBotX]): Unit = {
    callback(ChannelKeyRemoved(event.getChannel, event.getUser, event.getKey))
  }

  override def onRemoveChannelLimit(event: RemoveChannelLimitEvent[PircBotX]): Unit = {
    callback(ChannelLimitRemoved(event.getChannel, event.getUser))
  }

  override def onRemoveInviteOnly(event: RemoveInviteOnlyEvent[PircBotX]): Unit = {
    callback(InviteOnlyRemoved(event.getChannel, event.getUser))
  }

  override def onRemoveModerated(event: RemoveModeratedEvent[PircBotX]): Unit = {
    callback(ModeratedRemoved(event.getChannel, event.getUser))
  }

  override def onRemoveNoExternalMessages(event: RemoveNoExternalMessagesEvent[PircBotX]): Unit = {
    callback(NoExternalMessagesRemoved(event.getChannel, event.getUser))
  }

  override def onRemovePrivate(event: RemovePrivateEvent[PircBotX]): Unit = {
    callback(PrivateRemoved(event.getChannel, event.getUser))
  }

  override def onRemoveSecret(event: RemoveSecretEvent[PircBotX]): Unit = {
    callback(SecretRemoved(event.getChannel, event.getUser))
  }

  override def onRemoveTopicProtection(event: RemoveTopicProtectionEvent[PircBotX]): Unit = {
    callback(TopicProtectionRemoved(event.getChannel, event.getUser))
  }

  override def onServerPing(event: ServerPingEvent[PircBotX]): Unit = {
    callback(ServerPing(event.getResponse))
  }

  override def onServerResponse(event: ServerResponseEvent[PircBotX]): Unit = {
    callback(ServerResponse(event.getCode, event.getRawLine, event.getParsedResponse.toList))
  }

  override def onSetChannelBan(event: SetChannelBanEvent[PircBotX]): Unit = {
    callback(ChannelBanSet(event.getChannel, event.getUser, event.getHostmask))
  }

  override def onSetChannelKey(event: SetChannelKeyEvent[PircBotX]): Unit = {
    callback(ChannelKeySet(event.getChannel, event.getUser, event.getKey))
  }

  override def onSetChannelLimit(event: SetChannelLimitEvent[PircBotX]): Unit = {
    callback(ChannelLimitSet(event.getChannel, event.getUser, event.getLimit))
  }

  override def onSetInviteOnly(event: SetInviteOnlyEvent[PircBotX]): Unit = {
    callback(InviteOnlySet(event.getChannel, event.getUser))
  }

  override def onSetModerated(event: SetModeratedEvent[PircBotX]): Unit = {
    callback(ModeratedSet(event.getChannel, event.getUser))
  }

  override def onSetNoExternalMessages(event: SetNoExternalMessagesEvent[PircBotX]): Unit = {
    callback(NoExternalMessagesSet(event.getChannel, event.getUser))
  }

  override def onSetPrivate(event: SetPrivateEvent[PircBotX]): Unit = {
    callback(PrivateSet(event.getChannel, event.getUser))
  }

  override def onSetSecret(event: SetSecretEvent[PircBotX]): Unit = {
    callback(SecretSet(event.getChannel, event.getUser))
  }

  override def onSetTopicProtection(event: SetTopicProtectionEvent[PircBotX]): Unit = {
    callback(TopicProtectionSet(event.getChannel, event.getUser))
  }

  override def onSocketConnect(event: SocketConnectEvent[PircBotX]): Unit = {
    callback(SocketConnected())
  }

  override def onSuperOp(event: SuperOpEvent[PircBotX]): Unit = {
    callback(SuperOp(event.getChannel, event.getUser, event.getRecipient, event.isSuperOp))
  }

  override def onTime(event: TimeEvent[PircBotX]): Unit = {
    callback(Time(event.getChannel, event.getUser))
  }

  override def onTopic(event: TopicEvent[PircBotX]): Unit = {
    callback(TopicSet(event.getChannel, event.getOldTopic, event.getTopic, event.getUser, event.isChanged, event.getDate))
  }

  override def onUnknown(event: UnknownEvent[PircBotX]): Unit = {
    callback(Unknown(event.getLine))
  }

  override def onUserList(event: UserListEvent[PircBotX]): Unit = {
    val users = event.getUsers.map(IrcUser(_)).toList
    callback(UserList(event.getChannel, users))
  }

  override def onUserMode(event: UserModeEvent[PircBotX]): Unit = {
    callback(UserMode(event.getUser, event.getRecipient, event.getMode))
  }

  override def onVersion(event: VersionEvent[PircBotX]): Unit = {
    callback(Version(event.getChannel, event.getUser))
  }

  override def onVoice(event: VoiceEvent[PircBotX]): Unit = {
    callback(Voice(event.getChannel, event.getUser, event.getRecipient, event.hasVoice))
  }

  override def onWhois(event: WhoisEvent[PircBotX]): Unit = {
    callback(Whois(event.getNick, event.getLogin, event.getHostname, event.getRealname, event.getChannels.toList,
      event.getServer, event.getServerInfo, event.getIdleSeconds, event.getSignOnTime, event.getRegisteredAs))
  }
}
