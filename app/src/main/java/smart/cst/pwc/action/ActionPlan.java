package smart.cst.pwc.action;

import smart.cst.pwc.action.academic.Academic;
import smart.cst.pwc.action.activitydetail.ActivityDetail;
import smart.cst.pwc.action.basevillage.Base;
import smart.cst.pwc.action.household.HouseHold;
import smart.cst.pwc.action.major.Major;
import smart.cst.pwc.action.meeting.Meeting;
import smart.cst.pwc.action.miscellaneous.Miscellaneous;
import smart.cst.pwc.action.salient.Salient;
import smart.cst.pwc.action.technologies.Technologies;
import smart.cst.pwc.action.technologyCustomisation.TechnologyCustomisation;
import smart.cst.pwc.action.technologyDevelopment.TechnologyDevelopment;
import smart.cst.pwc.action.uba.Uba;
import smart.cst.pwc.action.vdp.Vdp;
import smart.cst.pwc.action.village.Village;
import smart.cst.pwc.action.villageDevelopment.VillageDevelopment;
import smart.cst.pwc.action.workingGroup.Workinggroup;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by SanAji on 30-11-2018.
 */

public class ActionPlan implements Serializable {
    String id;

    ArrayList<Major> majord;
    ArrayList<Uba> ubad;
    ArrayList<Village> villaged;
    ArrayList<Workinggroup> Workinggroupd;
    ArrayList<Academic> Academicd;
    ArrayList<ActivityDetail> ActivityDetaild;
    ArrayList<Base> based;
    ArrayList<HouseHold> HouseHoldd;
    ArrayList<Meeting> meetingd;
    ArrayList<VillageDevelopment> villageDevelopmentd;
    ArrayList<Salient> Salientd;
    ArrayList<Technologies> Technologied;
    ArrayList<Vdp> Vdpd;
    ArrayList<TechnologyDevelopment> TechnologyDevelopmentd;
    ArrayList<TechnologyCustomisation> TechnologyCustomisationd;
    ArrayList<Miscellaneous> miscellaneoud;

    private String InstitutionparicipatingInstitution;
    private String Institutioncoordinate;
    private String InstitutionEmail;
    private String InstitutionContact;
    private String InstitutionVillage;
    private String InstitutionDistrict;

    private String activitystudent;
    private String activityStatus;
    private String activitystudentRemark;
    private String activityMeeting;
    private String activityMeetingStatus;
    private String activityMeetingRemark;
    private String activityInteraction;
    private String activityInteractionStatus;
    private String activityInteractionRemark;
    private String activityAwareness;
    private String activityAwarenessStatus;
    private String activityAwarenessRemark;

    private String reportingPeriod;
    private String holy;

    private String ExtraCurricular;

    private String interactionWithRural;
    private String interactionWithDistrict;

    private String activityPotential;
    private String activityChallenges;
    private String activityAnyOther;

    public ActionPlan(ArrayList<Major> majord, ArrayList<Uba> ubad, ArrayList<Village> villaged, ArrayList<Workinggroup> workinggroupd, ArrayList<Academic> academicd, ArrayList<ActivityDetail> activityDetaild, ArrayList<Base> based, ArrayList<HouseHold> houseHoldd, ArrayList<Meeting> meetingd, ArrayList<VillageDevelopment> villageDevelopmentd, ArrayList<Salient> salientd, ArrayList<Technologies> technologied, ArrayList<Vdp> vdpd, ArrayList<TechnologyDevelopment> technologyDevelopmentd, ArrayList<TechnologyCustomisation> technologyCustomisationd, ArrayList<Miscellaneous> miscellaneoud, String institutionparicipatingInstitution, String institutioncoordinate, String institutionEmail, String institutionContact, String institutionVillage, String institutionDistrict, String activitystudent, String activityStatus, String activitystudentRemark, String activityMeeting, String activityMeetingStatus, String activityMeetingRemark, String activityInteraction, String activityInteractionStatus, String activityInteractionRemark, String activityAwareness, String activityAwarenessStatus, String activityAwarenessRemark, String reportingPeriod, String holy, String extraCurricular, String interactionWithRural, String interactionWithDistrict, String activityPotential, String activityChallenges, String activityAnyOther) {
        this.majord = majord;
        this.ubad = ubad;
        this.villaged = villaged;
        Workinggroupd = workinggroupd;
        Academicd = academicd;
        ActivityDetaild = activityDetaild;
        this.based = based;
        HouseHoldd = houseHoldd;
        this.meetingd = meetingd;
        this.villageDevelopmentd = villageDevelopmentd;
        Salientd = salientd;
        Technologied = technologied;
        Vdpd = vdpd;
        TechnologyDevelopmentd = technologyDevelopmentd;
        TechnologyCustomisationd = technologyCustomisationd;
        this.miscellaneoud = miscellaneoud;
        InstitutionparicipatingInstitution = institutionparicipatingInstitution;
        Institutioncoordinate = institutioncoordinate;
        InstitutionEmail = institutionEmail;
        InstitutionContact = institutionContact;
        InstitutionVillage = institutionVillage;
        InstitutionDistrict = institutionDistrict;
        this.activitystudent = activitystudent;
        this.activityStatus = activityStatus;
        this.activitystudentRemark = activitystudentRemark;
        this.activityMeeting = activityMeeting;
        this.activityMeetingStatus = activityMeetingStatus;
        this.activityMeetingRemark = activityMeetingRemark;
        this.activityInteraction = activityInteraction;
        this.activityInteractionStatus = activityInteractionStatus;
        this.activityInteractionRemark = activityInteractionRemark;
        this.activityAwareness = activityAwareness;
        this.activityAwarenessStatus = activityAwarenessStatus;
        this.activityAwarenessRemark = activityAwarenessRemark;
        this.reportingPeriod = reportingPeriod;
        this.holy = holy;
        ExtraCurricular = extraCurricular;
        this.interactionWithRural = interactionWithRural;
        this.interactionWithDistrict = interactionWithDistrict;
        this.activityPotential = activityPotential;
        this.activityChallenges = activityChallenges;
        this.activityAnyOther = activityAnyOther;
    }

