package nec.cst.pra.action.workingGroup;

import nec.cst.pra.action.academic.Academic;
import nec.cst.pra.action.activitydetail.ActivityDetail;

/**
 * Created by SanAji on 30-11-2018.
 */

public class Workinggroup {
    public String workingGroupSerial;
    public String workingGroupName;
    public String workingGroupcoordinator;
    public String workingGroupDepartment;
    public String workingGroupContact;
    public String workingGroupAreaOfInterest;
    public String workingGroupSubject;
   

    public Workinggroup(String workingGroupSerial, String workingGroupName, String workingGroupcoordinator, String workingGroupDepartment, String workingGroupContact, String workingGroupAreaOfInterest, String workingGroupSubject) {
        this.workingGroupSerial = workingGroupSerial;
        this.workingGroupName = workingGroupName;
        this.workingGroupcoordinator = workingGroupcoordinator;
        this.workingGroupDepartment = workingGroupDepartment;
        this.workingGroupContact = workingGroupContact;
        this.workingGroupAreaOfInterest = workingGroupAreaOfInterest;
        this.workingGroupSubject = workingGroupSubject;
        
    }


    public String getWorkingGroupSerial() {
        return workingGroupSerial;
    }
    public void setWorkingGroupSerial(String workingGroupSerial) {
        this.workingGroupSerial = workingGroupSerial;
    }

    public String getWorkingGroupName() {
        return workingGroupName;
    }

    public void setWorkingGroupName(String workingGroupName) {
        this.workingGroupName = workingGroupName;
    }

    public String getWorkingGroupcoordinator() {
        return workingGroupcoordinator;
    }

    public void setWorkingGroupcoordinator(String workingGroupcoordinator) {
        this.workingGroupcoordinator = workingGroupcoordinator;
    }

    public String getWorkingGroupDepartment() {
        return workingGroupDepartment;
    }

    public void setWorkingGroupDepartment(String workingGroupDepartment) {
        this.workingGroupDepartment = workingGroupDepartment;
    }

    public String getWorkingGroupContact() {
        return workingGroupContact;
    }

    public void setWorkingGroupContact(String workingGroupContact) {
        this.workingGroupContact = workingGroupContact;
    }

    public String getWorkingGroupAreaOfInterest() {
        return workingGroupAreaOfInterest;
    }

    public void setWorkingGroupAreaOfInterest(String workingGroupAreaOfInterest) {
        this.workingGroupAreaOfInterest = workingGroupAreaOfInterest;
    }

    public String getWorkingGroupSubject() {
        return workingGroupSubject;
    }

    public void setWorkingGroupSubject(String workingGroupSubject) {
        this.workingGroupSubject = workingGroupSubject;
    }


    public static void add(Workinggroup bean) {
    }

    public static void add(Academic bean) {
    }

    public static void add(ActivityDetail bean) {
    }
}
