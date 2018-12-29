package nec.cst.pra.survey.adapters;

public class Response {

    String isValid;
    String student;
    String response;
    String gps;

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Response(String isValid, String student, String response,String gps) {
        this.isValid = isValid;
        this.student = student;
        this.response = response;
        this.gps=gps;
    }

    public Response() {
    }
}
