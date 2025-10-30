package barista.Factories;

import barista.Builders.IBuilder;
import barista.Builders.BuilderFacade;
import barista.Directors.IDrinkDirector;
import barista.Directors.DrinkDirectorFacade;
import barista.Drinks.Coffee.CoffeeBuilder;
import barista.Drinks.Coffee.ICoffee;
import barista.Drinks.Tea.ITea;
import barista.Drinks.Tea.TeaBuilder;

/**
 * Classic Factory - creates Latte Coffee and English Tea
 * Uses Facade pattern to interact with Director and Builder subsystems
 * Applies DIP: Depends on facades (abstractions), not concrete implementations
 */
public class ClassicFactory extends AFactory {
    private IDrinkDirector director;
    private IBuilder builder;
    
    public ClassicFactory() {
        this.director = new DrinkDirectorFacade();
        this.builder = new BuilderFacade();
    }

    @Override
    public ICoffee createCoffee() {
        CoffeeBuilder coffeeBuilder = builder.createCoffeeBuilder();
        return director.buildCoffee(coffeeBuilder, "Latte");
    }

    @Override
    public ITea createTea() {
        TeaBuilder teaBuilder = builder.createTeaBuilder();
        return director.buildTea(teaBuilder, "English");
    }
}
