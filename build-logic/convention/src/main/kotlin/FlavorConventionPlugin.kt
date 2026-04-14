import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class FlavorConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val extension = extensions.findByType(CommonExtension::class.java) ?: return
            extension.configureFlavors()
        }
    }
}

fun CommonExtension<*, *, *, *, *, *>.configureFlavors() {
    flavorDimensions += "environment"
    productFlavors {
        create("develop") {
            dimension = "environment"
            buildConfigField("Boolean", "ENABLE_LOGS", "true")
            buildConfigField("String", "BASE_URL", "\"https://newsapi.org/v2/\"")
        }
        create("production") {
            dimension = "environment"
            buildConfigField("Boolean", "ENABLE_LOGS", "false")
            buildConfigField("String", "BASE_URL", "\"https://newsapi.org/v2/\"")
        }
    }
}