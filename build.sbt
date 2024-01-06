import org.beangle.parent.Dependencies.*
import org.beangle.parent.Settings.*

ThisBuild / organization := "org.beangle.serializer"
ThisBuild / version := "0.1.7"

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

ThisBuild / description := "The Beangle Serializer Library"
ThisBuild / homepage := Some(url("https://beangle.github.io/serializer/index.html"))

val beangle_commons_core = "org.beangle.commons" %% "beangle-commons-core" % "5.6.8"
val beangle_commons_csv = "org.beangle.data" %% "beangle-data-csv" % "5.7.11"
val beangle_cdi_api = "org.beangle.cdi" %% "beangle-cdi-api" %  "0.6.1"

val commonDeps = Seq(logback_classic, logback_core, scalatest, beangle_commons_core, beangle_cdi_api)

lazy val root = (project in file("."))
  .settings()
  .aggregate(text,p_protobuf)

lazy val text = (project in file("text"))
  .settings(
    name := "beangle-serializer-text",
    common,
    libraryDependencies ++= (commonDeps ++ Seq(beangle_commons_csv))
  )

lazy val p_protobuf = (project in file("protobuf"))
  .settings(
    name := "beangle-serializer-protobuf",
    common,
    libraryDependencies ++= (commonDeps ++ Seq(protobuf))
  )

publish / skip := true
