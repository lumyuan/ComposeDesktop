import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
//    maven( url = "https://maven.aliyun.com/repository/public/")
//    maven( url = "https://maven.aliyun.com/repository/google/")
//
//    google()
//    mavenCentral()
//    maven("https://repo.eclipse.org/content/groups/releases/")
//    maven("https://maven.aliyun.com/nexus/content/groups/public/")
//
//    maven(url = "https://jitpack.io")
//    gradlePluginPortal()
//    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    // Note, if you develop a library, you should use compose.desktop.common.
    // compose.desktop.currentOs should be used in launcher-sourceSet
    // (in a separate module for demo project and in testMain).
    // With compose.desktop.common you will also lose @Preview functionality
    implementation(compose.desktop.currentOs)
    implementation(compose.material3)
    implementation(compose.preview)
    implementation(compose.animation)
    implementation(compose.compiler.auto)
    implementation(compose.foundation)
    implementation(compose.runtime)
    implementation(compose.materialIconsExtended)
    implementation(compose.animation)
    implementation(compose.animationGraphics)
    implementation(compose.ui)
    implementation(compose.uiTooling)
    @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class) implementation(compose.components.resources)
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb, TargetFormat.Exe)
            packageName = "ComposeDesktop"
            packageVersion = "1.0.0"
        }
    }
}
