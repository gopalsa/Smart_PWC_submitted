package smart.cst.pwc.action.miscellaneous;

/**
 * Created by SanAji on 30-11-2018.
 */

public class Miscellaneous {
    public String miscellaneousSerial;
    public String miscellaneousTitle;
    public String miscellaneousStatus;
    public String miscellaneousComments;

    public Miscellaneous(String miscellaneousSerial, String miscellaneousTitle, String miscellaneousStatus, String miscellaneousComments) {
        this.miscellaneousSerial = miscellaneousSerial;
        this.miscellaneousTitle = miscellaneousTitle;
        this.miscellaneousStatus = miscellaneousStatus;
        this.miscellaneousComments = miscellaneousComments;
    }

    public String getMiscellaneousSerial() {
        return miscellaneousSerial;
    }
    public void setMiscellaneousSerial(String miscellaneousSerial) {
        this.miscellaneousSerial = miscellaneousSerial;
    }

    public String getMiscellaneousTitle() {
        return miscellaneousTitle;
    }

    public void setMiscellaneousTitle(String miscellaneousTitle) {
        this.miscellaneousTitle = miscellaneousTitle;
    }

    public String getMiscellaneousStatus() {
        return miscellaneousStatus;
    }

    public void setMiscellaneousStatus(String miscellaneousStatus) {
        this.miscellaneousStatus = miscellaneousStatus;
    }

    public String getMiscellaneousComments() {
        return miscellaneousComments;
    }

    public void setMiscellaneousComments(String miscellaneousComments) {
        this.miscellaneousComments = miscellaneousComments;
    }
}
