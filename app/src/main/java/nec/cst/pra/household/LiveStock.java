package nec.cst.pra.household;

import java.io.Serializable;

/**
 * Created by arthi on 12/3/2018.
 */

public class LiveStock implements Serializable {
    public String animals;
    public String shelter;
    public String average;
    public String wasteKgs;

    public LiveStock(String animals, String shelter, String average, String wasteKgs) {
        this.animals = animals;
        this.shelter = shelter;
        this.average = average;
        this.wasteKgs = wasteKgs;
    }

    public String getAnimals() {
        return animals;
    }

    public void setAnimals(String animals) {
        this.animals = animals;
    }

    public String getShelter() {
        return shelter;
    }

    public void setShelter(String shelter) {
        this.shelter = shelter;
    }

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }

    public String getWasteKgs() {
        return wasteKgs;
    }

    public void setWasteKgs(String wasteKgs) {
        this.wasteKgs = wasteKgs;
    }
}
