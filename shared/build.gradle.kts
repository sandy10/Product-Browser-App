import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.kotlinSerialization)
    id("org.jetbrains.kotlin.plugin.compose")

   // kotlin("multiplatform")
   // id("org.jetbrains.compose")
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }
    
    sourceSets {
        commonMain.dependencies {
            // put your Multiplatform dependencies here
            implementation("org.jetbrains.compose.runtime:runtime:1.10.2")
            implementation("org.jetbrains.compose.foundation:foundation:1.10.2")
            implementation("org.jetbrains.compose.material:material:1.10.2")

            implementation("io.ktor:ktor-client-core:2.3.9")
            implementation("io.ktor:ktor-client-content-negotiation:2.3.9")
            implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.9")

            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
            implementation("io.ktor:ktor-client-logging:2.3.9")

            implementation("media.kamel:kamel-image:0.9.4")
        }

        androidMain.dependencies {
            implementation("io.ktor:ktor-client-okhttp:2.3.9")
        }

        iosMain.dependencies {
            implementation("io.ktor:ktor-client-darwin:2.3.9")
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
           // implementation(kotlin("test"))

            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.0")
        }
    }
}

android {
    namespace = "com.sandeep.productbrowserapp.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
