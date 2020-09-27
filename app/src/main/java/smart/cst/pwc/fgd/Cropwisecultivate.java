package smart.cst.pwc.fgd;

/**
 * Created by jmpriyanka on 01-12-2018.
 */

public class Cropwisecultivate {
    public String corp;
    public String kharifland;
    public String rabiland;
    public String summerland;
    public String kharifprod;
    public String rabiprod;
    public String summerprod;
    public String sellprice;
    public String totrevenue;

    public Cropwisecultivate(String corp, String kharifland, String rabiland, String summerland, String kharifprod, String rabiprod, String summerprod, String sellprice, String totrevenue) {
        this.corp = corp;
        this.kharifland = kharifland;
        this.rabiland = rabiland;
        this.summerland = summerland;
        this.kharifprod = kharifprod;
        this.rabiprod = rabiprod;
        this.summerprod = summerprod;
        this.sellprice = sellprice;
        this.totrevenue = totrevenue;
    }

    public String getCorp() {
        return corp;
    }

    public void setCorp(String corp) {
        this.corp = corp;
    }

    public String getKharifland() {
        return kharifland;
    }

    public void setKharifland(String kharifland) {
        this.kharifland = kharifland;
    }

    public String getRabiland() {
        return rabiland;
    }

    public void setRabiland(String rabiland) {
        this.rabiland = rabiland;
    }

    public String getSummerland() {
        return summerland;
    }

    public void setSummerland(String summerland) {
        this.summerland = summerland;
    }

    public String getKharifprod() {
        return kharifprod;
    }

    public void setKharifprod(String kharifprod) {
        this.kharifprod = kharifprod;
    }

    public String getRabiprod() {
        return rabiprod;
    }

    public void setRabiprod(String rabiprod) {
        this.rabiprod = rabiprod;
    }

    public String getSummerprod() {
        return summerprod;
    }

    public void setSummerprod(String summerprod) {
        this.summerprod = summerprod;
    }

    public String getSellprice() {
        return sellprice;
    }

    public void setSellprice(String sellprice) {
        this.sellprice = sellprice;
    }

    public String getTotrevenue() {
        return totrevenue;
    }

    public void setTotrevenue(String totrevenue) {
        this.totrevenue = totrevenue;
    }
}
