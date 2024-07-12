plugins { kotlin("jvm") version "1.9.22" }

group = "de.admiralbiscuit.akquinet.pokeman"

version = "1.0-SNAPSHOT"

repositories { mavenCentral() }

dependencies {
  implementation("io.arrow-kt:arrow-core:1.2.4")
  testImplementation("io.kotest:kotest-runner-junit5:5.9.1")
  testImplementation("io.kotest.extensions:kotest-assertions-arrow-jvm:1.4.0")
}

tasks.test { useJUnitPlatform() }

kotlin { jvmToolchain { languageVersion.set(JavaLanguageVersion.of("21")) } }
