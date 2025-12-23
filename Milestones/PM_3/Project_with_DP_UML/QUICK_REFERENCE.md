# Design Patterns Quick Reference Guide

A quick reference for developers working with the design patterns in this project.

## Quick Start

All patterns are Spring-managed beans. Simply autowire them:

```java
@Autowired
private IModelFactory modelFactory;

@Autowired
private PricingStrategy pricingStrategy;

@Autowired
private ControllerCommandInvoker commandInvoker;
```

---

## Pattern Cheat Sheet

### When to Use Each Pattern

| Pattern | Use When You Need To... |
|---------|------------------------|
| **Factory** | Create objects without specifying exact class |
| **Abstract Factory** | Create families of related objects (Events/Trips) |
| **Builder** | Construct complex objects step-by-step |
| **Prototype** | Clone existing objects |
| **Command** | Encapsulate requests as objects |
| **Strategy** | Switch between algorithms at runtime |
| **State** | Change behavior based on internal state |
| **Chain of Responsibility** | Pass requests through handler chain |
| **Decorator** | Add functionality without changing class |
| **Bridge** | Separate abstraction from implementation |
| **Adapter** | Make incompatible interfaces work together |
| **Memento** | Save and restore object state |

---

## Common Usage Patterns

### Creating Objects

```java
// Factory Pattern
User user = modelFactory.createModel(User.class);

// Abstract Factory Pattern
@Autowired
private EventFactory eventFactory;
Event event = eventFactory.createActivity();

// Builder Pattern
@Autowired
private ActivityDirector director;
Event event = director.constructEvent(
    "Conference", "Tech Conference", 
    LocalDateTime.now(), "Hall A", 100.0, 200, user
);

// Prototype Pattern
Event clonedEvent = originalEvent.clone();
```

### Behavioral Patterns

```java
// Command Pattern
IControllerCommand cmd = new CreateEventCommand(service, event, email);
Object result = commandInvoker.executeCommand(cmd);

// Strategy Pattern
Double price = earlyBirdPricing.calculatePrice(event, tickets);

// State Pattern
lifecycle.setEvent(event);
boolean canBook = lifecycle.canBook();

// Chain of Responsibility
authHandler.setNext(authzHandler);
authzHandler.setNext(validationHandler);
Object result = authHandler.handle(request);

// Memento Pattern
ActivityMemento memento = mementoFactory.createMemento(event);
caretaker.save(memento);
```

### Structural Patterns

```java
// Decorator Pattern
ITicketService service = new BaseTicketService();
service = new SignedQrDecorator(service);
service = new AuditLogDecorator(service);
String ticket = service.generateTicket(booking);

// Bridge Pattern
NotificationChannel channel = new EmailChannel();
NotificationMessage msg = new NewEventMessage(channel, "Event Title");
msg.sendNotification("user@example.com");

// Adapter Pattern
emailService.sendEmail("to@example.com", "Subject", "Body");
```

---

## Integration Examples

### Example 1: Creating an Event with Builder

```java
@Service
public class EventCreationService {
    
    @Autowired
    private ActivityDirector activityDirector;
    
    @Autowired
    private EventRepository eventRepository;
    
    public Event createNewEvent(EventDTO dto, User organizer) {
        Event event = activityDirector.constructEvent(
            dto.getTitle(),
            dto.getDescription(),
            dto.getStartDate(),
            dto.getLocation(),
            dto.getPrice(),
            dto.getCapacity(),
            organizer
        );
        
        return eventRepository.save(event);
    }
}
```

### Example 2: Pricing with Strategy

```java
@Service
public class PricingService {
    
    @Autowired
    private EarlyBirdPricingStrategy earlyBirdPricing;
    
    @Autowired
    private BulkGroupDiscountStrategy groupPricing;
    
    public Double calculatePrice(Event event, Integer tickets, String strategy) {
        PricingStrategy pricingStrategy = switch(strategy) {
            case "early_bird" -> earlyBirdPricing;
            case "group" -> groupPricing;
            default -> new StandardPricingStrategy();
        };
        
        return pricingStrategy.calculatePrice(event, tickets);
    }
}
```

### Example 3: Request Processing with Chain

```java
@Component
public class RequestProcessor {
    
    @Autowired
    private AuthenticationHandler authHandler;
    
    @Autowired
    private AuthorizationHandler authzHandler;
    
    @Autowired
    private ValidationHandler validationHandler;
    
    @PostConstruct
    public void setupChain() {
        authHandler.setNext(authzHandler);
        authzHandler.setNext(validationHandler);
    }
    
    public Object processRequest(Object request) {
        return authHandler.handle(request);
    }
}
```

### Example 4: State Management

```java
@Service
public class EventLifecycleService {
    
    @Autowired
    private ActivityLifecycle lifecycle;
    
    public boolean canUserBookEvent(Event event) {
        lifecycle.setEvent(event);
        return lifecycle.canBook();
    }
    
    public void updateEventState(Event event) {
        lifecycle.setEvent(event);
        lifecycle.transitionState();
    }
}
```

### Example 5: Enhanced Tickets with Decorator

```java
@Configuration
public class TicketServiceConfig {
    
    @Bean
    public ITicketService ticketService() {
        ITicketService base = new BaseTicketService();
        ITicketService signed = new SignedQrDecorator(base);
        return new AuditLogDecorator(signed);
    }
}

@Service
public class TicketingService {
    
    @Autowired
    private ITicketService ticketService;
    
    public String generateSecureTicket(Booking booking) {
        return ticketService.generateTicket(booking);
    }
}
```

---

## Testing Patterns

### Unit Test Example

```java
@SpringBootTest
class FactoryPatternTest {
    
    @Autowired
    private IModelFactory modelFactory;
    
    @Test
    void testCreateUser() {
        User user = modelFactory.createModel(User.class);
        assertNotNull(user);
    }
    
    @Test
    void testCreateEvent() {
        Event event = modelFactory.createModel(Event.class);
        assertNotNull(event);
    }
}
```

---

## Best Practices

### DO ✅
- Use dependency injection (@Autowired)
- Create beans in @Configuration classes when needed
- Chain decorators for multiple enhancements
- Setup chains in @PostConstruct methods
- Use appropriate strategy based on business rules
- Save mementos before critical operations

### DON'T ❌
- Don't create pattern instances manually (use Spring)
- Don't skip chain validation handlers
- Don't forget to call reset() on builders after build()
- Don't modify memento objects after creation
- Don't break the chain by returning null in handlers

---

## Troubleshooting

### Common Issues

**Q: Pattern classes not found?**
A: Make sure you've run `mvn clean compile` to compile all new classes.

**Q: Autowiring fails?**
A: Ensure classes are annotated with @Component or registered as @Bean.

**Q: Chain not working?**
A: Check that setNext() is called to link handlers properly.

**Q: Decorator not applying changes?**
A: Verify decorator wraps the service correctly in constructor.

---

## References

- Full Documentation: `DESIGN_PATTERNS_IMPLEMENTATION.md`
- PlantUML Mappings: `PATTERN_TO_DIAGRAM_MAPPING.md`
- Statistics: `IMPLEMENTATION_STATISTICS.md`
- Pattern Specs: `patterns_to_use.md`

---

## Support

For detailed information about each pattern, see:
- **DESIGN_PATTERNS_IMPLEMENTATION.md** - Complete guide with all patterns
- **Pattern source code** - Each class has JavaDoc comments
- **PlantUML diagrams** - In `Milestones/PM_3/Class Diagram/Before DP/`
