reactive-irc
============

Library that gives you a reactive way to write IRC bots in Scala

It uses latest version of PircBotX library (https://code.google.com/p/pircbotx/) and just provide convenient, actor-driven
way to write your bots.

Installation:
------------

Unfortunately, we didn't upload this project to central maven repository yet so you have to checkout and complete project
by yourself.

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