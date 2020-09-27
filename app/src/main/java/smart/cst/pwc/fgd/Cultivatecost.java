package smart.cst.pwc.fgd;

/**
 * Created by jmpriyanka on 01-12-2018.
 */

public class Cultivatecost {
    public String factors;
    public String cropone;
    public String croptwo;
    public String cropthree;

    public Cultivatecost(String factors, String cropone, String croptwo, String cropthree) {
        this.factors = factors;
        this.cropone = cropone;
        this.croptwo = croptwo;
        this.cropthree = cropthree;
    }

    public String getFactors() {
        return factors;
    }

    public void setFactors(String factors) {
        this.factors = factors;
    }

    public String getCropone() {
        return cropone;
    }

    public void setCropone(String cropone) {
        this.cropone = cropone;
    }

    public String getCroptwo() {
        return croptwo;
    }

    public void setCroptwo(String croptwo) {
        this.croptwo = croptwo;
    }

    public String getCropthree() {
        return cropthree;
    }

    public void setCropthree(String cropthree) {
        this.cropthree = cropthree;
    }
}
