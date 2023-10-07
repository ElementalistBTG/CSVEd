import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"//stable version 1.6.10
    id("org.jetbrains.compose") version "1.1.1"//stable version 1.1.1
}

group = "me.dinos"
version = "1.0.10"

repositories {
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    mavenCentral()
    mavenLocal()
    google()
    maven {
        url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        name = "Compose for Desktop DEV"
    }
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation("com.github.doyaaaaaken:kotlin-csv-jvm:1.2.0")//stable version 1.2.0 (readNext method is deprecated after this version)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.2")//stable version 1.6.2, latest 1.7.3
    implementation("org.jetbrains.skiko:skiko-awt:0.7.22") //stable version 0.7.22 (last to work with koltin 1.6), latest 0.7.81
    implementation("org.jetbrains.skiko:skiko-awt-runtime-windows-x64:0.7.22")//stable version 0.7.22
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
            //includeAllModules = true
            //modules("java.instrument", "java.prefs", "jdk.unsupported")
        }
    }
}