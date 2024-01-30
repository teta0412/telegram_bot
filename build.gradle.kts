plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation ("org.telegram:telegrambots:6.9.7.0")
    implementation ("org.telegram:telegrambots-abilities:6.9.7.0")
    implementation ("org.json:json:20090211")

}

tasks.test {
    useJUnitPlatform()
}
tasks.create("stage"){
    dependsOn("build", "clean");
}

