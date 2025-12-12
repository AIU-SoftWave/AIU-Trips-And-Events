# Test Automation Quick Reference Guide

## Running Tests

### Run All Tests
```bash
cd Project/backend
mvn test
```

### Run Specific Test Class
```bash
mvn test -Dtest=AuthServiceTest
mvn test -Dtest=EventServiceTest
mvn test -Dtest=BookingServiceTest
```

### Run Specific Test Method
```bash
mvn test -Dtest=AuthServiceTest#testLoginWithValidCredentials_Success
```

### Run Only Unit Tests
```bash
mvn test -Dtest=*ServiceTest
```

### Run Only Integration Tests
```bash
mvn test -Dtest=*IntegrationTest
```

### Skip Tests During Build
```bash
mvn clean install -DskipTests
```

## Test Structure

```
Project/backend/src/test/java/com/aiu/trips/
├── controller/                    # Integration Tests
│   ├── AuthControllerIntegrationTest.java
│   ├── EventControllerIntegrationTest.java
│   └── BookingControllerIntegrationTest.java
├── service/                       # Unit Tests
│   ├── AuthServiceTest.java
│   ├── EventServiceTest.java
│   └── BookingServiceTest.java
├── selenium/                      # UI Tests
│   ├── AuthenticationUITest.java
│   └── EventManagementUITest.java
└── config/
    └── TestConfig.java            # Test Configuration
```

## Test Reports

### View Test Results
After running tests, check:
```bash
# Surefire reports (detailed XML)
Project/backend/target/surefire-reports/

# Console summary
# Shows at end of mvn test output
```

### Generated Reports
- `BUG_REPORT.md` - Comprehensive bug analysis
- `TEST_EXECUTION_SUMMARY.md` - Test execution statistics
- `surefire-reports/*.xml` - Detailed test results

## Test Categories

### Unit Tests (39 tests)
**Purpose:** Test individual components in isolation  
**Framework:** JUnit 5 + Mockito  
**Database:** Mock repositories  
**Speed:** Fast (< 2 seconds)

#### AuthServiceTest (9 tests)
- Login validation
- Registration process
- Password management
- JWT token generation

#### EventServiceTest (15 tests)
- Event CRUD operations
- Validation rules
- Capacity management
- Date handling

#### BookingServiceTest (15 tests)
- Booking creation
- Seat availability
- QR code generation
- Booking history

### Integration Tests (21 tests)
**Purpose:** Test API endpoints end-to-end  
**Framework:** Spring Boot Test + MockMvc  
**Database:** H2 in-memory  
**Speed:** Medium (< 20 seconds)

#### AuthControllerIntegrationTest (5 tests)
- Registration endpoint
- Login endpoint
- Authentication flows

#### EventControllerIntegrationTest (5 tests)
- Event CRUD endpoints
- Event listing and filtering

#### BookingControllerIntegrationTest (11 tests)
- Booking endpoints
- Ticket generation
- QR validation

### UI Tests (13 tests - Currently Blocked)
**Purpose:** Test user interface workflows  
**Framework:** Selenium WebDriver  
**Browser:** Chrome (headless)  
**Speed:** Slow (depends on UI)

#### AuthenticationUITest (6 tests)
- Login page
- Registration page
- Password reset
- Logout

#### EventManagementUITest (7 tests)
- Event browsing
- Event creation
- Event editing
- Event deletion

## Common Test Patterns

### Unit Test Pattern
```java
@ExtendWith(MockitoExtension.class)
class ServiceTest {
    @Mock
    private Repository repository;
    
    @InjectMocks
    private Service service;
    
    @Test
    void testFeature_Success() {
        // Arrange
        when(repository.method()).thenReturn(data);
        
        // Act
        Result result = service.doSomething();
        
        // Assert
        assertEquals(expected, result);
        verify(repository, times(1)).method();
    }
}
```

### Integration Test Pattern
```java
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(TestConfig.class)
class ControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void testEndpoint_Success() throws Exception {
        mockMvc.perform(get("/api/endpoint"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.field").value("value"));
    }
}
```

### Selenium UI Test Pattern
```java
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UITest {
    private static WebDriver driver;
    private static WebDriverWait wait;
    
    @BeforeAll
    static void setupClass() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    @Test
    @Order(1)
    void testFeature() {
        driver.get(BASE_URL + "/page");
        WebElement element = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.id("id")));
        element.click();
        assertTrue(condition);
    }
}
```

## Test Data

