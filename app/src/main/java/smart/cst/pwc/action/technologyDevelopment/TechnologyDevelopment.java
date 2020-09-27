package smart.cst.pwc.action.technologyDevelopment;

/**
 * Created by SanAji on 30-11-2018.
 */

public class TechnologyDevelopment {
    public String technologyDevelopmentSerial;
    public String technologyDevelopmentTitle;
    public String technologyDevelopmentStatus;
    public String technologyDevelopmentComments;

    public TechnologyDevelopment(String technologyDevelopmentSerial, String technologyDevelopmentTitle, String technologyDevelopmentStatus, String technologyDevelopmentComments) {
        this.technologyDevelopmentSerial = technologyDevelopmentSerial;
        this.technologyDevelopmentTitle = technologyDevelopmentTitle;
        this.technologyDevelopmentStatus = technologyDevelopmentStatus;
        this.technologyDevelopmentComments = technologyDevelopmentComments;
    }

    public String getTechnologyDevelopmentSerial() {
        return technologyDevelopmentSerial;
    }
    public void setTechnologyDevelopmentSerial(String technologyDevelopmentSerial) {
        this.technologyDevelopmentSerial = technologyDevelopmentSerial;
    }

    public String getTechnologyDevelopmentTitle() {
        return technologyDevelopmentTitle;
    }

    public void setTechnologyDevelopmentTitle(String technologyDevelopmentTitle) {
        this.technologyDevelopmentTitle = technologyDevelopmentTitle;
    }

    public String getTechnologyDevelopmentStatus() {
        return technologyDevelopmentStatus;
    }

    public void setTechnologyDevelopmentStatus(String technologyDevelopmentStatus) {
        this.technologyDevelopmentStatus = technologyDevelopmentStatus;
    }

    public String getTechnologyDevelopmentComments() {
        return technologyDevelopmentComments;
    }

    public void setTechnologyDevelopmentComments(String technologyDevelopmentComments) {
        this.technologyDevelopmentComments = technologyDevelopmentComments;
    }
}
