package smart.cst.pwc.survey;

import android.graphics.Bitmap;

public class PrintSurveyItem {
    String question;
    String subtitle;
    Bitmap bitmap;
    String inference;
    String relevance;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getInference() {
        return inference;
    }

    public void setInference(String inference) {
        this.inference = inference;
    }

    public String getRelevance() {
        return relevance;
    }

    public void setRelevance(String relevance) {
        this.relevance = relevance;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public PrintSurveyItem(String question, String subtitle, Bitmap bitmap, String inference, String relevance) {
        this.question = question;
        this.subtitle = subtitle;
        this.bitmap = bitmap;
        this.inference = inference;
        this.relevance = relevance;
    }
}
