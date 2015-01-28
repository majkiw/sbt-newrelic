sbt-newrelic
=============

newrelic support for any artifacts built with [sbt-native-packager](https://github.com/sbt/sbt-native-packager)

Useful Links
-------------

The following is a list of useful links related to newrelic:

* [New Relic Java Agent](https://docs.newrelic.com/docs/agents/java-agent)
* [New Relic Java Release Notes](https://docs.newrelic.com/docs/release-notes/agent-release-notes/java-release-notes)
* [New Relic Java Agent Artifacts](http://download.newrelic.com/newrelic/java-agent/newrelic-agent/)
* [New Relic Java Agent config file template](https://docs.newrelic.com/sites/default/files/atoms/files/newrelic.yml)
* [New Relic Java Agent Configuration](https://docs.newrelic.com/docs/agents/java-agent/configuration/java-agent-configuration-config-file)

Prerequisites
-------------
The plugin assumes that sbt-native-packager 0.7.x or 0.8.x has been included in your SBT build configuration, and its settings have been
initialized. This can by done by adding the plugin following instructions at http://www.scala-sbt.org/sbt-native-packager/ or by adding
another plugin that includes and initializes it (e.g. the SBT plugin for Play 2.3.x).

Installation
------------
Add the following to your `project/plugins.sbt` file:

```scala
addSbtPlugin("com.gilt.sbt" % "sbt-newrelic" % "0.0.3")
```

Configuration
-------------
The following represents the minimum amount of code required in a `build.sbt` to use [sbt-newrelic](https://github.com/gilt/gilt-sbt-newrelic)

To use the NewRelic settings in your project, add `NewRelic.packagerSettings` to your build. If you are manually adding sbt-native-packager
archetypes, you should add this line after the archetype has set default values for settings.

```scala
NewRelic.packagerSettings
```

To use a specific New Relic Java Agent version, add the following to your `build.sbt` file:

```scala
newrelicVersion := "3.13.0"
```

To add a New Relic license key to the generated `newrelic.yml` file, add the following to your `build.sbt` file:

```scala
newrelicLicenseKey := Some("1234567890abcdef")
```

An alternative approach is to pass the license key to your application via the `NEW_RELIC_LICENSE_KEY` environment variable.

To enable custom tracing, add the following to your `build.sbt` file:

```scala
newrelicCustomTracing := true
```

To include the New Relic client API in your project's library dependencies, add to your build:

```scala
newrelicIncludeApi := true
```

To provide a static `newrelic.yml` file instead of a generated file, place the
file somewhere (e.g. `resourceDirectory`, which is `conf/` by default for Play
applications, or `src/main/resources` for many other builds), and refer to it
from your build like this:

```scala
newrelicConfig := (resourceDirectory in Compile).value / "newrelic.yml"
```

The application name reported to New Relic may be modified by setting `newrelicAppName`, the default value is the name of the project.

```scala
newrelicAppName := "My Application"
```
