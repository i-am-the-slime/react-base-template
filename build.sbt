import sbt.Project.projectToRef

name := "scalareactjs"

organization := "codes.mark"

lazy val clients = Seq(client)

lazy val scalaVersionNumber = "2.11.7"

lazy val server = (project in file("server")).settings(
  scalaVersion := scalaVersionNumber,
  scalaJSProjects := clients,
  pipelineStages := Seq(scalaJSProd, gzip),
  resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases",
  libraryDependencies ++= Seq(
    "com.vmunier"          %% "play-scalajs-scripts" % "0.3.0"  ,
    "org.scalaz"           %% "scalaz-core"          % "7.1.3"  ,
    "org.scalaz.stream"    %% "scalaz-stream"        % "0.7.2a" ,
    "com.chuusai"          %% "shapeless"            % "2.2.5"  ,
    "org.webjars"           % "jquery"               % "1.11.1" ,
    specs2 % Test
  )
).enablePlugins(PlayScala)
 .aggregate(clients.map(projectToRef):_*)
 .dependsOn(sharedJvm)

lazy val client = (project in file("client")).settings(
  scalaVersion := scalaVersionNumber,
  persistLauncher := true,
  persistLauncher in Test := false,
  sourceMapsDirectories += sharedJs.base / "..",
  libraryDependencies ++= Seq(
    "org.scala-js"                                    %%% "scalajs-dom"  % "0.8.2",
    "com.github.japgolly.scalajs-react"               %%% "core"         % "0.10.1",
    "com.github.japgolly.scalajs-react"               %%% "ext-scalaz71" % "0.10.1",
    "com.github.japgolly.scalajs-react"               %%% "ext-monocle"  % "0.10.1"
  ),
  jsDependencies +=
  "org.webjars" % "react" % "0.14.1" / "react-with-addons.js" commonJSName "React"
).enablePlugins(ScalaJSPlugin, ScalaJSPlay).
  dependsOn(sharedJs)

lazy val shared = (crossProject.crossType(CrossType.Pure) in file("shared")).
  settings(scalaVersion := scalaVersionNumber).
  jsConfigure(_ enablePlugins ScalaJSPlay).
  jsSettings(sourceMapsBase := baseDirectory.value / "..")

lazy val sharedJvm = shared.jvm
lazy val sharedJs = shared.js

// loads the Play project at sbt startup
onLoad in Global := (Command.process("project server", _: State)) compose (onLoad in Global).value
