pluginManagement {
    repositories {
//        maven( url = "https://maven.aliyun.com/repository/public/")
//        maven( url = "https://maven.aliyun.com/repository/google/")
//
//        google()
//        mavenCentral()
//        maven("https://repo.eclipse.org/content/groups/releases/")
//        maven("https://maven.aliyun.com/nexus/content/groups/public/")
//
//        maven(url = "https://jitpack.io")
//        gradlePluginPortal()
//        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    plugins {
        kotlin("jvm").version(extra["kotlin.version"] as String)
        id("org.jetbrains.compose").version(extra["compose.version"] as String)
    }
}

rootProject.name = "ComposeDesktop"
