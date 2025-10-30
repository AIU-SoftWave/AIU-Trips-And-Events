package barista.Builders;

import barista.Drinks.Coffee.CoffeeBuilder;
import barista.Drinks.Tea.TeaBuilder;

/**
 * Interface for Builder Factory
 * Defines methods to create different types of builders
 */
public interface IBuilder {
    
    /**
     * Create a new coffee builder
     */
    CoffeeBuilder createCoffeeBuilder();
    
    /**
     * Create a new tea builder
     */
    TeaBuilder createTeaBuilder();
}
