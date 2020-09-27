package smart.cst.pwc.village;

import java.io.Serializable;

public class Base implements Serializable {
    String url;
    String isImage;

    public Base(String url, String isImage) {
        this.url = url;
        this.isImage = isImage;
    }

    public Base() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIsImage() {
        return isImage;
    }

    public void setIsImage(String isImage) {
        this.isImage = isImage;
    }
}
