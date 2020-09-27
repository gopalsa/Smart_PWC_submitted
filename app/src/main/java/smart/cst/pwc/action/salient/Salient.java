package smart.cst.pwc.action.salient;

/**
 * Created by SanAji on 30-11-2018.
 */

public class Salient {
    public String salientSerial;
    public String salientProject;
    public String salientTimelines;
    public String salientSourceOfFund;
    public String salientCost;
    public String salientAreaOfIntervention;
    public String salientExpectedOutcomes;

    public Salient(String salientSerial, String salientProject, String salientTimelines, String salientSourceOfFund, String salientCost, String salientAreaOfIntervention, String salientExpectedOutcomes) {
        this.salientSerial = salientSerial;
        this.salientProject = salientProject;
        this.salientTimelines = salientTimelines;
        this.salientSourceOfFund = salientSourceOfFund;
        this.salientCost = salientCost;
        this.salientAreaOfIntervention = salientAreaOfIntervention;
        this.salientExpectedOutcomes = salientExpectedOutcomes;
    }


    public String getSalientSerial() {
        return salientSerial;
    }

    public void setSalientSerial(String salientSerial) {
        this.salientSerial = salientSerial;
    }

    public String getSalientProject() {
        return salientProject;
    }

    public void setSalientProject(String salientProject) {
        this.salientProject = salientProject;
    }

    public String getSalientTimelines() {
        return salientTimelines;
    }

    public void setSalientTimelines(String salientTimelines) {
        this.salientTimelines = salientTimelines;
    }

    public String getSalientSourceOfFund() {
        return salientSourceOfFund;
    }

    public void setSalientSourceOfFund(String salientSourceOfFund) {
        this.salientSourceOfFund = salientSourceOfFund;
    }

    public String getSalientCost() {
        return salientCost;
    }

    public void setSalientCost(String salientCost) {
        this.salientCost = salientCost;
    }

    public String getSalientAreaOfIntervention() {
        return salientAreaOfIntervention;
    }

    public void setSalientAreaOfIntervention(String salientAreaOfIntervention) {
        this.salientAreaOfIntervention = salientAreaOfIntervention;
    }

    public String salientExpectedOutcomes() {
        return salientExpectedOutcomes;
    }

    public void salientExpectedOutcomes(String salientExpectedOutcomes) {
        this.salientExpectedOutcomes = salientExpectedOutcomes;
    }


    public String getExpectedOutcomes() {
        return salientExpectedOutcomes;
    }
}
