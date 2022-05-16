package com.joshcough.trollabot

import cats.effect.IO
import doobie.Transactor

case class Chatbot(xa: Transactor[IO]) {

  val trollabotDb: TrollabotDb = TrollabotDb(xa)
  val db: TrollabotDbIO = TrollabotDbIO(trollabotDb)
  val commands: Commands = Commands(trollabotDb)

  val irc: Irc = Irc(chatMessage => {
    val responses = commands.findAndRun(chatMessage)
    responses.foreach {
      case RespondWith(s)   => irc.privMsg(chatMessage.channel.name, s)
      case Join(newChannel) => join(newChannel)
      case Part             => irc.part(chatMessage.channel.name)
    }
  })

  def join(streamName: String): Unit = {
    irc.join(streamName)
    irc.privMsg(streamName, s"Hola mi hombres muy estupido!")
  }

  def run(): Unit = {
    irc.login()
    val streams = db.getJoinedStreams

    println("Joining these streams: " + streams)
    streams.foreach(s => join(s.name))
    irc.processMessages()
    println("Done processing messages, shutting down.")
  }

  def close(): Unit = {
    irc.close()
    println("Trollabot shutting down!")
  }
}
