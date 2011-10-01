
package code
package snippet

import net.liftweb.{common, http, util}
import common.{Loggable}
import util.{Helpers}
import scala.xml.{NodeSeq}
import http.{js,StatefulSnippet}
import js._
import JsCmds._
import JE._
import Helpers._

import up._
import HList._
import KList._
import ~>._

import scala.math.{BigDecimal}
import lib.magicform._
import lib.formats._

import model.derivatives._

class SellDerivative extends StatefulSnippet with Loggable {
    
    def dispatch = {
        case "render" => render
    }
    
    case class Order(
        to:         To,
        securities: Seq[Security],
        on:         String,
        condition:  Condition
    )
    object Order {
        def fromHList(
            hl: To:+:Seq[Security]:+:String:+:Condition:+:HNil
        ): Order = hl match {
            case t:+:s:+:o:+:c:+:HNil => Order(t, s, o, c)
        }
    }
    
    case class Security(
        ticker: String,
        shares: BigDecimal
    )
    object Security {
        def fromHList(hl: String :+: BigDecimal :+: HNil): Security = hl match {
            case t :+: s :+: HNil => Security(t, s)
        }
    }
    
    abstract class To
    case class ToUser(user: String) extends To
    object ToUser {
        def fromHList(hl: String :+: HNil) = hl match {
            case u :+: HNil => ToUser(u)
        }
    }
    
    case object ToAuction extends To
    
    object form extends Form[Order](this,
        AggregateField(Order.fromHList _,
                CaseField[To]("to",
                    "user" -> AggregateField(ToUser.fromHList _,
                                      StringField("recipient", "")
                                  :^: KNil
                              ),
                    "auction" -> ConstField(ToAuction)
                )
            :^: ListField(this, "securities",
                    AggregateField(Security.fromHList _,
                            StringField("ticker", "")
                        :^: NumberField("shares", "1")
                        :^: KNil
                    )
                )
            :^: StringField("on", "February 42th")
            :^: CaseField[Condition]("cond",
                    "always" -> ConstField(CondAlways),
                    "when"   -> ConstField(CondAlways)
                )
            :^: KNil
        )
    )
    {
        def act(s: Order) {
            logger.info(s)
        }
    }
    
    def render = form.render
}


