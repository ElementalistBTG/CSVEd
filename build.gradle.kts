import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.21"
    id("org.jetbrains.compose") version "1.0.0-alpha3"
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

    implementation("io.reactivex.rxjava3:rxjava:3.0.6")
    implementation("io.reactivex.rxjava3:rxkotlin:3.0.1")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")

    implementation("androidx.compose.ui:ui:1.2.0-alpha03")


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
            windows.iconFile.set(project.file("icon.png"))
        }
    }
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "MainKt"
    }
    configurations["compileClasspath"].forEach { file: File ->
        from(zipTree(file.absoluteFile))
    }
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}