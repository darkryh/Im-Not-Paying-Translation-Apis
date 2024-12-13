import org.jetbrains.kotlin.gradle.dsl.JvmTarget

val imNotPayingTranslationApisVersion: String by project
val javaStringVersion: String by project
val javaVersion = JavaVersion.toVersion(javaStringVersion)
val javaVirtualMachineTarget = JvmTarget.fromTarget(javaStringVersion)

plugins {
    id("java-library")
    id("maven-publish")
    id("signing")
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

publishing {
    repositories {
        maven {
            name = "OSSRH"
            url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = System.getenv("OSSRH_USERNAME") ?: "placeholder"
                password = System.getenv("OSSRH_PASSWORD") ?: "placeholder"
            }
        }
    }

    publications {
        register<MavenPublication>("release") {
            from(components["java"])

            groupId = "io.github.darkryh.translator"
            artifactId = "translator-desktop"
            version = imNotPayingTranslationApisVersion

            pom {
                name.set(project.name)
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
                    connection.set("scm:git:git://github.com/darkryh/Im-Not-Paying-Translation-Apis.git")
                    developerConnection.set("scm:git:ssh://github.com/darkryh/Im-Not-Paying-Translation-Apis.git")
                    url.set("https://github.com/darkryh/Im-Not-Paying-Translation-Apis")
                }
            }
        }
    }
}

signing {
    afterEvaluate {
        useGpgCmd()
        sign(publishing.publications["release"])
    }
}