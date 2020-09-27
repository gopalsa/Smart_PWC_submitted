package smart.cst.pwc.fgd;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import net.cachapa.expandablelayout.ExpandableLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import smart.cst.pwc.CustomFontEditText;
import smart.cst.pwc.GPSTracker;
import smart.cst.pwc.R;
import smart.cst.pwc.app.AppController;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivityFGD extends AppCompatActivity implements OnFGDItemClick {

    private static final int FINE_LOCATION_CODE = 199;
    public EditText geotag;

    PeopleattendAdapter peopleattendAdapter;
    private RecyclerView peopleattendList;
    private ArrayList<Peopleattend> peopleattendArrayList = new ArrayList<>();

    CropwisecultivateAdapter cropwisecultivateAdapter;
    private RecyclerView cropwisecultivateList;
    private ArrayList<Cropwisecultivate> cropwisecultivateArrayList = new ArrayList<>();

    CultivatecostAdapter cultivatecostAdapter;
    private RecyclerView cultivatecostList;
    private ArrayList<Cultivatecost> cultivatecostArrayList = new ArrayList<>();

    AvgwageAdapter avgwageAdapter;
    private RecyclerView avgwageList;
    private ArrayList<Avgwage> avgwageArrayList = new ArrayList<>();

    PriceincreaseAdapter priceincreaseAdapter;
    private RecyclerView priceincrList;
    private ArrayList<Priceincrease> priceincreaseArrayList = new ArrayList<>();

    StorageAdapter storageAdapter;
    private RecyclerView storageList;
    private ArrayList<Storage> storageArrayList = new ArrayList<>();

    AvgrateAdapter avgrateAdapter;
    private RecyclerView avgrateList;
    private ArrayList<Avgrate> avgrateArrayList = new ArrayList<>();

    private RecyclerView fgdList;

    ProgressDialog pDialog;

    EditText name;
    EditText tdate;
    EditText grpinagri;
    EditText landsize;
    EditText lastyrcultivate;
    EditText irrigationfacility;
    EditText kharif;
    EditText rabi;
    EditText summer;
    EditText irregationrent;
    EditText soiltestnum;
    EditText loan;
    EditText avgborwpseas;
    EditText avgborwpyr;
    EditText cropcprice;
    EditText delaysale;
    EditText diflocation;
    EditText sellvegetable;
    EditText marketpract;
    EditText yesreason;
    EditText colmarket;
    EditText familysize;
    EditText vlagepopulation;
    EditText numhouseinfarm;
    EditText totcultivatearea;
    EditText agrionlyincome;
    EditText otherincome;
    EditText agriproduce;
    CustomFontEditText landowner;
    CustomFontEditText irrigationsrc;
    CustomFontEditText crppattern;
    CustomFontEditText farminputs;
    CustomFontEditText buyseeds;
    CustomFontEditText avgdistance;
    CustomFontEditText srcirrigation;
    CustomFontEditText extenservice;
    CustomFontEditText getinfo;
    CustomFontEditText guidance;
    CustomFontEditText lastyrtraining;
    CustomFontEditText soiltest;
    CustomFontEditText soiltestfreq;
    CustomFontEditText weatherinfo;
    CustomFontEditText weatherinfosrc;
    CustomFontEditText pepborwnum;
    CustomFontEditText srcborw;
    CustomFontEditText loanrepaid;
    CustomFontEditText fruits;
    CustomFontEditText cereals;
    CustomFontEditText vegetables;
    CustomFontEditText sellplace;
    CustomFontEditText vegecost;
    CustomFontEditText payment;
    CustomFontEditText colpeoplepract;
    CustomFontEditText livestock;
    CustomFontEditText edulevel;
    CustomFontEditText producergrp;
    CustomFontEditText member;
    TextView submit;

    MainFGD mainFGD = null;
    private String TAG = getClass().getSimpleName();

    public static final String mypreference = "mypref";
    public static final String buSurveyerId = "buSurveyerIdKey";
    SharedPreferences sharedpreferences;

    GPSTracker gps;
    ImageView location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fgd);


        TextView expand_button = (TextView) findViewById(R.id.expand_button);
        final ImageView rotation = (ImageView) findViewById(R.id.rotation);
        final ExpandableLayout expandLayout = (ExpandableLayout) findViewById(R.id.expandable_layout_0);
        final LinearLayout expandText = (LinearLayout) findViewById(R.id.expandText);
        expand_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (expandLayout.isExpanded()) {
                    expandLayout.collapse();
                    rotation.setRotation(180);
                } else {
                    expandLayout.expand();
                    rotation.setRotation(0);
                }
            }
        });


        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        sharedpreferences = this.getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        name = (EditText) findViewById(R.id.name);
        location = (ImageView) findViewById(R.id.location);
        tdate = (EditText) findViewById(R.id.tdate);
        grpinagri = (EditText) findViewById(R.id.grpinagri);
        landsize = (EditText) findViewById(R.id.landsize);
        lastyrcultivate = (EditText) findViewById(R.id.lastyrcultivate);
        irrigationfacility = (EditText) findViewById(R.id.irrigationfacility);
        kharif = (EditText) findViewById(R.id.kharif);
        rabi = (EditText) findViewById(R.id.rabi);
        summer = (EditText) findViewById(R.id.summer);
        irregationrent = (EditText) findViewById(R.id.irregationrent);
        soiltestnum = (EditText) findViewById(R.id.soiltestnum);
        loan = (EditText) findViewById(R.id.loan);
        avgborwpseas = (EditText) findViewById(R.id.avgborwpseas);
        avgborwpyr = (EditText) findViewById(R.id.avgborwpyr);
        cropcprice = (EditText) findViewById(R.id.cropcprice);
        delaysale = (EditText) findViewById(R.id.delaysale);
        diflocation = (EditText) findViewById(R.id.diflocation);
        sellvegetable = (EditText) findViewById(R.id.sellvegetable);
        marketpract = (EditText) findViewById(R.id.marketpract);
        yesreason = (EditText) findViewById(R.id.yesreason);
        colmarket = (EditText) findViewById(R.id.colmarket);
        familysize = (EditText) findViewById(R.id.familysize);
        vlagepopulation = (EditText) findViewById(R.id.vlagepopulation);
        numhouseinfarm = (EditText) findViewById(R.id.numhouseinfarm);
        totcultivatearea = (EditText) findViewById(R.id.totcultivatearea);
        agrionlyincome = (EditText) findViewById(R.id.agrionlyincome);
        otherincome = (EditText) findViewById(R.id.otherincome);
        agriproduce = (EditText) findViewById(R.id.agriproduce);
        landowner = (CustomFontEditText) findViewById(R.id.landowner);
        irrigationsrc = (CustomFontEditText) findViewById(R.id.irrigationsrc);
        crppattern = (CustomFontEditText) findViewById(R.id.crppattern);
        farminputs = (CustomFontEditText) findViewById(R.id.farminputs);
        buyseeds = (CustomFontEditText) findViewById(R.id.buyseeds);
        avgdistance = (CustomFontEditText) findViewById(R.id.avgdistance);
        srcirrigation = (CustomFontEditText) findViewById(R.id.srcirrigation);
        extenservice = (CustomFontEditText) findViewById(R.id.extenservice);
        getinfo = (CustomFontEditText) findViewById(R.id.getinfo);
        guidance = (CustomFontEditText) findViewById(R.id.guidance);
        lastyrtraining = (CustomFontEditText) findViewById(R.id.lastyrtraining);
        soiltest = (CustomFontEditText) findViewById(R.id.soiltest);
        soiltestfreq = (CustomFontEditText) findViewById(R.id.soiltestfreq);
        weatherinfo = (CustomFontEditText) findViewById(R.id.weatherinfo);
        weatherinfosrc = (CustomFontEditText) findViewById(R.id.weatherinfosrc);
        pepborwnum = (CustomFontEditText) findViewById(R.id.pepborwnum);
        srcborw = (CustomFontEditText) findViewById(R.id.srcborw);
        loanrepaid = (CustomFontEditText) findViewById(R.id.loanrepaid);
        fruits = (CustomFontEditText) findViewById(R.id.fruits);
        cereals = (CustomFontEditText) findViewById(R.id.cereals);
        vegetables = (CustomFontEditText) findViewById(R.id.vegetables);
        sellplace = (CustomFontEditText) findViewById(R.id.sellplace);
        vegecost = (CustomFontEditText) findViewById(R.id.vegecost);
        payment = (CustomFontEditText) findViewById(R.id.payment);
        colpeoplepract = (CustomFontEditText) findViewById(R.id.colpeoplepract);
        livestock = (CustomFontEditText) findViewById(R.id.livestock);
        edulevel = (CustomFontEditText) findViewById(R.id.edulevel);
        producergrp = (CustomFontEditText) findViewById(R.id.producergrp);
        member = (CustomFontEditText) findViewById(R.id.member);
        submit = (TextView) findViewById(R.id.submit);

        peopleattendList = (RecyclerView) findViewById(R.id.peopleattendList);
        peopleattendAdapter = new PeopleattendAdapter(this, peopleattendArrayList, this);
        final LinearLayoutManager addManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        peopleattendList.setLayoutManager(addManager);
        peopleattendList.setAdapter(peopleattendAdapter);
        TextView peopleattend = (TextView) findViewById(R.id.peopleattend);
        peopleattend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPeopleattendDialog(-1);
            }
        });

        cropwisecultivateList = (RecyclerView) findViewById(R.id.cropwisecultivateList);
        cropwisecultivateAdapter = new CropwisecultivateAdapter(this, cropwisecultivateArrayList, this);
        final LinearLayoutManager crop = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        cropwisecultivateList.setLayoutManager(crop);
        cropwisecultivateList.setAdapter(cropwisecultivateAdapter);
        TextView cropwisecultivate = (TextView) findViewById(R.id.cropwisecultivate);
        cropwisecultivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCropwisecultivateDialog(-1);
            }
        });

        cultivatecostList = (RecyclerView) findViewById(R.id.cultivatecostList);
        cultivatecostAdapter = new CultivatecostAdapter(this, cultivatecostArrayList, this);
        final LinearLayoutManager cost = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        cultivatecostList.setLayoutManager(cost);
        cultivatecostList.setAdapter(cultivatecostAdapter);
        TextView cultivatecost = (TextView) findViewById(R.id.cultivatecost);
        cultivatecost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCultivatecostDialog(-1);
            }
        });

        avgwageList = (RecyclerView) findViewById(R.id.avgwageList);
        avgwageAdapter = new AvgwageAdapter(this, avgwageArrayList, this);
        final LinearLayoutManager wage = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        avgwageList.setLayoutManager(wage);
        avgwageList.setAdapter(avgwageAdapter);
        TextView avgwage = (TextView) findViewById(R.id.avgwage);
        avgwage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAvgwageDialog(-1);
            }
        });

        priceincrList = (RecyclerView) findViewById(R.id.priceincrList);
        priceincreaseAdapter = new PriceincreaseAdapter(this, priceincreaseArrayList, this);
        final LinearLayoutManager price = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        priceincrList.setLayoutManager(price);
        priceincrList.setAdapter(priceincreaseAdapter);
        TextView priceincr = (TextView) findViewById(R.id.priceincr);
        priceincr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPriceincrDialog(-1);
            }
        });

        storageList = (RecyclerView) findViewById(R.id.storageList);
        storageAdapter = new StorageAdapter(this, storageArrayList, this);
        final LinearLayoutManager store = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        storageList.setLayoutManager(store);
        storageList.setAdapter(storageAdapter);
        TextView storage = (TextView) findViewById(R.id.storage);
        storage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStorageDialog(-1);
            }
        });

        avgrateList = (RecyclerView) findViewById(R.id.avgrateList);
        avgrateAdapter = new AvgrateAdapter(this, avgrateArrayList, this);
        final LinearLayoutManager rate = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        avgrateList.setLayoutManager(rate);
        avgrateList.setAdapter(storageAdapter);
        TextView avgrate = (TextView) findViewById(R.id.avgrate);
        avgrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAvgrateDialog(-1);
            }
        });

        geotag = (EditText) findViewById(R.id.geotag);
        ImageView georefresh = (ImageView) findViewById(R.id.refresh);

        geotag.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (!checkPermission(new String[]{ACCESS_FINE_LOCATION})) {
                        requestPermission(new String[]{ACCESS_FINE_LOCATION}, FINE_LOCATION_CODE);
                    } else {
                        locationFetcher(geotag);
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
                    locationFetcher(geotag);
                }


            }
        });


        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String uri = String.format(Locale.ENGLISH, "geo:%f,%f",
                            String.valueOf(geotag.getText().toString().split(",")[0]),
                            String.valueOf(geotag.getText().toString().split(",")[1]));
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    startActivity(intent);
                } catch (Exception e) {
                    String uri = String.format(Locale.ENGLISH, "geo:%f,%f",
                            0,
                            0);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    startActivity(intent);

                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainFGD tempmainbean = new MainFGD(name.getText().toString(),
                        tdate.getText().toString(),
                        geotag.getText().toString(),
                        grpinagri.getText().toString(),
                        landsize.getText().toString(),
                        landowner.getText().toString(),
                        lastyrcultivate.getText().toString(),
                        irrigationfacility.getText().toString(),
                        irrigationsrc.getText().toString(),
                        crppattern.getText().toString(),
                        kharif.getText().toString(),
                        rabi.getText().toString(),
                        summer.getText().toString(),
                        farminputs.getText().toString(),
                        buyseeds.getText().toString(),
                        avgdistance.getText().toString(),
                        srcirrigation.getText().toString(),
                        irregationrent.getText().toString(),
                        extenservice.getText().toString(),
                        getinfo.getText().toString(),
                        guidance.getText().toString(),
                        lastyrtraining.getText().toString(),
                        soiltest.getText().toString(),
                        soiltestnum.getText().toString(),
                        soiltestfreq.getText().toString(),
                        weatherinfo.getText().toString(),
                        weatherinfosrc.getText().toString(),
                        loan.getText().toString(),
                        avgborwpseas.getText().toString(),
                        avgborwpyr.getText().toString(),
                        pepborwnum.getText().toString(),
                        srcborw.getText().toString(),
                        loanrepaid.getText().toString(),
                        cropcprice.getText().toString(),
                        delaysale.getText().toString(),
                        fruits.getText().toString(),
                        cereals.getText().toString(),
                        vegetables.getText().toString(),
                        sellvegetable.getText().toString(),
                        sellplace.getText().toString(),
                        diflocation.getText().toString(),
                        vegecost.getText().toString(),
                        payment.getText().toString(),
                        marketpract.getText().toString(),
                        colpeoplepract.getText().toString(),
                        yesreason.getText().toString(),
                        colmarket.getText().toString(),
                        familysize.getText().toString(),
                        vlagepopulation.getText().toString(),
                        numhouseinfarm.getText().toString(),
                        totcultivatearea.getText().toString(),
                        agrionlyincome.getText().toString(),
                        otherincome.getText().toString(),
                        livestock.getText().toString(),
                        edulevel.getText().toString(),
                        agriproduce.getText().toString(),
                        producergrp.getText().toString(),
                        member.getText().toString(),
                        peopleattendArrayList,
                        cropwisecultivateArrayList,
                        cultivatecostArrayList,
                        avgwageArrayList,
                        priceincreaseArrayList,
                        storageArrayList,
                        avgrateArrayList);

                String jsonVal = new Gson().toJson(tempmainbean);
                Log.e("xxxxxxxxxxxxx", jsonVal);
                if (mainFGD == null) {
                    tempmainbean.setId(String.valueOf(System.currentTimeMillis()));
                } else {
                    tempmainbean.setId(mainFGD.id);
                }
                getCreateTest(tempmainbean.id, sharedpreferences.getString(buSurveyerId, ""), jsonVal);
            }
        });

        try {

            mainFGD = (MainFGD) getIntent().getSerializableExtra("object");
            if (mainFGD != null) {
                name.setText(mainFGD.name);
                tdate.setText(mainFGD.tdate);
                geotag.setText(mainFGD.location);
                grpinagri.setText(mainFGD.grpinagri);
                landsize.setText(mainFGD.landsize);
                landowner.setText(mainFGD.landowner);
                lastyrcultivate.setText(mainFGD.lastyrcultivate);
                irrigationfacility.setText(mainFGD.irrigationfacility);
                irrigationsrc.setText(mainFGD.irrigationsrc);
                crppattern.setText(mainFGD.crppattern);
                kharif.setText(mainFGD.kharif);
                rabi.setText(mainFGD.rabi);
                summer.setText(mainFGD.summer);
                farminputs.setText(mainFGD.farminputs);
                buyseeds.setText(mainFGD.buyseeds);
                avgdistance.setText(mainFGD.avgdistance);
                srcirrigation.setText(mainFGD.srcirrigation);
                irregationrent.setText(mainFGD.irregationrent);
                extenservice.setText(mainFGD.extenservice);
                getinfo.setText(mainFGD.getinfo);
                guidance.setText(mainFGD.guidance);
                lastyrtraining.setText(mainFGD.lastyrtraining);
                soiltest.setText(mainFGD.soiltest);
                soiltestnum.setText(mainFGD.soiltestnum);
                soiltestfreq.setText(mainFGD.soiltestfreq);
                weatherinfo.setText(mainFGD.weatherinfo);
                weatherinfosrc.setText(mainFGD.weatherinfosrc);
                loan.setText(mainFGD.loan);
                avgborwpseas.setText(mainFGD.avgborwpseas);
                avgborwpyr.setText(mainFGD.avgborwpyr);
                pepborwnum.setText(mainFGD.pepborwnum);
                srcborw.setText(mainFGD.srcborw);
                loanrepaid.setText(mainFGD.loanrepaid);
                cropcprice.setText(mainFGD.cropcprice);
                delaysale.setText(mainFGD.delaysale);
                fruits.setText(mainFGD.fruits);
                cereals.setText(mainFGD.cereals);
                vegetables.setText(mainFGD.vegetables);
                sellvegetable.setText(mainFGD.sellvegetable);
                sellplace.setText(mainFGD.sellplace);
                diflocation.setText(mainFGD.diflocation);
                vegecost.setText(mainFGD.vegecost);
                payment.setText(mainFGD.payment);
                marketpract.setText(mainFGD.marketpract);
                colpeoplepract.setText(mainFGD.colpeoplepract);
                yesreason.setText(mainFGD.yesreason);
                colmarket.setText(mainFGD.colmarket);
                familysize.setText(mainFGD.familysize);
                vlagepopulation.setText(mainFGD.vlagepopulation);
                numhouseinfarm.setText(mainFGD.numhouseinfarm);
                totcultivatearea.setText(mainFGD.totcultivatearea);
                agrionlyincome.setText(mainFGD.agrionlyincome);
                otherincome.setText(mainFGD.otherincome);
                livestock.setText(mainFGD.livestock);
                edulevel.setText(mainFGD.edulevel);
                agriproduce.setText(mainFGD.agriproduce);
                producergrp.setText(mainFGD.producergrp);
                member.setText(mainFGD.member);

                peopleattendArrayList = mainFGD.peopleattends;
                peopleattendAdapter.notifyData(peopleattendArrayList);

                cropwisecultivateArrayList = mainFGD.cropwisecultivates;
                cropwisecultivateAdapter.notifyData(cropwisecultivateArrayList);

                cultivatecostArrayList = mainFGD.cultivatecosts;
                cultivatecostAdapter.notifyData(cultivatecostArrayList);

                avgwageArrayList = mainFGD.avgwages;
                avgwageAdapter.notifyData(avgwageArrayList);

                priceincreaseArrayList = mainFGD.priceincreases;
                priceincreaseAdapter.notifyData(priceincreaseArrayList);

                storageArrayList = mainFGD.storages;
                storageAdapter.notifyData(storageArrayList);

                avgrateArrayList = mainFGD.avgrates;
                avgrateAdapter.notifyData(avgrateArrayList);

                georefresh.setVisibility(View.INVISIBLE);

            }
        } catch (Exception e) {
            Log.e("xxxxxx", "Something went wrong");
        }

    }

    public void showPeopleattendDialog(final int position) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivityFGD.this);
        LayoutInflater inflater = MainActivityFGD.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.peopleattend, null);

        final TextView submit = (TextView) dialogView.findViewById(R.id.submit);
        final EditText men = (EditText) dialogView.findViewById(R.id.men);
        final EditText women = (EditText) dialogView.findViewById(R.id.women);
        final EditText total = (EditText) dialogView.findViewById(R.id.total);
        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();

        men.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString() != null && women.getText().toString() != null && women.getText().toString().length() > 0
                        && s.toString().length() > 0) {
                    int mencount = Integer.parseInt(s.toString());
                    int womencount = Integer.parseInt(women.getText().toString());
                    total.setText(String.valueOf(mencount + womencount));
                }
            }
        });

        women.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString() != null && men.getText().toString() != null && men.getText().toString().length() > 0
                        && s.toString().length() > 0) {
                    int womencount = Integer.parseInt(s.toString());
                    int mencount = Integer.parseInt(men.getText().toString());
                    total.setText(String.valueOf(mencount + womencount));
                }
            }
        });
        if (position != -1) {
            submit.setText("UPtdate");
            Peopleattend bean = peopleattendArrayList.get(position);
            men.setText(bean.men);
            women.setText(bean.women);
            total.setText(bean.total);

        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == -1) {
                    Peopleattend bean = new Peopleattend(men.getText().toString(), women.getText().toString(), total.getText().toString());
                    peopleattendArrayList.add(bean);
                } else {
                    peopleattendArrayList.get(position).setMen(men.getText().toString());
                    peopleattendArrayList.get(position).setWomen(women.getText().toString());

                }
                peopleattendAdapter.notifyData(peopleattendArrayList);
                b.cancel();
            }
        });
        b.show();
    }

    public void showCropwisecultivateDialog(final int position) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivityFGD.this);
        LayoutInflater inflater = MainActivityFGD.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.cropwisecultivate, null);

        final TextView submit = (TextView) dialogView.findViewById(R.id.submit);
        final EditText corp = (EditText) dialogView.findViewById(R.id.corp);
        final EditText kharifland = (EditText) dialogView.findViewById(R.id.kharifland);
        final EditText rabiland = (EditText) dialogView.findViewById(R.id.rabiland);
        final EditText summerland = (EditText) dialogView.findViewById(R.id.summerland);
        final EditText kharifprod = (EditText) dialogView.findViewById(R.id.kharifprod);
        final EditText rabiprod = (EditText) dialogView.findViewById(R.id.rabiprod);
        final EditText summerprod = (EditText) dialogView.findViewById(R.id.summerprod);
        final EditText sellprice = (EditText) dialogView.findViewById(R.id.sellprice);
        final EditText totrevenue = (EditText) dialogView.findViewById(R.id.totrevenue);
        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();

        kharifprod.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (s.toString() != null && rabiprod.getText().toString() != null && summerprod.getText().toString() != null && sellprice.getText().toString() != null &&
                            s.toString().length() > 0 && rabiprod.toString().length() > 0 && summerprod.toString().length() > 0 && sellprice.toString().length() > 0) {
                        int khariftprod = Integer.parseInt(s.toString());
                        int rabitprod = Integer.parseInt(rabiprod.getText().toString());
                        int summertprod = Integer.parseInt(summerprod.getText().toString());
                        int selltprice = Integer.parseInt(sellprice.getText().toString());
                        totrevenue.setText(String.valueOf((khariftprod + rabitprod + summertprod) * selltprice));
                    }
                } catch (Exception e) {
                    Log.e("xxxxxx", "Something went wrong");
                }
            }
        });

        rabiprod.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (s.toString() != null && kharifprod.getText().toString() != null && summerprod.getText().toString() != null && sellprice.getText().toString() != null &&
                            s.toString().length() > 0 && kharifprod.toString().length() > 0 && summerprod.toString().length() > 0 && sellprice.toString().length() > 0) {
                        int rabitprod = Integer.parseInt(s.toString());
                        int khariftprod = Integer.parseInt(kharifprod.getText().toString());
                        int summertprod = Integer.parseInt(summerprod.getText().toString());
                        int selltprice = Integer.parseInt(sellprice.getText().toString());
                        totrevenue.setText(String.valueOf((khariftprod + rabitprod + summertprod) * selltprice));
                    }
                } catch (Exception e) {
                    Log.e("xxxxxx", "Something went wrong");
                }
            }
        });

        summerprod.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (s.toString() != null && kharifprod.getText().toString() != null && rabiprod.getText().toString() != null && sellprice.getText().toString() != null &&
                            s.toString().length() > 0 && rabiprod.toString().length() > 0 && kharifprod.toString().length() > 0 && sellprice.toString().length() > 0) {
                        int summertprod = Integer.parseInt(s.toString());
                        int khariftprod = Integer.parseInt(kharifprod.getText().toString());
                        int rabitprod = Integer.parseInt(rabiprod.getText().toString());
                        int selltprice = Integer.parseInt(sellprice.getText().toString());
                        totrevenue.setText(String.valueOf((khariftprod + rabitprod + summertprod) * selltprice));
                    }
                } catch (Exception e) {
                    Log.e("xxxxxx", "Something went wrong");
                }
            }
        });

        sellprice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (s.toString() != null && kharifprod.getText().toString() != null && summerprod.getText().toString() != null && rabiprod.getText().toString() != null &&
                            s.toString().length() > 0 && rabiprod.toString().length() > 0 && summerprod.toString().length() > 0 && rabiprod.toString().length() > 0) {
                        int selltprice = Integer.parseInt(s.toString());
                        int khariftprod = Integer.parseInt(kharifprod.getText().toString());
                        int summertprod = Integer.parseInt(summerprod.getText().toString());
                        int rabitprod = Integer.parseInt(rabiprod.getText().toString());
                        totrevenue.setText(String.valueOf((khariftprod + rabitprod + summertprod) * selltprice));
                    }

                } catch (Exception e) {
                    Log.e("xxxxxx", "Something went wrong");
                }
            }
        });


        if (position != -1) {
            submit.setText("UPtdate");
            Cropwisecultivate bean = cropwisecultivateArrayList.get(position);
            corp.setText(bean.corp);
            kharifland.setText(bean.kharifland);
            rabiland.setText(bean.rabiland);
            summerland.setText(bean.summerland);
            kharifprod.setText(bean.kharifprod);
            rabiprod.setText(bean.rabiprod);
            summerprod.setText(bean.summerprod);
            sellprice.setText(bean.sellprice);
            totrevenue.setText(bean.totrevenue);


        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == -1) {
                    Cropwisecultivate bean = new Cropwisecultivate(corp.getText().toString(), kharifland.getText().toString(), rabiland.getText().toString(), summerland.getText().toString(), kharifprod.getText().toString(), rabiprod.getText().toString(), summerprod.getText().toString(), sellprice.getText().toString(), totrevenue.getText().toString());
                    cropwisecultivateArrayList.add(bean);
                } else {
                    cropwisecultivateArrayList.get(position).setCorp(corp.getText().toString());
                    cropwisecultivateArrayList.get(position).setKharifland(kharifland.getText().toString());
                    cropwisecultivateArrayList.get(position).setRabiland(rabiland.getText().toString());
                    cropwisecultivateArrayList.get(position).setSummerland(summerland.getText().toString());
                    cropwisecultivateArrayList.get(position).setKharifprod(kharifprod.getText().toString());
                    cropwisecultivateArrayList.get(position).setRabiprod(rabiprod.getText().toString());
                    cropwisecultivateArrayList.get(position).setSummerprod(summerprod.getText().toString());
                    cropwisecultivateArrayList.get(position).setSellprice(sellprice.getText().toString());
                    cropwisecultivateArrayList.get(position).setTotrevenue(totrevenue.getText().toString());

                }
                cropwisecultivateAdapter.notifyData(cropwisecultivateArrayList);
                b.cancel();
            }
        });
        b.show();
    }

    public void showCultivatecostDialog(final int position) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivityFGD.this);
        LayoutInflater inflater = MainActivityFGD.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.cultivatecost, null);

        final TextView submit = (TextView) dialogView.findViewById(R.id.submit);
        final EditText factors = (EditText) dialogView.findViewById(R.id.factors);
        final EditText cropone = (EditText) dialogView.findViewById(R.id.cropone);
        final EditText croptwo = (EditText) dialogView.findViewById(R.id.croptwo);
        final EditText cropthree = (EditText) dialogView.findViewById(R.id.cropthree);
        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();

        if (position != -1) {
            submit.setText("UPtdate");
            Cultivatecost bean = cultivatecostArrayList.get(position);
            factors.setText(bean.factors);
            cropone.setText(bean.cropone);
            croptwo.setText(bean.croptwo);
            cropthree.setText(bean.cropthree);

        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == -1) {
                    Cultivatecost bean = new Cultivatecost(factors.getText().toString(), cropone.getText().toString(), croptwo.getText().toString(), cropthree.getText().toString());
                    cultivatecostArrayList.add(bean);
                } else {
                    cultivatecostArrayList.get(position).setFactors(factors.getText().toString());
                    cultivatecostArrayList.get(position).setCropone(cropone.getText().toString());
                    cultivatecostArrayList.get(position).setFactors(factors.getText().toString());
                    cultivatecostArrayList.get(position).setCropone(cropone.getText().toString());

                }
                cultivatecostAdapter.notifyData(cultivatecostArrayList);
                b.cancel();
            }
        });
        b.show();
    }

    public void showAvgwageDialog(final int position) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivityFGD.this);
        LayoutInflater inflater = MainActivityFGD.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.avgwage, null);

        final TextView submit = (TextView) dialogView.findViewById(R.id.submit);
        final EditText activity = (EditText) dialogView.findViewById(R.id.activity);
        final EditText male = (EditText) dialogView.findViewById(R.id.male);
        final EditText female = (EditText) dialogView.findViewById(R.id.female);

        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();

        if (position != -1) {
            submit.setText("UPtdate");
            Avgwage bean = avgwageArrayList.get(position);
            activity.setText(bean.activity);
            male.setText(bean.male);
            female.setText(bean.female);


        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == -1) {
                    Avgwage bean = new Avgwage(activity.getText().toString(), male.getText().toString(), female.getText().toString());
                    avgwageArrayList.add(bean);
                } else {
                    avgwageArrayList.get(position).setActivity(activity.getText().toString());
                    avgwageArrayList.get(position).setMale(male.getText().toString());
                    avgwageArrayList.get(position).setFemale(female.getText().toString());

                }
                avgwageAdapter.notifyData(avgwageArrayList);
                b.cancel();
            }
        });
        b.show();
    }

    public void showPriceincrDialog(final int position) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivityFGD.this);
        LayoutInflater inflater = MainActivityFGD.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.priceincrease, null);

        final TextView submit = (TextView) dialogView.findViewById(R.id.submit);
        final EditText produce = (EditText) dialogView.findViewById(R.id.produce);
        final EditText yesnum = (EditText) dialogView.findViewById(R.id.yesnum);
        final EditText nonum = (EditText) dialogView.findViewById(R.id.nonum);

        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();

        if (position != -1) {
            submit.setText("UPtdate");
            Priceincrease bean = priceincreaseArrayList.get(position);
            produce.setText(bean.produce);
            yesnum.setText(bean.yesnum);
            nonum.setText(bean.nonum);


        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == -1) {
                    Priceincrease bean = new Priceincrease(produce.getText().toString(), yesnum.getText().toString(), nonum.getText().toString());
                    priceincreaseArrayList.add(bean);
                } else {
                    priceincreaseArrayList.get(position).setProduce(produce.getText().toString());
                    priceincreaseArrayList.get(position).setYesnum(yesnum.getText().toString());
                    priceincreaseArrayList.get(position).setNonum(nonum.getText().toString());

                }
                priceincreaseAdapter.notifyData(priceincreaseArrayList);
                b.cancel();
            }
        });
        b.show();
    }

    public void showStorageDialog(final int position) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivityFGD.this);
        LayoutInflater inflater = MainActivityFGD.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.storage, null);

        final TextView submit = (TextView) dialogView.findViewById(R.id.submit);
        final EditText facility = (EditText) dialogView.findViewById(R.id.facility);
        final EditText yes = (EditText) dialogView.findViewById(R.id.yes);
        final EditText no = (EditText) dialogView.findViewById(R.id.no);
        final EditText farmdist = (EditText) dialogView.findViewById(R.id.farmdist);
        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();

        if (position != -1) {
            submit.setText("UPtdate");
            Storage bean = storageArrayList.get(position);
            facility.setText(bean.facility);
            yes.setText(bean.yes);
            no.setText(bean.no);
            farmdist.setText(bean.farmdist);

        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == -1) {
                    Storage bean = new Storage(facility.getText().toString(), yes.getText().toString(), no.getText().toString(), farmdist.getText().toString());
                    storageArrayList.add(bean);
                } else {
                    storageArrayList.get(position).setFacility(facility.getText().toString());
                    storageArrayList.get(position).setYes(yes.getText().toString());
                    storageArrayList.get(position).setNo(no.getText().toString());
                    storageArrayList.get(position).setFarmdist(farmdist.getText().toString());

                }
                storageAdapter.notifyData(storageArrayList);
                b.cancel();
            }
        });
        b.show();
    }

    public void showAvgrateDialog(final int position) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivityFGD.this);
        LayoutInflater inflater = MainActivityFGD.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.avgrate, null);

        final TextView submit = (TextView) dialogView.findViewById(R.id.submit);
        final EditText cropname = (EditText) dialogView.findViewById(R.id.cropname);
        final EditText min = (EditText) dialogView.findViewById(R.id.min);
        final EditText max = (EditText) dialogView.findViewById(R.id.max);

        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();

        if (position != -1) {
            submit.setText("UPtdate");
            Avgrate bean = avgrateArrayList.get(position);
            cropname.setText(bean.cropname);
            min.setText(bean.min);
            max.setText(bean.max);


        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == -1) {
                    Avgrate bean = new Avgrate(cropname.getText().toString(), min.getText().toString(), max.getText().toString());
                    avgrateArrayList.add(bean);
                } else {
                    avgrateArrayList.get(position).setCropname(cropname.getText().toString());
                    avgrateArrayList.get(position).setMin(min.getText().toString());
                    avgrateArrayList.get(position).setMax(max.getText().toString());

                }
                avgrateAdapter.notifyData(avgrateArrayList);
                b.cancel();
            }
        });
        b.show();
    }

    @Override
    public void itemPeopleattendClick(int position) {
        showPeopleattendDialog(position);
    }

    @Override
    public void itemCropwisecultivateClick(int position) {
        showCropwisecultivateDialog(position);
    }

    @Override
    public void itemCultivatecostClick(int position) {
        showCultivatecostDialog(position);
    }

    @Override
    public void itemAvgwageClick(int position) {
        showAvgwageDialog(position);
    }

    @Override
    public void itemPriceincreaseClick(int position) {
        showPriceincrDialog(position);
    }

    @Override
    public void itemStorageClick(int position) {
        showStorageDialog(position);
    }

    @Override
    public void itemAvgrateClick(int position) {
        showAvgrateDialog(position);
    }

    private void getCreateTest(final String mId, final String surveyer, final String data) {
        this.pDialog.setMessage("Creating...");
        showDialog();
        StringRequest local16 = new StringRequest(1, "http://climatesmartcity.com/UBA/pwcfgd.php", new Response.Listener<String>() {
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
                    locationFetcher(geotag);
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied, You cannot access location data", Toast.LENGTH_LONG).show();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                            showMessageOKCancel("Please Enable this permission to identify" +
                                            "  your Survey on the google map by other Surveyors",
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
        new AlertDialog.Builder(MainActivityFGD.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void locationFetcher(EditText gpsLocal) {
        // check if GPS enabled
        gps = new GPSTracker(MainActivityFGD.this);
        if (gps.canGetLocation()) {
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            gpsLocal.setText(latitude + "," + longitude);
            try {
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(MainActivityFGD.this, Locale.getDefault());
                addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                String addresstxt = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String stateTemp = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                //   addressLocal.setText(addresstxt + "," + addresses.get(0).getPostalCode());

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
