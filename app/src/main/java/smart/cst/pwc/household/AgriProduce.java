package smart.cst.pwc.household;

import java.io.Serializable;

/**
 * Created by arthi on 12/3/2018.
 */

public class AgriProduce implements Serializable {
    public String crop;
    public String cropPrev;
    public String product;

    public AgriProduce(String crop, String cropPrev, String product) {
        this.crop = crop;
        this.cropPrev = cropPrev;
        this.product = product;
    }

    public String getCrop() {
        return crop;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    public String getCropPrev() {
        return cropPrev;
    }

    public void setCropPrev(String cropPrev) {
        this.cropPrev = cropPrev;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }
}
