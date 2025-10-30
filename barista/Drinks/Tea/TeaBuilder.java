package barista.Drinks.Tea;

import barista.Builders.AbstractDrinkBuilder;

/**
 * Concrete builder for Tea products
 */
public class TeaBuilder extends AbstractDrinkBuilder<ITea> {
    
    /**
     * Set the tea type (Green, English, Black, etc.)
     */
    public void setTeaType(String type) {
        if (drink != null) {
            drink.setTeaType(type);
        }
    }
}
