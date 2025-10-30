package barista.Builders;

import barista.Drinks.IDrink;

public interface IDrinkBuilder<T extends IDrink> {
    void reset();

    void setName(String name);

    void setTemperature(int temperature);

    T build();
}
