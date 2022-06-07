import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    id("org.jetbrains.compose") version "1.1.1"
}

group = "me.dinos"
version = "1.0.6"

/*
version = "1.0.3" -> working on Windows 7
kotlin("jvm") version "1.5.31"
id("org.jetbrains.compose") version "1.0.0"
kotlinOptions.jvmTarget = "15"
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
includeAllModules = true

version = "1.0.6"
kotlin("jvm") version "1.6.10"
id("org.jetbrains.compose") version "1.1.1"
kotlinOptions.jvmTarget = "15"
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1-native-mt")
includeAllModules = true

 */


repositories {
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    mavenCentral()
    mavenLocal()
    google()
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation("com.github.doyaaaaaken:kotlin-csv-jvm:1.2.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1-native-mt")
    implementation("org.jetbrains.skiko:skiko-awt:0.7.22")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "15"
    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
}

compose.desktop {
    application {
        mainClass = "ui.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Msi)//for windows only
            packageName = "CSVEd"
            packageVersion = "1.0.0"
            description = "CSV editor for Radio Mobile csv files"
            copyright = "©2022 Dinos Michelis. All rights reserved."
            //modules to include
            includeAllModules = true
            //modules("java.instrument", "java.prefs", "jdk.unsupported")
        }
    }
}