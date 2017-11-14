package info.androidhive.recyclerview;

import com.github.bkhezry.mapdrawingtools.model.DataModel;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


/**
 * Created by Gopal on 15-10-2017.
 */

public class ConvertUtils {

    public static String prefixurl = "http://maps.googleapis.com/maps/api/staticmap?" +
            "zoom=17&size=600x600&maptype=satellite&sensor=false&path=color%3ared|weight:1|fillcolor%3awhite";


    public static JsonObject sample(String value) {
        JsonParser parser = new JsonParser();
        String result = value.replaceAll("\\\\", "");
        result = result.substring(1, result.length() - 1);
        JsonObject o = parser.parse(result).getAsJsonObject();
        return o;
    }


    public static DataModel urltoDatamodel(String url) {

        DataModel dataModel = new DataModel();
        String latlatLonsample = url.replace(prefixurl, "").substring(1);
        String[] latLon = latlatLonsample.split("\\|");
        LatLng[] points = new LatLng[latLon.length];
        for (int i = 0; i < latLon.length; i++) {
            double lat = Double.parseDouble(latLon[i].split(",")[0]);
            double lon = Double.parseDouble(latLon[i].split(",")[1]);
            points[i] = new LatLng(lat, lon);
        }
        dataModel.setPoints(points);
        dataModel.setCount(points.length);
        return dataModel;
    }
}
