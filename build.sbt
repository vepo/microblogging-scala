scalaVersion := "3.2.0"
version := "0.1"
name := "microblogging"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.14" % "test"

resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases"
addSbtPlugin("com.artima.supersafe" % "sbtplugin" % "1.1.12")
addSbtPlugin("com.github.sbt" % "sbt-jacoco" % "3.0.3")