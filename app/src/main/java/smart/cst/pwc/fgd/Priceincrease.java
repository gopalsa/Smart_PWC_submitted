package smart.cst.pwc.fgd;

/**
 * Created by jmpriyanka on 01-12-2018.
 */

public class Priceincrease {
    public String produce;
    public String yesnum;
    public String nonum;

    public Priceincrease(String produce, String yesnum, String nonum) {
        this.produce = produce;
        this.yesnum = yesnum;
        this.nonum = nonum;
    }

    public String getProduce() {
        return produce;
    }

    public void setProduce(String produce) {
        this.produce = produce;
    }

    public String getYesnum() {
        return yesnum;
    }

    public void setYesnum(String yesnum) {
        this.yesnum = yesnum;
    }

    public String getNonum() {
        return nonum;
    }

    public void setNonum(String nonum) {
        this.nonum = nonum;
    }
}
