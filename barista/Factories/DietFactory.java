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
 * Diet Factory - creates Black Coffee and Green Tea
 * Uses Facade pattern to interact with Director and Builder subsystems
 * Applies DIP: Depends on facades (abstractions), not concrete implementations
 */
public class DietFactory extends AFactory {
    private IDrinkDirector director;
    private IBuilder builder;
    
    public DietFactory() {
        this.director = new DrinkDirectorFacade();
        this.builder = new BuilderFacade();
    }

    @Override
    public ICoffee createCoffee() {
        CoffeeBuilder coffeeBuilder = builder.createCoffeeBuilder();
        return director.buildCoffee(coffeeBuilder, "Black");
    }

    @Override
    public ITea createTea() {
        TeaBuilder teaBuilder = builder.createTeaBuilder();
        return director.buildTea(teaBuilder, "Green");
    }
}
