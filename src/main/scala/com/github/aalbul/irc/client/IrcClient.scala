package com.github.aalbul.irc.client

import akka.actor.{ActorRef, Actor}
import org.pircbotx._
import com.github.aalbul.irc.client.protocol.{ControlStatements, PircBotXRunnable, DelegatingListener}
import grizzled.slf4j.Logging
import com.github.aalbul.irc.client.handlers.{ProtocolHandlers, UserHandlers, ChannelHandlers}
import scala.collection.JavaConversions._
import org.pircbotx.hooks.CoreHooks
import com.github.aalbul.irc.client.IrcClient.RegisterListener
import com.github.aalbul.irc.client.IrcClient.Initialize
import com.github.aalbul.irc.client.IrcClient.UnregisterListener
import scala.Some

/**
 * Created by nuru on 04.01.14.
 *
 * Actor that is responsible for PircBotX client supervision
 */
object IrcClient extends ControlStatements {
  case class RegisterListener(listener: ActorRef)
  case class UnregisterListener(listener: ActorRef)
  case class Initialize(config: ClientConfig)
}

class IrcClient() extends Actor with ChannelHandlers with UserHandlers with ProtocolHandlers
  with Logging {

  private var handlers: Set[ActorRef] = Set()
  private var internalClient: Option[InternalClient] = None

  protected def dao = internalClient.get.dao
  protected def client = internalClient.get.client

  def receive: Receive = channelHandlers orElse userHandlers orElse protocolHandlers orElse {
    case RegisterListener(listener) => handlers = handlers + listener
    case UnregisterListener(listener) => handlers = handlers - listener
    case Initialize(config) => initialize(config)
  }

  /**
   * Initialize bot connection
   * @param config - configuration instance
   */
  private def initialize(config: ClientConfig) {
    debug("Initializing new session.")
    internalClient.map { cl =>
      debug("Previous session exists. Disconnecting and cleaning up resources.")
      cl.client.sendIRC().quitServer()
      cl.runnable.interrupt()
    }
    val cl = new PircBotX(buildConfiguration(config))
    val runnable = new Thread(new PircBotXRunnable(cl))
    internalClient = Some(new InternalClient(runnable, cl, cl.getUserChannelDao))
    runnable.start()
  }

  /**
   * Build PircX configuration
   * @param config - external configuration instance
   * @return PircX configuration instance
   */
  private def buildConfiguration(config: ClientConfig) = {
    val callback: Any => Unit = { message => handlers.foreach(_ ! message) }

    val configurationBuilder = new Configuration.Builder()
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

    configurationBuilder.buildConfiguration()
  }
}

protected class InternalClient(val runnable: Thread, val client: PircBotX, val dao: UserChannelDao[User, Channel])