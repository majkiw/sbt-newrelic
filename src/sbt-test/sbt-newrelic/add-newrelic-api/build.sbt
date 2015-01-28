import com.typesafe.sbt.SbtNativePackager._
import NativePackagerKeys._

packageArchetype.java_application

name := "app"

NewRelic.packagerSettings

newrelicVersion := "3.13.0"
