package barista.Drinks.Tea;

public class GreenTea implements ITea {
    private String name;
    private int temperature;
    private String teaType;

    public GreenTea() {
        this.name = "Green Tea";
        this.temperature = 60;
        this.teaType = "Green Tea";
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
    public void setTeaType(String teaType) {
        this.teaType = teaType;
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
    public String getTeaType() {
        return teaType;
    }
}
