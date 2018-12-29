package nec.cst.pra;

/**
 * Created by Gopal on 18-11-2017.
 */

public class Member {
    String image;
    String name;
    String designation;
    String designationinpra;
    String address;
    String pincode;
    String geotag;
    String phone;
    String education;
    String experience;
    String socialmedia;


    public Member(String image, String name, String designation, String designationinpra, String address, String pincode, String geotag, String phone, String education, String experience, String socialmedia) {
        this.image = image;
        this.name = name;
        this.designation = designation;
        this.designationinpra = designationinpra;
        this.address = address;
        this.pincode = pincode;
        this.geotag = geotag;
        this.phone = phone;
        this.education = education;
        this.experience = experience;
        this.socialmedia = socialmedia;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getGeotag() {
        return geotag;
    }

    public void setGeotag(String geotag) {
        this.geotag = geotag;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDesignationinpra() {
        return designationinpra;
    }

    public void setDesignationinpra(String designationinpra) {
        this.designationinpra = designationinpra;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getSocialmedia() {
        return socialmedia;
    }

    public void setSocialmedia(String socialmedia) {
        this.socialmedia = socialmedia;
    }
}
