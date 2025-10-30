package barista.Directors;

import barista.Drinks.Coffee.CoffeeBuilder;
import barista.Drinks.Coffee.ICoffee;
import barista.Drinks.Tea.ITea;
import barista.Drinks.Tea.TeaBuilder;

/**
 * Facade for Director Subsystem
 * Provides simplified interface for building drinks
 * Other subsystems interact with Director Subsystem through this facade
 */
public class DrinkDirectorFacade implements IDrinkDirector {
    private final CoffeeDirector coffeeDirector;
    private final TeaDirector teaDirector;
    
    public enum CoffeeType {
        LATTE,
        BLACK
    }
    
    public enum TeaType {
        ENGLISH,
        GREEN
    }
    
    public DrinkDirectorFacade() {
        this.coffeeDirector = new CoffeeDirector();
        this.teaDirector = new TeaDirector();
    }
    
    /**
     * Build coffee using the coffee director
     */
    public ICoffee buildCoffee(CoffeeBuilder builder, String coffeeType) {
        try {
            CoffeeType type = CoffeeType.valueOf(coffeeType.toUpperCase());
            return switch (type) {
                case LATTE -> coffeeDirector.makeLatte(builder);
                case BLACK -> coffeeDirector.makeBlackCoffee(builder);
            };
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid coffee type: " + coffeeType);
        }
    }
    
    /**
     * Build tea using the tea director
     */
    public ITea buildTea(TeaBuilder builder, String teaType) {
        try {
            TeaType type = TeaType.valueOf(teaType.toUpperCase());
            return switch (type) {
                case ENGLISH -> teaDirector.makeEnglishTea(builder);
                case GREEN -> teaDirector.makeGreenTea(builder);
            };
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid tea type: " + teaType);
        }
    }
}
