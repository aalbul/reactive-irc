package org.nemesis.irc.client

import akka.actor.Actor
import org.pircbotx.{PircBotX, Configuration}
import org.nemesis.irc.client.protocol.{PircBotXRunnable, DelegatingListener}
import grizzled.slf4j.Logging
import org.nemesis.irc.client.handlers.{ProtocolHandlers, UserHandlers, ChannelHandlers}
import scala.collection.JavaConversions._
import org.pircbotx.hooks.CoreHooks

/**
 * Created by nuru on 04.01.14.
 */
class IrcClient(config: ClientConfig, botPath: String) extends Actor with ChannelHandlers with UserHandlers
  with ProtocolHandlers with Logging {

  private val bot = context.actorSelection(botPath)

  val callback: Any => Unit = { message => bot ! message }
  private val configurationBuilder = new Configuration.Builder()
    .setName(config.name)
    .setLogin(config.name)
    .setRealName(config.realName)
    .setAutoNickChange(true)
    .setServerHostname(config.host)
    .setServerPort(config.port)
    .addListener(new DelegatingListener(callback))
    .setCapEnabled(true)

  config.channels.foreach { channel => channel.key match {
      case Some(channelKey) => configurationBuilder.addAutoJoinChannel(channel.name, channelKey)
      case None => configurationBuilder.addAutoJoinChannel(channel.name)
    }
  }

  //Removing core hooks
  configurationBuilder
    .getListenerManager
    .removeListener(configurationBuilder.getListenerManager.getListeners.find(_.isInstanceOf[CoreHooks]).get)

  val client = new PircBotX(configurationBuilder.buildConfiguration())
  val dao = client.getUserChannelDao

  var runnable: Option[Thread] = None

  override def preStart(): Unit = {
    runnable = Some(new Thread(new PircBotXRunnable(client, callback)))
    runnable.get.start()
  }

  def receive: Receive = channelHandlers orElse userHandlers orElse protocolHandlers
}