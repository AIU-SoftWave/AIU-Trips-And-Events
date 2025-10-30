package barista.decorator;

import barista.Drinks.IDrink;

/**
 * Base abstract decorator class that wraps an IDrink component.
 * All concrete decorators extend this class.
 */
public abstract class DrinkDecorator implements IDrink {
    protected IDrink wrapped;

    public DrinkDecorator(IDrink drink) {
        this.wrapped = drink;
    }

    @Override
    public abstract String getName();

    @Override
    public abstract int getTemperature();

    @Override
    public abstract void setName(String name);

    @Override
    public abstract void setTemperature(int temp);
}
