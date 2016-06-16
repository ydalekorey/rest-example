organization := "com.fdp"

name := "Examples: Scala Calculator"

moduleName := "scala-product-catalogue-coding-test"

version := "1.0.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  ws,
  "org.mockito" % "mockito-core" % "1.10.19" % Test,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test,
  "info.cukes" %% "cucumber-scala" % "1.2.4" % Test,
  "info.cukes" % "cucumber-junit" % "1.2.4" % Test,
  "junit" % "junit" % "4.12" % Test
)

unmanagedResourceDirectories in Test <+= baseDirectory( _ / "features" )

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

fork in run := false

