name := "Seismic Scala"

version := "1.0"

organization := "com.github.lrlucena"

scalaVersion := "2.11.8"

// disable publishing the main jar produced by `package`
publishArtifact in (Compile, packageBin) := true

// disable publishing the main API jar
publishArtifact in (Compile, packageDoc) := false

// disable publishing the main sources jar
publishArtifact in (Compile, packageSrc) := false