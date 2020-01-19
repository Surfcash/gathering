lazy val commonSettings = Seq(
  organization := "gg.warcraft",
  version := "15.0.0-SNAPSHOT",
  scalaVersion := "2.13.1",
  scalacOptions ++= Seq(
    // additional scalac options go here
  ),
  resolvers ++= Seq(
    Resolver.mavenLocal
  )
)

lazy val assemblySettings = Seq(
  assemblyJarName in assembly := s"${name.value}-${version.value}-all.jar",
  assemblyMergeStrategy in assembly := {
    case PathList("META-INF", it @ _*) => MergeStrategy.discard
    case "module-info.class"           => MergeStrategy.discard
    case it                            => (assemblyMergeStrategy in assembly).value(it)
  }
)

lazy val commonDependencies = Seq(
  "gg.warcraft" %% "monolith-api" % "15.0.0-SNAPSHOT",
  "org.scalatest" %% "scalatest" % "3.0.8" % Test
)

lazy val gathering = (project in file("gathering"))
  .settings(
    name := "gathering",
    commonSettings,
    libraryDependencies ++= commonDependencies
  )

lazy val gatheringSpigot = (project in file("gathering-spigot"))
  .settings(
    name := "gathering-spigot",
    commonSettings,
    assemblySettings,
    resolvers ++= Seq(
      "PaperMC" at "https://papermc.io/repo/repository/maven-public/"
    ),
    libraryDependencies ++= commonDependencies ++ Seq(
      "com.destroystokyo.paper" % "paper-api" % "1.15.1-R0.1-SNAPSHOT" % Provided
    )
  )
  .dependsOn(gathering)
