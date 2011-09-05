
package code
package lib

import net.liftweb.{mapper, common}
import mapper._
import common.{Box, Full}
import scala.util.Random
import FuncTools._

object UserPopulation {
    
    def usernameForId(openid: String): String =
        (   for (
                 user <- findByOpenID(openid)
            ) yield(user.username is)
        ) getOrElse "Nobody"
    
    def loginUser(openid: String) { touchUser(openid) }
    
    private def touchUser(openid: String): Unit =
        if (! userExists(openid)) initUser(openid)
    
    private def initUser(openid: String) {
        def newName = "comrade" ++ (Random.nextInt(10000) toString)
        val name = untilOK(newName, !userExists(_: String))
        
        val user: UserDB = UserDB.create
        user openid   openid
        user username name
        user name     name
        
        user.save()
    }
    
    private def userExists(openid: String): Boolean =
        findByOpenID(openid) match {
            case Full(_) => true
            case _       => false
        }
    
    private def findByOpenID(openid: String): Box[UserDB] =
        UserDB find(By(UserDB.openid, openid))
    
    class UserDB extends LongKeyedMapper[UserDB] {
        def getSingleton = UserDB
        def primaryKeyField = id
        
        object id       extends MappedLongIndex(this)
        object openid   extends MappedString(this, 1024)
        object username extends MappedString(this, 256)
        object name     extends MappedString(this, 256)
    }
    object UserDB extends UserDB with LongKeyedMetaMapper[UserDB]
}

