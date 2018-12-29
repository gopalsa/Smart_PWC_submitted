package nec.cst.pra.survey;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import fr.arnaudguyon.xmltojsonlib.XmlToJson;

import nec.cst.pra.GPSTracker;
import nec.cst.pra.R;
import nec.cst.pra.app.AppConfig;
import nec.cst.pra.app.GlideApp;
import nec.cst.pra.app.HeaderFooterPageEvent;
import nec.cst.pra.app.Imageutils;
import nec.cst.pra.electricity.Electricity;
import nec.cst.pra.electricity.ElectricityAddapter;
import nec.cst.pra.electricity.ElectricityClick;
import nec.cst.pra.needs.MasterNeedsAdapter;
import nec.cst.pra.needs.Need;
import nec.cst.pra.needs.NeedClick;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;


public class MainActivity extends AppCompatActivity implements NeedClick, Imageutils.ImageAttachmentListener,
        ElectricityClick {
    private static final int FINE_LOCATION_CODE = 199;
    TextView addSurvey;
    ProgressDialog pDialog;
    DbHelperSurvey dbHelperSurvey;
    public static final String mypreference = "mypref";
    SharedPreferences sharedpreferences;
    public static final String buStudentId = "buStudentIdKey";
    private RecyclerView needsList;
    private MasterNeedsAdapter mRecyclerAdapterNeeds;
    private ArrayList<Need> needs;


    Imageutils imageutils;
    EditText name;
    EditText gramPanchayat;
    EditText wards;
    EditText block;
    EditText district;
    EditText state;
    EditText constituency;
    EditText distanceDistrictHQ;
    EditText villageArea;
    EditText arableLandAgricultureArea;
    EditText forestArea;
    EditText housingAbadiArea;
    EditText areaUnderWaterBodies;
    EditText commonLandsArea;
    EditText wasteLand;
    EditText waterTable;
    EditText geoTag;

    EditText distanceHighway;
    EditText villageConnectedPaccaRoad;
    EditText roadLength;
    EditText yearOfConstruction;
    EditText schemeConstructed;
    EditText presentStatus;
    EditText lengthOfKachha;
    EditText lengthOfPakkka;
    EditText modeOfTransport;
    EditText frequencyOfAvailable;
    EditText typeOfForest;
    EditText communityForest;
    EditText governmentForest;
    EditText mainForestTrees;
    EditText energySpecies1;
    EditText energyArea1;
    EditText energySpecies2;
    EditText energyArea2;
    EditText energySpecies3;
    EditText energyArea3;


    String surveyId = null;
    GPSTracker gps;


    private RecyclerView electricityList;
    private ArrayList<Electricity> electricityArrayList = new ArrayList<>();
    ElectricityAddapter electricityAddapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.village_survey_common);

        imageutils = new Imageutils(this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        dbHelperSurvey = new DbHelperSurvey(this);

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        getSupportActionBar().setTitle("Add Survey");

        addSurvey = (TextView) findViewById(R.id.submit);

        prepareNeeds();
        needsList = (RecyclerView) findViewById(R.id.needsList);
        mRecyclerAdapterNeeds = new MasterNeedsAdapter(this, needs, this);
        needsList.setLayoutManager(new GridLayoutManager(this, 2));
        needsList.setAdapter(mRecyclerAdapterNeeds);


        electricityList = (RecyclerView) findViewById(R.id.electricityList);
        electricityAddapter = new ElectricityAddapter(this, electricityArrayList, this);
        final LinearLayoutManager agriManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        electricityList.setLayoutManager(agriManager);
        electricityList.setAdapter(electricityAddapter);
        ImageView addAppliance = (ImageView) findViewById(R.id.addAppliance);
        addAppliance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showApplianceDialog(-1);
            }
        });


        name = (EditText) findViewById(R.id.name);
        gramPanchayat = (EditText) findViewById(R.id.gramPanchayat);
        wards = (EditText) findViewById(R.id.wards);
        block = (EditText) findViewById(R.id.block);
        district = (EditText) findViewById(R.id.district);
        state = (EditText) findViewById(R.id.state);
        constituency = (EditText) findViewById(R.id.constituency);
        distanceDistrictHQ = (EditText) findViewById(R.id.distanceDistrictHQ);
        villageArea = (EditText) findViewById(R.id.villageArea);
        arableLandAgricultureArea = (EditText) findViewById(R.id.arableLandAgricultureArea);
        forestArea = (EditText) findViewById(R.id.forestArea);
        housingAbadiArea = (EditText) findViewById(R.id.housingAbadiArea);
        areaUnderWaterBodies = (EditText) findViewById(R.id.areaUnderWaterBodies);
        commonLandsArea = (EditText) findViewById(R.id.commonLandsArea);
        wasteLand = (EditText) findViewById(R.id.wasteLand);
        waterTable = (EditText) findViewById(R.id.waterTable);

        distanceHighway = (EditText) findViewById(R.id.distanceHighway);
        villageConnectedPaccaRoad = (EditText) findViewById(R.id.villageConnectedPaccaRoad);
        roadLength = (EditText) findViewById(R.id.roadLength);
        yearOfConstruction = (EditText) findViewById(R.id.yearOfConstruction);
        schemeConstructed = (EditText) findViewById(R.id.schemeConstructed);
        presentStatus = (EditText) findViewById(R.id.presentStatus);
        lengthOfKachha = (EditText) findViewById(R.id.lengthOfKachha);
        lengthOfPakkka = (EditText) findViewById(R.id.lengthOfPakkka);
        modeOfTransport = (EditText) findViewById(R.id.modeOfTransport);
        frequencyOfAvailable = (EditText) findViewById(R.id.frequencyOfAvailable);
        typeOfForest = (EditText) findViewById(R.id.typeOfForest);
        communityForest = (EditText) findViewById(R.id.communityForest);
        governmentForest = (EditText) findViewById(R.id.governmentForest);
        mainForestTrees = (EditText) findViewById(R.id.mainForestTrees);
        energySpecies1 = (EditText) findViewById(R.id.energySpecies1);
        energyArea1 = (EditText) findViewById(R.id.energyArea1);
        energySpecies2 = (EditText) findViewById(R.id.energySpecies2);
        energyArea2 = (EditText) findViewById(R.id.energyArea2);
        energySpecies3 = (EditText) findViewById(R.id.energySpecies3);
        energyArea3 = (EditText) findViewById(R.id.energyArea3);


        geoTag = (EditText) findViewById(R.id.geotag);
        ImageView georefresh = (ImageView) findViewById(R.id.refresh);

        geoTag.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (!checkPermission(new String[]{ACCESS_FINE_LOCATION})) {
                        requestPermission(new String[]{ACCESS_FINE_LOCATION}, FINE_LOCATION_CODE);
                    } else {
                        locationFetcher(geoTag, name);
                    }
                }
            }
        });

        georefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!checkPermission(new String[]{ACCESS_FINE_LOCATION})) {
                    requestPermission(new String[]{ACCESS_FINE_LOCATION}, FINE_LOCATION_CODE);
                } else {
                    locationFetcher(geoTag, name);
                }


            }
        });


        addSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Survey survey = new Survey(
                        name.getText().toString(),
                        gramPanchayat.getText().toString(),
                        wards.getText().toString(),
                        block.getText().toString(),
                        district.getText().toString(),
                        state.getText().toString(),
                        constituency.getText().toString(),
                        distanceDistrictHQ.getText().toString(),
                        villageArea.getText().toString(),
                        arableLandAgricultureArea.getText().toString(),
                        forestArea.getText().toString(),
                        housingAbadiArea.getText().toString(),
                        areaUnderWaterBodies.getText().toString(),
                        commonLandsArea.getText().toString(),
                        wasteLand.getText().toString(),
                        waterTable.getText().toString(),
                        geoTag.getText().toString(),
                        needs,
                        distanceHighway.getText().toString(),
                        villageConnectedPaccaRoad.getText().toString(),
                        roadLength.getText().toString(),
                        yearOfConstruction.getText().toString(),
                        schemeConstructed.getText().toString(),
                        presentStatus.getText().toString(),
                        lengthOfKachha.getText().toString(),
                        lengthOfPakkka.getText().toString(),
                        modeOfTransport.getText().toString(),
                        frequencyOfAvailable.getText().toString(),
                        typeOfForest.getText().toString(),
                        communityForest.getText().toString(),
                        governmentForest.getText().toString(),
                        mainForestTrees.getText().toString(),
                        energySpecies1.getText().toString(),
                        energyArea1.getText().toString(),
                        energySpecies2.getText().toString(),
                        energyArea2.getText().toString(),
                        energySpecies3.getText().toString(),
                        energyArea3.getText().toString(),
                        electricityArrayList
                );
                String studentId = sharedpreferences.getString(buStudentId, "");
                if (surveyId != null) {
                    int i = dbHelperSurvey.updateNote(surveyId, studentId, new Gson().toJson(survey));
                    if (i > 0) {
                        Toast.makeText(getApplicationContext(), "Updated successFully", Toast.LENGTH_SHORT).show();
                    } else {
                        dbHelperSurvey.insertSurvey(studentId + " " + String.valueOf(System.currentTimeMillis()), studentId, new Gson().toJson(survey));
                        Toast.makeText(getApplicationContext(), "Recorded successFully", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    dbHelperSurvey.insertSurvey(studentId + " " + String.valueOf(System.currentTimeMillis()), studentId, new Gson().toJson(survey));
                    Toast.makeText(getApplicationContext(), "Recorded successFully", Toast.LENGTH_SHORT).show();
                }

                finish();
            }
        });

        try {
            Survey bean = (Survey) dbHelperSurvey.getAllNotes().get(0);
            if (bean != null) {

                surveyId = bean.id;
                name.setText(bean.name);
                gramPanchayat.setText(bean.gramPanchayat);
                wards.setText(bean.wards);
                block.setText(bean.block);
                district.setText(bean.district);
                state.setText(bean.state);
                constituency.setText(bean.constituency);
                distanceDistrictHQ.setText(bean.distanceDistrictHQ);
                villageArea.setText(bean.villageArea);
                arableLandAgricultureArea.setText(bean.arableLandAgricultureArea);
                forestArea.setText(bean.forestArea);
                housingAbadiArea.setText(bean.housingAbadiArea);
                areaUnderWaterBodies.setText(bean.areaUnderWaterBodies);
                commonLandsArea.setText(bean.commonLandsArea);
                wasteLand.setText(bean.wasteLand);
                waterTable.setText(bean.waterTable);
                geoTag.setText(bean.geoTag);

                distanceHighway.setText(bean.distanceHighway);
                villageConnectedPaccaRoad.setText(bean.villageConnectedPaccaRoad);
                roadLength.setText(bean.roadLength);
                yearOfConstruction.setText(bean.yearOfConstruction);
                schemeConstructed.setText(bean.schemeConstructed);
                presentStatus.setText(bean.presentStatus);
                lengthOfKachha.setText(bean.lengthOfKachha);
                lengthOfPakkka.setText(bean.lengthOfPakkka);
                modeOfTransport.setText(bean.modeOfTransport);
                frequencyOfAvailable.setText(bean.frequencyOfAvailable);
                typeOfForest.setText(bean.typeOfForest);
                communityForest.setText(bean.communityForest);
                governmentForest.setText(bean.governmentForest);
                mainForestTrees.setText(bean.mainForestTrees);
                energySpecies1.setText(bean.energySpecies1);
                energyArea1.setText(bean.energyArea1);
                energySpecies2.setText(bean.energySpecies2);
                energyArea2.setText(bean.energyArea2);
                energySpecies3.setText(bean.energySpecies3);
                energyArea3.setText(bean.energyArea3);


                if (bean.needs != null && bean.needs.size() != 0) {
                    needs = bean.needs;
                    mRecyclerAdapterNeeds.notifyData(needs);
                }

                if (bean.electricityArrayList != null && bean.electricityArrayList.size() != 0) {
                    electricityArrayList = bean.electricityArrayList;
                    electricityAddapter.notifyData(electricityArrayList);
                }


                addSurvey.setText("Update");
            }
        } catch (Exception e) {
            Log.e("xxxxxx", "Something went wrong");
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 190) {
            if (resultCode == RESULT_OK) {
                String result = data.getStringExtra("result");
                try {


                } catch (Exception e) {
                    Log.e("xxxxxxxxxxxx", e.toString());
                }
            }
        } else if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                String result = data.getStringExtra("result");
                JSONObject jsonObj = null;
                try {
                    XmlToJson xmlToJson = new XmlToJson.Builder(result).build();
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
                        //   aadhar.setText(String.valueOf(barcodedata.getLong("uid")));
                    }
                    if (!barcodedata.isNull("name")) {
                        name.setText(barcodedata.getString("name"));
                    }
                    if (!barcodedata.isNull("gname")) {
                        //     fathername.setText(barcodedata.getString("gname"));
                    }
                    if (!barcodedata.isNull("pc")) {
                        pincode = barcodedata.getString("pc");
                    }
                    //address.setText(addresstxt1 + "\n" + addresstxt2 + "\n" + pincode);
                } catch (Exception e) {
                    Log.e("xxxxxxxxx", e.toString());
                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == 102) {
            if (resultCode == RESULT_OK) {
                String result = data.getStringExtra("result");
                //ration.setText(result);
            }
        } else {
            imageutils.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void image_attachment(int from, String filename, Bitmap file, Uri uri) {

    }

    private void imageAttachment(int from, String filename, Bitmap file, Uri uri, CircleImageView circleImageView) {
        String path = Environment.getExternalStorageDirectory() + File.separator + "ImageAttach" + File.separator;
        circleImageView.setMfilePath(imageutils.getPath(uri));
        circleImageView.setIsImage("false");
        if (filename != null) {
            circleImageView.setIsImage("true");
            imageutils.createImage(file, filename, path, false);
        }

        GlideApp.with(MainActivity.this).load(imageutils.getPath(uri))
                .dontAnimate()
                .thumbnail(0.5f)
                .placeholder(R.drawable.file)
                .into(circleImageView);
    }

    private void prepareNeeds() {
        needs = new ArrayList<>();

        needs.add(new Need("Primary Schools(Govt.)", "true", "0", "0"));
        needs.add(new Need("Primary Schools(Private)", "true", "0", "0"));
        needs.add(new Need("Middle Schools (Govt)", "true", "0", "0"));
        needs.add(new Need("Middle Schools(Private)", "true", "0", "0"));
        needs.add(new Need("Secondary Schools(Private)", "true", "0", "0"));
        needs.add(new Need("Secondary Schools(Govt.)", "true", "0", "0"));
        needs.add(new Need("ITI Diploma  Institutes (Govt)", "true", "0", "0"));
        needs.add(new Need("ITI Diploma Institutes(Private)", "true", "0", "0"));
        needs.add(new Need("Colleges(Govt.)", "true", "0", "0"));
        needs.add(new Need("Collages(Private)", "true", "0", "0"));
        needs.add(new Need("Banks / ATM", "true", "0", "0"));
        needs.add(new Need("Primary Health Centres", "true", "0", "0"));
        needs.add(new Need("Civil Hospital", "true", "0", "0"));
        needs.add(new Need("SHG’s", "true", "0", "0"));
        needs.add(new Need("NGOs", "true", "0", "0"));
        needs.add(new Need("Post Office", "true", "0", "0"));
        needs.add(new Need("Gas agencies", "true", "0", "0"));
        needs.add(new Need("Training Centres and specify which", "true", "0", "0"));
        needs.add(new Need("Electricity Office", "true", "0", "0"));
        needs.add(new Need("Anganwadi Kendra", "true", "0", "0"));
        needs.add(new Need("Petrol Pumps in village", "true", "0", "0"));
        needs.add(new Need("Kisan Sewa Kendra", "true", "0", "0"));
        needs.add(new Need("Krishi Mandi", "true", "0", "0"));
        needs.add(new Need("Fare Price Shop", "true", "0", "0"));
        needs.add(new Need("Milk Cooperative/Collection Centre", "true", "0", "0"));
        needs.add(new Need("Railway Station", "true", "0", "0"));
        needs.add(new Need("Bus Stop", "true", "0", "0"));
        needs.add(new Need("Veterinary Care Centre", "true", "0", "0"));
        needs.add(new Need("Sports Facility/Grounds", "true", "0", "0"));
        needs.add(new Need("Number of common sanitation complexes", "true", "0", "0"));

    }


    @Override
    public void onAddClick(int position, int count) {
        needs.get(position).setCount(String.valueOf(count));
        mRecyclerAdapterNeeds.notifyDataItem(needs, position);
    }

    @Override
    public void onMinusClick(int position, int count) {
        needs.get(position).setCount(String.valueOf(count));
        mRecyclerAdapterNeeds.notifyDataItem(needs, position);
    }

    @Override
    public void onAvailable(boolean isAvailable, int position) {
        needs.get(position).setIsAvailable(String.valueOf(isAvailable));
    }

    @Override
    public void onDistance(int position, String value) {
        needs.get(position).setDistance(value);
        mRecyclerAdapterNeeds.notifyDataItem(needs, position);
    }


    private void requestPermission(String[] permissions, int code) {
        ActivityCompat.requestPermissions(this, permissions, code);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == FINE_LOCATION_CODE) {
            if (grantResults.length > 0) {
                boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                if (locationAccepted) {
                    Toast.makeText(getApplicationContext(), "Permission Granted, Now you can access location data.", Toast.LENGTH_LONG).show();
                    locationFetcher(geoTag, name);
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied, You cannot access location data", Toast.LENGTH_LONG).show();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                            showMessageOKCancel(getResources().getString(R.string.locationPermissionAlert),
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(new String[]{ACCESS_FINE_LOCATION},
                                                        FINE_LOCATION_CODE);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }
            }
        } else {
            imageutils.request_permission_result(requestCode, permissions, grantResults);
        }
    }

    private boolean checkPermission(String[] permissions) {
        boolean result = false;
        for (int i = 0; i < permissions.length; i++) {
            result = ContextCompat.checkSelfPermission(getApplicationContext(), permissions[i]) == 0;
            if (!result) {
                return false;
            }
        }
        return result;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void locationFetcher(EditText gpsLocal, EditText addressLocal) {
        // check if GPS enabled
        gps = new GPSTracker(MainActivity.this);
        if (gps.canGetLocation()) {
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            gpsLocal.setText(latitude + "," + longitude);
            try {
                if (addressLocal != null) {
                    Geocoder geocoder;
                    List<Address> addresses;
                    geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                    addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    String addresstxt = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    addressLocal.setText(addresstxt + "," + addresses.get(0).getPostalCode());
                }
            } catch (Exception e) {
                Log.d("Error", e.toString());
            }
            // \n is for new line
            //       Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
    }

    @Override
    public void onItemClick(int position) {
        showApplianceDialog(position);
    }


    public void showApplianceDialog(final int position) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = MainActivity.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.electricity_register, null);

        final TextView submit = (TextView) dialogView.findViewById(R.id.submit);
        final EditText communityPlace = (EditText) dialogView.findViewById(R.id.communityPlace);
        final EditText electricalAppliances = (EditText) dialogView.findViewById(R.id.electricalAppliances);
        final EditText count = (EditText) dialogView.findViewById(R.id.count);
        final EditText duration = (EditText) dialogView.findViewById(R.id.duration);


        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();


        if (position != -1) {
            submit.setText("UPDATE");
            Electricity bean = electricityArrayList.get(position);
            communityPlace.setText(bean.getName());
            count.setText(bean.geteCount());
            duration.setText(bean.geteHousrs());
            electricalAppliances.setText(bean.getElectricalAppliances());


        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == -1) {
                    Electricity bean = new Electricity(communityPlace.getText().toString(),
                            electricalAppliances.getText().toString(), count.getText().toString(),
                            duration.getText().toString()
                    );
                    electricityArrayList.add(bean);
                } else {
                    electricityArrayList.get(position).setName(communityPlace.getText().toString());
                    electricityArrayList.get(position).seteCount(count.getText().toString());
                    electricityArrayList.get(position).seteHousrs(duration.getText().toString());
                    electricityArrayList.get(position).setElectricalAppliances(electricalAppliances.getText().toString());


                }
                electricityAddapter.notifyData(electricityArrayList);
                b.cancel();
            }
        });
        b.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_print, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.print) {
            try {
                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/PDF";
                File dir = new File(path);
                if (!dir.exists())
                    dir.mkdirs();
                Log.d("PDFCreator", "PDF Path: " + path);
                File file = new File(dir,  dbHelperSurvey.getAllNotes().get(0).name + ".pdf");
                FileOutputStream fOut = new FileOutputStream(file);
                Document document = new Document();
                document.setMargins(60, 60, 60, 60);
                PdfWriter pdfWriter = PdfWriter.getInstance(document, fOut);
                Rectangle rect = new Rectangle(175, 20, 530, 800);
                pdfWriter.setBoxSize("art", rect);

                Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.cst_pdf);
                Bitmap bu = BitmapFactory.decodeResource(getResources(), R.drawable.bu_logo);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                icon.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
                bu.compress(Bitmap.CompressFormat.PNG, 100, stream1);
                byte[] byteArray1 = stream1.toByteArray();

                HeaderFooterPageEvent event = new HeaderFooterPageEvent(Image.getInstance(byteArray), Image.getInstance(byteArray1));
                pdfWriter.setPageEvent(event);

                document.open();
                AppConfig.addMetaData(document);
                // AppConfig.addTitlePage(document);
                AppConfig.addContent(document, dbHelperSurvey.getAllNotes().get(0), MainActivity.this);
                document.close();


            } catch (Error | Exception e) {
                e.printStackTrace();
            }
            Uri photoURI = FileProvider.getUriForFile(getApplicationContext(),
                    getApplicationContext().getPackageName() + ".smart.cst.pra.provider",

                    new File(Environment.getExternalStorageDirectory()
                            .getAbsolutePath() + "/PDF/" +  dbHelperSurvey.getAllNotes().get(0).name + ".pdf"));

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(photoURI
                    , "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
