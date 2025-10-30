package barista.decorator;

import barista.Drinks.IDrink;

/**
 * Concrete decorator that adds sugar to a drink.
 */
public class SugarAddon extends DrinkDecorator {

    public SugarAddon(IDrink drink) {
        super(drink);
    }

    @Override
    public String getName() {
        return wrapped.getName() + " with Sugar";
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
