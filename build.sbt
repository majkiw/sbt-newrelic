import com.typesafe.sbt.SbtGit._

organization := "com.gilt.sbt"

name := "sbt-newrelic"

sbtPlugin := true

scalacOptions ++= List(
  "-unchecked",
  "-deprecation",
  "-Xlint",
  "-language:_",
  "-target:jvm-1.6",
  "-encoding", "UTF-8"
)

addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.0.0-M3" % "provided")

ScriptedPlugin.scriptedSettings

scriptedLaunchOpts := { scriptedLaunchOpts.value ++
  Seq("-Xmx1024M", "-XX:MaxPermSize=256M", "-Dplugin.version=" + version.value)
}

scriptedBufferLog := false

scalariformSettings

versionWithGit

git.baseVersion := "0.0.4"

publishMavenStyle := false

bintraySettings

bintrayPublishSettings

bintray.Keys.bintrayOrganization in bintray.Keys.bintray := Some("giltgroupe")

bintray.Keys.packageLabels in bintray.Keys.bintray := Seq("sbt", "newrelic", "sbt-native-packager")

licenses += ("MIT", url("http://opensource.org/licenses/MIT"))
