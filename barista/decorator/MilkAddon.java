package barista.decorator;

import barista.Drinks.IDrink;

/**
 * Concrete decorator that adds milk to a drink.
 */
public class MilkAddon extends DrinkDecorator {

    public MilkAddon(IDrink drink) {
        super(drink);
    }

    @Override
    public String getName() {
        return wrapped.getName() + " with Milk";
    }

    @Override
    public int getTemperature() {
        return wrapped.getTemperature();
    }

    @Override
    public void setName(String name) {
        wrapped.setName(name);
    }

    @Override
    public void setTemperature(int temp) {
        wrapped.setTemperature(temp);
    }
}
