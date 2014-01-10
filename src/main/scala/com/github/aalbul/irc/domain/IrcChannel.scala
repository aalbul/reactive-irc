package com.github.aalbul.irc.domain

import java.util.UUID
import org.pircbotx.Channel

/**
 * Created by nuru on 04.01.14.
 */
case class IrcChannel(id: UUID, name: String, mode: String, topic: String, topicTimestamp: Long, createdTimestamp: Long,
                      topicSetter: String, moderated: Boolean, noExternalMessages: Boolean, inviteOnly: Boolean,
                      secret: Boolean, channelPrivate: Boolean, topicProtection: Boolean, channelLimit: Int,
                      channelKey: String)

object IrcChannel {
  def apply(channel: Channel): IrcChannel = new IrcChannel(
    channel.getChannelId, channel.getName, channel.getMode, channel.getTopic, channel.getTopicTimestamp,
    channel.getCreateTimestamp, channel.getTopicSetter, channel.isModerated, channel.isNoExternalMessages,
    channel.isInviteOnly, channel.isSecret, channel.isChannelPrivate, channel.hasTopicProtection,
    channel.getChannelLimit, channel.getChannelKey
  )
}