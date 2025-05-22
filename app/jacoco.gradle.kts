apply(plugin = "jacoco")

// Access jacoco extension property, then set the version explicitly:
val jacocoExtension = extensions.getByName("jacoco") as org.gradle.testing.jacoco.plugins.JacocoPluginExtension
jacocoExtension.toolVersion = "0.8.10"

tasks.register<JacocoReport>("jacocoTestReport") {
    dependsOn("testDebugUnitTest")

    reports {
        xml.required.set(true)
        html.required.set(true)
    }

    val fileFilter = listOf(
        "**/R.class",
        "**/R$*.class",
        "**/BuildConfig.*",
        "**/Manifest*.*",
        "**/*Test*.*",
        "android/**/*.*"
    )

    val debugTree = fileTree("${buildDir}/intermediates/javac/debug") {
        exclude(fileFilter)
    }

    val kotlinDebugTree = fileTree("${buildDir}/tmp/kotlin-classes/debug") {
        exclude(fileFilter)
    }

    classDirectories.setFrom(files(debugTree, kotlinDebugTree))
    sourceDirectories.setFrom(files("src/main/java", "src/main/kotlin"))
    executionData.setFrom(fileTree(buildDir).include(
        "jacoco/testDebugUnitTest.exec",
        "outputs/code_coverage/debugAndroidTest/connected/**/*.ec"
    ))
}
