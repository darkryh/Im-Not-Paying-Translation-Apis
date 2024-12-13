import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

val imNotPayingTranslationApisVersion: String by project
val javaStringVersion: String by project
val javaVersion = JavaVersion.toVersion(javaStringVersion)
val javaVirtualMachineTarget = JvmTarget.fromTarget(javaStringVersion)

plugins {
    id("java-library")
    id("com.vanniktech.maven.publish") version "0.30.0" // Simplifies Maven publishing
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
    api(libs.kcef)
}

// Configure the Maven Publish Plugin
mavenPublishing {
    publishToMavenCentral(SonatypeHost.DEFAULT)

    coordinates("io.github.darkryh.translator", "translator-desktop", imNotPayingTranslationApisVersion)

    pom {
        name.set("Translator Desktop")
        description.set("Driver implementation for desktop")
        url.set("https://github.com/darkryh/Im-Not-Paying-Translation-Apis")

        licenses {
            license {
                name.set("Apache License 2.0")
                url.set("https://www.apache.org/licenses/LICENSE-2.0")
            }
        }
        developers {
            developer {
                id.set("darkryh")
                name.set("Xavier Alexander Torres Calderón")
                email.set("alex_torres-xc@hotmail.com")
            }
        }
        scm {
            url.set("https://github.com/darkryh/Im-Not-Paying-Translation-Apis")
            connection.set("scm:git:git://github.com/darkryh/Im-Not-Paying-Translation-Apis.git")
            developerConnection.set("scm:git:ssh://github.com/darkryh/Im-Not-Paying-Translation-Apis.git")
        }
    }

    signAllPublications()
}