package shoaziz.example.com.weatherapplication.model;

public class Statistics {
    private String day;
    private int night,noon;

    public Statistics(String day, int night, int noon) {
        this.day = day;
        this.night = night;
        this.noon = noon;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getNight() {
        return night;
    }

    public void setNight(int night) {
        this.night = night;
    }

    public int getNoon() {
        return noon;
    }

    public void setNoon(int noon) {
        this.noon = noon;
    }
}
