package barista.Drinks.Coffee;

public class BlackCoffee implements ICoffee  {
    private String name;
    private int temperature;
    private String coffeeType;
    
    public BlackCoffee() {
        this.name = "Black Coffee";
        this.temperature = 80;
        this.coffeeType = "Black";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getTemperature() {
        return temperature;
    }

    @Override
    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    @Override
    public String getCoffeeType() {
        return coffeeType;
    }

    @Override
    public void setCoffeeType(String coffeeType) {
        this.coffeeType = coffeeType;
    }
}
