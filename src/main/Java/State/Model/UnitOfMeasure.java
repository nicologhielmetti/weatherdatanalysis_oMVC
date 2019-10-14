package State.Model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="UnitOfMeasure")
public class UnitOfMeasure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUnitOfMeasure;
    private String temperature;
    private String pressure;
    private String humidity;
    private String rain;
    private String windModule;
    private String windDirection;
    private String dewPoint;
    private String uvRadiation;
    private String snowLevel;
    private String pollutionLevel;

    public UnitOfMeasure() {
    }

    public UnitOfMeasure(String temperature, String pressure, String humidity, String rain, String windModule, String windDirection, String dewPoint, String uvRadiation, String snowLevel, String pollutionLevel) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.rain = rain;
        this.windModule = windModule;
        this.windDirection = windDirection;
        this.dewPoint = dewPoint;
        this.uvRadiation = uvRadiation;
        this.snowLevel = snowLevel;
        this.pollutionLevel = pollutionLevel;
    }

    public Integer getIdUnitOfMeasure() {
        return idUnitOfMeasure;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getRain() {
        return rain;
    }

    public void setRain(String rain) {
        this.rain = rain;
    }

    public String getWindModule() {
        return windModule;
    }

    public void setWindModule(String windModule) {
        this.windModule = windModule;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getDewPoint() {
        return dewPoint;
    }

    public void setDewPoint(String dewPt) {
        this.dewPoint = dewPoint;
    }

    public String getUvRadiation() {
        return uvRadiation;
    }

    public void setUvRadiation(String uvRadiation) {
        this.uvRadiation = uvRadiation;
    }

    public String getSnowLevel() {
        return snowLevel;
    }

    public void setSnowLevel(String snowLevel) {
        this.snowLevel = snowLevel;
    }

    public String getPollutionLevel() {
        return pollutionLevel;
    }

    public void setPollutionLevel(String pollutionLevel) {
        this.pollutionLevel = pollutionLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnitOfMeasure that = (UnitOfMeasure) o;
        return Objects.equals(idUnitOfMeasure, that.idUnitOfMeasure) &&
                Objects.equals(temperature, that.temperature) &&
                Objects.equals(pressure, that.pressure) &&
                Objects.equals(humidity, that.humidity) &&
                Objects.equals(rain, that.rain) &&
                Objects.equals(windModule, that.windModule) &&
                Objects.equals(windDirection, that.windDirection) &&
                Objects.equals(dewPoint, that.dewPoint) &&
                Objects.equals(uvRadiation, that.uvRadiation) &&
                Objects.equals(snowLevel, that.snowLevel) &&
                Objects.equals(pollutionLevel, that.pollutionLevel);
    }

}
