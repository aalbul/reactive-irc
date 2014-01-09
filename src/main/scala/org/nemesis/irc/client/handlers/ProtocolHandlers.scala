package org.nemesis.irc.client.handlers

import akka.actor.Actor
import org.nemesis.irc.client.protocol.ControlStatements._
import org.nemesis.irc.domain.Messages._
import scala.collection.JavaConversions._

/**
 * Created by nuru on 06.01.14.
 *
 * Protocol-related message handlers
 */
trait ProtocolHandlers extends Handlers {

  def protocolHandlers: Actor.Receive = {
    case GetEnabledCapabilities() => sender ! EnabledCapabilities(client.getEnabledCapabilities.toList)
    case GetServerInfo() =>
      val i = client.getServerInfo
      sender ! ServerInfo(i.getServerName, i.getServerVersion, i.getUserModes, i.getIsupportRaw.toMap, i.getPrefixes,
      i.getChannelTypes, i.getChannelModes, i.getChanlimit, i.getNetwork, i.getExceptBans, i.getExceptInvites,
      i.getInvites, i.getStatusMessage, i.getCaseMapping, i.getEList, i.getChannelIDLength, i.getStandard, i.getLanguage,
      i.getMotd, ServerInfoFlags(i.isWallOps, i.isWallVoices, i.isRFC2812, i.isPenalty, i.isForcedNickChanges, i.isSafeList,
      i.isNoQuit, i.isUserIPExists, i.isCPrivMsgExists, i.isCNoticeExists, i.isKnockExists, i.isVChannels, i.isWhoX,
      i.isCallerID, i.isAccept), ServerInfoStats(i.getMaxModes, i.getMaxChannels, i.getMaxNickLength, i.getMaxBans,
      i.getMaxList.toMap.map{ case(key, value) => key -> value.toInt }, i.getTopicLength, i.getKickLength, i.getChannelLength,
      i.getSilence, i.getAwayLength, i.getMaxTargets, i.getWatchMax, i.getHighestConnections, i.getHighestClients,
      i.getTotalUsers, i.getTotalInvisibleUsers, i.getTotalServers, i.getTotalOperatorsOnline, i.getTotalUnknownConnections,
      i.getTotalChannelsFormed, i.getServerUsers, i.getConnectedServers))
    case Disconnect(message) => client.sendIRC().quitServer(message.getOrElse(""))
    case SendServerPong(response) => client.sendRaw().rawLine("PONG " + response)
  }
}
