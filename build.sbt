lazy val root = (project in file("."))
  .settings(
    name := "trollabot-scala",
    version := "0.1.0",
    scalaVersion := "2.13.8",
    assembly / mainClass := Some("App")
  )

libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "3.3.3",
  "org.slf4j" % "slf4j-nop" % "1.7.26",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.3",
  "org.postgresql" % "postgresql" % "42.3.4", //org.postgresql.ds.PGSimpleDataSource dependency
  "org.scalatest" %% "scalatest" % "3.2.6" % Test,
  "com.typesafe" % "config" % "1.4.2"
)

scalacOptions += "-deprecation"

run / fork := true