    public ArrayList<Major> getMajord() {
        return majord;
    }

    public void setMajord(ArrayList<Major> majord) {
        this.majord = majord;
    }

    public ArrayList<Uba> getUbad() {
        return ubad;
    }

    public void setUbad(ArrayList<Uba> ubad) {
        this.ubad = ubad;
    }

    public ArrayList<Village> getVillaged() {
        return villaged;
    }

    public void setVillaged(ArrayList<Village> villaged) {
        this.villaged = villaged;
    }

    public ArrayList<Workinggroup> getWorkinggroupd() {
        return Workinggroupd;
    }

    public void setWorkinggroupd(ArrayList<Workinggroup> workinggroupd) {
        Workinggroupd = workinggroupd;
    }

    public ArrayList<Academic> getAcademicd() {
        return Academicd;
    }

    public void setAcademicd(ArrayList<Academic> academicd) {
        Academicd = academicd;
    }

    public ArrayList<ActivityDetail> getActivityDetaild() {
        return ActivityDetaild;
    }

    public void setActivityDetaild(ArrayList<ActivityDetail> activityDetaild) {
        ActivityDetaild = activityDetaild;
    }

    public ArrayList<Base> getBased() {
        return based;
    }

    public void setBased(ArrayList<Base> based) {
        this.based = based;
    }

    public ArrayList<HouseHold> getHouseHoldd() {
        return HouseHoldd;
    }

    public void setHouseHoldd(ArrayList<HouseHold> houseHoldd) {
        HouseHoldd = houseHoldd;
    }

    public ArrayList<Meeting> getMeetingd() {
        return meetingd;
    }

    public void setMeetingd(ArrayList<Meeting> meetingd) {
        this.meetingd = meetingd;
    }

    public ArrayList<VillageDevelopment> getVillageDevelopmentd() {
        return villageDevelopmentd;
    }

    public void setVillageDevelopmentd(ArrayList<VillageDevelopment> villageDevelopmentd) {
        this.villageDevelopmentd = villageDevelopmentd;
    }

    public ArrayList<Salient> getSalientd() {
        return Salientd;
    }

    public void setSalientd(ArrayList<Salient> salientd) {
        Salientd = salientd;
    }

    public ArrayList<Technologies> getTechnologied() {
        return Technologied;
    }

    public void setTechnologied(ArrayList<Technologies> technologied) {
        Technologied = technologied;
    }

    public ArrayList<Vdp> getVdpd() {
        return Vdpd;
    }

    public void setVdpd(ArrayList<Vdp> vdpd) {
        Vdpd = vdpd;
    }

    public ArrayList<TechnologyDevelopment> getTechnologyDevelopmentd() {
        return TechnologyDevelopmentd;
    }

    public void setTechnologyDevelopmentd(ArrayList<TechnologyDevelopment> technologyDevelopmentd) {
        TechnologyDevelopmentd = technologyDevelopmentd;
    }

    public ArrayList<TechnologyCustomisation> getTechnologyCustomisationd() {
        return TechnologyCustomisationd;
    }

    public void setTechnologyCustomisationd(ArrayList<TechnologyCustomisation> technologyCustomisationd) {
        TechnologyCustomisationd = technologyCustomisationd;
    }

    public ArrayList<Miscellaneous> getMiscellaneoud() {
        return miscellaneoud;
    }

    public void setMiscellaneoud(ArrayList<Miscellaneous> miscellaneoud) {
        this.miscellaneoud = miscellaneoud;
    }

    public String getInstitutionparicipatingInstitution() {
        return InstitutionparicipatingInstitution;
    }

