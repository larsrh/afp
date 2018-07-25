lazy val standardSettings = Seq(
  organization := "info.hupel.afp",
  sonatypeProfileName := "info.hupel",
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
  publish := (()),
  publishLocal := (()),
  publishArtifact := false
)


// Modules

lazy val root = project.in(file("."))
  .settings(standardSettings)
  .settings(noPublishSettings)
  .aggregate(
    afp2017, afp2018
  )

lazy val afp2017 = project.in(file("afp-2017"))
  .enablePlugins(LibisabellePlugin)
  .settings(moduleName := "afp-2017")
  .settings(standardSettings)
  .settings(
    isabelleVersions := Seq(Version.Stable("2017")),
    isabelleSessions in Compile := Seq("Open_Induction")
  )

lazy val afp2018 = project.in(file("afp-2018"))
  .enablePlugins(LibisabellePlugin)
  .settings(moduleName := "afp-2018")
  .settings(standardSettings)
  .settings(
    isabelleVersions := Seq(Version.Stable("2018-RC2")),
    isabelleSessions in Compile := Seq("Open_Induction")
  )


// Release stuff

import ReleaseTransformations._

releaseVcsSign := true
releaseCrossBuild := false

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  releaseStepCommand("publishSigned"),
  setNextVersion,
  commitNextVersion,
  releaseStepCommand("sonatypeRelease")
)


// Miscellaneous

cancelable in Global := true
