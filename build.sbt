name := "doobie-with-cats-effects"
version := "0.1.0-SNAPSHOT"
scalaVersion := "2.13.12"

idePackagePrefix := Some("dv.khusanjon")

libraryDependencies ++= {

  lazy val doobieVersion = "0.13.4"

  Seq(
    "org.tpolecat"          %% "doobie-core"            % doobieVersion,
    "org.tpolecat"          %% "doobie-h2"              % doobieVersion,
    "org.tpolecat"          %% "doobie-hikari"          % doobieVersion,
    "org.tpolecat"          %% "doobie-specs2"          % doobieVersion,
    "org.tpolecat"          %% "doobie-scalatest"       % doobieVersion       % "test",
    "io.monix"              %% "monix"                  % "3.4.1",
    "mysql"                 % "mysql-connector-java"    % "8.0.33",
    "org.slf4j"             % "slf4j-api"               % "2.0.5",
    "ch.qos.logback"        % "logback-classic"         % "1.4.7"
  )

}

resolvers ++= Seq(
  "Typesafe repository" at "https://repo.typesafe.com/typesafe/releases/"
)
