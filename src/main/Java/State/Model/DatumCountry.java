package State.Model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "DatumCountry")
public class DatumCountry extends Datum {

    private Float dewPoint;

    public DatumCountry() {}

    public DatumCountry(DatumPK datumPK, Float temperature, Float pressure, Float humidity, Float rain, Float windModule, String windDirection, Float dewPoint) {
        super(datumPK,temperature,pressure,humidity,rain,windModule,windDirection);
        this.dewPoint = dewPoint;
    }

    public Float getDewPoint() {
        return dewPoint;
    }

    public void setDewPoint(Float dewPoint) {
        this.dewPoint = dewPoint;
    }

    @Override
    public String getFieldsNameAsCSV() {
        return super.getFieldsNameAsCSV() + "," + "dewPoint";
    }

    @Override
    public String getFieldsAsCSV() {
        return super.getFieldsAsCSV() + "," + dewPoint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DatumCountry that = (DatumCountry) o;
        return Objects.equals(dewPoint, that.dewPoint);
    }
}
