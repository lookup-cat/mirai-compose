pluginManagement {
    repositories {
        gradlePluginPortal()
        jcenter()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

}

rootProject.name = "mirai-compose"

include (":mirai-compose")

enableFeaturePreview("VERSION_CATALOGS")