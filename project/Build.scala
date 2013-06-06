import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "homenagens"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
		"commons-lang" % "commons-lang" % "2.6",
        "commons-collections" % "commons-collections" % "3.2.1",
        "commons-codec" % "commons-codec" % "1.4",
        "commons-io" % "commons-io" % "2.3", 
        "org.apache.commons" % "commons-email" % "1.1",
        "net.sf.jasperreports" % "jasperreports" % "5.0.4",
        "postgresql" % "postgresql" % "9.1-901.jdbc4",
    javaCore,
    javaJdbc,
    javaEbean
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
      resolvers += "Jaspersoft Repo" at "http://jasperreports.sourceforge.net/maven2"
    // Add your own project settings here      
  )

}
