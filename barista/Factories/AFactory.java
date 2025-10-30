package barista.Factories;

import barista.Drinks.Coffee.ICoffee;
import barista.Drinks.Tea.ITea;

public abstract class AFactory {
    public abstract ICoffee createCoffee();
    public abstract ITea createTea();
}
