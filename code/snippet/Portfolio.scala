
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

import control.LoginManager
import lib.formats._

import model.{Schema}
import Schema.{User, StockAsset, byUsername}
import LoginManager.{currentLogin}
import org.squeryl.PrimitiveTypeMode.inTransaction

class Portfolio extends Loggable {
    
    val (
        myStockAssets,
        myCashAmount,
        myDerivativeAssets
    )
        = inTransaction {
            for {
                name <- currentLogin
                user <- byUsername(name)
            }
            yield {
                val port = user.mainPortfolio
                (
                    port.myStockAssets,
                    port.cash,
                    port.myDerivativeAssets
                )
            }
        } getOrElse (
            Nil,
            BigDecimal("0.00"),
            Nil
        )
    // TODO: pre-get prices
    
    def cash = "p *" #> (myCashAmount toDollarString)

    def stocks =
        "#stock *" #> (myStockAssets map ( a =>
              "#ticker *" #> a.ticker
            & "#volume *" #> (stockVolume(a) toDollarString)
            & "ticker=ticker [ticker]" #> a.ticker
        ))
    
    def derivativeAssets =
        "#derivativeAsset" #> (myDerivativeAssets map {a =>
            val deriv = a.derivative
            (
                  "#securities *" #> (deriv.security toHumanString)
                & "#exec *"       #> (deriv.exec toString)
                & "#condition *"  #> (deriv.condition toHumanString)
                & "#peer *"       #> ("user=blank [user]" #> a.peer.owner.username)
            )
        })
    
    def ifHaveStocks(in: NodeSeq) =
        if (myStockAssets isEmpty) Nil
        else in
        
    def ifNoStocks(in: NodeSeq) =
        if (myStockAssets isEmpty) in
        else Nil
    
    def ifHaveDerivativeAssets(in: NodeSeq) =
        if (myDerivativeAssets isEmpty) Nil
        else in
        
    def ifNoDerivativeAssets(in: NodeSeq) =
        if (myDerivativeAssets isEmpty) in
        else Nil

    // TODO: This is not right
    def stockVolume(stock: StockAsset): BigDecimal =
        BigDecimal("3.14")
}