    public void setInstitutionparicipatingInstitution(String institutionparicipatingInstitution) {
        InstitutionparicipatingInstitution = institutionparicipatingInstitution;
    }

    public String getInstitutioncoordinate() {
        return Institutioncoordinate;
    }

    public void setInstitutioncoordinate(String institutioncoordinate) {
        Institutioncoordinate = institutioncoordinate;
    }

    public String getInstitutionEmail() {
        return InstitutionEmail;
    }

    public void setInstitutionEmail(String institutionEmail) {
        InstitutionEmail = institutionEmail;
    }

    public String getInstitutionContact() {
        return InstitutionContact;
    }

    public void setInstitutionContact(String institutionContact) {
        InstitutionContact = institutionContact;
    }

    public String getInstitutionVillage() {
        return InstitutionVillage;
    }

    public void setInstitutionVillage(String institutionVillage) {
        InstitutionVillage = institutionVillage;
    }

    public String getInstitutionDistrict() {
        return InstitutionDistrict;
    }

    public void setInstitutionDistrict(String institutionDistrict) {
        InstitutionDistrict = institutionDistrict;
    }

    public String getActivitystudent() {
        return activitystudent;
    }

    public void setActivitystudent(String activitystudent) {
        this.activitystudent = activitystudent;
    }

    public String getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(String activityStatus) {
        this.activityStatus = activityStatus;
    }

    public String getActivitystudentRemark() {
        return activitystudentRemark;
    }

    public void setActivitystudentRemark(String activitystudentRemark) {
        this.activitystudentRemark = activitystudentRemark;
    }

    public String getActivityMeeting() {
        return activityMeeting;
    }

    public void setActivityMeeting(String activityMeeting) {
        this.activityMeeting = activityMeeting;
    }

    public String getActivityMeetingStatus() {
        return activityMeetingStatus;
    }

    public void setActivityMeetingStatus(String activityMeetingStatus) {
        this.activityMeetingStatus = activityMeetingStatus;
    }

    public String getActivityMeetingRemark() {
        return activityMeetingRemark;
    }

    public void setActivityMeetingRemark(String activityMeetingRemark) {
        this.activityMeetingRemark = activityMeetingRemark;
    }

    public String getActivityInteraction() {
        return activityInteraction;
    }

    public void setActivityInteraction(String activityInteraction) {
        this.activityInteraction = activityInteraction;
    }

    public String getActivityInteractionStatus() {
        return activityInteractionStatus;
    }

    public void setActivityInteractionStatus(String activityInteractionStatus) {
        this.activityInteractionStatus = activityInteractionStatus;
    }

    public String getActivityInteractionRemark() {
        return activityInteractionRemark;
    }

    public void setActivityInteractionRemark(String activityInteractionRemark) {
        this.activityInteractionRemark = activityInteractionRemark;
    }

    public String getActivityAwareness() {
        return activityAwareness;
    }

    public void setActivityAwareness(String activityAwareness) {
        this.activityAwareness = activityAwareness;
    }

    public String getActivityAwarenessStatus() {
        return activityAwarenessStatus;
    }

    public void setActivityAwarenessStatus(String activityAwarenessStatus) {
        this.activityAwarenessStatus = activityAwarenessStatus;
    }

    public String getActivityAwarenessRemark() {
        return activityAwarenessRemark;
    }

    public void setActivityAwarenessRemark(String activityAwarenessRemark) {
        this.activityAwarenessRemark = activityAwarenessRemark;
    }

    public String getReportingPeriod() {
        return reportingPeriod;
    }

    public void setReportingPeriod(String reportingPeriod) {
        this.reportingPeriod = reportingPeriod;
    }

    public String getHoly() {
        return holy;
    }

    public void setHoly(String holy) {
        this.holy = holy;
    }

    public String getExtraCurricular() {
        return ExtraCurricular;
    }

    public void setExtraCurricular(String extraCurricular) {
        ExtraCurricular = extraCurricular;
    }

    public String getInteractionWithRural() {
        return interactionWithRural;
    }

    public void setInteractionWithRural(String interactionWithRural) {
        this.interactionWithRural = interactionWithRural;
    }

    public String getInteractionWithDistrict() {
        return interactionWithDistrict;
    }

    public void setInteractionWithDistrict(String interactionWithDistrict) {
        this.interactionWithDistrict = interactionWithDistrict;
    }

    public String getActivityPotential() {
        return activityPotential;
    }

    public void setActivityPotential(String activityPotential) {
        this.activityPotential = activityPotential;
    }

    public String getActivityChallenges() {
        return activityChallenges;
    }

    public void setActivityChallenges(String activityChallenges) {
        this.activityChallenges = activityChallenges;
    }

    public String getActivityAnyOther() {
        return activityAnyOther;
    }

    public void setActivityAnyOther(String activityAnyOther) {
        this.activityAnyOther = activityAnyOther;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
