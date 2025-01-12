plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "16.0"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
            isStatic = true
        }
    }
    
    sourceSets {
        commonMain.dependencies {

            val commonMain by getting {
                dependencies {
                    implementation(libs.ktor.core)
                    implementation(libs.ktor.serialization)
                    implementation(libs.ktor.serialization.json)
                    implementation(libs.sql.delight.runtime)
                    implementation(libs.sql.delight.coroutines.ext)
                    implementation(libs.kot.datetime)
                }
            }
            val commonTest by getting {
                dependencies {
                    implementation(kotlin("test"))
                    implementation(libs.assertK)
                    implementation(libs.turbine)
                }
            }
            val androidMain by getting {
                dependencies {
                    implementation(libs.ktor.android)
                    implementation(libs.sql.delight.android.driver)
                }
            }
            val androidUnitTest by getting
            val iosX64Main by getting
            val iosArm64Main by getting
            val iosSimulatorArm64Main by getting
            val iosMain by creating {
                dependsOn(commonMain)
                iosX64Main.dependsOn(this)
                iosArm64Main.dependsOn(this)
                iosSimulatorArm64Main.dependsOn(this)

                dependencies {
                    implementation(libs.ktor.ios)
                    implementation(libs.sql.delight.native.driver)
                }
            }
            val iosX64Test by getting
            val iosArm64Test by getting
            val iosSimulatorArm64Test by getting
            val iosTest by creating {
                dependsOn(commonTest)
                iosX64Test.dependsOn(this)
                iosArm64Test.dependsOn(this)
                iosSimulatorArm64Test.dependsOn(this)
            }
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "it.pierosilvestri.translator"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}