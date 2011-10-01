
package code
package snippet

import net.liftweb.{common, http, util}
import common.{Loggable}
import util.{Helpers}
import scala.xml.{NodeSeq}
import http._
import js._
import JsCmds._
import JE._
import Helpers._

import scala.math.{BigDecimal}
import lib.magicform._
import lib.formats._
import lib.BadInput

import up._
import HList._
import KList._
import ~>._

class BuyStock extends StatefulSnippet with Loggable {
    
    def dispatch = {
        case "render" => render _
    }
    
    def render(in: NodeSeq): NodeSeq = form.render(in)
    
    case class Order(
        ticker: String,
        volume: BigDecimal
        )
    object Order {
        def fromHList(hl: String :+: BigDecimal :+: HNil) = hl match {
            case t :+: v :+: HNil => Order(t, v)
        }
    }
    
    object form extends Form[Order](this,
        AggregateField(Order.fromHList _,
                StringField("ticker", "")
            :^: NumberField("volume", "10.00")
            :^: KNil
        )
    )
    {
        def act(order: Order) {
            userBuyStock(order.ticker, order.volume)
        }
    }
    
    def userBuyStock(ticker: String, volume: BigDecimal) {
        import control.{BuyStock => Buyer}
        import Buyer._
        import control.LoginManager.NotLoggedIn
        import comet.NewsHub
        
        if (volume < 0)
            throw throw BadInput("You need to by more than $0.00 of the stock")
        
        try {
            Buyer.userBuyStock(ticker, volume)
            NewsHub()
        }
        catch {
            case NotEnoughCash(have, need) => throw BadInput(
                "You need at least %s you only have %s" format (
                    need toDollarString,
                    have toDollarString
                )
            )
            case NotLoggedIn() =>
                throw BadInput("You must be logged in to buy stock")
                
            case e => throw e
        }
    }
}

