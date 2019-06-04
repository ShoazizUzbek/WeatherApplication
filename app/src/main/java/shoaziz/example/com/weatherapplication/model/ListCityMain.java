package shoaziz.example.com.weatherapplication.model;

public class ListCityMain {
    private int id;
    private String name;
    private String weatherCondition;
    private int celsius;
    public ListCityMain(int id) {
        this.id = id;
    }


    public ListCityMain(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public ListCityMain(int id, String name, String weatherCondition, int celsius) {
        this.celsius = celsius;
        this.weatherCondition = weatherCondition;
        this.id = id;
        this.name = name;
    }
    public int getCelsius() {
        return celsius;
    }

    public void setCelsius(int celsius) {
        this.celsius = celsius;
    }
    public String getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(String weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
