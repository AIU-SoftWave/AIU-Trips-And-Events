package barista.Builders;

import barista.Drinks.IDrink;

/**
 * Abstract base class for drink builders
 * Provides common implementation for building drinks
 */
public abstract class AbstractDrinkBuilder<T extends IDrink> implements IDrinkBuilder<T> {
    // Made public so Director can set it
    public T drink;

    @Override
    public void reset() {
        drink = null; // reset state, wait for director to set a new instance
    }

    @Override
    public void setName(String name) {
        if (drink != null)
            drink.setName(name);
    }

    @Override
    public void setTemperature(int temperature) {
        if (drink != null)
            drink.setTemperature(temperature);
    }

    @Override
    public T build() {
        return drink;
    }
}
