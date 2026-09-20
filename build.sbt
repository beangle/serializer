import org.beangle.parent.Dependencies.*
import org.beangle.parent.Settings.*

organization := "org.beangle.serializer"
version := "0.1.30-SNAPSHOT"

scmInfo := Some(
  ScmInfo(
    uri("https://github.com/beangle/serializer"),
    "scm:git@github.com:beangle/serializer.git"
  )
)

developers := List(
  Developer(
    id = "chaostone",
    name = "Tihua Duan",
    email = "duantihua@gmail.com",
    url = uri("http://github.com/duantihua")
  )
)

description := "The Beangle Serializer Library"
homepage := Some(uri("https://beangle.github.io/serializer/index.html"))

val beangle_commons = "org.beangle.commons" % "beangle-commons" % "6.3.7"

lazy val root = (project in file("."))
  .settings(
    name := "beangle-serializer",
    common,
    libraryDependencies ++= Seq(scalatest),
    libraryDependencies ++= Seq(beangle_commons),
    libraryDependencies ++= Seq(protobuf % "optional")
  )
