# Test Configuration

## Test Environment Setup

### 1. application-test.properties

```properties
# H2 In-Memory Database
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect

# JWT Configuration for Tests
jwt.secret=testSecretKeyForJWTTokenGenerationInTestEnvironment
jwt.expiration=86400000

# Logging
logging.level.org.springframework=WARN
logging.level.com.aiu.trips=DEBUG

# Disable Open-in-View
spring.jpa.open-in-view=false
```

### 2. TestConfig.java

```java
package com.aiu.trips.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@TestConfiguration
@Profile("test")
public class TestConfig {
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil();
    }
}
```

### 3. Maven Configuration (pom.xml)

```xml
<dependencies>
    <!-- JUnit 5 -->
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter</artifactId>
        <scope>test</scope>
    </dependency>
    
    <!-- Mockito -->
    <dependency>
        <groupId>org.mockito</groupId>
        <artifactId>mockito-core</artifactId>
        <scope>test</scope>
    </dependency>
    
    <!-- Spring Boot Test -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
    
    <!-- Spring Security Test -->
    <dependency>
        <groupId>org.springframework.security</groupId>
        <artifactId>spring-security-test</artifactId>
        <scope>test</scope>
    </dependency>
    
    <!-- H2 Database -->
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>

<build>
    <plugins>
        <!-- Surefire Plugin for Test Execution -->
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <version>3.2.2</version>
            <configuration>
                <includes>
                    <include>**/*Test.java</include>
                    <include>**/*IntegrationTest.java</include>
                </includes>
                <excludes>
                    <exclude>**/*UITest.java</exclude>
                </excludes>
            </configuration>
        </plugin>
    </plugins>
</build>
```

---

## Running Tests

### Command Line

```bash
# Run all tests (excluding UI)
mvn test -Dtest='!**/*UITest'

# Run specific test class
mvn test -Dtest=AuthServiceTest

# Run specific test method
mvn test -Dtest=AuthServiceTest#testLogin_WithValidCredentials

# Run all unit tests
mvn test -Dtest=*ServiceTest

# Run all integration tests
mvn test -Dtest=*IntegrationTest

# Run with debug output
mvn test -X

# Run with specific profile
mvn test -Ptest
```

### IDE Configuration

#### IntelliJ IDEA
1. Right-click on test class/method
2. Select "Run test..."
3. Configure VM options if needed: `-Dspring.profiles.active=test`

#### Eclipse
1. Right-click on test class
2. Run As → JUnit Test
3. Add test profile in Run Configurations

---

## Test Database Setup

### H2 Console (Optional)
```properties
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

Access at: `http://localhost:8080/h2-console` during test debugging

---

This configuration ensures consistent, isolated, and reliable test execution across all environments.
