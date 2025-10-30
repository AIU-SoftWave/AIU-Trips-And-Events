package barista.Builders;

import barista.Drinks.Coffee.CoffeeBuilder;
import barista.Drinks.Tea.TeaBuilder;

/**
 * Facade for Builder Subsystem
 * Provides simplified interface for creating builders
 * Other subsystems interact with Builder Subsystem through this facade
 */
public class BuilderFacade implements IBuilder {
    
    /**
     * Create a new coffee builder
     */
    public CoffeeBuilder createCoffeeBuilder() {
        return new CoffeeBuilder();
    }
    
    /**
     * Create a new tea builder
     */
    public TeaBuilder createTeaBuilder() {
        return new TeaBuilder();
    }
}
