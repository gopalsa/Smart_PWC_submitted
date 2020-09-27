package smart.cst.pwc.needs;

import java.io.Serializable;

public class Need implements Serializable {
    String name;
    String isAvailable;
    String distance;
    String count;

    public Need(String name, String isAvailable, String distance, String count) {
        this.name = name;
        this.isAvailable = isAvailable;
        this.distance = distance;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(String isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
