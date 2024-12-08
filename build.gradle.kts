plugins {
	java
	id("org.springframework.boot") version "3.4.0"
	id("io.spring.dependency-management") version "1.1.6"
	id("org.springdoc.openapi-gradle-plugin") version "1.9.0"
}

group = "cloud.reivax"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(23)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-web")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
//	Logging
	implementation("org.slf4j:slf4j-api:2.0.16")
//	Validation Annotations
	implementation("org.springframework.boot:spring-boot-starter-validation")
//	Spring Docs Dependencies
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.7.0")
//	Mapping dependencies
	implementation("org.mapstruct:mapstruct:1.6.3")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
