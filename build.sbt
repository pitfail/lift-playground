
name := "Lift playground"

scalaVersion := "2.9.1"

scalacOptions ++= Seq(
    "-deprecation",
    "-unchecked"
//    "-explaintypes",
//    "-Xprint:typer"
)

libraryDependencies ++= Seq(
	"net.liftweb" % "lift-webkit_2.9.0" % "2.4-M3" % "compile->default",
	"net.liftweb" % "lift-openid_2.9.0" % "2.4-M3",
	"org.mortbay.jetty" % "jetty" % "6.1.22" % "jetty",
	"junit" % "junit" % "4.5" % "test->default",
	"org.scala-tools.testing" % "specs_2.9.0" % "1.6.8" % "test->default",
	"org.slf4j" % "slf4j-simple" % "1.6.1",
	"com.h2database" % "h2" % "1.3.159",
	// This is now unmanaged
	//"org.scribe" % "scribe" % "1.2.1",
	"org.squeryl" % "squeryl_2.9.0" % "0.9.4"
)

seq(webSettings: _*)

// vim: ft=scala

