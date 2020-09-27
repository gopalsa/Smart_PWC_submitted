package smart.cst.pwc.fgd;

/**
 * Created by jmpriyanka on 01-12-2018.
 */

public class Peopleattend {
    public String men;
    public String women;
    public String total;

    public Peopleattend(String men, String women, String total) {
        this.men = men;
        this.women = women;
        this.total = total;
    }

    public String getMen() {
        return men;
    }

    public void setMen(String men) {
        this.men = men;
    }

    public String getWomen() {
        return women;
    }

    public void setWomen(String women) {
        this.women = women;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
