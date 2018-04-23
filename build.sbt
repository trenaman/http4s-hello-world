organization := "com.fellurian"
name := "hello-world"
scalaVersion := "2.12.1"

enablePlugins(JavaAppPackaging)

libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-blaze-server" % "0.15.4a",
  "org.http4s" %% "http4s-dsl"          % "0.15.4a",
  "org.http4s" %% "http4s-argonaut"     % "0.15.4a",
  "ch.qos.logback"        % "logback-classic"           % "1.1.11"
)

libraryDependencies += "org.slf4j" % "slf4j-simple" % "1.6.4"

import ReleaseTransformations._

defaultLinuxInstallLocation in Docker := "/opt/hello-world"
linuxPackageMappings += packageTemplateMapping(s"/opt/hello-world/logs")()
bashScriptConfigLocation := Some("${app_home}/../conf/application.ini")

mappings in Universal ++= {
  val base = baseDirectory.value
  val confDir = base / "conf"

  for {
    (file, relativePath) <-  (confDir.*** --- confDir) x relativeTo(confDir)
  } yield file -> s"/conf/$relativePath"
}

dockerRepository := Some("123782165517.dkr.ecr.us-east-1.amazonaws.com")

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,              // : ReleaseStep
  inquireVersions,                        // : ReleaseStep
  runTest,                                // : ReleaseStep
  setReleaseVersion,                      // : ReleaseStep
  commitReleaseVersion,                   // : ReleaseStep, performs the initial git checks
  tagRelease,                             // : ReleaseStep
//  publishArtifacts,                       // : ReleaseStep, checks whether `publishTo` is properly set up
  setNextVersion,                         // : ReleaseStep
  commitNextVersion,                      // : ReleaseStep
  pushChanges                             // : ReleaseStep, also checks that an upstream branch is properly configured
)

javaOptions in run ++= scala.io.Source.fromFile("./conf/application.ini").getLines().toSeq.map(l=> if(l.startsWith("-J-")) l.drop(2) else l)
