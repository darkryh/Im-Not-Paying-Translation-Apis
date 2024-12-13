import org.jetbrains.kotlin.gradle.dsl.JvmTarget

val imNotPayingTranslationApisVersion: String by project
val javaStringVersion: String by project
val javaVersion = JavaVersion.toVersion(javaStringVersion)
val javaVirtualMachineTarget = JvmTarget.fromTarget(javaStringVersion)

plugins {
    id("java-library")
    alias(libs.plugins.compose)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

java {
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
}

kotlin {
    compilerOptions {
        jvmTarget = javaVirtualMachineTarget
    }
}

dependencies {
    implementation(project(":Translator-Core"))
    implementation(project(":Translator-Desktop"))

    implementation(compose.desktop.currentOs)
}