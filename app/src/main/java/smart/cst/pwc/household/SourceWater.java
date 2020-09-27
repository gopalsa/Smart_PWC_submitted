package smart.cst.pwc.household;/*87/8

/**
 * Created by arthi on 12/3/2018.
 */

import java.io.Serializable;

public class SourceWater implements Serializable {
    public String sourWater;
    public String option;
    public String distance;
    public String waterStorage;

    public SourceWater(String sourWater, String option, String distance, String waterStorage) {
        this.sourWater = sourWater;
        this.option = option;
        this.distance = distance;
        this.waterStorage = waterStorage;
    }

    public String getSourWater() {
        return sourWater;
    }

    public void setSourWater(String sourWater) {
        this.sourWater = sourWater;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getWaterStorage() {
        return waterStorage;
    }

    public void setWaterStorage(String waterStorage) {
        this.waterStorage = waterStorage;
    }
}
