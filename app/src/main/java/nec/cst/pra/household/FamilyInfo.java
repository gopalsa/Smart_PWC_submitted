package nec.cst.pra.household;

import java.io.Serializable;

/**
 * Created by arthi on 12/3/2018.
 */


public class FamilyInfo implements Serializable {
    public String relationship;
    public String familyName;
    public String familyAge;
    public String familySex;
    public String maritalStatus;
    public String education;
    public String sclCol;
    public String aadhaarNo;
    public String bank;
    public String computeLiterate;
    public String socialSecurity;
    public String majorHealth;
    public String jobCard;
    public String groupTick;
    public String occupationCode;

    public FamilyInfo(String relationship, String familyName, String familyAge, String familySex, String maritalStatus, String education, String sclCol, String aadhaarNo, String bank, String computeLiterate, String socialSecurity, String majorHealth, String jobCard, String groupTick, String occupationCode) {
        this.relationship = relationship;
        this.familyName = familyName;
        this.familyAge = familyAge;
        this.familySex = familySex;
        this.maritalStatus = maritalStatus;
        this.education = education;
        this.sclCol = sclCol;
        this.aadhaarNo = aadhaarNo;
        this.bank = bank;
        this.computeLiterate = computeLiterate;
        this.socialSecurity = socialSecurity;
        this.majorHealth = majorHealth;
        this.jobCard = jobCard;
        this.groupTick = groupTick;
        this.occupationCode = occupationCode;
    }

    public String getFamilySex() {
        return familySex;
    }

    public void setFamilySex(String familySex) {
        this.familySex = familySex;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getFamilyAge() {
        return familyAge;
    }

    public void setFamilyAge(String familyAge) {
        this.familyAge = familyAge;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getSclCol() {
        return sclCol;
    }

    public void setSclCol(String sclCol) {
        this.sclCol = sclCol;
    }

    public String getAadhaarNo() {
        return aadhaarNo;
    }

    public void setAadhaarNo(String aadhaarNo) {
        this.aadhaarNo = aadhaarNo;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getComputeLiterate() {
        return computeLiterate;
    }

    public void setComputeLiterate(String computeLiterate) {
        this.computeLiterate = computeLiterate;
    }

    public String getSocialSecurity() {
        return socialSecurity;
    }

    public void setSocialSecurity(String socialSecurity) {
        this.socialSecurity = socialSecurity;
    }

    public String getMajorHealth() {
        return majorHealth;
    }

    public void setMajorHealth(String majorHealth) {
        this.majorHealth = majorHealth;
    }

    public String getJobCard() {
        return jobCard;
    }

    public void setJobCard(String jobCard) {
        this.jobCard = jobCard;
    }

    public String getGroupTick() {
        return groupTick;
    }

    public void setGroupTick(String groupTick) {
        this.groupTick = groupTick;
    }

    public String getOccupationCode() {
        return occupationCode;
    }

    public void setOccupationCode(String occupationCode) {
        this.occupationCode = occupationCode;
    }
}
