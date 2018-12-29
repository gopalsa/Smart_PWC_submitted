package nec.cst.pra.action.technologyCustomisation;

/**
 * Created by SanAji on 30-11-2018.
 */

public class TechnologyCustomisation {
    public String technologyCustomisationSerial;
    public String technologyCustomisationtTitle;
    public String technologyCustomisationStatus;
    public String technologyCustomisationComments;

    public TechnologyCustomisation(String technologyCustomisationSerial, String technologyCustomisationtTitle, String technologyCustomisationStatus, String technologyCustomisationComments) {
        this.technologyCustomisationSerial = technologyCustomisationSerial;
        this.technologyCustomisationtTitle = technologyCustomisationtTitle;
        this.technologyCustomisationStatus = technologyCustomisationStatus;
        this.technologyCustomisationComments = technologyCustomisationComments;
    }


    public String getTechnologyCustomisationSerial() {
        return technologyCustomisationSerial;
    }
    public void setTechnologyCustomisationSerial(String technologyCustomisationSerial) {
        this.technologyCustomisationSerial = technologyCustomisationSerial;
    }

    public String getTechnologyCustomisationtTitle() {
        return technologyCustomisationtTitle;
    }

    public void setTechnologyCustomisationtTitle(String technologyCustomisationtTitle) {
        this.technologyCustomisationtTitle = technologyCustomisationtTitle;
    }

    public String getTechnologyCustomisationStatus() {
        return technologyCustomisationStatus;
    }

    public void setTechnologyCustomisationStatus(String technologyCustomisationStatus) {
        this.technologyCustomisationStatus = technologyCustomisationStatus;
    }

    public String getTechnologyCustomisationComments() {
        return technologyCustomisationComments;
    }

    public void setTechnologyCustomisationComments(String technologyCustomisationComments) {
        this.technologyCustomisationComments = technologyCustomisationComments;
    }
}
