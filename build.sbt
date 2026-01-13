import org.beangle.parent.Dependencies.*
import org.beangle.parent.Settings.*

ThisBuild / organization := "org.beangle.serializer"
ThisBuild / version := "0.1.23"

ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/beangle/serializer"),
    "scm:git@github.com:beangle/serializer.git"
  )
)

ThisBuild / developers := List(
  Developer(
    id = "chaostone",
    name = "Tihua Duan",
    email = "duantihua@gmail.com",
    url = url("http://github.com/duantihua")
  )
)

ThisBuild / description := "The Beangle Serializer Library"
ThisBuild / homepage := Some(url("https://beangle.github.io/serializer/index.html"))

val beangle_commons = "org.beangle.commons" % "beangle-commons" % "5.7.0"

lazy val root = (project in file("."))
  .settings(
    name := "beangle-serializer",
    common,
    libraryDependencies ++= Seq(scalatest),
    libraryDependencies ++= Seq(beangle_commons),
    libraryDependencies ++= Seq(protobuf % "optional")
  )
