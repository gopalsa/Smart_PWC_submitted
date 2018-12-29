package nec.cst.pra;

import com.github.bkhezry.extramaputils.model.ViewOption;

/**
 * Created by LENOVO on 22-07-2017.
 */

public class Plot {
   String tag;
    String plotname;
    String plotimage;
    String plotarea;
    ViewOption viewOption;

    public Plot(String tag, String plotname, String plotimage, String plotarea, ViewOption viewOption) {
        this.tag = tag;
        this.plotname = plotname;
        this.plotimage = plotimage;
        this.plotarea = plotarea;
        this.viewOption=viewOption;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getPlotname() {
        return plotname;
    }

    public void setPlotname(String plotname) {
        this.plotname = plotname;
    }

    public String getPlotimage() {
        return plotimage;
    }

    public void setPlotimage(String plotimage) {
        this.plotimage = plotimage;
    }

    public String getPlotarea() {
        return plotarea;
    }

    public void setPlotarea(String plotarea) {
        this.plotarea = plotarea;
    }

    public ViewOption getViewOption() {
        return viewOption;
    }

    public void setViewOption(ViewOption viewOption) {
        this.viewOption = viewOption;
    }
}
