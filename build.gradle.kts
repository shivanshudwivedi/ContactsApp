 import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    id("java")
    application
}

var lastName = "Dwivedi" // Please change string to your last name
group = "edu.trinity.cpsc215f23"
version = "1.0.0" + "-" + lastName

repositories {
    mavenCentral()
}


dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        showStandardStreams = true
        exceptionFormat = TestExceptionFormat.FULL
        events("standardOut", "started", "passed", "skipped", "failed")
    }
}

application {
    mainClass = "edu.trinity.cpsc215f23.ContactsApp"
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}


// Run Gradle task "assemble" to produce the distribution zip file.
// This section lines will ensure your .java files for main and test are
// included in the distribution zip file. When your project is complete,
// submit this generated build/distributions/*.zip to the Moodle assignment.
distributions {
    main {
        contents {
            from(sourceSets.main.get().allSource) {
                into("src/main")
            }
            from(sourceSets.test.get().allSource) {
                into("src/test")
            }
        }
    }
}

// Run gradle task "jar" to get an executable jar.
// Once created, run with java -jar .\build\libs\project2-1.0-SNAPSHOT.jar
tasks.jar { // could also be a new task rather than the default one
    manifest {
        attributes["Main-Class"] = "edu.trinity.cpsc215f23.ContactsApp"
    }
}

//
//tasks.register("project-archive") {
//    description = "Archives the Gradle project files into a zip file. For instructor use only."
//    project.exec {
//        commandLine("bash", "-c", "git ls-files | zip --names-stdin project2.zip")
//    }
//}
