package nec.cst.pra.action.academic;

/**
 * Created by SanAji on 30-11-2018.
 */

public class Academic {
    public String academicSerial;
    public String academicAcademicActivity;
    public String academicFaculty;
    public String academicTopic;
    public String academicKey;

    public Academic(String academicSerial, String academicAcademicActivity, String academicFaculty, String academicTopic, String academicKey) {
        this.academicSerial = academicSerial;
        this.academicAcademicActivity = academicAcademicActivity;
        this.academicFaculty = academicFaculty;
        this.academicTopic = academicTopic;
        this.academicKey = academicKey;
    }

    public Academic(String s, String s1, String s2, String s3, String s4, String s5) {

    }

    public String getAcademicSerial() {
        return academicSerial;
    }


    public void setAcademicSerial(String academicSerial) {
        this.academicSerial = academicSerial;
    }

    public String getAcademicAcademicActivity() {
        return academicAcademicActivity;
    }

    public void setAcademicAcademicActivity(String academicAcademicActivity) {
        this.academicAcademicActivity = academicAcademicActivity;
    }

    public String getAcademicFaculty() {
        return academicFaculty;
    }

    public void setAcademicFaculty(String academicFaculty) {
        this.academicFaculty = academicFaculty;
    }

    public String getAcademicTopic() {
        return academicTopic;
    }

    public void setAcademicTopic(String academicTopic) {
        this.academicTopic = academicTopic;
    }

    public String getAcademicKey() {
        return academicKey;
    }

    public void setAcademicKey(String academicKey) {
        this.academicKey = academicKey;
    }
}
