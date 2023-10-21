import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "com.coursework"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

@OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
kotlin {
    jvm {
        jvmToolchain(11)
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation("com.darkrockstudios:mpfilepicker:2.1.0")
                implementation ("org.jetbrains.kotlinx:kotlin-deeplearning-tensorflow:0.5.2")
                api(compose.runtime)
                api(compose.ui)
                api(compose.foundation)
                api(compose.material3)
            }
        }
        val jvmTest by getting
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Exe)
            packageName = "ImageFilter"
            packageVersion = "1.0.0"
            windows{
                packageVersion = "1.0.0"
                exePackageVersion = "1.0.0"
            }
        }
    }
}