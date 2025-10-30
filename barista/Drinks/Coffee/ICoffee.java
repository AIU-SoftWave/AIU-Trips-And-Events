package barista.Drinks.Coffee;

import barista.Drinks.IDrink;

public interface ICoffee extends IDrink {
    String getCoffeeType();
    void setCoffeeType(String coffeeType);
}
