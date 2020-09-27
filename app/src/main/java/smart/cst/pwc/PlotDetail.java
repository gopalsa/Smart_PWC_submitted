package smart.cst.pwc;

/**
 * Created by Gopal on 16-10-2017.
 */

public class PlotDetail {

    String plotid;
    String fPlotDetail;
    String sPlotDetail;
    String soilDetail;
    String waterDetail;
    String cropDetail;

    public PlotDetail() {
    }


    public void basicPlotDetail(String plotid, String fPlotDetail) {
        this.plotid = plotid;
        this.fPlotDetail = fPlotDetail;
    }

    public void secondPlotDetail(String sPlotDetail) {
        this.sPlotDetail = sPlotDetail;
    }

    public void soilDetail(String soilDetail) {
        this.soilDetail = soilDetail;
    }

    public void waterDetail(String waterDetail) {
        this.waterDetail = waterDetail;
    }

    public void cropDetail(String cropDetail) {
        this.cropDetail = cropDetail;
    }


    public String getPlotid() {
        return plotid;
    }

    public void setPlotid(String plotid) {
        this.plotid = plotid;
    }

    public String getFPlotDetail() {
        return fPlotDetail;
    }

    public void setFisttPlotDetail(String fisttPlotDetail) {
        this.fPlotDetail = fPlotDetail;
    }

    public String getsPlotDetail() {
        return sPlotDetail;
    }

    public void setsPlotDetail(String sPlotDetail) {
        this.sPlotDetail = sPlotDetail;
    }

    public String getSoilDetail() {
        return soilDetail;
    }

    public void setSoilDetail(String soilDetail) {
        this.soilDetail = soilDetail;
    }

    public String getWaterDetail() {
        return waterDetail;
    }

    public void setWaterDetail(String waterDetail) {
        this.waterDetail = waterDetail;
    }


    public String getCropDetail() {
        return cropDetail;
    }

    public void setCropDetail(String cropDetail) {
        this.cropDetail = cropDetail;
    }

}
