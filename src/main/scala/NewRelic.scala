package com.gilt.sbt.newrelic

import sbt._
import sbt.Keys._

import com.typesafe.sbt.SbtNativePackager
import com.typesafe.sbt.packager.archetypes.{JavaAppPackaging, TemplateWriter}
import com.typesafe.sbt.packager.universal.UniversalPlugin
import JavaAppPackaging.autoImport._
import UniversalPlugin.autoImport._

object NewRelic extends AutoPlugin {

  object autoImport {
    val newrelicVersion = settingKey[String]("New Relic version")
    val newrelicAgent = taskKey[File]("New Relic agent jar location")
    val newrelicAppName = settingKey[String]("App Name reported to New Relic monitoring")
    val newrelicConfig = taskKey[File]("Generates a New Relic configuration file")
    val newrelicConfigTemplate = settingKey[java.net.URL]("Location of New Relic configuration template")
    val newrelicLicenseKey = settingKey[Option[String]]("License Key for New Relic account")
    val newrelicCustomTracing = settingKey[Boolean]("Option to scan and instrument @Trace annotations")
    val newrelicTemplateReplacements = settingKey[Seq[(String, String)]]("Replacements for New Relic configuration template")
    val newrelicIncludeApi = settingKey[Boolean]("Add New Relic API artifacts to library dependencies")
  }

  import autoImport._

  override val requires = JavaAppPackaging && UniversalPlugin

  val nrConfig = config("newrelic-agent").hide

  override def projectSettings = packagerSettings

  def packagerSettings: Seq[Setting[_]] = Seq(
    ivyConfigurations += nrConfig,
    newrelicVersion := "3.11.0",
    newrelicAgent := findNewrelicAgent(update.value),
    newrelicAppName := name.value,
    newrelicConfig := makeNewRelicConfig((target in Universal).value, newrelicConfigTemplate.value, newrelicTemplateReplacements.value),
    newrelicConfigTemplate := getNewrelicConfigTemplate,
    newrelicLicenseKey := None,
    newrelicCustomTracing := false,
    newrelicTemplateReplacements := Seq(
      "app_name" → newrelicAppName.value,
      "license_key" → newrelicLicenseKey.value.getOrElse(""),
      "custom_tracing" → newrelicCustomTracing.value.toString
    ),
    newrelicIncludeApi := false,
    libraryDependencies += "com.newrelic.agent.java" % "newrelic-agent" % newrelicVersion.value % nrConfig,
    libraryDependencies ++= {
      if (newrelicIncludeApi.value)
        Seq("com.newrelic.agent.java" % "newrelic-api" % newrelicVersion.value)
      else
        Seq.empty
    },
    mappings in Universal ++= Seq(
      newrelicAgent.value -> "newrelic/newrelic.jar",
      newrelicConfig.value -> "newrelic/newrelic.yml"
    ),
    bashScriptExtraDefines += """addJava "-javaagent:${app_home}/../newrelic/newrelic.jar""""
  )

  private[newrelic] def makeNewRelicConfig(tmpDir: File, source: java.net.URL, replacements: Seq[(String, String)]): File = {
    val fileContents = TemplateWriter.generateScript(source, replacements)
    val nrFile = tmpDir / "tmp" / "newrelic.yml"
    IO.write(nrFile, fileContents)
    nrFile
  }

  protected def getNewrelicConfigTemplate: java.net.URL = getClass.getResource("newrelic.yml.template")

  private[this] val newRelicFilter: DependencyFilter =
    configurationFilter("newrelic-agent") && artifactFilter(`type` = "jar")

  def findNewrelicAgent(report: UpdateReport) = report.matching(newRelicFilter).head
}
