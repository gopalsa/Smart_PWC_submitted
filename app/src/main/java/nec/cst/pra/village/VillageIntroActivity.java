package nec.cst.pra.village;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import nec.cst.pra.ConvertUtils;
import nec.cst.pra.GPSTracker;
import nec.cst.pra.R;
import nec.cst.pra.Vrp;
import nec.cst.pra.app.AppConfig;
import nec.cst.pra.app.AppController;
import nec.cst.pra.db.DbMeet;
import nec.cst.pra.db.DbProfile;
import nec.cst.pra.db.DbVrp;

/**
 * Created by vidhushi.g on 8/10/17.
 */

public class VillageIntroActivity extends AppCompatActivity implements OnMapReadyCallback {
    DbVrp dbVrp;
    DbProfile dbProfile;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String vrpid = "vrpidKey";
    public static final String update = "updateKey";
    public static final String tittle = "tittleKey";
    public static final String villageIntro = "villageIntroKey";
    public static final String villageReference = "villageReferenceKey";
    String vrpId = "";
    private Vrp vrp;
    private ProgressDialog pDialog;
    private GoogleMap mMap;
    String imagePath = "";
    GPSTracker gps;
    DbMeet dbMeet;
    EditText reference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_village_intro);
        dbVrp = new DbVrp(this);
        dbProfile = new DbProfile(this);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(vrpid)) {
            vrpId = sharedpreferences.getString(vrpid, "").trim();
        }
        gps = new GPSTracker(VillageIntroActivity.this);
        dbMeet = new DbMeet(this);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        vrp = new Gson().fromJson(ConvertUtils.sample(dbVrp.getDataByvrpid(vrpId).get(1)), Vrp.class);
        getSupportActionBar().setTitle("Gram Panchayat Profile");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ImageView refresh = (ImageView) findViewById(R.id.refresh);
        reference = (EditText) findViewById(R.id.referenceGp1);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMap != null) {
                    locationFetch();
                } else {
                    Toast.makeText(getApplicationContext(), "Map is Not Ready", Toast.LENGTH_SHORT).show();
                }
            }
        });

        TextView submit = (TextView) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(villageIntro, imagePath);
                editor.putString(villageReference, reference.getText().toString());
                editor.commit();
                Intent io = new Intent(VillageIntroActivity.this,
                        VillageMeetingActivity.class);
                startActivity(io);
                finish();
            }
        });

    }

    private void locationFetch() {
        gps = new GPSTracker(VillageIntroActivity.this);
        if (gps.canGetLocation()) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(VillageIntroActivity.this, Locale.getDefault());
            String address = "";
            try {
                addresses = geocoder.getFromLocation(latitude
                        , longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                String addressGeo = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String postalCode = addresses.get(0).getPostalCode();
                address = addressGeo;
                imagePath = String.valueOf(latitude) + "," + String.valueOf(longitude) + "," + addressGeo;

            } catch (Exception e) {
                Log.d("Team member", e.toString());
            }


            try {
                List<ArrayList<String>> datas = dbMeet.getAllData();
                String dataString = datas.get(0).get(4);

                VillageMeeting villageMeeting = new Gson().fromJson(dataString, VillageMeeting.class);
                imagePath = villageMeeting.getVillageGeo();
                address = imagePath.split(",")[2];
                reference.setText(villageMeeting.reference);
            } catch (Exception e) {

            }

            MarkerOptions marker = new MarkerOptions()
                    .position(new LatLng(latitude, longitude))
                    .title(vrp.getName()).snippet(address);
            marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            mMap.addMarker(marker).showInfoWindow();
            CameraPosition cameraPosition = new CameraPosition.Builder().target(
                    new LatLng(latitude, longitude)).zoom(12).build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


            for (int i = 0; i < 4; i++) {
                MarkerOptions rMarker = new MarkerOptions()
                        .position(getLocation(latitude, longitude, 30))
                        .title("Gram panchayat " + String.valueOf(i + 1));
                marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                mMap.addMarker(rMarker).showInfoWindow();

            }

        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }

    }

    private void registerProduct(final String mvrpid, final String mname,
                                 final String moldcontact,
                                 final String contact, final String mdata, final boolean mupdate
            , final AlertDialog b, final int mposition) {
        // Tag used to cancel the request
        String tag_string_req = "req_posting";

        pDialog.setMessage("Posting ...");
        showDialog();
        String url;
        if (mupdate) {
            url = AppConfig.URL_PROFILE_UPDATE;
        } else {
            url = AppConfig.URL_PROFILE_CREATE;
        }
        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("tag", "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                    } else {
                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("message");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("tag", "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("vrpid", mvrpid);
                params.put("data", mdata);
                params.put("name", mname);
                params.put("contact", contact);
                if (mupdate) {
                    params.put("ocontact", moldcontact);
                }
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    private void getAllProduct() {
        // Tag used to cancel the request
        String tag_string_req = "req_posting";

        pDialog.setMessage("Fetching...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_ALL_PROFILE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("tag", "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        JSONArray jsonArray = jObj.getJSONArray("datas");
                        dbProfile.deleteAll();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                dbProfile.addData(vrpId, jsonObject.getString("name"), jsonObject.getString("contact"), jsonObject.getString("data"));
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("message");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("tag", "Fetch Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("vrpid", vrpId);
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    private boolean checkInternetConnection() {
        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_lan, menu);
        MenuItem item = menu.findItem(R.id.language);
        item.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        locationFetch();


    }


    public static LatLng getLocation(double x0, double y0, int radius) {
        Random random = new Random();

        // Convert radius from meters to degrees
        double radiusInDegrees = radius / 111000f;
        double u = random.nextDouble();
        double v = random.nextDouble();
        double w = radiusInDegrees * Math.sqrt(u);
        double t = 2 * Math.PI * v;
        double x = w * Math.cos(t);
        double y = w * Math.sin(t);
        // Adjust the x-coordinate for the shrinking of the east-west distances
        double new_x = x / Math.cos(y0);

        double foundLongitude = new_x + x0;
        double foundLatitude = y + y0;
        return new LatLng(foundLongitude, foundLatitude);
    }

}
