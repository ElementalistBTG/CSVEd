import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    id("org.jetbrains.compose") version "1.1.1"
}

group = "me.dinos"
version = "1.0"

repositories {
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    mavenCentral()
    mavenLocal()
    google()
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation("com.github.doyaaaaaken:kotlin-csv-jvm:1.2.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
}

// Add ProGuard to buildscript classpath
//buildscript {
//    repositories {
//        mavenCentral()
//    }
//    dependencies {
//        classpath("com.guardsquare:proguard-gradle:7.2.0")
//    }
//}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
}

compose.desktop {
    application {
        mainClass = "ui.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Msi)//for windows only
            packageName = "CSVEd"
            packageVersion = "1.0.3"
            description = "CSV editor for Radio Mobile csv files"
            copyright = "©2022 Dinos Michelis. All rights reserved."
//            val iconsRoot = project.file("src/main/resources")
//            windows.iconFile.set(iconsRoot.resolve("icon.ico"))
        }
    }
}

//tasks.jar {
//    manifest {
//        attributes["Main-Class"] = "MainKt"
//    }
//    configurations["compileClasspath"].forEach { file: File ->
//        from(zipTree(file.absoluteFile))
//    }
//    duplicatesStrategy = DuplicatesStrategy.INCLUDE
//}

// Define task to obfuscate the JAR and output to <name>.min.jar
//tasks.register<proguard.gradle.ProGuardTask>("obfuscate") {
//    val packageUberJarForCurrentOS by tasks.getting
//    dependsOn(packageUberJarForCurrentOS)
//    val files = packageUberJarForCurrentOS.outputs.files
//    injars(files)
//    outjars(files.map { file -> File(file.parentFile, "${file.nameWithoutExtension}.min.jar") })
//
//    val library = if (System.getProperty("java.version").startsWith("1.")) "lib/rt.jar" else "jmods"
//    libraryjars("${System.getProperty("java.home")}/$library")
//
//    configuration("proguard-rules.pro")
//}
