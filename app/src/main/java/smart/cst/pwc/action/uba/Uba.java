package smart.cst.pwc.action.uba;

/**
 * Created by SanAji on 30-11-2018.
 */

public class Uba {
    public String ubaPostal;
    public String ubaTelephone;
    public String ubaMobile;
    public String ubaFax;
    public String ubaEmail;

    public Uba(String ubaPostal, String ubaTelephone, String ubaMobile, String ubaFax, String ubaEmail) {
        this.ubaPostal = ubaPostal;
        this.ubaTelephone = ubaTelephone;
        this.ubaMobile = ubaMobile;
        this.ubaFax = ubaFax;
        this.ubaEmail = ubaEmail;
    }

    public String getubaPostal() {
        return ubaPostal;}


    public void setUbaPostal(String ubaPostal) {
        this.ubaPostal = ubaPostal;
    }

    public String getUbaTelephone() {
        return ubaTelephone;
    }

    public void setUbaTelephone(String ubaTelephone) {
        this.ubaTelephone = ubaTelephone;
    }

    public String getUbaMobile() {
        return ubaMobile;
    }

    public void setUbaMobile(String ubaMobile) {
        this.ubaMobile = ubaMobile;
    }

    public String getUbaFax() {
        return ubaFax;
    }

    public void setUbaFax(String ubaFax) {
        this.ubaFax = ubaFax;
    }

    public String getUbaEmail() {
        return ubaEmail;
    }

    public void setUbaEmail(String ubaEmail) {
        this.ubaEmail = ubaEmail;
    }
}


