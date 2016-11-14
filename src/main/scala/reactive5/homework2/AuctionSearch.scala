package reactive5.homework2

import akka.actor.{Actor, ActorRef}
import akka.event.LoggingReceive
import reactive5.homework2.AuctionSearch.{AddAuction, SearchAuction, SearchResult}

class AuctionSearch extends Actor {

  var map:Map[String, ActorRef] = Map()

  override def receive: Receive = LoggingReceive {
    case msg: AddAuction =>
      map = map + ((msg.title, msg.auction))
    case msg: SearchAuction =>
      val list: List[ActorRef] = map.filterKeys(_.contains(msg.query)).values.toList
      sender() ! SearchResult(msg.query, list)
  }
}

object AuctionSearch {

  case class AddAuction(title: String, auction:ActorRef)
  case class SearchAuction(query: String)
  case class SearchResult(query: String, auctions: List[ActorRef])

}