package nec.cst.pra.household;

public class SurveyItem {


    public String question;
    public String field;
    public boolean isGraph;

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

    public boolean isGraph() {
        return isGraph;
    }

    public void setGraph(boolean graph) {
        isGraph = graph;
    }
}
