import com.typesafe.sbt.SbtNativePackager._
import NativePackagerKeys._


lazy val p1 = project.in(file("p1")).settings(name := "p1").settings(packageArchetype.java_application: _*).settings(NewRelic.packagerSettings: _*)

lazy val p2 = project.in(file("p2")).settings(name := "p2").settings(packageArchetype.java_application: _*).settings(NewRelic.packagerSettings: _*).settings(newrelicLicenseKey := Some("1234567890abcdef"))

lazy val p3 = project.in(file("p3")).settings(name := "p3")
