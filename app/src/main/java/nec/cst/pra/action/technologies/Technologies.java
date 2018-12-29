package nec.cst.pra.action.technologies;

/**
 * Created by SanAji on 30-11-2018.
 */

public class Technologies {
    public String technologiesSerial;
    public String technologiesTechnology;
    public String technologiesSubjectArea;
    public String technologiesWhetherTransferred;
    public String technologiesImpact;

    public Technologies(String technologiesSerial, String technologiesTechnology, String technologiesSubjectArea, String technologiesWhetherTransferred, String technologiesImpact) {
        this.technologiesSerial = technologiesSerial;
        this.technologiesTechnology = technologiesTechnology;
        this.technologiesSubjectArea = technologiesSubjectArea;
        this.technologiesWhetherTransferred = technologiesWhetherTransferred;
        this.technologiesImpact = technologiesImpact;
    }

    public String getTechnologiesSerial() {
        return technologiesSerial;
    }

    public void setTechnologiesSerial(String technologiesSerial) {
        this.technologiesSerial = technologiesSerial;
    }

    public String getTechnologiesTechnology() {
        return technologiesTechnology;
    }

    public void setTechnologiesTechnology(String technologiesTechnology) {
        this.technologiesTechnology = technologiesTechnology;
    }

    public String getTechnologiesSubjectArea() {
        return technologiesSubjectArea;
    }

    public void setTechnologiesSubjectArea(String technologiesSubjectArea) {
        this.technologiesSubjectArea = technologiesSubjectArea;
    }

    public String getTechnologiesWhetherTransferred() {
        return technologiesWhetherTransferred;
    }

    public void setTechnologiesWhetherTransferred(String technologiesWhetherTransferred) {
        this.technologiesWhetherTransferred = technologiesWhetherTransferred;
    }

    public String getTechnologiesImpact() {
        return technologiesImpact;
    }

    public void setTechnologiesImpact(String technologiesImpact) {
        this.technologiesImpact = technologiesImpact;
    }
}


