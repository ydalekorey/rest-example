organization := "com.fdp"

name := "Examples: Scala Calculator"

moduleName := "scala-product-catalogue-coding-test"

version := "1.0.0-SNAPSHOT"

/*At the time of this writing, support for Play 2.5 was not available in the official swagger-play repository.
This repository is a copy of the Play-2.5 folder in pikel's fork.
It was created this way to make it easier to add as a git dependency.
WARNING: This repository will be discarded once support is added to the official swagger-play repo.*/
lazy val swagger = RootProject(uri("https://github.com/CreditCardsCom/swagger-play.git"))

lazy val root = (project in file(".")).enablePlugins(PlayScala).dependsOn(swagger)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  ws,
  "com.typesafe.play" %% "play-slick" % "2.0.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "2.0.0",
  "com.h2database" % "h2" % "1.4.187",

  "org.mockito" % "mockito-core" % "1.10.19" % Test,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test,
  "info.cukes" %% "cucumber-scala" % "1.2.4" % Test,
  "info.cukes" % "cucumber-junit" % "1.2.4" % Test,
  "junit" % "junit" % "4.12" % Test
)

unmanagedResourceDirectories in Test <+= baseDirectory( _ / "features" )

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

fork in run := false

