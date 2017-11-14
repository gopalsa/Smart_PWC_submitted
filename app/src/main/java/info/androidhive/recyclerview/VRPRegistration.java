package info.androidhive.recyclerview;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import fr.arnaudguyon.xmltojsonlib.XmlToJson;
import info.androidhive.recyclerview.app.AppConfig;
import info.androidhive.recyclerview.app.AppController;
import info.androidhive.recyclerview.db.DbVrp;
import info.androidhive.recyclerview.service.HttpService;
import katomaran.evao.lib.qrmodule.activity.QrScannerActivity;

/**
 * Created by vidhushi.g on 4/10/17.
 */

public class VRPRegistration extends AppCompatActivity

{
    private int CAMERA_PERMISSION_CODE = 23;
    DbVrp dbVrp;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String vrpid = "vrpidKey";
    public static final String update = "updateKey";
    String imageUri = "";
    private LinearLayout otpLin;
    GPSTracker gps;

    private ProgressDialog pDialog;
    private static final String TAG = VRPRegistration.class.getSimpleName();
    private NestedScrollView scroll;
    //private DbProfile dbProfile;
    private AutoCompleteTextView email;
    private CircleImageView image;
    CustomFontEditText aadharnumber;
    CustomFontEditText projectname;
    CustomFontEditText geotag;
    CustomFontEditText voname;
    CustomFontEditText panchayat;
    CustomFontEditText address;
    CustomFontEditText vopresident;
    CustomFontEditText farmercontact2;
    CustomFontEditText farmercontact;
    CustomFontEditText villagehouseholds;
    CustomFontEditText villagearea;
    CustomFontEditText villagepopulationmale;
    CustomFontEditText villagepopulationfemale;
    CustomFontEditText villagepopulationchildmale;
    CustomFontEditText villagepopulationchildfemale;
    CustomFontEditText noofshg;
    CustomFontEditText totalvomembers;
    CustomFontEditText totalfpomembers;
    CustomFontEditText totalstmembers;
    CustomFontEditText totalscmembers;
    CustomFontEditText totalobcmembers;
    CustomFontEditText totalgeneralmembers;
    CustomFontEditText vobankaccountnumber;
    CustomFontEditText vobankifscnumber;
    private CustomFontTextView submit;
    private ImageView aadharimage;
    CustomFontEditText password;
    CustomFontEditText confirmpassword;
    CustomFontEditText otp;
    CustomFontEditText forestarea;
    CustomFontEditText cultivatedarea;
    CustomFontEditText otherarea;
    CustomFontEditText totalpopulation;
    CustomFontEditText villagepopulationothers;
    CustomFontEditText villagepopulationchildothers;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        gps = new GPSTracker(VRPRegistration.this);

