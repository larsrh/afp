lazy val standardSettings = Seq(
  organization := "info.hupel",
  homepage := Some(url("http://lars.hupel.info/libisabelle/")),
  licenses := Seq(
    "MIT" -> url("http://opensource.org/licenses/MIT"),
    "BSD" -> url("http://opensource.org/licenses/BSD-3-Clause"),
    "LGPL" -> url("http://opensource.org/licenses/LGPL-2.1")
  ),
  publishArtifact in Test := false,
  pomIncludeRepository := { _ => false },
  resolvers += Resolver.sonatypeRepo("releases"),
  autoScalaLibrary := false,
  crossPaths := false,
  isabelleSources in Compile := List(
    baseDirectory.value,
    (baseDirectory in ThisBuild).value / "root"
  ),
  target := (baseDirectory in ThisBuild).value / "target" / name.value,
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (version.value.endsWith("SNAPSHOT"))
      Some("snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("releases"  at nexus + "service/local/staging/deploy/maven2")
  },
  pomExtra := (
    <developers>
      <developer>
        <id>larsrh</id>
        <name>Lars Hupel</name>
        <url>http://lars.hupel.info</url>
      </developer>
    </developers>
    <scm>
      <connection>scm:git:github.com/larsrh/afp.git</connection>
      <developerConnection>scm:git:git@github.com:larsrh/afp.git</developerConnection>
      <url>https://github.com/larsrh/afp</url>
    </scm>
  ),
  credentials += Credentials(
    Option(System.getProperty("build.publish.credentials")) map (new File(_)) getOrElse (Path.userHome / ".ivy2" / ".credentials")
  )
)

lazy val noPublishSettings = Seq(
  publish := (),
  publishLocal := (),
  publishArtifact := false
)


// Modules

lazy val root = project.in(file("."))
  .settings(standardSettings)
  .settings(noPublishSettings)
  .aggregate(
    afp2016, afp2016_1
  )

lazy val afp2016 = project.in(file("afp-2016"))
  .enablePlugins(LibisabellePlugin)
  .settings(moduleName := "afp-2016")
  .settings(standardSettings)
  .settings(
    isabelleVersions := Seq("2016"),
    isabelleSessions in Compile := Seq("Open_Induction")
  )

lazy val afp2016_1 = project.in(file("afp-2016-1"))
  .enablePlugins(LibisabellePlugin)
  .settings(moduleName := "afp-2016-1")
  .settings(standardSettings)
  .settings(
    isabelleVersions := Seq("2016-1"),
    isabelleSessions in Compile := Seq("Open_Induction")
  )


// Release stuff

import ReleaseTransformations._

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  ReleaseStep(action = Command.process("publishSigned", _), enableCrossBuild = true),
  setNextVersion,
  commitNextVersion,
  ReleaseStep(action = Command.process("sonatypeReleaseAll", _), enableCrossBuild = true)
)


// Miscellaneous

cancelable in Global := true
