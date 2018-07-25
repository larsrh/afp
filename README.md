# SBT build of the AFP

The [Archive of Formal Proofs](https://www.isa-afp.org/) is a library of formal proof developments using Isabelle.
This repository serves the sole purpose of repackaging all of the AFP into artifacts on Maven Central.
They can be used with [isabellectl](https://github.com/larsrh/libisabelle) and/or [sbt-libisabelle](https://github.com/larsrh/sbt-libisabelle/).

## Packaging

This build will publish libisabelle-compatible AFP source artifacts for AFP2016 and AFP2016-1.
Their version numbers are `major.minor.date`, e.g. `1.0.20170204`.
This means "some snapshot of the AFP on 2017-02-04".
The exact revision can be found out by looking at the corresponding Git tag.

Since the AFP is a "rolling release" library which usually does not break compatibility (except for adding new sessions), it is only expected to bump the major/minor versions for a breaking release of libisabelle, or when support for an Isabelle release is dropped.

## Compatibility matrix

| AFP version      | sbt-libisabelle version | Isabelle versions  |
| ---------------  | ----------------------- | ------------------ |
| 1.0.x            | 0.5.x – 0.7.x           | 2016, 2016-1       |
| 1.1.x            | 0.5.x – 0.7.x           | 2016, 2016-1, 2017 |
| 2.0.x (upcoming) | 0.5.x – 0.7.x           | 2017, 2018         |


For compatibility with `libisabelle` itself, check the [compatibility matrix](https://github.com/larsrh/sbt-libisabelle/#compatibility-matrix) of `sbt-libisabelle`.

## Usage with isabellectl

In the command line, you can use the `--afp` switch.
It will automatically use the latest available AFP package for the appropriate Isabelle version.

## Usage from SBT

```scala
libraryDependencies += "info.hupel.afp" % afp-2017" % "1.1.+"
```

This will select the latest available AFP package for Isabelle2017.
Note that there are no "cross" artifacts, i.e., packages that work for multiple Isabelle versions.
