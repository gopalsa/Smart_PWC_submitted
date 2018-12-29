package nec.cst.pra.action.household;

/**
 * Created by SanAji on 30-11-2018.
 */

public class HouseHold {
    public String householdSerial;
    public String householdVillage;
    public String householdComplete;
    public String householdToComplete;

    public HouseHold(String householdSerial, String householdVillage, String householdComplete, String householdToComplete) {
        this.householdSerial = householdSerial;
        this.householdVillage = householdVillage;
        this.householdComplete = householdComplete;
        this.householdToComplete = householdToComplete;
    }

    public String getHouseholdSerial() {
        return householdSerial;
    }

    public void setHouseholdSerial(String householdSerial) {
        this.householdSerial = householdSerial;
    }

    public String getHouseholdVillage() {
        return householdVillage;
    }

    public void setHouseholdVillage(String householdVillage) {
        this.householdVillage = householdVillage;
    }

    public String getHouseholdComplete() {
        return householdComplete;
    }

    public void setHouseholdComplete(String householdComplete) {
        this.householdComplete = householdComplete;
    }

    public String getHouseholdToComplete() {
        return householdToComplete;
    }

    public void setHouseholdToComplete(String householdToComplete) {
        this.householdToComplete = householdToComplete;
    }


}
