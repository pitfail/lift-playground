
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

class SellThisStock extends StatefulSnippet with Loggable
{
    def dispatch = {
        case "render" => render
    }
    
    def render(in: NodeSeq): NodeSeq = form.render(in)
    
    object form extends Form[String](this,
        AttrField("ticker")
    )
    {
        def act(ticker: String) {
            logger.info("Selling %s" format ticker)
            userSellStock(ticker)
        }
    }
    
    def userSellStock(ticker: String) {
        import control.{SellStock => Seller}
        import Seller._
        import control.LoginManager.NotLoggedIn
        import comet.NewsHub

        try {
            Seller.userSellAllStock(ticker)
            NewsHub()
        }
        catch {
            case NotLoggedIn() =>
                throw BadInput("You must be logged in to trade")
                
            case DontOwnStock(_) =>
                throw BadInput("You don't own this stock")
        }
    }
}

