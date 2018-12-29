package nec.cst.pra.action.villageDevelopment;

/**
 * Created by SanAji on 30-11-2018.
 */

public class VillageDevelopment {
    public String villageDevelopmentSerial;
    public String villageDevelopmentName;
    public String villageDevelopmentStatus;

    public VillageDevelopment(String villageDevelopmentSerial, String villageDevelopmentName, String villageDevelopmentStatus) {
        this.villageDevelopmentSerial = villageDevelopmentSerial;
        this.villageDevelopmentName = villageDevelopmentName;
        this.villageDevelopmentStatus = villageDevelopmentStatus;
    }

    public String getVillageDevelopmentSerial() {
        return villageDevelopmentSerial;
    }

    public void setVillageDevelopmentSerial(String villageDevelopmentSerial) {
        this.villageDevelopmentSerial = villageDevelopmentSerial;
    }

    public String getVillageDevelopmentName() {
        return villageDevelopmentName;
    }

    public void setVillageDevelopmentName(String villageDevelopmentName) {
        this.villageDevelopmentName = villageDevelopmentName;
    }

    public String getVillageDevelopmentStatus() {
        return villageDevelopmentStatus;
    }

    public void setVillageDevelopmentStatus(String villageDevelopmentStatus) {
        this.villageDevelopmentStatus = villageDevelopmentStatus;
    }

    public static void add(VillageDevelopment bean) {
    }
}
