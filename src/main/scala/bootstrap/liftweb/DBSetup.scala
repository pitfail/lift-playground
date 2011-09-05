
package bootstrap.liftweb

import net.liftweb.{mapper, common, http, util}
import common.{Box,Full,Empty}
import util.{Props}
import http.{LiftRules}
import mapper.{
    DB, DefaultConnectionIdentifier, StandardDBVendor,
    Schemifier
}
import code.lib.{UserPopulation}

object DBSetup {
    def apply() {
        if (! DB.jndiJdbcConnAvailable_?) makeConnection()
        
        Schemifier.schemify(true, Schemifier.infoF _, UserPopulation.UserDB)
    }
    
    private def makeConnection() {
        val vendor = new StandardDBVendor(
            Props get "db.driver" openOr "org.h2.Driver",
            Props get "db.url"    openOr "jdbc:h2:lift_proto;AUTO_SERVER=TRUE",
            Props get "db.user",
            Props get "db.password"
        )
        
        LiftRules.unloadHooks append(vendor.closeAllConnections_!_)
        DB.defineConnectionManager(DefaultConnectionIdentifier, vendor)
    }
}

