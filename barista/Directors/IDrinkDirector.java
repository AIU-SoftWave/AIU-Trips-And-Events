package barista.Directors;

import barista.Drinks.Coffee.CoffeeBuilder;
import barista.Drinks.Coffee.ICoffee;
import barista.Drinks.Tea.ITea;
import barista.Drinks.Tea.TeaBuilder;

/**
 * Interface for Director Subsystem
 * Defines the contract for building drinks
 */
public interface IDrinkDirector {
    
    /**
     * Build coffee using the provided builder
     */
    ICoffee buildCoffee(CoffeeBuilder builder, String coffeeType);
    
    /**
     * Build tea using the provided builder
     */
    ITea buildTea(TeaBuilder builder, String teaType);
}
