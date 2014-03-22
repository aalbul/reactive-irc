reactive-irc
============

Library that gives you a reactive way to write IRC bots in Scala

It uses latest version of PircBotX library (https://code.google.com/p/pircbotx/) and just provide convenient, actor-driven
way to write your bots.

Installation:
------------

Just add this to your SBT file:

```
libraryDependencies += "com.github.aalbul" %% "irc-actor" % "0.7.0"
```

For Maven:

``` xml
<dependency>
	<groupId>com.github.aalbul</groupId>
	<artifactId>irc-actor_2.10</artifactId>
	<version>0.7.0</version>
</dependency>
```

Usage: Writing bot.
------------

All you need to do is to create bot that will sub-class `ReactiveBot` trait.

``` scala
class EchoBot extends ReactiveBot {
  def receive: Receive = coreHooks() orElse {
    case PrivateMessage(user, message) => sender ! SendPrivateMessage(user.nick, "echo: " + message)
    case _@message => debug(message)
  }
}
```

Usage: Bootstrap
------------

To bootstrap your application you need to initialize second part: `IrcClient` like this:

``` scala
object Runner {
  def main(args: Array[String]) {
    val config = ClientConfig("FloodBot", "Irc Bot", "irc.freenode.net", 6667, Seq(ChannelConf("#floodBot")))

    val actorSystem = ActorSystem("irc-bot")
    val client = actorSystem.actorOf(Props[IrcClient], "irc-client")
    val bot = actorSystem.actorOf(Props[EchoBot], "actor-bot")

    client ! Initialize(config)
    client ! RegisterListener(bot)
  }
}
```