package Model;

public class Entity {
    private String outlook;
    private String temperature;
    private String humidity;
    private String windy;
    private String play;

    public Entity(String outlook, String temperature, String humidity, String windy, String play) {
        this.outlook = outlook;
        this.temperature = temperature;
        this.humidity = humidity;
        this.windy = windy;
        this.play = play;
    }

    public String getOutlook() {
        return outlook;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getWindy() {
        return windy;
    }

    public String getPlay() {
        return play;
    }
}
