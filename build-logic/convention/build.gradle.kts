// build-logic/convention/build.gradle.kts
plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidFlavors") {
            id = "com.henry.android.flavors"
            implementationClass = "FlavorConventionPlugin"
        }
    }
}