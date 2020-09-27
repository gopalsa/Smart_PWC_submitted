package smart.cst.pwc.electricity;

import java.io.Serializable;

public class Electricity implements Serializable {
    String name;
    String electricalAppliances;
    String eCount;
    String eHousrs;

    public Electricity(String name, String electricalAppliances, String eCount, String eHousrs) {
        this.name = name;
        this.electricalAppliances = electricalAppliances;
        this.eCount = eCount;
        this.eHousrs = eHousrs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getElectricalAppliances() {
        return electricalAppliances;
    }

    public void setElectricalAppliances(String electricalAppliances) {
        this.electricalAppliances = electricalAppliances;
    }

    public String geteCount() {
        return eCount;
    }

    public void seteCount(String eCount) {
        this.eCount = eCount;
    }

    public String geteHousrs() {
        return eHousrs;
    }

    public void seteHousrs(String eHousrs) {
        this.eHousrs = eHousrs;
    }
}
