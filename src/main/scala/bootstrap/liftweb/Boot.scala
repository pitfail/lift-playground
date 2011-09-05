
package bootstrap.liftweb

import net.liftweb._
import util._
import Helpers._

import common._
import http._
import sitemap._
import Loc._

import code.lib.{SiteIDVendor}

class Boot {
    def boot {
        val lr = LiftRules.realInstance
        import lr.{
            setSiteMap, ajaxStart, ajaxEnd, jsArtifacts, early,
            dispatch
        }
        
        // where to search snippet
        LiftRules.addToPackages("code")

        val entries = List(
            Menu.i("Home") / "index",
                Menu(Loc("Static", Link(List("static"), true, "/static/index"), "Static Content"))
            )
        setSiteMap(SiteMap(entries:_*))

        //Show the spinny image when an Ajax call starts
        ajaxStart = Full(() => jsArtifacts.show("ajax-loader").cmd)
        ajaxEnd   = Full(() => jsArtifacts.hide("ajax-loader").cmd)

        // Force the request to be UTF-8
        early.append(_.setCharacterEncoding("UTF-8"))
        
        // Handler for OpenID logins
        dispatch.append(SiteIDVendor.dispatchPF)
        
        DBSetup()
    }
}

