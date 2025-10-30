package barista.Directors;

import barista.Drinks.Tea.EnglishTea;
import barista.Drinks.Tea.GreenTea;
import barista.Drinks.Tea.ITea;
import barista.Drinks.Tea.TeaBuilder;

/**
 * Director class that knows how to build different types of tea
 * using the TeaBuilder
 */
public class TeaDirector {
    
    /**
     * Build an English Tea using the builder
     */
    public ITea makeEnglishTea(TeaBuilder builder) {
        builder.reset();
        
        // Create the base product
        EnglishTea englishTea = new EnglishTea();
        builder.drink = englishTea;
        
        // Configure using builder methods
        builder.setName("English Tea");
        builder.setTemperature(85);
        builder.setTeaType("English");
        
        return builder.build();
    }
    
    /**
     * Build a Green Tea using the builder
     */
    public ITea makeGreenTea(TeaBuilder builder) {
        builder.reset();
        
        // Create the base product
        GreenTea greenTea = new GreenTea();
        builder.drink = greenTea;
        
        // Configure using builder methods
        builder.setName("Green Tea");
        builder.setTemperature(70);
        builder.setTeaType("Green");
        
        return builder.build();
    }
}
