// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    id("jacoco")
}

apply(from = "$rootDir/app/jacoco.gradle.kts")