        dbVrp = new DbVrp(this);
        //dbProfile = new DbProfile(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>VO registration</font>"));
        aadharimage = (ImageView) findViewById(R.id.aadharimg);
        scroll = (NestedScrollView) findViewById(R.id.scroll);
        image = (CircleImageView) findViewById(R.id.imageview);
        aadharnumber = (CustomFontEditText) findViewById(R.id.aadharnumer);
        projectname = (CustomFontEditText) findViewById(R.id.projectname);
        geotag = (CustomFontEditText) findViewById(R.id.geotag);
        voname = (CustomFontEditText) findViewById(R.id.voname);
        panchayat = (CustomFontEditText) findViewById(R.id.panchayat);
        address = (CustomFontEditText) findViewById(R.id.address);
        vopresident = (CustomFontEditText) findViewById(R.id.vopresident);
        farmercontact2 = (CustomFontEditText) findViewById(R.id.farmercontact2);
        farmercontact = (CustomFontEditText) findViewById(R.id.farmercontact);
        villagehouseholds = (CustomFontEditText) findViewById(R.id.villagehouseholds);
        ImageView georefresh = (ImageView) findViewById(R.id.refresh);
        villagearea = (CustomFontEditText) findViewById(R.id.villagearea);
        forestarea = (CustomFontEditText) findViewById(R.id.forestarea);
        cultivatedarea = (CustomFontEditText) findViewById(R.id.cultivatedarea);
        otherarea = (CustomFontEditText) findViewById(R.id.otherarea);
        email = (AutoCompleteTextView) findViewById(R.id.autocomplete);
        totalpopulation = (CustomFontEditText) findViewById(R.id.totalpopulation);
        villagepopulationmale = (CustomFontEditText) findViewById(R.id.villagepopulationmale);
        villagepopulationfemale = (CustomFontEditText) findViewById(R.id.villagepopulationfemale);
        villagepopulationothers = (CustomFontEditText) findViewById(R.id.villagepopulationothers);
        villagepopulationchildmale = (CustomFontEditText) findViewById(R.id.villagepopulationchildmale);
        villagepopulationchildfemale = (CustomFontEditText) findViewById(R.id.villagepopulationchildfemale);
        villagepopulationchildothers = (CustomFontEditText) findViewById(R.id.villagepopulationchildothers);
        noofshg = (CustomFontEditText) findViewById(R.id.noofshg);
        totalvomembers = (CustomFontEditText) findViewById(R.id.totalvomembers);
        totalfpomembers = (CustomFontEditText) findViewById(R.id.totalfpomembers);
        totalstmembers = (CustomFontEditText) findViewById(R.id.totalstmembers);
        totalscmembers = (CustomFontEditText) findViewById(R.id.totalscmembers);
        totalobcmembers = (CustomFontEditText) findViewById(R.id.totalobcmembers);
        totalgeneralmembers = (CustomFontEditText) findViewById(R.id.totalgeneralmembers);
        vobankaccountnumber = (CustomFontEditText) findViewById(R.id.vobankaccountnumber);
        vobankifscnumber = (CustomFontEditText) findViewById(R.id.vobankifscnumber);
        submit = (CustomFontTextView) findViewById(R.id.r_submittxt);
        password = (CustomFontEditText) findViewById(R.id.password);
        confirmpassword = (CustomFontEditText) findViewById(R.id.confirmpassword);
        otp = (CustomFontEditText) findViewById(R.id.otp);
        otpLin = (LinearLayout) findViewById(R.id.otplin);
        otpLin.setVisibility(View.GONE);
        georefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check if GPS enabled
                gps = new GPSTracker(VRPRegistration.this);
                if (gps.canGetLocation()) {

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

                    geotag.setText(latitude + "," + longitude);
                    // \n is for new line
                    //       Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                } else {
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }
            }
        });
        // check if GPS enabled
        if (gps.canGetLocation()) {
            gps = new GPSTracker(VRPRegistration.this);
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            geotag.setText(latitude + "," + longitude);
            // \n is for new line
            //       Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickSetup setup = new PickSetup();
                PickImageDialog.build(setup).setOnPickResult(new IPickResult() {
                    @Override
                    public void onPickResult(PickResult pickResult) {
                        Glide.with(VRPRegistration.this).load(pickResult.getUri())
                                .centerCrop()
                                .dontAnimate()
                                .thumbnail(0.5f)
                                .placeholder(R.drawable.profile)
                                .into(image);
                        imageUri = pickResult.getUri().toString();
                    }
                })
                        .show(VRPRegistration.this);
            }
        });


        aadharimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isReadStorageAllowed()) {
                    requestStoragePermission();
                } else {
                    startActivityForResult(
                            new Intent(VRPRegistration.this, QrScannerActivity.class),
                            QrScannerActivity.QR_REQUEST_CODE);
                }
            }
        });
        if (sharedpreferences.contains(update)) {
            try {
                Vrp vrp = new Gson()
                        .fromJson(ConvertUtils.sample(dbVrp.getDataByvrpid(sharedpreferences.getString(vrpid, "")).get(1)), Vrp.class);
                imageUri = vrp.getImage();
                otpLin.setVisibility(View.GONE);

                aadharnumber.setText(vrp.getAadharnumber());
                geotag.setText(vrp.getGeotag());
                address.setText(vrp.getAddress());
                email.setText(vrp.getGmail());
                projectname.setText(vrp.getProjectname());
                voname.setText(vrp.getVoname());
                panchayat.setText(vrp.getPanchayat());
                vopresident.setText(vrp.getVopresident());
                farmercontact2.setText(vrp.getFarmercontact2());
                farmercontact.setText(vrp.getFarmercontact());
                villagehouseholds.setText(vrp.getVillagehouseholds());
                villagearea.setText(vrp.getVillagearea());
                totalpopulation.setText(vrp.getTotalpopulation());
                forestarea.setText(vrp.getForestarea());
                cultivatedarea.setText(vrp.getCultivatedarea());
                otherarea.setText(vrp.getOtherarea());
                villagepopulationmale.setText(vrp.getVillagepopulationmale());
                villagepopulationfemale.setText(vrp.getVillagepopulationfemale());
                villagepopulationothers.setText(vrp.getVillagepopulationothers());
                villagepopulationchildmale.setText(vrp.getVillagepopulationchildmale());
                villagepopulationchildfemale.setText(vrp.getVillagepopulationchildfemale());
                villagepopulationchildothers.setText(vrp.getVillagepopulationchildothers());
                noofshg.setText(vrp.getNoofshg());
                totalvomembers.setText(vrp.getTotalvomembers());
                totalfpomembers.setText(vrp.getTotalfpomembers());
                totalstmembers.setText(vrp.getTotalstmembers());
                totalscmembers.setText(vrp.getTotalscmembers());
                totalobcmembers.setText(vrp.getTotalobcmembers());
                totalgeneralmembers.setText(vrp.getTotalgeneralmembers());
                vobankaccountnumber.setText(vrp.getVobankaccountnumber());
                vobankifscnumber.setText(vrp.getVobankifscnumber());


                password.setText(dbVrp.getDataByvrpid(sharedpreferences.getString(vrpid, "")).get(2));
                confirmpassword.setText(dbVrp.getDataByvrpid(sharedpreferences.getString(vrpid, "")).get(2));
                Glide.with(VRPRegistration.this).load(imageUri)
                        .centerCrop()
                        .dontAnimate()
                        .thumbnail(0.5f)
                        .placeholder(R.drawable.profile)
                        .into(image);
                submit.setText("SUBMIT");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (submit.getText().toString().trim().equals("SUBMIT")) {
                    if (!(farmercontact.getText().toString() != null && farmercontact.getText().toString().length() == 10)) {
                        farmercontact.setError("Enter valid number");
                    } else if (!(farmercontact2.getText().toString() != null && farmercontact2.getText().toString().length() == 10)) {
                        farmercontact2.setError("Enter valid number");
                    } else if (!password.getText().toString().trim().equals(confirmpassword.getText().toString().trim())) {
                        Toast.makeText(getApplicationContext(), "Password dosn't match", Toast.LENGTH_SHORT).show();
                    } else if (projectname.getText().toString().length() <= 0 || panchayat.getText().toString().length() <= 0
                            || address.getText().toString().length() <= 0
                            || vopresident.getText().toString().length() <= 0
                            || villagehouseholds.getText().toString().length() <= 0
                            || villagearea.getText().toString().length() <= 0
                            || villagepopulationmale.getText().toString().length() <= 0
                            || villagepopulationfemale.getText().toString().length() <= 0
                            || villagepopulationchildmale.getText().toString().length() <= 0
                            || villagepopulationchildfemale.getText().toString().length() <= 0
                            || noofshg.getText().toString().length() <= 0
                            || totalvomembers.getText().toString().length() <= 0
                            || totalfpomembers.getText().toString().length() <= 0
                            || totalstmembers.getText().toString().length() <= 0
                            || totalscmembers.getText().toString().length() <= 0
                            || totalobcmembers.getText().toString().length() <= 0
                            || totalgeneralmembers.getText().toString().length() <= 0
                            || vobankaccountnumber.getText().toString().length() <= 0
                            || vobankifscnumber.getText().toString().length() <= 0
                            || address.getText().toString().length() <= 0
                            || email.getText().toString().length() <= 0
                            ) {
                        Toast.makeText(getApplicationContext(), "Enter all fields", Toast.LENGTH_SHORT).show();
                    } else {
                        String vrpidnew;
                        Vrp farmerdata = new Vrp();
                        farmerdata.setData(sharedpreferences.getString(vrpid, ""), imageUri
                                , aadharnumber.getText().toString()
                                , projectname.getText().toString()
                                , geotag.getText().toString()
                                , voname.getText().toString()
                                , panchayat.getText().toString()
                                , address.getText().toString()
                                , vopresident.getText().toString()
                                , farmercontact2.getText().toString()
                                , farmercontact.getText().toString()
                                , villagehouseholds.getText().toString()
                                , villagearea.getText().toString()
                                , forestarea.getText().toString()
                                , cultivatedarea.getText().toString()
                                , otherarea.getText().toString()
                                , totalpopulation.getText().toString()
                                , villagepopulationmale.getText().toString()
                                , villagepopulationfemale.getText().toString()
                                , villagepopulationothers.getText().toString()
                                , villagepopulationchildmale.getText().toString()
                                , villagepopulationchildfemale.getText().toString()
                                , villagepopulationchildothers.getText().toString()
                                , noofshg.getText().toString()
                                , totalvomembers.getText().toString()
                                , totalfpomembers.getText().toString()
                                , totalstmembers.getText().toString()
                                , totalscmembers.getText().toString()
                                , totalobcmembers.getText().toString()
                                , totalgeneralmembers.getText().toString()
                                , email.getText().toString()
                                , vobankaccountnumber.getText().toString()
                                , vobankifscnumber.getText().toString());
                        if (sharedpreferences.contains(update)) {
                            if (checkInternetConnection()) {
                                registerUser(sharedpreferences.getString(vrpid, ""), new Gson().toJson(farmerdata),
                                        password.getText().toString(), vopresident.getText().toString(), farmercontact2.getText().toString(), true);
                            } else {
                                Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            String pincode = address.getText().toString().substring(address.getText().toString().length() - 6, address.getText().toString().length());
                            if (dbVrp.getCountByvrpid(pincode + "vrp_1") == 0) {
                                vrpidnew = pincode + "vrp_";
                            } else {
                                vrpidnew = pincode + "vrp_";
                            }
                            farmerdata.setId(vrpidnew);
                            if (checkInternetConnection()) {
                                registerUser(vrpidnew, new Gson().toJson(farmerdata),
                                        password.getText().toString(), vopresident.getText().toString(), farmercontact2.getText().toString(), false);
                                submit.setText("Enter OTP");
                                otpLin.setVisibility(View.VISIBLE);
                                scroll.fullScroll(View.FOCUS_DOWN);
                                gps = new GPSTracker(VRPRegistration.this);
                            } else {
                                Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                } else if (submit.getText().toString().trim().equals("Enter OTP")) {
                    //do nothing
                } else if (submit.getText().toString().trim().equals("Verify")) {
                    String mOtp = otp.getText().toString().trim();

                    if (!mOtp.isEmpty()) {
                        Intent grapprIntent = new Intent(getApplicationContext(), HttpService.class);
                        grapprIntent.putExtra("otp", mOtp);
                        startService(grapprIntent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Please enter the OTP", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


        otp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                submit.setText("Verify");
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_logout, menu);
        if (!sharedpreferences.contains(update)) {
            MenuItem item = menu.findItem(R.id.logout);
            item.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                SharedPreferences.Editor editor1 = sharedpreferences.edit();
                editor1.remove(update);
                editor1.commit();
                finish();
                return true;
            case R.id.logout:
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.remove(vrpid);
                editor.remove(update);
                editor.commit();
                Intent io = new Intent(VRPRegistration.this, MainActivity.class);
                io.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                io.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(io);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == QrScannerActivity.QR_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                JSONObject jsonObj = null;
                try {
                    XmlToJson xmlToJson = new XmlToJson.Builder(data.getExtras().getString(QrScannerActivity.QR_RESULT_STR)).build();
                    jsonObj = xmlToJson.toJson();
                    JSONObject barcodedata = jsonObj.getJSONObject("PrintLetterBarcodeData");
                    Log.d("xxxxxxxxx", barcodedata.toString());
                    String addresstxt1 = "";
                    String addresstxt2 = "";
                    String pincode = "";
                    if (!barcodedata.isNull("street")) {
                        addresstxt1 = addresstxt1 + barcodedata.getString("street");
                    } else if (!barcodedata.isNull("lm")) {
                        addresstxt1 = addresstxt1 + barcodedata.getString("lm");
                    }

                    if (!barcodedata.isNull("vtc")) {
                        addresstxt2 = addresstxt2 + barcodedata.getString("vtc") + ",";
                    }
                    if (!barcodedata.isNull("subdist")) {
                        addresstxt2 = addresstxt2 + barcodedata.getString("subdist") + ",";
                    }
                    if (!barcodedata.isNull("dist")) {
                        addresstxt2 = addresstxt2 + barcodedata.getString("dist") + ",";
                        addresstxt2 = addresstxt2 + barcodedata.getString("state") + ",";
                        addresstxt2 = addresstxt2 + " India";
                    }
                    if (!barcodedata.isNull("uid")) {
                        aadharnumber.setText(String.valueOf(barcodedata.getLong("uid")));
                    }
                    if (!barcodedata.isNull("name")) {
                        vopresident.setText(barcodedata.getString("name"));
                    }
                    if (!barcodedata.isNull("gname")) {
                        //  v.setText(barcodedata.getString("gname"));
                    }
                    if (!barcodedata.isNull("pc")) {
                        pincode = barcodedata.getString("pc");
                    }
                    address.setText(addresstxt1 + "\n" + addresstxt2 + "\n" + pincode);
                } catch (Exception e) {
                    Log.e("xxxxxxxxx", e.toString());
                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private boolean isReadStorageAllowed() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;
        return false;
    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivityForResult(
                        new Intent(VRPRegistration.this, QrScannerActivity.class),
                        QrScannerActivity.QR_REQUEST_CODE);
            } else {
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }


    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     */
    private void registerUser(final String mvrpid, final String data,
                              final String password, final String username, final String mMobile, final boolean mUpdate) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";
        String url;
        if (mUpdate) {
            url = AppConfig.URL_UPDATE;
            pDialog.setMessage("Updating ...");
        } else {
            url = AppConfig.URL_REQUEST_SMS;
            pDialog.setMessage("Registering ...");
        }
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        if (mUpdate) {
                            dbVrp.updatedataByvrpid(sharedpreferences.getString(vrpid, ""), new Gson().toJson(data));
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.remove(update);
                            editor.commit();
                            Intent io = new Intent(VRPRegistration.this, ProfileActivity.class);
                            startActivity(io);
                            finish();
                        } else {
                            int mId = jObj.getInt("size");
                            dbVrp.addData(mvrpid + String.valueOf(mId + 1), new Gson().toJson(data));
                            dbVrp.updatePassByvrpid(mvrpid + String.valueOf(mId + 1), password);
                            Toast.makeText(getApplicationContext(), "vrp successfully registered. Try login now!", Toast.LENGTH_LONG).show();
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(vrpid, mvrpid + String.valueOf(mId + 1));
                            editor.commit();
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
                Log.e(TAG, "Registration Error: " + error.getMessage());
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
                params.put("data", data);
                params.put("password", password);
                params.put("username", username);
                params.put("mobile", mMobile);
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


}
