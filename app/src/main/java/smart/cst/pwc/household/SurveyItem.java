package smart.cst.pwc.household;

public class SurveyItem {


    public String question;
    public String field;
    public boolean isGraph;
    public boolean isYesNo;
    public String options;


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public SurveyItem(String question, String field) {
        this.question = question;
        this.field = field;
    }

    public SurveyItem(String question, String field, boolean isGraph) {
        this.question = question;
        this.field = field;
        this.isGraph = isGraph;
    }

    public SurveyItem(String question, String field, boolean isGraph, boolean isYesNo) {
        this.question = question;
        this.field = field;
        this.isGraph = isGraph;
        this.isYesNo = isYesNo;
    }

    public SurveyItem(String question, String field, boolean isGraph, String options) {
        this.question = question;
        this.field = field;
        this.isGraph = isGraph;
        this.options = options;
    }

    public boolean isYesNo() {
        return isYesNo;
    }

    public void setYesNo(boolean yesNo) {
        isYesNo = yesNo;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public boolean isGraph() {
        return isGraph;
    }

    public void setGraph(boolean graph) {
        isGraph = graph;
    }
}
