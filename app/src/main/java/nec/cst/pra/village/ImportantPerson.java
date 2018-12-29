package nec.cst.pra.village;

public class ImportantPerson {

    String image;
    String name;
    String fatherName;
    String age;
    String gender;
    String designation;
    String geoTag;
    String yearFrom;
    String yearTo;
    String electedOrSelected;

    public ImportantPerson(String image, String name, String designation) {
        this.image = image;
        this.name = name;
        this.designation = designation;
    }

    public ImportantPerson(String image, String name, String fatherName, String age, String gender, String designation, String geoTag, String yearFrom, String yearTo, String electedOrSelected) {
        this.image = image;
        this.name = name;
        this.fatherName = fatherName;
        this.age = age;
        this.gender = gender;
        this.designation = designation;
        this.geoTag = geoTag;
        this.yearFrom = yearFrom;
        this.yearTo = yearTo;
        this.electedOrSelected = electedOrSelected;
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

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getGeoTag() {
        return geoTag;
    }

    public void setGeoTag(String geoTag) {
        this.geoTag = geoTag;
    }

    public String getYearFrom() {
        return yearFrom;
    }

    public void setYearFrom(String yearFrom) {
        this.yearFrom = yearFrom;
    }

    public String getYearTo() {
        return yearTo;
    }

    public void setYearTo(String yearTo) {
        this.yearTo = yearTo;
    }

    public String getElectedOrSelected() {
        return electedOrSelected;
    }

    public void setElectedOrSelected(String electedOrSelected) {
        this.electedOrSelected = electedOrSelected;
    }
}
