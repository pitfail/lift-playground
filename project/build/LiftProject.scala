
import sbt._

class LiftProject(info: ProjectInfo) extends DefaultWebProject(info) {
  val liftVersion = "2.4-M3"

  // uncomment the following if you want to use the snapshot repo
  // val scalatoolsSnapshot = ScalaToolsSnapshots

  // val dppsLocalMavenRepo = "m2" at "file:///home/dpp/.m2/repository"

  // If you're using JRebel for Lift development, uncomment
  // this line
  override def scanDirectories = Nil

  override def libraryDependencies = Set(
      "net.liftweb" %% "lift-webkit" % liftVersion % "compile->default",
      "net.liftweb" % "lift-openid_2.9.0" % "2.4-M3",
      "org.mortbay.jetty" % "jetty" % "6.1.22" % "test->default",
      "junit" % "junit" % "4.5" % "test->default",
      "org.scala-tools.testing" %% "specs" % "1.6.8" % "test->default",
      "org.slf4j" % "slf4j-simple" % "1.6.1",
      //"org.xerial.thirdparty" % "sqlitejdbc-nested" % "3.6.2"
      "com.h2database" % "h2" % "1.3.159"
) ++ super.libraryDependencies
}

