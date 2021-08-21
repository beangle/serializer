import Dependencies._
import BuildSettings._
import sbt.url

ThisBuild / organization := "org.beangle.serializer"
ThisBuild / version := "0.0.20"

ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/beangle/serializer"),
    "scm:git@github.com:beangle/serializer.git"
  )
)

ThisBuild / developers := List(
  Developer(
    id    = "chaostone",
    name  = "Tihua Duan",
    email = "duantihua@gmail.com",
    url   = url("http://github.com/duantihua")
  )
)

ThisBuild / description := "The Beangle Data Library"
ThisBuild / homepage := Some(url("https://beangle.github.io/serializer/index.html"))
ThisBuild / resolvers += Resolver.mavenLocal

lazy val root = (project in file("."))
  .settings()
  .aggregate(text,fst,protobuf)

lazy val text = (project in file("text"))
  .settings(
    name := "beangle-serializer-text",
    commonSettings,
    libraryDependencies ++= (commonDeps ++ Seq(Dependencies.commonsCsv))
  )

lazy val fst = (project in file("fst"))
  .settings(
    name := "beangle-serializer-fst",
    commonSettings,
    libraryDependencies ++= (commonDeps ++ Seq(Dependencies.fst))
  )

lazy val protobuf = (project in file("protobuf"))
  .settings(
    name := "beangle-serializer-protobuf",
    commonSettings,
    libraryDependencies ++= (commonDeps ++ Seq(Dependencies.protobuf))
  )

publish / skip := true
