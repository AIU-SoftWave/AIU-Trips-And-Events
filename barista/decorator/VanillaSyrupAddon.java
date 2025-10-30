package barista.decorator;

import barista.Drinks.IDrink;

/**
 * Concrete decorator that adds vanilla syrup to a drink.
 */
public class VanillaSyrupAddon extends DrinkDecorator {

    public VanillaSyrupAddon(IDrink drink) {
        super(drink);
    }

    @Override
    public String getName() {
        return wrapped.getName() + " with Vanilla Syrup";
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
