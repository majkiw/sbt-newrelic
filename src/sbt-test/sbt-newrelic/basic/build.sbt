import com.typesafe.sbt.SbtNativePackager._
import NativePackagerKeys._

packageArchetype.java_application

name := "app"

NewRelic.packagerSettings

newrelicCustomTracing := true
