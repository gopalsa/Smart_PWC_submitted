package smart.cst.pwc.action.major;

/**
 * Created by SanAji on 30-11-2018.
 */

public class Major {
    private String issue;
    private String proposed;
    private String formulate;

    public Major(String issue, String proposed, String formulate) {
        this.issue = issue;
        this.proposed = proposed;
        this.formulate = formulate;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getProposed() {
        return proposed;
    }

    public void setProposed(String proposed) {
        this.proposed = proposed;
    }

    public String getFormulate() {
        return formulate;
    }

    public void setFormulate(String formulate) {
        this.formulate = formulate;
    }
}
