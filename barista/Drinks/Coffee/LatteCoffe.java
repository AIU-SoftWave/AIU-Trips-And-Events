package barista.Drinks.Coffee;

public class LatteCoffe implements ICoffee {
    private String name;
    private int temperature;
    private String coffeeType;
    
    public LatteCoffe() {
        this.name = "Latte Coffee";
        this.temperature = 60;
        this.coffeeType = "Latte Coffee";
    }
    @Override
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }
    @Override
    public void setCoffeeType(String coffeeType) {
        this.coffeeType = coffeeType;
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public int getTemperature() {
        return temperature;
    }
    @Override
    public String getCoffeeType() {
        return coffeeType;
    }
}
