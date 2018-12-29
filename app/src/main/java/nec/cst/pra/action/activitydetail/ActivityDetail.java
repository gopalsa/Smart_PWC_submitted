package nec.cst.pra.action.activitydetail;

/**
 * Created by SanAji on 30-11-2018.
 */

public class ActivityDetail {
    public String activityDetailSerialNo;
    public String activityDetailDate;
    public String activityDetailVillage;
    public String activityDetailActivity;
    public String activityDetailFaculty;


    public ActivityDetail(String activityDetailSerial, String activityDetailDate, String activityDetailVillage, String activityDetailActivity, String activityDetailFaculty) {
        this.activityDetailSerialNo = activityDetailSerial;
        this.activityDetailDate = activityDetailDate;
        this.activityDetailVillage = activityDetailVillage;
        this.activityDetailActivity = activityDetailActivity;
        this.activityDetailFaculty = activityDetailFaculty;
    }

    public String getActivityDetailSerialNo() {
        return activityDetailSerialNo;
    }


    public void setActivityDetailSerialNo(String activityDetailSerialNo) {
        this.activityDetailSerialNo = activityDetailSerialNo;
    }

    public String getActivityDetailDate() {
        return activityDetailDate;
    }

    public void setActivityDetailDate(String activityDetailDate) {
        this.activityDetailDate = activityDetailDate;
    }

    public String getActivityDetailVillage() {
        return activityDetailVillage;
    }

    public void setActivityDetailVillage(String activityDetailVillage) {
        this.activityDetailVillage = activityDetailVillage;
    }

    public String getActivityDetailActivity() {
        return activityDetailActivity;
    }

    public void setActivityDetailActivity(String activityDetailActivity) {
        this.activityDetailActivity = activityDetailActivity;
    }

    public String getActivityDetailFaculty() {
        return activityDetailFaculty;
    }

    public void setActivityDetailFaculty(String activityDetailFaculty) {
        this.activityDetailFaculty = activityDetailFaculty;
    }

    public static void add(ActivityDetail bean) {
    }
}
