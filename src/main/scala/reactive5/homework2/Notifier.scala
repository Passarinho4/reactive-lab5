package reactive5.homework2

import akka.actor.SupervisorStrategy.{Escalate, Restart}
import akka.actor.{Actor, ActorRef, ActorSelection, OneForOneStrategy, Props, SupervisorStrategy}
import akka.event.LoggingReceive
import reactive5.homework2.Notifier.Notify
import reactive5.homework2.NotifierRequest.RandomException

class Notifier extends Actor {

  private val publisherServer: ActorSelection = context.actorSelection("akka.tcp://AuctionPublisherSystem@127.0.0.1:2556/user/publisherServer")

  override def supervisorStrategy: SupervisorStrategy = OneForOneStrategy(loggingEnabled = true) {
    case e:RandomException =>
      println(s"[Notifier] - RandomException occured - retry [${e.id}]")
      Restart
    case _ => Escalate
  }

  override def receive: Receive = LoggingReceive {
    case msg:Notify =>
      val request: ActorRef = context.actorOf(Props(new NotifierRequest(publisherServer)))
      request ! msg
  }

}

object Notifier {
  case class Notify(title: String, buyer: ActorRef, price: BigDecimal)
}
