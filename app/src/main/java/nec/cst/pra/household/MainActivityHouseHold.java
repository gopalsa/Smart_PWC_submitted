package nec.cst.pra.household;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import nec.cst.pra.ConvertUtils;
import nec.cst.pra.GPSTracker;
import nec.cst.pra.R;
import nec.cst.pra.Vrp;
import nec.cst.pra.app.AppController;
import nec.cst.pra.db.DbProfile;
import nec.cst.pra.db.DbVrp;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivityHouseHold extends AppCompatActivity implements OnItemClick {
    private static final int FINE_LOCATION_CODE = 199;
    ProgressDialog pDialog;
    public EditText geotag;
    public EditText village;
    public EditText wardNo;
    public EditText district;
    public EditText gramPanchayat;
    public EditText block;
    public EditText state;
    public EditText name;
    public EditText gender;
    public EditText age;
    public EditText household;
    public EditText contactNo;
    public EditText idCardType;
    public EditText idCardNo;
    public EditText householdId;
    public EditText nameOfhead;
    public EditText houseGender;
    public EditText category;
    public EditText povertyStatus;
    public EditText ownHouse;
    public EditText typeOfHouse;
    public EditText toilet;
    public EditText drainage;
    public EditText wasteSystem;
    public EditText compostPit;
    public EditText biogasPlant;
    public EditText annualInncom;
    public EditText pmJan;
    public EditText pmUjjwala;
    public EditText pmAwas;
    public EditText sukanya;
    public EditText mudra;
    public EditText pmJivan;
    public EditText pmSuraksha;
    public EditText atal;
    public EditText fasal;
    public EditText kaushal;
    public EditText krishi;
    public EditText janAushadi;
    public EditText swachh;
    public EditText soilHealth;
    public EditText ladli;
    public EditText janani;
    public EditText kisan;
    public EditText pipedWater;
    public EditText communityWater;
    public EditText handPump;
    public EditText openWell;
    public EditText modeWaterStorage;
    public EditText anyOtherSource;
    public EditText electHousehold;
    public EditText electDay;
    public EditText electLight;
    public EditText electCooking;
    public EditText electCullah;
    public EditText cultivableArea;
    public EditText irrigatedArea;
    public EditText unIrrigated;
    public EditText barran;
    public EditText unCultArea;
    public EditText chemicalFertilisers;
    public EditText chemicalInsecticides;
    public EditText weedicide;
    public EditText manures;
    public EditText irrigation;
    public EditText irrigationSystem;
    public EditText cows;
    public EditText Buffalo;
    public EditText goatsSheep;
    public EditText calves;
    public EditText bullocks;
    public EditText poultryDucks;
    public EditText liveStockOthers;
    public EditText shelterforLivestock;
    public EditText averageMilk;
    public EditText animalWaste;
    public EditText sign;
    public EditText survey;
    FamilyInfoAddapter familyInfoAddapter;
    EnergyPowerAddapter energyPowerAddapter;
    AgriProduceAddapter agriProduceAddapter;
    MajorPrbAddapter majorPrbAddapter;

    private RecyclerView energyList;
    private ArrayList<EnergyPower> energyArrayList = new ArrayList<>();
    private RecyclerView agriprocList;
    private ArrayList<AgriProduce> agriprocArrayList = new ArrayList<>();
    private RecyclerView familyList;
    private ArrayList<FamilyInfo> familyArrayList = new ArrayList<>();
    private RecyclerView majorList;
    private ArrayList<MajorPrb> majorArrayList = new ArrayList<>();


    Mainbean mainbean = null;
    public static final String mypreference = "mypref";
    public static final String buSurveyerId = "buSurveyerIdKey";
    SharedPreferences sharedpreferences;
    private String TAG = getClass().getSimpleName();

    public static final String vrpid = "vrpidKey";
    public static final String update = "updateKey";
    String vrpId = "";
    public static final String tittle = "tittleKey";

    private Vrp vrp;
    DbVrp dbVrp;

    GPSTracker gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_house);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        sharedpreferences = this.getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        dbVrp = new DbVrp(this);
        if (sharedpreferences.contains(vrpid)) {
            vrpId = sharedpreferences.getString(vrpid, "").trim();
        }

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        vrp = new Gson().fromJson(ConvertUtils.sample(dbVrp.getDataByvrpid(vrpId).get(1)), Vrp.class);


        energyList = (RecyclerView) findViewById(R.id.energyList);
        energyPowerAddapter = new EnergyPowerAddapter(this, energyArrayList, this);
        final LinearLayoutManager addManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        energyList.setLayoutManager(addManager);
        energyList.setAdapter(energyPowerAddapter);
        TextView energytext = (TextView) findViewById(R.id.energytext);
        energytext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEnergyDialog(-1);
            }
        });


        agriprocList = (RecyclerView) findViewById(R.id.agriProList);
        agriProduceAddapter = new AgriProduceAddapter(this, agriprocArrayList, this);
        final LinearLayoutManager agriManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        agriprocList.setLayoutManager(agriManager);
        agriprocList.setAdapter(agriProduceAddapter);
        TextView agriProtext = (TextView) findViewById(R.id.agriProtext);
        agriProtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAgriProDialog(-1);
            }
        });

        familyList = (RecyclerView) findViewById(R.id.familyList);
        familyInfoAddapter = new FamilyInfoAddapter(this, familyArrayList, this);
        final LinearLayoutManager familyManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        familyList.setLayoutManager(familyManager);
        familyList.setAdapter(familyInfoAddapter);
        TextView familytext = (TextView) findViewById(R.id.familytext);
        familytext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFamilyDialog(-1);
            }
        });


        majorList = (RecyclerView) findViewById(R.id.majorProList);
        majorPrbAddapter = new MajorPrbAddapter(this, majorArrayList, this);
        final LinearLayoutManager majorManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        majorList.setLayoutManager(majorManager);
        majorList.setAdapter(majorPrbAddapter);
        TextView majortext = (TextView) findViewById(R.id.majorProtext);
        majortext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMajorDialog(-1);
            }
        });


        geotag = (EditText) findViewById(R.id.geotag);
        village = (EditText) findViewById(R.id.village);
        wardNo = (EditText) findViewById(R.id.wardNo);
        district = (EditText) findViewById(R.id.district);
        gramPanchayat = (EditText) findViewById(R.id.gramPanchayat);
        block = (EditText) findViewById(R.id.block);
        state = (EditText) findViewById(R.id.state);
        name = (EditText) findViewById(R.id.name);
        gender = (EditText) findViewById(R.id.gender);
        age = (EditText) findViewById(R.id.age);
        household = (EditText) findViewById(R.id.household);
        contactNo = (EditText) findViewById(R.id.contactNo);
        idCardType = (EditText) findViewById(R.id.idCardType);
        idCardNo = (EditText) findViewById(R.id.idCardNo);
        householdId = (EditText) findViewById(R.id.householdId);
        nameOfhead = (EditText) findViewById(R.id.nameOfhead);
        houseGender = (EditText) findViewById(R.id.houseGender);
        category = (EditText) findViewById(R.id.category);
        povertyStatus = (EditText) findViewById(R.id.povertyStatus);
        ownHouse = (EditText) findViewById(R.id.ownHouse);
        typeOfHouse = (EditText) findViewById(R.id.typeOfHouse);
        toilet = (EditText) findViewById(R.id.toilet);
        drainage = (EditText) findViewById(R.id.drainage);
        wasteSystem = (EditText) findViewById(R.id.wasteSystem);
        compostPit = (EditText) findViewById(R.id.compostPit);
        biogasPlant = (EditText) findViewById(R.id.biogasPlant);
        annualInncom = (EditText) findViewById(R.id.annualInncom);
        pmJan = (EditText) findViewById(R.id.pmJan);
        pmUjjwala = (EditText) findViewById(R.id.pmUjjwala);
        pmAwas = (EditText) findViewById(R.id.pmAwas);
        sukanya = (EditText) findViewById(R.id.sukanya);
        mudra = (EditText) findViewById(R.id.mudra);
        pmJivan = (EditText) findViewById(R.id.pmJivan);
        pmSuraksha = (EditText) findViewById(R.id.pmSuraksha);
        atal = (EditText) findViewById(R.id.atal);
        fasal = (EditText) findViewById(R.id.fasal);
        kaushal = (EditText) findViewById(R.id.kaushal);
        krishi = (EditText) findViewById(R.id.krishi);
        janAushadi = (EditText) findViewById(R.id.janAushadi);
        swachh = (EditText) findViewById(R.id.swachh);
        soilHealth = (EditText) findViewById(R.id.soilHealth);
        ladli = (EditText) findViewById(R.id.ladli);
        janani = (EditText) findViewById(R.id.janani);
        kisan = (EditText) findViewById(R.id.kisan);
        pipedWater = (EditText) findViewById(R.id.pipedWater);
        communityWater = (EditText) findViewById(R.id.communityWater);
        handPump = (EditText) findViewById(R.id.handPump);
        openWell = (EditText) findViewById(R.id.openWell);
        modeWaterStorage = (EditText) findViewById(R.id.modeWaterStorage);
        anyOtherSource = (EditText) findViewById(R.id.anyOtherSource);
        electHousehold = (EditText) findViewById(R.id.electHousehold);
        electDay = (EditText) findViewById(R.id.electDay);
        electLight = (EditText) findViewById(R.id.electLight);
        electCooking = (EditText) findViewById(R.id.electCooking);
        electCullah = (EditText) findViewById(R.id.electCullah);
        cultivableArea = (EditText) findViewById(R.id.cultivableArea);
        irrigatedArea = (EditText) findViewById(R.id.irrigatedArea);
        unIrrigated = (EditText) findViewById(R.id.unIrrigated);
        barran = (EditText) findViewById(R.id.barran);
        unCultArea = (EditText) findViewById(R.id.unCultArea);
        chemicalFertilisers = (EditText) findViewById(R.id.chemicalFertilisers);
        chemicalInsecticides = (EditText) findViewById(R.id.chemicalInsecticides);
        weedicide = (EditText) findViewById(R.id.weedicide);
        manures = (EditText) findViewById(R.id.manures);
        irrigation = (EditText) findViewById(R.id.irrigation);
        irrigationSystem = (EditText) findViewById(R.id.irrigationSystem);
        cows = (EditText) findViewById(R.id.cows);
        Buffalo = (EditText) findViewById(R.id.Buffalo);
        goatsSheep = (EditText) findViewById(R.id.goatsSheep);
        calves = (EditText) findViewById(R.id.calves);
        bullocks = (EditText) findViewById(R.id.bullocks);
        poultryDucks = (EditText) findViewById(R.id.poultryDucks);
        liveStockOthers = (EditText) findViewById(R.id.liveStockOthers);
        shelterforLivestock = (EditText) findViewById(R.id.shelterforLivestock);
        averageMilk = (EditText) findViewById(R.id.averageMilk);
        animalWaste = (EditText) findViewById(R.id.animalWaste);
        sign = (EditText) findViewById(R.id.sign);
        sign.setEnabled(false);
        if (vrp != null) {
            sign.setText(vrp.getName());
        }
        survey = (EditText) findViewById(R.id.survey);

        ImageView georefresh = (ImageView) findViewById(R.id.refresh);

        geotag.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (!checkPermission(new String[]{ACCESS_FINE_LOCATION})) {
                        requestPermission(new String[]{ACCESS_FINE_LOCATION}, FINE_LOCATION_CODE);
                    } else {
                        locationFetcher(geotag, village);
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
                    locationFetcher(geotag, village);
                }


            }
        });

        TextView submit = (TextView) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (geotag.getText().toString().length() <= 0) {
                    geotag.setError("Enter Geotag");
                } else if (village.getText().toString().length() <= 0) {
                    village.setError("Enter village");
                } else if (wardNo.getText().toString().length() <= 0) {
                    wardNo.setError("Enter ward No");
                } else if (district.getText().toString().length() <= 0) {
                    district.setError("Enter district");
                } else if (gramPanchayat.getText().toString().length() <= 0) {
                    gramPanchayat.setError("Enter gram Panchayat");
                } else if (block.getText().toString().length() <= 0) {
                    block.setError("Enter block");
                } else if (state.getText().toString().length() <= 0) {
                    state.setError("Enter state");
                } else if (name.getText().toString().length() <= 0) {
                    name.setError("Enter name");
                } else if (gender.getText().toString().length() <= 0) {
                    gender.setError("Enter gender");
                } else if (age.getText().toString().length() <= 0) {
                    age.setError("Enter age");
                } else if (household.getText().toString().length() <= 0) {
                    household.setError("Enter household");
                } else if (contactNo.getText().toString().length() <= 0) {
                    contactNo.setError("Enter contact No");
                } else if (idCardType.getText().toString().length() <= 0) {
                    idCardType.setError("Enter id Card Type");
                } else if (idCardNo.getText().toString().length() <= 0) {
                    idCardNo.setError("Enter idCard No");
                } else if (householdId.getText().toString().length() <= 0) {
                    householdId.setError("Enter householdId");
                } else if (nameOfhead.getText().toString().length() <= 0) {
                    nameOfhead.setError("Enter name Of head");
                } else if (houseGender.getText().toString().length() <= 0) {
                    houseGender.setError("Enter house Gender");
                } else if (category.getText().toString().length() <= 0) {
                    category.setError("Enter category");
                } else if (povertyStatus.getText().toString().length() <= 0) {
                    povertyStatus.setError("Enter poverty Status");
                } else if (ownHouse.getText().toString().length() <= 0) {
                    ownHouse.setError("Enter own House");
                } else if (typeOfHouse.getText().toString().length() <= 0) {
                    typeOfHouse.setError("Enter type Of House");
                } else if (toilet.getText().toString().length() <= 0) {
                    toilet.setError("Enter toilet");
                } else if (drainage.getText().toString().length() <= 0) {
                    drainage.setError("Enter drainage");
                } else if (wasteSystem.getText().toString().length() <= 0) {
                    wasteSystem.setError("Enter waste System");
                } else if (compostPit.getText().toString().length() <= 0) {
                    compostPit.setError("Enter compostPit");
                } else if (biogasPlant.getText().toString().length() <= 0) {
                    biogasPlant.setError("Enter biogas Plant");
                } else if (electHousehold.getText().toString().length() <= 0) {
                    electHousehold.setError("Enter elect Household");
                } else if (electDay.getText().toString().length() <= 0) {
                    electDay.setError("Enter elect Day");
                } else if (electLight.getText().toString().length() <= 0) {
                    electLight.setError("Enter elect Light");
                } else if (electCooking.getText().toString().length() <= 0) {
                    electCooking.setError("Enter elect Cooking");
                } else if (electCullah.getText().toString().length() <= 0) {
                    electCullah.setError("Enter elect Cullah");
                } else if (cultivableArea.getText().toString().length() <= 0) {
                    cultivableArea.setError("Enter cultivable Area");
                } else if (irrigatedArea.getText().toString().length() <= 0) {
                    irrigatedArea.setError("Enter irrigated Area");
                } else if (unIrrigated.getText().toString().length() <= 0) {
                    unIrrigated.setError("Enter unIrrigated");
                } else if (barran.getText().toString().length() <= 0) {
                    barran.setError("Enter barran");
                } else if (unCultArea.getText().toString().length() <= 0) {
                    unCultArea.setError("Enter unCult Area");
                } else if (irrigation.getText().toString().length() <= 0) {
                    irrigation.setError("Enter irrigation");
                } else if (sign.getText().toString().length() <= 0) {
                    sign.setError("Enter sign");
                } else if (survey.getText().toString().length() <= 0) {
                    survey.setError("Enter survey");
                } else if (familyArrayList.size() <= 0) {
                    Toast.makeText(getApplicationContext(), "Enter Family Member", Toast.LENGTH_SHORT).show();
                } else if (energyArrayList.size() <= 0) {
                    Toast.makeText(getApplicationContext(), "Enter Energy Power", Toast.LENGTH_SHORT).show();
                } else if (agriprocArrayList.size() <= 0) {
                    Toast.makeText(getApplicationContext(), "Enter Agri Produce", Toast.LENGTH_SHORT).show();
                } else {
                    Mainbean tempMainbean = new Mainbean(
                            geotag.getText().toString(),
                            village.getText().toString(),
                            wardNo.getText().toString(),
                            district.getText().toString(),
                            gramPanchayat.getText().toString(),
                            block.getText().toString(),
                            state.getText().toString(),
                            name.getText().toString(),
                            gender.getText().toString(),
                            age.getText().toString(),
                            household.getText().toString(),
                            contactNo.getText().toString(),
                            idCardType.getText().toString(),
                            idCardNo.getText().toString(),
                            householdId.getText().toString(),
                            nameOfhead.getText().toString(),
                            houseGender.getText().toString(),
                            category.getText().toString(),
                            povertyStatus.getText().toString(),
                            ownHouse.getText().toString(),
                            typeOfHouse.getText().toString(),
                            toilet.getText().toString(),
                            drainage.getText().toString(),
                            wasteSystem.getText().toString(),
                            compostPit.getText().toString(),
                            biogasPlant.getText().toString(),
                            annualInncom.getText().toString(),
                            pmJan.getText().toString(),
                            pmUjjwala.getText().toString(),
                            pmAwas.getText().toString(),
                            sukanya.getText().toString(),
                            mudra.getText().toString(),
                            pmJivan.getText().toString(),
                            pmSuraksha.getText().toString(),
                            atal.getText().toString(),
                            fasal.getText().toString(),
                            kaushal.getText().toString(),
                            krishi.getText().toString(),
                            janAushadi.getText().toString(),
                            swachh.getText().toString(),
                            soilHealth.getText().toString(),
                            ladli.getText().toString(),
                            janani.getText().toString(),
                            kisan.getText().toString(),
                            pipedWater.getText().toString(),
                            communityWater.getText().toString(),
                            handPump.getText().toString(),
                            openWell.getText().toString(),
                            modeWaterStorage.getText().toString(),
                            anyOtherSource.getText().toString(),
                            electHousehold.getText().toString(),
                            electDay.getText().toString(),
                            electLight.getText().toString(),
                            electCooking.getText().toString(),
                            electCullah.getText().toString(),
                            cultivableArea.getText().toString(),
                            irrigatedArea.getText().toString(),
                            unIrrigated.getText().toString(),
                            barran.getText().toString(),
                            unCultArea.getText().toString(),
                            chemicalFertilisers.getText().toString(),
                            chemicalInsecticides.getText().toString(),
                            weedicide.getText().toString(),
                            manures.getText().toString(),
                            irrigation.getText().toString(),
                            irrigationSystem.getText().toString(),
                            cows.getText().toString(),
                            Buffalo.getText().toString(),
                            goatsSheep.getText().toString(),
                            calves.getText().toString(),
                            bullocks.getText().toString(),
                            poultryDucks.getText().toString(),
                            liveStockOthers.getText().toString(),
                            shelterforLivestock.getText().toString(),
                            averageMilk.getText().toString(),
                            animalWaste.getText().toString(),
                            sign.getText().toString(),
                            survey.getText().toString(),
                            familyArrayList,
                            energyArrayList,
                            agriprocArrayList,
                            majorArrayList);
                    String jsonVal = new Gson().toJson(tempMainbean);
                    Log.e("xxxxxxxxxxxxx", jsonVal);
                    if (mainbean == null) {
                        tempMainbean.setId(String.valueOf(System.currentTimeMillis()));
                    } else {
                        tempMainbean.setId(mainbean.id);
                    }

                    getCreateTest(tempMainbean.id, sharedpreferences.getString(buSurveyerId, ""), jsonVal);
                }
            }
        });


        try {
            mainbean = (Mainbean) getIntent().getSerializableExtra("object");
            if (mainbean != null) {
                geotag.setText(mainbean.geoTag);
                village.setText(mainbean.village);
                wardNo.setText(mainbean.wardNo);
                district.setText(mainbean.district);
                gramPanchayat.setText(mainbean.gramPanchayat);
                block.setText(mainbean.block);
                state.setText(mainbean.state);
                name.setText(mainbean.name);
                gender.setText(mainbean.gender);
                age.setText(mainbean.age);
                household.setText(mainbean.household);
                contactNo.setText(mainbean.contactNo);
                idCardType.setText(mainbean.idCardType);
                idCardNo.setText(mainbean.idCardNo);
                householdId.setText(mainbean.householdId);
                nameOfhead.setText(mainbean.nameOfhead);
                houseGender.setText(mainbean.houseGender);
                category.setText(mainbean.category);
                povertyStatus.setText(mainbean.povertyStatus);
                ownHouse.setText(mainbean.ownHouse);
                typeOfHouse.setText(mainbean.typeOfHouse);
                toilet.setText(mainbean.toilet);
                drainage.setText(mainbean.drainage);
                wasteSystem.setText(mainbean.wasteSystem);
                compostPit.setText(mainbean.compostPit);
                biogasPlant.setText(mainbean.biogasPlant);
                annualInncom.setText(mainbean.annualInncom);
                pmJan.setText(mainbean.pmJan);
                pmUjjwala.setText(mainbean.pmUjjwala);
                pmAwas.setText(mainbean.pmAwas);
                sukanya.setText(mainbean.sukanya);
                mudra.setText(mainbean.mudra);
                pmJivan.setText(mainbean.pmJivan);
                pmSuraksha.setText(mainbean.pmSuraksha);
                atal.setText(mainbean.atal);
                fasal.setText(mainbean.fasal);
                kaushal.setText(mainbean.kaushal);
                krishi.setText(mainbean.krishi);
                janAushadi.setText(mainbean.janAushadi);
                swachh.setText(mainbean.swachh);
                soilHealth.setText(mainbean.soilHealth);
                ladli.setText(mainbean.ladli);
                janani.setText(mainbean.janani);
                kisan.setText(mainbean.kisan);
                pipedWater.setText(mainbean.pipedWater);
                communityWater.setText(mainbean.communityWater);
                handPump.setText(mainbean.handPump);
                openWell.setText(mainbean.openWell);
                modeWaterStorage.setText(mainbean.modeWaterStorage);
                anyOtherSource.setText(mainbean.anyOtherSource);
                electHousehold.setText(mainbean.electHousehold);
                electDay.setText(mainbean.electDay);
                electLight.setText(mainbean.electLight);
                electCooking.setText(mainbean.electCooking);
                electCullah.setText(mainbean.electCullah);
                cultivableArea.setText(mainbean.cultivableArea);
                irrigatedArea.setText(mainbean.irrigatedArea);
                unIrrigated.setText(mainbean.unIrrigated);
                barran.setText(mainbean.barran);
                unCultArea.setText(mainbean.unCultArea);
                chemicalFertilisers.setText(mainbean.chemicalFertilisers);
                chemicalInsecticides.setText(mainbean.chemicalInsecticides);
                weedicide.setText(mainbean.weedicide);
                manures.setText(mainbean.manures);
                irrigation.setText(mainbean.irrigation);
                irrigationSystem.setText(mainbean.irrigationSystem);
                cows.setText(mainbean.cows);
                Buffalo.setText(mainbean.Buffalo);
                goatsSheep.setText(mainbean.goatsSheep);
                calves.setText(mainbean.calves);
                bullocks.setText(mainbean.bullocks);
                poultryDucks.setText(mainbean.poultryDucks);
                liveStockOthers.setText(mainbean.liveStockOthers);
                shelterforLivestock.setText(mainbean.shelterforLivestock);
                averageMilk.setText(mainbean.averageMilk);
                animalWaste.setText(mainbean.animalWaste);
                sign.setText(mainbean.sign);
                survey.setText(mainbean.survey);


                familyArrayList = mainbean.familyInfos;
                familyInfoAddapter.notifyData(familyArrayList);

                energyArrayList = mainbean.energyPowers;
                energyPowerAddapter.notifyData(energyArrayList);

                agriprocArrayList = mainbean.agriProduces;
                agriProduceAddapter.notifyData(agriprocArrayList);


                majorArrayList = mainbean.majorPrbs;
                majorPrbAddapter.notifyData(majorArrayList);
            }
        } catch (Exception e) {
            Log.e("xxxxxx", "Something went wrong");
        }

    }


    public void showEnergyDialog(final int position) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivityHouseHold.this);
        LayoutInflater inflater = MainActivityHouseHold.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.energypower, null);

        final TextView submit = (TextView) dialogView.findViewById(R.id.submit);
        final EditText application = (EditText) dialogView.findViewById(R.id.application);
        final EditText nos = (EditText) dialogView.findViewById(R.id.nos);
        final EditText duration = (EditText) dialogView.findViewById(R.id.duration);

        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();


        if (position != -1) {
            submit.setText("UPDATE");
            EnergyPower bean = energyArrayList.get(position);
            application.setText(bean.application);
            nos.setText(bean.nos);
            duration.setText(bean.duration);

        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == -1) {
                    EnergyPower bean = new EnergyPower(application.getText().toString(), nos.getText().toString(), duration.getText().toString());
                    energyArrayList.add(bean);
                } else {
                    energyArrayList.get(position).setApplication(application.getText().toString());
                    energyArrayList.get(position).setNos(nos.getText().toString());
                    energyArrayList.get(position).setDuration(duration.getText().toString());


                }
                energyPowerAddapter.notifyData(energyArrayList);
                b.cancel();
            }
        });
        b.show();
    }


    public void showAgriProDialog(final int position) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivityHouseHold.this);
        LayoutInflater inflater = MainActivityHouseHold.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.agriproduce, null);

        final TextView submit = (TextView) dialogView.findViewById(R.id.submit);
        final EditText crop = (EditText) dialogView.findViewById(R.id.crop);
        final EditText cropPrev = (EditText) dialogView.findViewById(R.id.cropPrev);
        final EditText product = (EditText) dialogView.findViewById(R.id.product);


        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();


        if (position != -1) {
            submit.setText("UPDATE");
            AgriProduce bean = agriprocArrayList.get(position);
            crop.setText(bean.crop);
            cropPrev.setText(bean.cropPrev);
            product.setText(bean.product);


        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (crop.getText().toString().length() <= 0) {
                    crop.setError("Enter crop");
                } else if (cropPrev.getText().toString().length() <= 0) {
                    cropPrev.setError("Enter crop Prev");
                } else if (product.getText().toString().length() <= 0) {
                    product.setError("Enter product");
                } else {
                    if (position == -1) {
                        AgriProduce bean = new AgriProduce(crop.getText().toString(), cropPrev.getText().toString(), product.getText().toString());
                        agriprocArrayList.add(bean);
                    } else {
                        agriprocArrayList.get(position).setCrop(crop.getText().toString());
                        agriprocArrayList.get(position).setCropPrev(cropPrev.getText().toString());
                        agriprocArrayList.get(position).setProduct(product.getText().toString());


                    }
                    agriProduceAddapter.notifyData(agriprocArrayList);
                    b.cancel();
                }
            }
        });
        b.show();
    }

    public void showFamilyDialog(final int position) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivityHouseHold.this);
        LayoutInflater inflater = MainActivityHouseHold.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.familyinfo, null);

        final TextView submit = (TextView) dialogView.findViewById(R.id.submit);
        final EditText relationship = (EditText) dialogView.findViewById(R.id.relationship);
        final EditText name = (EditText) dialogView.findViewById(R.id.familyName);
        final EditText age = (EditText) dialogView.findViewById(R.id.familyAge);
        final EditText sex = (EditText) dialogView.findViewById(R.id.familySex);
        final EditText maritalStatus = (EditText) dialogView.findViewById(R.id.maritalStatus);
        final EditText education = (EditText) dialogView.findViewById(R.id.education);
        final EditText sclCol = (EditText) dialogView.findViewById(R.id.sclCol);
        final EditText aadhaarNo = (EditText) dialogView.findViewById(R.id.aadhaarNo);
        final EditText bank = (EditText) dialogView.findViewById(R.id.bank);
        final EditText computeLiterate = (EditText) dialogView.findViewById(R.id.computeLiterate);
        final EditText socialSecurity = (EditText) dialogView.findViewById(R.id.socialSecurity);
        final EditText majorHealth = (EditText) dialogView.findViewById(R.id.majorHealth);
        final EditText jobCard = (EditText) dialogView.findViewById(R.id.jobCard);
        final EditText groupTick = (EditText) dialogView.findViewById(R.id.groupTick);
        final EditText occupationCode = (EditText) dialogView.findViewById(R.id.occupationCode);


        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();


        if (position != -1) {
            submit.setText("UPDATE");
            FamilyInfo bean = familyArrayList.get(position);
            relationship.setText(bean.relationship);
            name.setText(bean.familyName);
            age.setText(bean.familyAge);
            sex.setText(bean.familySex);
            maritalStatus.setText(bean.maritalStatus);
            education.setText(bean.education);
            sclCol.setText(bean.sclCol);
            aadhaarNo.setText(bean.aadhaarNo);
            bank.setText(bean.bank);
            computeLiterate.setText(bean.computeLiterate);
            socialSecurity.setText(bean.socialSecurity);
            majorHealth.setText(bean.majorHealth);
            jobCard.setText(bean.jobCard);
            groupTick.setText(bean.groupTick);
            occupationCode.setText(bean.occupationCode);


        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().length() <= 0) {
                    name.setError("Enter name");
                } else if (age.getText().toString().length() <= 0) {
                    age.setError("Enter age");
                } else if (sex.getText().toString().length() <= 0) {
                    sex.setError("Enter sex");
                } else if (maritalStatus.getText().toString().length() <= 0) {
                    maritalStatus.setError("Enter maritalStatus");
                } else if (education.getText().toString().length() <= 0) {
                    education.setError("Enter education");
                } else if (sclCol.getText().toString().length() <= 0) {
                    sclCol.setError("Enter sclCol");
                } else if (aadhaarNo.getText().toString().length() <= 0) {
                    aadhaarNo.setError("Enter aadhaarNo");
                } else if (bank.getText().toString().length() <= 0) {
                    bank.setError("Enter bank");
                } else if (computeLiterate.getText().toString().length() <= 0) {
                    computeLiterate.setError("Enter computeLiterate");
                } else if (socialSecurity.getText().toString().length() <= 0) {
                    socialSecurity.setError("Enter socialSecurity");
                } else if (majorHealth.getText().toString().length() <= 0) {
                    majorHealth.setError("Enter majorHealth");
                } else if (jobCard.getText().toString().length() <= 0) {
                    jobCard.setError("Enter jobCard");
                } else if (groupTick.getText().toString().length() <= 0) {
                    groupTick.setError("Enter groupTick");
                } else if (occupationCode.getText().toString().length() <= 0) {
                    occupationCode.setError("Enter occupationCode");
                } else {
                    if (position == -1) {
                        FamilyInfo bean = new FamilyInfo(relationship.getText().toString(),
                                name.getText().toString(), age.getText().toString(),
                                sex.getText().toString(), maritalStatus.getText().toString(),
                                education.getText().toString(), sclCol.getText().toString(),
                                aadhaarNo.getText().toString(), bank.getText().toString(), computeLiterate.getText().toString(), socialSecurity.getText().toString(), majorHealth.getText().toString(), jobCard.getText().toString(), groupTick.getText().toString(), occupationCode.getText().toString()

                        );
                        familyArrayList.add(bean);
                    } else {
                        familyArrayList.get(position).setFamilyName(name.getText().toString());
                        familyArrayList.get(position).setFamilyAge(age.getText().toString());
                        familyArrayList.get(position).setFamilySex(sex.getText().toString());
                        familyArrayList.get(position).setMaritalStatus(maritalStatus.getText().toString());
                        familyArrayList.get(position).setEducation(education.getText().toString());
                        familyArrayList.get(position).setSclCol(sclCol.getText().toString());
                        familyArrayList.get(position).setAadhaarNo(aadhaarNo.getText().toString());
                        familyArrayList.get(position).setBank(bank.getText().toString());
                        familyArrayList.get(position).setComputeLiterate(computeLiterate.getText().toString());
                        familyArrayList.get(position).setSocialSecurity(socialSecurity.getText().toString());
                        familyArrayList.get(position).setMajorHealth(majorHealth.getText().toString());
                        familyArrayList.get(position).setJobCard(jobCard.getText().toString());
                        familyArrayList.get(position).setGroupTick(groupTick.getText().toString());
                        familyArrayList.get(position).setOccupationCode(occupationCode.getText().toString());


                    }
                    familyInfoAddapter.notifyData(familyArrayList);
                    b.cancel();
                }
            }
        });
        b.show();
    }

    public void showMajorDialog(final int position) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivityHouseHold.this);
        LayoutInflater inflater = MainActivityHouseHold.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.majorprb, null);

        final TextView submit = (TextView) dialogView.findViewById(R.id.submit);
        final EditText problem = (EditText) dialogView.findViewById(R.id.problem);
        final EditText possible = (EditText) dialogView.findViewById(R.id.possible);


        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();


        if (position != -1) {
            submit.setText("UPDATE");
            MajorPrb bean = majorArrayList.get(position);
            problem.setText(bean.problem);
            possible.setText(bean.possible);


        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (problem.getText().toString().length() <= 0) {
                    problem.setError("Enter problem");
                } else if (possible.getText().toString().length() <= 0) {
                    possible.setError("Enter possible");
                } else {
                    if (position == -1) {
                        MajorPrb bean = new MajorPrb(problem.getText().toString(), possible.getText().toString());
                        majorArrayList.add(bean);
                    } else {
                        majorArrayList.get(position).setProblem(problem.getText().toString());
                        majorArrayList.get(position).setPossible(possible.getText().toString());
                    }
                    majorPrbAddapter.notifyData(majorArrayList);
                    b.cancel();
                }
            }
        });
        b.show();
    }


    @Override
    public void itemAgriClick(int position) {

    }

    @Override
    public void itemAgriproduceClick(int position) {
        showAgriProDialog(position);
    }

    @Override
    public void itemEnergyPowerClick(int position) {
        showEnergyDialog(position);
    }

    @Override
    public void itemFamilyIfoClick(int position) {
        showFamilyDialog(position);
    }

    @Override
    public void itemGovschClick(int position) {

    }

    @Override
    public void itemLiveStockClick(int position) {

    }


    @Override
    public void itemMajorPrbClick(int position) {
        showMajorDialog(position);
    }

    @Override
    public void itemSourceWaterClick(int position) {

    }


    private void getCreateTest(final String mId, final String surveyer, final String data) {
        this.pDialog.setMessage("Creating...");
        showDialog();
        StringRequest local16 = new StringRequest(1, "http://coconutfpo.smartfpo.com/AMBank/UBA.php", new Response.Listener<String>() {
            public void onResponse(String paramString) {
                Log.d("tag", "Register Response: " + paramString.toString());
                hideDialog();
                try {
                    JSONObject localJSONObject1 = new JSONObject(paramString);
                    String str = localJSONObject1.getString("message");
                    if (localJSONObject1.getInt("success") == 1) {
                        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                    return;
                } catch (JSONException localJSONException) {
                    localJSONException.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError paramVolleyError) {
                Log.e("tag", "Fetch Error: " + paramVolleyError.getMessage());
                Toast.makeText(getApplicationContext(), paramVolleyError.getMessage(), Toast.LENGTH_SHORT).show();
                hideDialog();
            }
        }) {
            protected Map<String, String> getParams() {
                HashMap<String, String> localHashMap = new HashMap<String, String>();
                if (mId != null) {
                    localHashMap.put("id", mId);
                }
                localHashMap.put("formId", mId);
                localHashMap.put("surveyer", surveyer);
                localHashMap.put("data", data);
                localHashMap.put("db", "nec");

                return localHashMap;
            }
        };
        AppController.getInstance().addToRequestQueue(local16, TAG);
    }

    private void hideDialog() {

        if (this.pDialog.isShowing()) this.pDialog.dismiss();
    }

    private void showDialog() {

        if (!this.pDialog.isShowing()) this.pDialog.show();
    }


    private void requestPermission(String[] permissions, int code) {
        ActivityCompat.requestPermissions(this, permissions, code);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
        if (requestCode == FINE_LOCATION_CODE) {
            if (grantResults.length > 0) {
                boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                if (locationAccepted) {
                    Toast.makeText(getApplicationContext(), "Permission Granted, Now you can access location data.", Toast.LENGTH_LONG).show();
                    locationFetcher(geotag, village);
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
        new AlertDialog.Builder(MainActivityHouseHold.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void locationFetcher(EditText gpsLocal, EditText addressLocal) {
        // check if GPS enabled
        gps = new GPSTracker(MainActivityHouseHold.this);
        if (gps.canGetLocation()) {
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            gpsLocal.setText(latitude + "," + longitude);
            try {
                if (addressLocal != null) {
                    Geocoder geocoder;
                    List<Address> addresses;
                    geocoder = new Geocoder(MainActivityHouseHold.this, Locale.getDefault());
                    addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    String addresstxt = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city = addresses.get(0).getLocality();
                    String stateTemp = addresses.get(0).getAdminArea();
                    if (mainbean == null) {
                        state.setText(stateTemp);
                    }
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


}