
package code
package snippet

import net.liftweb._
import common._
import http._
import js._
import JsCmds._
import JE._

import comet.TheBoxServer

object SetBox extends Loggable {
    def render = SHtml onSubmit (s => {
        logger.debug("line submitted")
        TheBoxServer ! s
        SetValById("setbox_in", "")
    })
}

