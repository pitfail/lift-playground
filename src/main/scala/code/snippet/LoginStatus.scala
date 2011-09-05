
package code
package snippet

import net.liftweb._
import common._
import http._
import util._
import js._
import JsCmds._
import JE._
import Helpers._
import scala.xml.NodeSeq

import code.lib.{SiteIDVendor, UserPopulation}

// The little box at the top of the sidebar that shows whether you're logged
// in.

object LoginStatus extends Loggable {
    import UserPopulation.{usernameForId}
    
    def loggedIn: Boolean = SiteIDVendor.loggedInAs.is match {
        case None    => false
        case Some(_) => true
    }
    
    def ifLoggedIn(in: NodeSeq): NodeSeq = {
        if (loggedIn) in
        else Nil
    }
    
    def ifLoggedOut(in: NodeSeq): NodeSeq = {
        if (!loggedIn) in
        else Nil
    }
    
    def username(in: NodeSeq): NodeSeq = {
        val name = SiteIDVendor.loggedInAs.is match {
            case Some(uid) => usernameForId(uid getIdentifier)
            case _         => "Nobody"
        }
        <a href="/user">{name}</a>
    }
}

