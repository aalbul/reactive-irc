package org.nemesis.irc.domain

import java.util.UUID
import org.pircbotx.User

/**
 * Created by nuru on 04.01.14.
 */
case class IrcUser(id: UUID, nick: String, name: String, login: String, hostMask: String, awayMessage: String, server: String)

object IrcUser {
  def apply(user: User): IrcUser = new IrcUser(
    user.getUserId, user.getNick, user.getRealName, user.getLogin, user.getHostmask, user.getAwayMessage, user.getServer
  )
}