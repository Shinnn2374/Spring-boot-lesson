import org.jooq.meta.jaxb.Logging

plugins {
	java
	id("org.springframework.boot") version "3.3.3"
	id("io.spring.dependency-management") version "1.1.6"
	id("nu.studer.jooq") version "8.2.1"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(22)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
	maven {
		url = uri("https://plugins.gradle.org/m2/")
	}
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
	runtimeOnly("org.postgresql:postgresql")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	jooqGenerator("org.postgresql:postgresql:42.7.3")
}

jooq {
	version.set("3.18.4")
	edition.set(nu.studer.gradle.jooq.JooqEdition.OSS)

	configurations{
		create("main"){
			generateSchemaSourceOnCompilation.set(true)
			jooqConfiguration.apply {
				logging = Logging.WARN
				jdbc.apply {
					driver = "org.postgresql.Driver"
					url = "jdbc:postgresql://localhost:5432/tasks"
					user = "postgres"
					password = "postgres"
				}
				generator.apply {
					name = "org.jooq.codegen.DefaultGenerator"
					database.apply {
						inputSchema = "tasks_schema"
					}
					generate.apply {
						isDeprecated = false
						isRecords = true
						isImmutablePojos = true
						isFluentSetters = true
					}
					target.apply {
						packageName = "com.example.firstLesson.jooq.db"
						directory = "build/generated-src/jooq/main"
					}
					strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
				}
			}
		}
	}
}

tasks.named<nu.studer.gradle.jooq.JooqGenerate>("generateJooq") {
	(launcher::set)
	(javaToolchains.launcherFor{
		languageVersion.set(JavaLanguageVersion.of(22))
	})
}

tasks.withType<Test> {
	useJUnitPlatform()
}
