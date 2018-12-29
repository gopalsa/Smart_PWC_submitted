package nec.cst.pra.action.village;

import nec.cst.pra.action.villageDevelopment.VillageDevelopment;

/**
 * Created by SanAji on 30-11-2018.
 */

public class Village extends VillageDevelopment  {
    public String villageSerial;
    public String villageBlock;
    public String villageVillage;
    public String villageDistrict;
    public String villageHeadOfVillage;
    public String villageKeyContact;

    public Village(String villageDevelopmentSerial, String villageDevelopmentName, String villageDevelopmentStatus, String villageSerial, String villageBlock) {
        super(villageDevelopmentSerial, villageDevelopmentName, villageDevelopmentStatus);
        this.villageSerial = villageSerial;
        this.villageBlock = villageBlock;
        this.villageVillage = villageVillage;
        this.villageDistrict = villageDistrict;
        this.villageHeadOfVillage = villageHeadOfVillage;
        this.villageKeyContact = villageKeyContact;
    }



    public String getVillageSerial() {
        return villageSerial;
    }


    public void setVillageSerial(String villageSerial) {
        this.villageSerial = villageSerial;
    }

    public String getVillageVillage() {
        return villageVillage;
    }

    public void setVillageVillage(String villageVillage) {
        this.villageVillage = villageVillage;
    }

    public String getVillageBlock() {
        return villageBlock;
    }

    public void setVillageBlock(String villageBlock) {
        this.villageBlock = villageBlock;
    }

    public String getVillageDistrict() {
        return villageDistrict;
    }

    public void setVillageDistrict(String villageDistrict) {
        this.villageDistrict = villageDistrict;
    }

    public String getVillageHeadOfVillage() {
        return villageHeadOfVillage;
    }

    public void setVillageHeadOfVillage(String villageHeadOfVillage) {
        this.villageHeadOfVillage = villageHeadOfVillage;
    }

    public String getVillageKeyContact() {
        return villageKeyContact;
    }

    public void setVillageKeyContact(String villageKeyContact) {
        this.villageKeyContact = villageKeyContact;
    }
}
