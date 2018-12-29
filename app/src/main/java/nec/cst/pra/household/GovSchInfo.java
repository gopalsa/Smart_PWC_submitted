package nec.cst.pra.household;

import java.io.Serializable;

/**
 * Created by arthi on 12/3/2018.
 */

public class GovSchInfo implements Serializable {
    public String govName;
    public String govpersonBenfit;

    public GovSchInfo(String govName, String govpersonBenfit) {
        this.govName = govName;
        this.govpersonBenfit = govpersonBenfit;
    }

    public String getGovName() {
        return govName;
    }

    public void setGovName(String govName) {
        this.govName = govName;
    }

    public String getGovpersonBenfit() {
        return govpersonBenfit;
    }

    public void setGovpersonBenfit(String govpersonBenfit) {
        this.govpersonBenfit = govpersonBenfit;
    }
}
