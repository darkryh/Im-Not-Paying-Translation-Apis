import org.jetbrains.kotlin.gradle.dsl.JvmTarget

val imNotPayingTranslationApisVersion: String by project
val javaStringVersion: String by project
val javaVersion = JavaVersion.toVersion(javaStringVersion)
val javaVirtualMachineTarget = JvmTarget.fromTarget(javaStringVersion)
val compileProjectSdkVersion: String by project
val projectSdkMinVersion: String by project

plugins {
    alias(libs.plugins.android.library)
    id("com.vanniktech.maven.publish") version "0.30.0"
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.ead.lib.imnotpayingtranslationapis.translator.android"
    compileSdk = compileProjectSdkVersion.toInt()

    defaultConfig {
        minSdk = projectSdkMinVersion.toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }

    kotlinOptions {
        jvmTarget = javaStringVersion
    }
}

dependencies {
    implementation(libs.androidx.webkit)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(project(":Translator-Core"))
}

mavenPublishing {
    publishToMavenCentral(com.vanniktech.maven.publish.SonatypeHost.CENTRAL_PORTAL)

    coordinates("io.github.darkryh.translator", "translator-android", imNotPayingTranslationApisVersion)

    pom {
        name.set("Translator Android")
        description.set("Driver implementation for Android")
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