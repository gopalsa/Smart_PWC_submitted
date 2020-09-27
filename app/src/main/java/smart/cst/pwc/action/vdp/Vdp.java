package smart.cst.pwc.action.vdp;

/**
 * Created by SanAji on 30-11-2018.
 */

public class Vdp {
    public String vdpAddplace;
    public String vdpAction;


    public Vdp(String vdpAddplace, String vdpAction) {
        this.vdpAddplace = vdpAddplace;
        this.vdpAction = vdpAction;
    }

    public String getVdpAddplace() {
        return vdpAddplace;
    }


    public void setVdpAddplace(String vdpAddplace) {
        this.vdpAddplace = vdpAddplace;
    }

    public String getVdpAction() {
        return vdpAction;
    }

    public void setVdpAction(String vdpAction) {
        this.vdpAction = vdpAction;
    }
}
