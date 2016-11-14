package reactive5.homework2

import java.util.UUID
import java.util.concurrent.ThreadLocalRandom

import akka.actor.Status.Success
import akka.actor.{Actor, ActorSelection}
import akka.event.LoggingReceive
import reactive5.homework2.Notifier.Notify
import reactive5.homework2.auctionpublisher.PublisherServer
import reactive5.homework2.NotifierRequest.RandomException
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

class NotifierRequest(publisherServer: ActorSelection) extends Actor {

  implicit val timeout = Timeout(5 seconds)

  override def receive: Receive = LoggingReceive {
    case msg: Notify => {
      val msgSender = sender()
      if (ThreadLocalRandom.current().nextDouble() < 0.2) {
        val exId: String = UUID.randomUUID().toString
        throw RandomException(s"[Notifier Request] Random exception occurred - ${msg.title}, ${msg.price}, ${msg.buyer}, $exId",
          exId)
      } else {
        val result = publisherServer ? PublisherServer.PublisherNotification(msg.title, msg.buyer, msg.price)
        result.onSuccess { case _ => {
          println("[Notifier Request] Successfully published notification.")
          msgSender ! Success
        }
          result.onFailure { case e => throw new RuntimeException(e) }
        }
      }
      context.stop(self)
    }
  }
}

object NotifierRequest {

  case class RandomException(message: String, id:String) extends RuntimeException(message)

}