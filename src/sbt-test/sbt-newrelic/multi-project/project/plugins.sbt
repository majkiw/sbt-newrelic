addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.0.0-M1")

{
  val pluginVersion = System.getProperty("plugin.version")
  if(pluginVersion == null)
    throw new RuntimeException("""|The system property 'plugin.version' is not defined.
                                  |Specify this property using the scriptedLaunchOpts -D.""".stripMargin)
  else addSbtPlugin("com.gilt.sbt" % "sbt-newrelic" % pluginVersion)
}
