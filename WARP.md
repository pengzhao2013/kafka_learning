# WARP.md

This file provides guidance to WARP (warp.dev) when working with code in this repository.

## Project Overview

This is a Kafka learning project (`kafka_learning`) that demonstrates Apache Kafka client usage. The project is a Maven-based Java application focused on experimenting with Kafka producers and utility tools.

## Build and Development Commands

### Build
```bash
mvn clean compile
```

### Run Main Test (Kafka Producer Example)
```bash
mvn exec:java -Dexec.mainClass="com.yupi.kafka.MainTest"
```

### Run Password Tools
```bash
mvn exec:java -Dexec.mainClass="com.yupi.kafka.tools.PasswordTools"
```

### Test
```bash
mvn test
```

### Package
```bash
mvn clean package
```

## Architecture & Structure

- **Package Structure**: `com.yupi.kafka.*` - All code is organized under this base package
- **Main Application**: `MainTest.java` - Contains Kafka producer example that connects to a Kafka broker and sends messages
- **Tools Package**: `com.yupi.kafka.tools.*` - Utility classes for various operations
- **Maven Project**: Uses Java 8, Spring Boot 2.7.5, and Kafka Clients 3.8.0

## Key Dependencies

- **Apache Kafka**: `kafka-clients:3.8.0` - Core Kafka client library
- **Spring Boot**: `spring-boot-starter:2.7.5` - Application framework
- **Utility Libraries**: Guava, JCTools, Disruptor for performance-oriented operations
- **Logging**: Logback for logging
- **Development Tools**: Lombok for reducing boilerplate code

## Important Notes

- The project targets Java 8 compilation
- Kafka broker connection is currently hardcoded to `110.42.203.200:9092` in MainTest
- The codebase includes performance-oriented libraries (JCTools, Disruptor) suggesting focus on high-performance scenarios
- Uses standard Maven directory structure (`src/main/java`)