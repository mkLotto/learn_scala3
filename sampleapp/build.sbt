val scala3Version = "3.0.2"
val scalikejdbcVersion = "4.0.0-RC1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "scala3-simple",
    version := "0.1.0",

    scalaVersion := scala3Version,

    libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test",
    libraryDependencies ++= Seq(
      "com.typesafe.play" %% "play-json" % "2.10.0-RC5",
      "org.scalikejdbc" %% "scalikejdbc" % scalikejdbcVersion,
      "org.scalikejdbc" %% "scalikejdbc-config"  % scalikejdbcVersion,
      "mysql" % "mysql-connector-java" % "5.1.29"))
