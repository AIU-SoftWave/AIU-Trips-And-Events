package barista.Drinks.Tea;

import barista.Drinks.IDrink;

public interface ITea extends IDrink {
    String getTeaType();
    void setTeaType(String teaType);
}