### Test Database
- **Type:** H2 in-memory
- **Mode:** Create-drop per test
- **Configuration:** `src/test/resources/application-test.properties`

### Test Users
```java
// Admin User
email: admin@aiu.edu
password: admin123
role: ADMIN

// Student User  
email: student@aiu.edu
password: student123
role: STUDENT
```

### Test Events
```java
// Sample Event
title: "Tech Conference"
type: EVENT
capacity: 100
price: 50.0
startDate: Now + 30 days
location: "Main Hall"
```

## Troubleshooting

### Tests Not Running
```bash
# Check Java version
java -version  # Should be 17

# Clean and recompile
mvn clean compile test-compile

# Verify dependencies
mvn dependency:tree
```

### Test Failures
1. Check test logs in `target/surefire-reports/`
2. Review `BUG_REPORT.md` for known issues
3. Verify database state is clean between tests
4. Check authentication setup for integration tests

### UI Tests Not Running
**Issue:** WebDriverManager cannot download ChromeDriver  
**Solutions:**
1. Pre-install ChromeDriver locally
2. Use bundled ChromeDriver
3. Configure network proxy
4. Skip UI tests: `mvn test -Dtest=!*UITest`

### Database Constraint Violations
**Issue:** Duplicate keys or FK violations  
**Solutions:**
1. Check `@DirtiesContext` annotation
2. Verify `@Transactional` on tests
3. Use unique test data (UUID instead of timestamp)

## Test Configuration

### application-test.properties
```properties
# H2 Database
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.jpa.hibernate.ddl-auto=create-drop

# Logging
logging.level.com.aiu.trips=DEBUG

# Security (if needed)
jwt.secret=test-secret-key-for-testing-purposes-only
jwt.expiration=3600000
```

### Test Configuration Class
```java
@TestConfiguration
public class TestConfig {
    @Bean
    @Primary
    public RequestHandler requestHandlerChain() {
        // No-op handler for testing
        return request -> {};
    }
}
```

## Maven Dependencies

### Testing Dependencies
```xml
<!-- JUnit 5 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>

<!-- Selenium -->
<dependency>
    <groupId>org.seleniumhq.selenium</groupId>
    <artifactId>selenium-java</artifactId>
    <version>4.15.0</version>
    <scope>test</scope>
</dependency>

<!-- WebDriverManager -->
<dependency>
    <groupId>io.github.bonigarcia</groupId>
    <artifactId>webdrivermanager</artifactId>
    <version>5.6.2</version>
    <scope>test</scope>
</dependency>

<!-- H2 Database -->
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>test</scope>
</dependency>
```

## CI/CD Integration

### GitHub Actions Example
```yaml
name: Run Tests

on: [push, pull_request]

jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - name: Set up JDK 17
        uses: actions/setup-java@v2
        with:
          java-version: '17'
      - name: Run tests
        run: mvn test
      - name: Upload test results
        uses: actions/upload-artifact@v2
        with:
          name: test-reports
          path: Project/backend/target/surefire-reports/
```

## Quick Commands

### Development Workflow
```bash
# 1. Make code changes
vim src/main/java/...

# 2. Run affected tests
mvn test -Dtest=MyServiceTest

# 3. Run all tests
mvn test

# 4. Check coverage (if configured)
mvn jacoco:report

# 5. Build
mvn clean install
```

### Test Maintenance
```bash
# Update test dependencies
mvn versions:display-dependency-updates

# Clean test outputs
mvn clean

# Recompile tests only
mvn test-compile

# Debug specific test
mvn test -Dtest=TestClass -X
```

## Best Practices

### Writing Tests
1. ✅ One assertion per test (when possible)
2. ✅ Clear test names (testMethod_Condition_Expected)
3. ✅ Use Arrange-Act-Assert pattern
4. ✅ Mock external dependencies
5. ✅ Clean up after tests (@AfterEach)

### Test Data
1. ✅ Use test-specific data
2. ✅ Don't depend on test execution order
3. ✅ Generate unique identifiers (UUID)
4. ✅ Use builders or factories for complex objects

### Performance
1. ✅ Keep unit tests fast (< 100ms each)
2. ✅ Limit integration tests scope
3. ✅ Use @DirtiesContext sparingly
4. ✅ Parallelize when possible

## References

- [JUnit 5 Documentation](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito Documentation](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)
- [Spring Boot Testing](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.testing)
- [Selenium Documentation](https://www.selenium.dev/documentation/)

---

**Last Updated:** December 12, 2025  
**Maintainer:** Development Team  
**For Issues:** See BUG_REPORT.md
