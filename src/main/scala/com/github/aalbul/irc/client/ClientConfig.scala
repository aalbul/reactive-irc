package com.github.aalbul.irc.client

/**
 * Created by nuru on 04.01.14.
 */
case class ClientConfig(name: String,
                        realName: String,
                        host: String,
                        port: Int = 6667,
                        channels: Seq[ChannelConf])
case class ChannelConf(name: String, key: Option[String] = None)