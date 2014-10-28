lazy val p1 = project.in(file("p1")).settings(name := "p1").enablePlugins(JavaAppPackaging, NewRelic)

lazy val p2 = project.in(file("p2")).settings(name := "p2").enablePlugins(JavaAppPackaging, NewRelic)

lazy val p3 = project.in(file("p3")).settings(name := "p3")
