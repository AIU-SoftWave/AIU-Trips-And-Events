package barista.Directors;

import barista.Drinks.Coffee.BlackCoffee;
import barista.Drinks.Coffee.CoffeeBuilder;
import barista.Drinks.Coffee.ICoffee;
import barista.Drinks.Coffee.LatteCoffe;

/**
 * Director class that knows how to build different types of coffee
 * using the CoffeeBuilder
 */
public class CoffeeDirector {
    
    /**
     * Build a Latte Coffee using the builder
     */
    public ICoffee makeLatte(CoffeeBuilder builder) {
        builder.reset();
        
        // Create the base product
        LatteCoffe latte = new LatteCoffe();
        builder.drink = latte;
        
        // Configure using builder methods
        builder.setName("Latte Coffee");
        builder.setTemperature(65);
        builder.setCoffeeType("Latte");
        
        return builder.build();
    }
    
    /**
     * Build a Black Coffee using the builder
     */
    public ICoffee makeBlackCoffee(CoffeeBuilder builder) {
        builder.reset();
        
        // Create the base product
        BlackCoffee blackCoffee = new BlackCoffee();
        builder.drink = blackCoffee;
        
        // Configure using builder methods
        builder.setName("Black Coffee");
        builder.setTemperature(85);
        builder.setCoffeeType("Black");
        
        return builder.build();
    }
}
