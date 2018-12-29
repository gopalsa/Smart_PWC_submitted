package nec.cst.pra.household;

import java.io.Serializable;

/**
 * Created by arthi on 12/3/2018.
 */

public class EnergyPower implements Serializable {
    public String application;
    public String nos;
    public String duration;

    public EnergyPower(String application, String nos, String duration) {
        this.application = application;
        this.nos = nos;
        this.duration = duration;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getNos() {
        return nos;
    }

    public void setNos(String nos) {
        this.nos = nos;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
