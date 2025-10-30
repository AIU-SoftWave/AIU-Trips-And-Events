package barista.Drinks.Tea;

public class EnglishTea implements ITea {
    private String name;
    private int temperature;
    private String teaType;
    
    public EnglishTea() {
        this.name = "English Tea";
        this.temperature = 80;
        this.teaType = "English";
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
    public String getTeaType() {
        return teaType;
    }

    @Override
    public void setTeaType(String teaType) {
        this.teaType = teaType;
    }
}
