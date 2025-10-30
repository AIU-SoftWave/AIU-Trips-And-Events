package barista;

import barista.Factories.AFactory;
import barista.Drinks.IDrink;
import barista.decorator.MilkAddon;

public class BaristaMachine {
    private AFactory factory;

    public BaristaMachine(AFactory factory) {
        this.factory = factory;
    }
    public void serveCombo() {
        System.out.println(factory.createCoffee().getName());
        System.out.println(factory.createTea().getName());
    }
    public void serveCoffee() {
        System.out.println(factory.createCoffee().getName());
    }
    public void serveTea() {
        System.out.println(factory.createTea().getName());
    }
    
    public void serveCoffeeWithMilk() {
        // Create base coffee
        IDrink coffee = factory.createCoffee();
        
        // Decorate with milk addon
        coffee = new MilkAddon(coffee);
        
        System.out.println(coffee.getName());
    }
}
