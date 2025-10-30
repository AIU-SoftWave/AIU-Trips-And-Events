package barista.Drinks.Coffee;

import barista.Builders.AbstractDrinkBuilder;

/**
 * Concrete builder for Coffee products
 */
public class CoffeeBuilder extends AbstractDrinkBuilder<ICoffee> {
    
    /**
     * Set the coffee type (Latte, Black, Espresso, etc.)
     */
    public void setCoffeeType(String type) {
        if (drink != null) {
            drink.setCoffeeType(type);
        }
    }
}
