allprojects {
    repositories {
        mavenLocal()
        maven("https://maven.aliyun.com/repository/public")
        maven("https://maven.aliyun.com/repository/central")
        maven("https://maven.aliyun.com/repository/gradle-plugin/")
        mavenCentral()
    }
}

plugins {
    kotlin("jvm") version "1.9.10" apply false
}

rootProject.apply {
    group = "com.github.yin-jinlong"
    version = "0.0.1-SNAPSHOT"
}
