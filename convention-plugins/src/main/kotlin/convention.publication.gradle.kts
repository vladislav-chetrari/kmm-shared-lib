import java.util.*

plugins {
    `maven-publish`
    signing
}

// Stub secrets to let the project sync and build without the publication values set up
ext["signing.keyId"] = null
ext["signing.password"] = null
ext["signing.secretKeyRingFile"] = null
ext["ossrhUsername"] = null
ext["ossrhPassword"] = null

// Grabbing secrets from local.properties file or from environment variables, which could be used on CI
val secretPropsFile = project.rootProject.file("local.properties")
if (secretPropsFile.exists()) secretPropsFile.reader().use {
    Properties().apply {
        load(it)
    }
}.onEach { (name, value) ->
    ext[name.toString()] = value
} else {
    ext["signing.keyId"] = System.getenv("SIGNING_KEY_ID")
    ext["signing.password"] = System.getenv("SIGNING_PASSWORD")
    ext["signing.secretKeyRingFile"] = System.getenv("SIGNING_SECRET_KEY_RING_FILE")
    ext["ossrhUsername"] = System.getenv("OSSRH_USERNAME")
    ext["ossrhPassword"] = System.getenv("OSSRH_PASSWORD")
}

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}

fun getExtraString(name: String) = ext[name]?.toString()

publishing {
    repositories {
        maven {
            name = "sonatype"
            setUrl("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = getExtraString("ossrhUsername")
                password = getExtraString("ossrhPassword")
            }
        }
    }

    // Configure all publications
    publications.withType<MavenPublication> {

        // Stub javadoc.jar artifact
        artifact(javadocJar.get())

        // Provide artifacts information requited by Maven Central
        pom {
            name.set("KMM Shared library")
            description.set("Sample Kotlin Multiplatform library (jvm + ios + js) test")
            url.set("https://github.com/vladislav-chetrari/kmm-shared-lib")

            licenses {
                license {
                    name.set("MIT")
                    url.set("https://opensource.org/licenses/MIT")
                }
            }
            developers {
                developer {
                    id.set("vlad.chetrari")
                    name.set("Vladislav Chetrari")
                    email.set("vlad.chetrari@betvictor.com")
                }
                developer {
                    id.set("vladislav-chetrari")
                    name.set("Vladislav Chetrari")
                    email.set("chetrari.vladislav@gmail.com")
                }
            }
            scm {
                url.set("https://github.com/vladislav-chetrari/kmm-shared-lib")
            }
        }
    }
}

signing {
    sign(publishing.publications)
}