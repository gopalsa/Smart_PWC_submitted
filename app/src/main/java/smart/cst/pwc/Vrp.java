package smart.cst.pwc;


public class Vrp {

    String studentid;
    String image;
    String name;
    String geotag;
    String address;
    String contact;
    String whatsapp;
    String institution;
    String gmail;
    String depNo;
    String password;


    public Vrp() {
    }

    public void setId(String studentid) {
        this.studentid = studentid;
    }

    public void setData(String studentid, String image, String name, String geotag,
                        String address, String contact, String whatsapp, String institution,
                        String gmail,String depNo, String password) {
        this.studentid = studentid;
        this.image = image;
        this.name = name;
        this.geotag = geotag;
        this.address = address;
        this.contact = contact;
        this.whatsapp = whatsapp;
        this.institution = institution;
        this.gmail = gmail;
        this.depNo=depNo;
        this.password = password;
    }

    public String getDepNo() {
        return depNo;
    }

    public void setDepNo(String depNo) {
        this.depNo = depNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
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

    public String getGeotag() {
        return geotag;
    }

    public void setGeotag(String geotag) {
        this.geotag = geotag;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
