package info.androidhive.recyclerview.utils;


import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import java.util.List;

//import com.google.maps.android.SphericalUtil;

public class CalUtils {
    public static double getArea(List<LatLng> latLngs) {
        return SphericalUtil.computeArea(latLngs);
    }

    public static double getLength(List<LatLng> latLngs) {
        return SphericalUtil.computeLength(latLngs);
    }
}
