import sbt.Keys._
import sbt._

object BuildSettings {
  val buildScalaVersion = "3.0.1"

  val commonSettings = Seq(
    organizationName := "The Beangle Software",
    licenses += ("GNU Lesser General Public License version 3", new URL("http://www.gnu.org/licenses/lgpl-3.0.txt")),
    startYear := Some(2005),
    scalaVersion := buildScalaVersion,
    scalacOptions := Seq("-Xtarget:11", "-deprecation", "-feature"),
    crossPaths := true,

    publishMavenStyle := true,
    publishConfiguration := publishConfiguration.value.withOverwrite(true),
    publishM2Configuration := publishM2Configuration.value.withOverwrite(true),
    publishLocalConfiguration := publishLocalConfiguration.value.withOverwrite(true),

    versionScheme := Some("early-semver"),
    pomIncludeRepository := { _ => false }, // Remove all additional repository other than Maven Central from POM
    publishTo := {
      val nexus = "https://oss.sonatype.org/"
      Some("releases" at nexus + "service/local/staging/deploy/maven2")
    })
}

object Dependencies {
  val logbackVer = "1.2.4"
  val scalatestVer = "3.2.9"
  val commonsVer = "5.2.5"
  val cdiVer = "0.3.2"

  val fstVer = "2.57"
  val protobufVer = "3.15.8"

  val scalatest = "org.scalatest" %% "scalatest" % scalatestVer % "test"
  val logbackClassic = "ch.qos.logback" % "logback-classic" % logbackVer % "test"
  val logbackCore = "ch.qos.logback" % "logback-core" % logbackVer % "test"

  val commonsCore = "org.beangle.commons" %% "beangle-commons-core" % commonsVer
  val commonsCsv = "org.beangle.commons" %% "beangle-commons-csv" % commonsVer
  val cdiApi = "org.beangle.cdi" %% "beangle-cdi-api" % cdiVer

  val fst = "de.ruedigermoeller" % "fst" % fstVer
  val protobuf = "com.google.protobuf" % "protobuf-java" % protobufVer

  var commonDeps = Seq(logbackClassic, logbackCore, scalatest, commonsCore, cdiApi)
}

