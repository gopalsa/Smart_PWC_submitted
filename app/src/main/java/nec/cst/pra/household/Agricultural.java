package nec.cst.pra.household;

import java.io.Serializable;

/**
 * Created by arthi on 12/3/2018.
 */

public class Agricultural implements Serializable{
    public String particulars;
    public String tickAppro;
    public String fertilizer;


    public Agricultural(String particulars, String tickAppro, String fertilizer
                       ) {
        this.particulars = particulars;
        this.tickAppro = tickAppro;
        this.fertilizer = fertilizer;

    }

    public String getParticulars() {
        return particulars;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }

    public String getTickAppro() {
        return tickAppro;
    }

    public void setTickAppro(String tickAppro) {
        this.tickAppro = tickAppro;
    }

    public String getFertilizer() {
        return fertilizer;
    }

    public void setFertilizer(String fertilizer) {
        this.fertilizer = fertilizer;
    }


}
