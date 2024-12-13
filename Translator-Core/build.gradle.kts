import org.jetbrains.kotlin.gradle.dsl.JvmTarget

val imNotPayingTranslationApisVersion: String by project
val javaStringVersion: String by project
val javaVersion = JavaVersion.toVersion(javaStringVersion)
val javaVirtualMachineTarget = JvmTarget.fromTarget(javaStringVersion)

plugins {
    id("java-library")
    id("com.vanniktech.maven.publish") version "0.30.0"
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
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
    api(libs.serializer)
    api(libs.kotlinx.coroutines.core)
}

mavenPublishing {
    publishToMavenCentral(com.vanniktech.maven.publish.SonatypeHost.CENTRAL_PORTAL)

    coordinates("io.github.darkryh.translator", "translator-core", imNotPayingTranslationApisVersion)

    pom {
        name.set("Translator Core")
        description.set("Core abstraction for translator")
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
            connection.set("scm:git:git://github.com/darkryh/Im-Not-Paying-Translation-Apis.git")
            developerConnection.set("scm:git:ssh://github.com/darkryh/Im-Not-Paying-Translation-Apis.git")
            url.set("https://github.com/darkryh/Im-Not-Paying-Translation-Apis")
        }
    }

    signAllPublications()
}
