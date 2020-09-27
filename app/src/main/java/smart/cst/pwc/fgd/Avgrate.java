package smart.cst.pwc.fgd;

/**
 * Created by jmpriyanka on 01-12-2018.
 */

public class Avgrate {
    public String cropname;
    public String min;
    public String max;

    public Avgrate(String cropname, String min, String max) {
        this.cropname = cropname;
        this.min = min;
        this.max = max;
    }

    public String getCropname() {
        return cropname;
    }

    public void setCropname(String cropname) {
        this.cropname = cropname;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }
}
