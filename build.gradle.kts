plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.hilt) apply false // Hilt for Dependency Injection
    alias(libs.plugins.navigation.safe.args) apply false // Navigation Safe Args
}
