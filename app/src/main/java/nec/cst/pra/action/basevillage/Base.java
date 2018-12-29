package nec.cst.pra.action.basevillage;

/**
 * Created by SanAji on 30-11-2018.
 */

public class Base {
    public String baseVillageSerial;
    public String baseVillageVillage;
    public String baseVillageComplete;

    public Base(String baseVillageSerial, String baseVillageVillage, String activityDetailSerial) {
        this.baseVillageSerial = baseVillageSerial;
        this.baseVillageVillage = baseVillageVillage;
        this.baseVillageComplete = activityDetailSerial;
    }

    public String getBaseVillageSerial() {
        return baseVillageSerial;
    }

    public void setBaseVillageSerial(String baseVillageSerial) {
        this.baseVillageSerial = baseVillageSerial;
    }

    public String getBaseVillageVillage() {
        return baseVillageVillage;
    }

    public void setBaseVillageVillage(String baseVillageVillage) {
        this.baseVillageVillage = baseVillageVillage;
    }

    public String getbaseVillageComplete() {
        return baseVillageComplete;
    }

    public void setbaseVillageComplete(String baseVillageComplete) {
        this.baseVillageComplete = baseVillageComplete;
    }

    public static void add(Base bean) {
    }
}
