package smart.cst.pwc.fgd;

/**
 * Created by jmpriyanka on 01-12-2018.
 */

public class Storage {
    public String facility;
    public String yes;
    public String no;
    public String farmdist;

    public Storage(String facility, String yes, String no, String farmdist) {
        this.facility = facility;
        this.yes = yes;
        this.no = no;
        this.farmdist = farmdist;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public String getYes() {
        return yes;
    }

    public void setYes(String yes) {
        this.yes = yes;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getFarmdist() {
        return farmdist;
    }

    public void setFarmdist(String farmdist) {
        this.farmdist = farmdist;
    }
}
