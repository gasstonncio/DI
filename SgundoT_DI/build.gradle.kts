// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.google.gms.google-services") version "4.4.2" apply false
    alias(libs.plugins.android.application) apply false
}
buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.3.15")
    }
}