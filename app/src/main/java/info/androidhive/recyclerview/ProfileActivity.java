package info.androidhive.recyclerview;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import info.androidhive.recyclerview.app.AppConfig;
import info.androidhive.recyclerview.app.AppController;
import info.androidhive.recyclerview.db.DbProfile;
import info.androidhive.recyclerview.db.DbVrp;
import katomaran.evao.lib.qrmodule.encoding.QrGenerator;

/**
 * Created by vidhushi.g on 8/10/17.
 */

public class ProfileActivity extends AppCompatActivity implements OnMapReadyCallback {
    DbVrp dbVrp;
    DbProfile dbProfile;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String vrpid = "vrpidKey";
    public static final String update = "updateKey";
    String vrpId = "";
    public static final String tittle = "tittleKey";

    private Vrp vrp;
    private ProgressDialog pDialog;
    private GoogleMap mMap;
    String imagePath = "";
    //bank
    private MultiSnapRecyclerView mRecyclerViewbank;
    private ProfileAdapter mRecyclerAdapterbank;
    private ArrayList<Plot> bankList = new ArrayList<>();


    //crop

    private MultiSnapRecyclerView mRecyclerViewcrop;
    private ProfileAdapter mRecyclerAdaptercrop;
    private ArrayList<Plot> cropList = new ArrayList<>();

    //animal
    private MultiSnapRecyclerView mRecyclerViewanimal;
    private ProfileAdapter mRecyclerAdapteranimal;
    private ArrayList<Plot> animalList = new ArrayList<>();

    //farm machine
    private MultiSnapRecyclerView mRecyclerViewfarmmachine;
    private ProfileAdapter mRecyclerAdapterfarmmachine;
    private ArrayList<Plot> farmmachineList = new ArrayList<>();

    //labour
    private MultiSnapRecyclerView mRecyclerViewlabour;
    private ProfileAdapter mRecyclerAdapterlabour;
    private ArrayList<Plot> labourList = new ArrayList<>();

    //soilresources
    private MultiSnapRecyclerView mRecyclerViewsoilresources;
    private ProfileAdapter mRecyclerAdaptersoilresources;
    private ArrayList<Plot> soilresourcesList = new ArrayList<>();

    //waterresources
    private MultiSnapRecyclerView mRecyclerViewwaterresources;
    private ProfileAdapter mRecyclerAdapterwaterresources;
    private ArrayList<Plot> waterresourcesList = new ArrayList<>();

    //toilets
    private MultiSnapRecyclerView mRecyclerViewtoilets;
    private ProfileAdapter mRecyclerAdaptertoilets;
    private ArrayList<Plot> toiletsList = new ArrayList<>();

    //electricity
    private MultiSnapRecyclerView mRecyclerViewelectricity;
    private ProfileAdapter mRecyclerAdapterelectricity;
    private ArrayList<Plot> electricityList = new ArrayList<>();

    //financialinstitutions
    private MultiSnapRecyclerView mRecyclerViewfinancialinstitutions;
    private ProfileAdapter mRecyclerAdapterfinancialinstitutions;
    private ArrayList<Plot> financialinstitutionsList = new ArrayList<>();

    //schools
    private MultiSnapRecyclerView mRecyclerViewschools;
    private ProfileAdapter mRecyclerAdapterschools;
    private ArrayList<Plot> schoolsList = new ArrayList<>();

    //hospitals
    private MultiSnapRecyclerView mRecyclerViewhospitals;
    private ProfileAdapter mRecyclerAdapterhospitals;
    private ArrayList<Plot> hospitalsList = new ArrayList<>();

    //shops
    private MultiSnapRecyclerView mRecyclerViewshops;
    private ProfileAdapter mRecyclerAdaptershops;
    private ArrayList<Plot> shopsList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        dbVrp = new DbVrp(this);
        dbProfile = new DbProfile(this);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(vrpid)) {
            vrpId = sharedpreferences.getString(vrpid, "").trim();
        }

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        vrp = new Gson().fromJson(ConvertUtils.sample(dbVrp.getDataByvrpid(vrpId).get(1)), Vrp.class);
        getSupportActionBar().setTitle("Village Profile");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        CustomFontTextView households = (CustomFontTextView) findViewById(R.id.households);
        CustomFontTextView area = (CustomFontTextView) findViewById(R.id.area);
        CustomFontTextView totalpopulation = (CustomFontTextView) findViewById(R.id.totalpopulation);
        CustomFontTextView forestarea = (CustomFontTextView) findViewById(R.id.forestarea);
        CustomFontTextView cultivatedarea = (CustomFontTextView) findViewById(R.id.cultivatedarea);
        CustomFontTextView otherarea = (CustomFontTextView) findViewById(R.id.otherarea);
        CustomFontTextView populationmale = (CustomFontTextView) findViewById(R.id.populationmale);
        CustomFontTextView populationfemale = (CustomFontTextView) findViewById(R.id.populationfemale);
        CustomFontTextView populationothers = (CustomFontTextView) findViewById(R.id.populationothers);
        CustomFontTextView childrenmale = (CustomFontTextView) findViewById(R.id.childrenmale);
        CustomFontTextView childrenfemale = (CustomFontTextView) findViewById(R.id.childrenfemale);
        CustomFontTextView childrenothers = (CustomFontTextView) findViewById(R.id.childrenothers);
        CustomFontTextView noofshg = (CustomFontTextView) findViewById(R.id.noofshg);
        CustomFontTextView vomembers = (CustomFontTextView) findViewById(R.id.vomembers);
        CustomFontTextView fpomembers = (CustomFontTextView) findViewById(R.id.fpomembers);
        CustomFontTextView stmembers = (CustomFontTextView) findViewById(R.id.stmembers);
        CustomFontTextView scmembers = (CustomFontTextView) findViewById(R.id.scmembers);
        CustomFontTextView obcmembers = (CustomFontTextView) findViewById(R.id.obcmembers);
        households.setText(vrp.getVillagehouseholds());
        area.setText(vrp.getVillagearea());
        populationmale.setText(vrp.getVillagepopulationmale());
        populationfemale.setText(vrp.getVillagepopulationfemale());
        populationothers.setText(vrp.getVillagepopulationothers());
        childrenmale.setText(vrp.getVillagepopulationchildmale());
        childrenfemale.setText(vrp.getVillagepopulationchildfemale());
        childrenothers.setText(vrp.getVillagepopulationchildothers());
        noofshg.setText(vrp.getNoofshg());
        forestarea.setText(vrp.getForestarea());
        cultivatedarea.setText(vrp.getCultivatedarea());
        otherarea.setText(vrp.getOtherarea());
        vomembers.setText(vrp.getTotalvomembers());
        fpomembers.setText(vrp.getTotalfpomembers());
        stmembers.setText(vrp.getTotalstmembers());
        scmembers.setText(vrp.getTotalscmembers());
        obcmembers.setText(vrp.getTotalobcmembers());
        totalpopulation.setText(vrp.getTotalpopulation());
        FloatingActionButton editLayout = (FloatingActionButton) findViewById(R.id.editlinear);
        editLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(update, "true");
                editor.commit();
                Intent account = new Intent(ProfileActivity.this, VRPRegistration.class);
                startActivity(account);
                finish();
            }
        });
        ImageView allout = (ImageView) findViewById(R.id.allout);
        allout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedpreferences = getSharedPreferences(mypreference,
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(tittle, "villgeprofile");
                editor.commit();
                Intent io = new Intent(ProfileActivity.this, CustomMarkerClusteringDemoActivity.class);
                io.putExtra("tittle", "villageprofile");
                startActivity(io);
            }
        });
        bankDetail();
        cropDetail();
        animalDetail();
        farmmachineDetail();
        labourDetail();
        soilresourcesDetail();
        waterresourcesDetail();
        toiletsDetail();
        ;
        electricityDetail();
        ;
        financialinstitutionsDetail();
        ;
        schoolsDetail();
        ;
        hospitalsDetail();
        ;
        shopsDetail();
        prepareData(true);
    }

    private void bankDetail() {
        final ImageView addbank = (ImageView) findViewById(R.id.addbank);
        final CustomFontTextView bankdetail = (CustomFontTextView) findViewById(R.id.bankdetail);
        addbank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBank(-1, "", "Add Bank");
            }
        });
        bankdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBank(-1, "", "Add Bank");
            }
        });
        mRecyclerViewbank = (MultiSnapRecyclerView) findViewById(R.id.banklist);
        mRecyclerAdapterbank = new ProfileAdapter(ProfileActivity.this, bankList);
        final LinearLayoutManager thirdManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewbank.setLayoutManager(thirdManager);
        mRecyclerViewbank.setAdapter(mRecyclerAdapterbank);
        mRecyclerViewbank.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerViewbank, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                final CharSequence[] items = {
                        "Edit", "Delete", "Qr code Pay"
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setTitle("Make your selection");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            addBank(position, bankList.get(position).getPlotname(), "Update Bank");
                        } else if (item == 1) {
                        } else if (item == 2) {
                            qrcode(position, bankList.get(position).getPlotname(), "Payment Qr Code");
                        }
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void cropDetail() {
        final ImageView addcrop = (ImageView) findViewById(R.id.addcrop);
        final CustomFontTextView cropdetail = (CustomFontTextView) findViewById(R.id.cropdetail);
        addcrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCrop(-1, "", "Add crop");
            }
        });
        cropdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCrop(-1, "", "Add crop");
            }
        });
        mRecyclerViewcrop = (MultiSnapRecyclerView) findViewById(R.id.croplist);
        mRecyclerAdaptercrop = new ProfileAdapter(ProfileActivity.this, cropList);
        final LinearLayoutManager thirdManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewcrop.setLayoutManager(thirdManager);
        mRecyclerViewcrop.setAdapter(mRecyclerAdaptercrop);
        mRecyclerViewcrop.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerViewcrop, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                addCrop(position, cropList.get(position).getPlotarea(), "Update crop");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void animalDetail() {
        final ImageView addanimal = (ImageView) findViewById(R.id.addanimal);
        final CustomFontTextView animaldetail = (CustomFontTextView) findViewById(R.id.animaldetail);
        addanimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAnimal(-1, "", "Add animal");
            }
        });
        animaldetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAnimal(-1, "", "Add animal");
            }
        });
        mRecyclerViewanimal = (MultiSnapRecyclerView) findViewById(R.id.animallist);
        mRecyclerAdapteranimal = new ProfileAdapter(ProfileActivity.this, animalList);
        final LinearLayoutManager thirdManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewanimal.setLayoutManager(thirdManager);
        mRecyclerViewanimal.setAdapter(mRecyclerAdapteranimal);
        mRecyclerViewanimal.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerViewanimal, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                addAnimal(position, animalList.get(position).getPlotname(), "Update animal");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void farmmachineDetail() {
        final ImageView addfarmmachine = (ImageView) findViewById(R.id.addfarmmachine);
        final CustomFontTextView farmmachinedetail = (CustomFontTextView) findViewById(R.id.farmmachinedetail);
        addfarmmachine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFarmmachine(-1, "", "Add farm machine");
            }
        });
        farmmachinedetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFarmmachine(-1, "", "Add farm machine");
            }
        });
        mRecyclerViewfarmmachine = (MultiSnapRecyclerView) findViewById(R.id.farmmachinelist);
        mRecyclerAdapterfarmmachine = new ProfileAdapter(ProfileActivity.this, farmmachineList);
        final LinearLayoutManager thirdManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewfarmmachine.setLayoutManager(thirdManager);
        mRecyclerViewfarmmachine.setAdapter(mRecyclerAdapterfarmmachine);
        mRecyclerViewfarmmachine.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerViewfarmmachine, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                addFarmmachine(position, farmmachineList.get(position).getPlotname(), "Update farm machine");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void labourDetail() {
        final ImageView addlabour = (ImageView) findViewById(R.id.addlabour);
        final CustomFontTextView labourdetail = (CustomFontTextView) findViewById(R.id.labourdetail);
        addlabour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addlabour(-1, "", "Add labour");
            }
        });
        labourdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addlabour(-1, "", "Add labour");
            }
        });
        mRecyclerViewlabour = (MultiSnapRecyclerView) findViewById(R.id.labourlist);
        mRecyclerAdapterlabour = new ProfileAdapter(ProfileActivity.this, labourList);
        final LinearLayoutManager thirdManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewlabour.setLayoutManager(thirdManager);
        mRecyclerViewlabour.setAdapter(mRecyclerAdapterlabour);
        mRecyclerViewlabour.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerViewlabour, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                addlabour(position, labourList.get(position).getPlotname(), "Update labour");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void soilresourcesDetail() {
        final ImageView addsoilresources = (ImageView) findViewById(R.id.addsoilresources);
        final CustomFontTextView soilresourcesdetail = (CustomFontTextView) findViewById(R.id.soilresourcesdetail);
        addsoilresources.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addsoilresources(-1, "", "Add soil resources");
            }
        });
        soilresourcesdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addsoilresources(-1, "", "Add soil resources");
            }
        });
        mRecyclerViewsoilresources = (MultiSnapRecyclerView) findViewById(R.id.soilresourceslist);
        mRecyclerAdaptersoilresources = new ProfileAdapter(ProfileActivity.this, soilresourcesList);
        final LinearLayoutManager thirdManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewsoilresources.setLayoutManager(thirdManager);
        mRecyclerViewsoilresources.setAdapter(mRecyclerAdaptersoilresources);
        mRecyclerViewsoilresources.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerViewsoilresources, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                addsoilresources(position, soilresourcesList.get(position).getPlotname(), "Update soil resources");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void waterresourcesDetail() {
        final ImageView addwaterresources = (ImageView) findViewById(R.id.addwaterresources);
        final CustomFontTextView waterresourcesdetail = (CustomFontTextView) findViewById(R.id.waterresourcesdetail);
        addwaterresources.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addwaterresources(-1, "", "Add water resources");
            }
        });
        waterresourcesdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addwaterresources(-1, "", "Add water resources");
            }
        });
        mRecyclerViewwaterresources = (MultiSnapRecyclerView) findViewById(R.id.waterresourceslist);
        mRecyclerAdapterwaterresources = new ProfileAdapter(ProfileActivity.this, waterresourcesList);
        final LinearLayoutManager thirdManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewwaterresources.setLayoutManager(thirdManager);
        mRecyclerViewwaterresources.setAdapter(mRecyclerAdapterwaterresources);
        mRecyclerViewwaterresources.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerViewwaterresources, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                addwaterresources(position, waterresourcesList.get(position).getPlotname(), "Update water resources");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void toiletsDetail() {
        final ImageView addtoilets = (ImageView) findViewById(R.id.addtoilets);
        final CustomFontTextView toiletsdetail = (CustomFontTextView) findViewById(R.id.toiletsdetail);
        addtoilets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addtoilets(-1, "", "Add toilets");
            }
        });
        toiletsdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addtoilets(-1, "", "Add toilets");
            }
        });
        mRecyclerViewtoilets = (MultiSnapRecyclerView) findViewById(R.id.toiletslist);
        mRecyclerAdaptertoilets = new ProfileAdapter(ProfileActivity.this, toiletsList);
        final LinearLayoutManager thirdManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewtoilets.setLayoutManager(thirdManager);
        mRecyclerViewtoilets.setAdapter(mRecyclerAdaptertoilets);
        mRecyclerViewtoilets.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerViewtoilets, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                addtoilets(position, toiletsList.get(position).getPlotname(), "Update toilets");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void electricityDetail() {
        final ImageView addelectricity = (ImageView) findViewById(R.id.addelectricity);
        final CustomFontTextView electricitydetail = (CustomFontTextView) findViewById(R.id.electricitydetail);
        addelectricity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addelectricity(-1, "", "Add electricity");
            }
        });
        electricitydetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addelectricity(-1, "", "Add electricity");
            }
        });
        mRecyclerViewelectricity = (MultiSnapRecyclerView) findViewById(R.id.electricitylist);
        mRecyclerAdapterelectricity = new ProfileAdapter(ProfileActivity.this, electricityList);
        final LinearLayoutManager thirdManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewelectricity.setLayoutManager(thirdManager);
        mRecyclerViewelectricity.setAdapter(mRecyclerAdapterelectricity);
        mRecyclerViewelectricity.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerViewelectricity, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                addelectricity(position, electricityList.get(position).getPlotname(), "Update electricity");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void financialinstitutionsDetail() {
        final ImageView addfinancialinstitutions = (ImageView) findViewById(R.id.addfinancialinstitutions);
        final CustomFontTextView financialinstitutionsdetail = (CustomFontTextView) findViewById(R.id.financialinstitutionsdetail);
        addfinancialinstitutions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addfinancialinstitutions(-1, "", "Add financial institutions");
            }
        });
        financialinstitutionsdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addfinancialinstitutions(-1, "", "Add financial institutions");
            }
        });
        mRecyclerViewfinancialinstitutions = (MultiSnapRecyclerView) findViewById(R.id.financialinstitutionslist);
        mRecyclerAdapterfinancialinstitutions = new ProfileAdapter(ProfileActivity.this, financialinstitutionsList);
        final LinearLayoutManager thirdManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewfinancialinstitutions.setLayoutManager(thirdManager);
        mRecyclerViewfinancialinstitutions.setAdapter(mRecyclerAdapterfinancialinstitutions);
        mRecyclerViewfinancialinstitutions.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerViewfinancialinstitutions, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                addfinancialinstitutions(position, financialinstitutionsList.get(position).getPlotname(), "Update Financial institutions");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void schoolsDetail() {
        final ImageView addschools = (ImageView) findViewById(R.id.addschools);
        final CustomFontTextView schoolsdetail = (CustomFontTextView) findViewById(R.id.schoolsdetail);
        addschools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addschools(-1, "", "Add schools");
            }
        });
        schoolsdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addschools(-1, "", "Add schools");
            }
        });
        mRecyclerViewschools = (MultiSnapRecyclerView) findViewById(R.id.schoolslist);
        mRecyclerAdapterschools = new ProfileAdapter(ProfileActivity.this, schoolsList);
        final LinearLayoutManager thirdManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewschools.setLayoutManager(thirdManager);
        mRecyclerViewschools.setAdapter(mRecyclerAdapterschools);
        mRecyclerViewschools.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerViewschools, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                addschools(position, schoolsList.get(position).getPlotname(), "Update schools");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void hospitalsDetail() {
        final ImageView addhospitals = (ImageView) findViewById(R.id.addhospitals);
        final CustomFontTextView hospitalsdetail = (CustomFontTextView) findViewById(R.id.hospitalsdetail);
        addhospitals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addhospitals(-1, "", "Add hospitals");
            }
        });
        hospitalsdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addhospitals(-1, "", "Add hospitals");
            }
        });
        mRecyclerViewhospitals = (MultiSnapRecyclerView) findViewById(R.id.hospitalslist);
        mRecyclerAdapterhospitals = new ProfileAdapter(ProfileActivity.this, hospitalsList);
        final LinearLayoutManager thirdManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewhospitals.setLayoutManager(thirdManager);
        mRecyclerViewhospitals.setAdapter(mRecyclerAdapterhospitals);
        mRecyclerViewhospitals.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerViewhospitals, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                addhospitals(position, hospitalsList.get(position).getPlotname(), "Update hospitals");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void shopsDetail() {
        final ImageView addshops = (ImageView) findViewById(R.id.addshops);
        final CustomFontTextView shopsdetail = (CustomFontTextView) findViewById(R.id.shopsdetail);
        addshops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addshops(-1, "", "Add shops");
            }
        });
        shopsdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addshops(-1, "", "Add shops");
            }
        });
        mRecyclerViewshops = (MultiSnapRecyclerView) findViewById(R.id.shopslist);
        mRecyclerAdaptershops = new ProfileAdapter(ProfileActivity.this, shopsList);
        final LinearLayoutManager thirdManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewshops.setLayoutManager(thirdManager);
        mRecyclerViewshops.setAdapter(mRecyclerAdaptershops);
        mRecyclerViewshops.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerViewshops, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                addshops(position, shopsList.get(position).getPlotname(), "Update shops");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    public void addlabour(final int position, final String conatct, String tittle) {
        imagePath = "";
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ProfileActivity.this);
        LayoutInflater inflater = ProfileActivity.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.common_popup, null);
        final ImageView itemclose = (ImageView) dialogView.findViewById(R.id.itemclose);
        final CustomFontEditText name = (CustomFontEditText) dialogView.findViewById(R.id.name);
        final CustomFontEditText description = (CustomFontEditText) dialogView.findViewById(R.id.description);
        final LinearLayout imagelin = (LinearLayout) dialogView.findViewById(R.id.imglin);
        final CircleImageView image = (CircleImageView) dialogView.findViewById(R.id.image);
        final CustomFontTextView imagetxt = (CustomFontTextView) dialogView.findViewById(R.id.imagetxt);
        CustomFontTextView submit = (CustomFontTextView) dialogView.findViewById(R.id.r_submittxt);
        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();
        b.setCancelable(false);
        itemclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.cancel();
            }
        });
        final CustomFontTextView itemtittle = (CustomFontTextView) dialogView.findViewById(R.id.itemtittle);
        itemtittle.setText(tittle);
        if (position != -1) {
            ArrayList<String> data = dbProfile.getData(vrpId, "labour", conatct);
            try {
                JSONObject jsonObject = new JSONObject(data.get(3));
                name.setText(jsonObject.getString("name"));
                description.setText(jsonObject.getString("description"));
                Glide.with(ProfileActivity.this).load(jsonObject.getString("image"))
                        .centerCrop()
                        .dontAnimate()
                        .thumbnail(0.5f)
                        .placeholder(R.drawable.profile)
                        .into(image);
                imagePath = jsonObject.getString("image");
                imagetxt.setText("update image");
            } catch (JSONException e) {
                Log.d("xxxxxxxxx", e.toString());
                Toast.makeText(getApplicationContext(), "went wrong", Toast.LENGTH_SHORT).show();
            }
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().length() <= 0 || description.getText().toString().length() <= 0) {
                    Toast.makeText(getApplicationContext(), "Enter all fileds", Toast.LENGTH_SHORT).show();
                } else {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("name", name.getText().toString());
                        jsonObject.put("description", description.getText().toString());
                        jsonObject.put("image", imagePath);
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "went wrong", Toast.LENGTH_SHORT).show();
                    }
                    if (position == -1) {
                        registerProduct(vrpId, "labour", name.getText().toString()
                                , name.getText().toString(), jsonObject.toString(), false, b, position);
                    } else {
                        registerProduct(vrpId, "labour", conatct
                                , name.getText().toString(), jsonObject.toString(), true, b, position);
                    }
                }
                b.cancel();
            }
        });
        imagetxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickSetup setup = new PickSetup();
                PickImageDialog.build(setup).setOnPickResult(new IPickResult() {
                    @Override
                    public void onPickResult(PickResult pickResult) {
                        Glide.with(ProfileActivity.this).load(pickResult.getUri())
                                .centerCrop()
                                .dontAnimate()
                                .thumbnail(0.5f)
                                .placeholder(R.drawable.profile)
                                .into(image);
                        imagePath = pickResult.getUri().toString();
                        imagetxt.setText(pickResult.getUri().toString());
                    }
                })
                        .show(ProfileActivity.this);
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickSetup setup = new PickSetup();
                PickImageDialog.build(setup).setOnPickResult(new IPickResult() {
                    @Override
                    public void onPickResult(PickResult pickResult) {
                        Glide.with(ProfileActivity.this).load(pickResult.getUri())
                                .centerCrop()
                                .dontAnimate()
                                .thumbnail(0.5f)
                                .placeholder(R.drawable.profile)
                                .into(image);
                        imagePath = pickResult.getUri().toString();
                        imagetxt.setText(pickResult.getUri().toString());
                    }
                })
                        .show(ProfileActivity.this);
            }
        });
        b.show();
    }

    public void addsoilresources(final int position, final String conatct, String tittle) {
        imagePath = "";
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ProfileActivity.this);
        LayoutInflater inflater = ProfileActivity.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.common_popup, null);
        final ImageView itemclose = (ImageView) dialogView.findViewById(R.id.itemclose);
        final CustomFontEditText name = (CustomFontEditText) dialogView.findViewById(R.id.name);
        final CustomFontEditText description = (CustomFontEditText) dialogView.findViewById(R.id.description);
        final LinearLayout imagelin = (LinearLayout) dialogView.findViewById(R.id.imglin);
        final CircleImageView image = (CircleImageView) dialogView.findViewById(R.id.image);
        final CustomFontTextView imagetxt = (CustomFontTextView) dialogView.findViewById(R.id.imagetxt);
        CustomFontTextView submit = (CustomFontTextView) dialogView.findViewById(R.id.r_submittxt);
        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();
        b.setCancelable(false);
        itemclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.cancel();
            }
        });
        final CustomFontTextView itemtittle = (CustomFontTextView) dialogView.findViewById(R.id.itemtittle);
        itemtittle.setText(tittle);
        if (position != -1) {
            ArrayList<String> data = dbProfile.getData(vrpId, "soilresources", conatct);
            try {
                JSONObject jsonObject = new JSONObject(data.get(3));
                name.setText(jsonObject.getString("name"));
                description.setText(jsonObject.getString("description"));
                Glide.with(ProfileActivity.this).load(jsonObject.getString("image"))
                        .centerCrop()
                        .dontAnimate()
                        .thumbnail(0.5f)
                        .placeholder(R.drawable.profile)
                        .into(image);
                imagePath = jsonObject.getString("image");
                imagetxt.setText("update image");
            } catch (JSONException e) {
                Log.d("xxxxxxxxx", e.toString());
                Toast.makeText(getApplicationContext(), "went wrong", Toast.LENGTH_SHORT).show();
            }
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().length() <= 0 || description.getText().toString().length() <= 0) {
                    Toast.makeText(getApplicationContext(), "Enter all fileds", Toast.LENGTH_SHORT).show();
                } else {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("name", name.getText().toString());
                        jsonObject.put("description", description.getText().toString());
                        jsonObject.put("image", imagePath);
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "went wrong", Toast.LENGTH_SHORT).show();
                    }
                    if (position == -1) {
                        registerProduct(vrpId, "soilresources", name.getText().toString()
                                , name.getText().toString(), jsonObject.toString(), false, b, position);
                    } else {
                        registerProduct(vrpId, "soilresources", conatct
                                , name.getText().toString(), jsonObject.toString(), true, b, position);
                    }
                }
                b.cancel();
            }
        });
        imagetxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickSetup setup = new PickSetup();
                PickImageDialog.build(setup).setOnPickResult(new IPickResult() {
                    @Override
                    public void onPickResult(PickResult pickResult) {
                        Glide.with(ProfileActivity.this).load(pickResult.getUri())
                                .centerCrop()
                                .dontAnimate()
                                .thumbnail(0.5f)
                                .placeholder(R.drawable.profile)
                                .into(image);
                        imagePath = pickResult.getUri().toString();
                        imagetxt.setText(pickResult.getUri().toString());
                    }
                })
                        .show(ProfileActivity.this);
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickSetup setup = new PickSetup();
                PickImageDialog.build(setup).setOnPickResult(new IPickResult() {
                    @Override
                    public void onPickResult(PickResult pickResult) {
                        Glide.with(ProfileActivity.this).load(pickResult.getUri())
                                .centerCrop()
                                .dontAnimate()
                                .thumbnail(0.5f)
                                .placeholder(R.drawable.profile)
                                .into(image);
                        imagePath = pickResult.getUri().toString();
                        imagetxt.setText(pickResult.getUri().toString());
                    }
                })
                        .show(ProfileActivity.this);
            }
        });
        b.show();
    }

    public void addwaterresources(final int position, final String conatct, String tittle) {
        imagePath = "";
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ProfileActivity.this);
        LayoutInflater inflater = ProfileActivity.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.common_popup, null);
        final ImageView itemclose = (ImageView) dialogView.findViewById(R.id.itemclose);
        final CustomFontEditText name = (CustomFontEditText) dialogView.findViewById(R.id.name);
        final CustomFontEditText description = (CustomFontEditText) dialogView.findViewById(R.id.description);
        final LinearLayout imagelin = (LinearLayout) dialogView.findViewById(R.id.imglin);
        final CircleImageView image = (CircleImageView) dialogView.findViewById(R.id.image);
        final CustomFontTextView imagetxt = (CustomFontTextView) dialogView.findViewById(R.id.imagetxt);
        CustomFontTextView submit = (CustomFontTextView) dialogView.findViewById(R.id.r_submittxt);
        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();
        b.setCancelable(false);
        itemclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.cancel();
            }
        });
        final CustomFontTextView itemtittle = (CustomFontTextView) dialogView.findViewById(R.id.itemtittle);
        itemtittle.setText(tittle);
        if (position != -1) {
            ArrayList<String> data = dbProfile.getData(vrpId, "waterresources", conatct);
            try {
                JSONObject jsonObject = new JSONObject(data.get(3));
                name.setText(jsonObject.getString("name"));
                description.setText(jsonObject.getString("description"));
                Glide.with(ProfileActivity.this).load(jsonObject.getString("image"))
                        .centerCrop()
                        .dontAnimate()
                        .thumbnail(0.5f)
                        .placeholder(R.drawable.profile)
                        .into(image);
                imagePath = jsonObject.getString("image");
                imagetxt.setText("update image");
            } catch (JSONException e) {
                Log.d("xxxxxxxxx", e.toString());
                Toast.makeText(getApplicationContext(), "went wrong", Toast.LENGTH_SHORT).show();
            }
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().length() <= 0 || description.getText().toString().length() <= 0) {
                    Toast.makeText(getApplicationContext(), "Enter all fileds", Toast.LENGTH_SHORT).show();
                } else {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("name", name.getText().toString());
                        jsonObject.put("description", description.getText().toString());
                        jsonObject.put("image", imagePath);
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "went wrong", Toast.LENGTH_SHORT).show();
                    }
                    if (position == -1) {
                        registerProduct(vrpId, "waterresources", name.getText().toString()
                                , name.getText().toString(), jsonObject.toString(), false, b, position);
                    } else {
                        registerProduct(vrpId, "waterresources", conatct
                                , name.getText().toString(), jsonObject.toString(), true, b, position);
                    }
                }
                b.cancel();
            }
        });
        imagetxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickSetup setup = new PickSetup();
                PickImageDialog.build(setup).setOnPickResult(new IPickResult() {
                    @Override
                    public void onPickResult(PickResult pickResult) {
                        Glide.with(ProfileActivity.this).load(pickResult.getUri())
                                .centerCrop()
                                .dontAnimate()
                                .thumbnail(0.5f)
                                .placeholder(R.drawable.profile)
                                .into(image);
                        imagePath = pickResult.getUri().toString();
                        imagetxt.setText(pickResult.getUri().toString());
                    }
                })
                        .show(ProfileActivity.this);
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickSetup setup = new PickSetup();
                PickImageDialog.build(setup).setOnPickResult(new IPickResult() {
                    @Override
                    public void onPickResult(PickResult pickResult) {
                        Glide.with(ProfileActivity.this).load(pickResult.getUri())
                                .centerCrop()
                                .dontAnimate()
                                .thumbnail(0.5f)
                                .placeholder(R.drawable.profile)
                                .into(image);
                        imagePath = pickResult.getUri().toString();
                        imagetxt.setText(pickResult.getUri().toString());
                    }
                })
                        .show(ProfileActivity.this);
            }
        });
        b.show();
    }

    public void addtoilets(final int position, final String conatct, String tittle) {
        imagePath = "";
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ProfileActivity.this);
        LayoutInflater inflater = ProfileActivity.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.common_popup, null);
        final ImageView itemclose = (ImageView) dialogView.findViewById(R.id.itemclose);
        final CustomFontEditText name = (CustomFontEditText) dialogView.findViewById(R.id.name);
        final CustomFontEditText description = (CustomFontEditText) dialogView.findViewById(R.id.description);
        final LinearLayout imagelin = (LinearLayout) dialogView.findViewById(R.id.imglin);
        final CircleImageView image = (CircleImageView) dialogView.findViewById(R.id.image);
        final CustomFontTextView imagetxt = (CustomFontTextView) dialogView.findViewById(R.id.imagetxt);
        CustomFontTextView submit = (CustomFontTextView) dialogView.findViewById(R.id.r_submittxt);
        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();
        b.setCancelable(false);
        itemclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.cancel();
            }
        });
        final CustomFontTextView itemtittle = (CustomFontTextView) dialogView.findViewById(R.id.itemtittle);
        itemtittle.setText(tittle);
        if (position != -1) {
            ArrayList<String> data = dbProfile.getData(vrpId, "toilets", conatct);
            try {
                JSONObject jsonObject = new JSONObject(data.get(3));
                name.setText(jsonObject.getString("name"));
                description.setText(jsonObject.getString("description"));
                Glide.with(ProfileActivity.this).load(jsonObject.getString("image"))
                        .centerCrop()
                        .dontAnimate()
                        .thumbnail(0.5f)
                        .placeholder(R.drawable.profile)
                        .into(image);
                imagePath = jsonObject.getString("image");
                imagetxt.setText("update image");
            } catch (JSONException e) {
                Log.d("xxxxxxxxx", e.toString());
                Toast.makeText(getApplicationContext(), "went wrong", Toast.LENGTH_SHORT).show();
            }
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().length() <= 0 || description.getText().toString().length() <= 0) {
                    Toast.makeText(getApplicationContext(), "Enter all fileds", Toast.LENGTH_SHORT).show();
                } else {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("name", name.getText().toString());
                        jsonObject.put("description", description.getText().toString());
                        jsonObject.put("image", imagePath);
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "went wrong", Toast.LENGTH_SHORT).show();
                    }
                    if (position == -1) {
                        registerProduct(vrpId, "toilets", name.getText().toString()
                                , name.getText().toString(), jsonObject.toString(), false, b, position);
                    } else {
                        registerProduct(vrpId, "toilets", conatct
                                , name.getText().toString(), jsonObject.toString(), true, b, position);
                    }
                }
                b.cancel();
            }
        });
        imagetxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickSetup setup = new PickSetup();
                PickImageDialog.build(setup).setOnPickResult(new IPickResult() {
                    @Override
                    public void onPickResult(PickResult pickResult) {
                        Glide.with(ProfileActivity.this).load(pickResult.getUri())
                                .centerCrop()
                                .dontAnimate()
                                .thumbnail(0.5f)
                                .placeholder(R.drawable.profile)
                                .into(image);
                        imagePath = pickResult.getUri().toString();
                        imagetxt.setText(pickResult.getUri().toString());
                    }
                })
                        .show(ProfileActivity.this);
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickSetup setup = new PickSetup();
                PickImageDialog.build(setup).setOnPickResult(new IPickResult() {
                    @Override
                    public void onPickResult(PickResult pickResult) {
                        Glide.with(ProfileActivity.this).load(pickResult.getUri())
                                .centerCrop()
                                .dontAnimate()
                                .thumbnail(0.5f)
                                .placeholder(R.drawable.profile)
                                .into(image);
                        imagePath = pickResult.getUri().toString();
                        imagetxt.setText(pickResult.getUri().toString());
                    }
                })
                        .show(ProfileActivity.this);
            }
        });
        b.show();
    }

    public void addelectricity(final int position, final String conatct, String tittle) {
        imagePath = "";
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ProfileActivity.this);
        LayoutInflater inflater = ProfileActivity.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.common_popup, null);
        final ImageView itemclose = (ImageView) dialogView.findViewById(R.id.itemclose);
        final CustomFontEditText name = (CustomFontEditText) dialogView.findViewById(R.id.name);
        final CustomFontEditText description = (CustomFontEditText) dialogView.findViewById(R.id.description);
        final LinearLayout imagelin = (LinearLayout) dialogView.findViewById(R.id.imglin);
        final CircleImageView image = (CircleImageView) dialogView.findViewById(R.id.image);
        final CustomFontTextView imagetxt = (CustomFontTextView) dialogView.findViewById(R.id.imagetxt);
        CustomFontTextView submit = (CustomFontTextView) dialogView.findViewById(R.id.r_submittxt);
        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();
        b.setCancelable(false);
        itemclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.cancel();
            }
        });
        final CustomFontTextView itemtittle = (CustomFontTextView) dialogView.findViewById(R.id.itemtittle);
        itemtittle.setText(tittle);
        if (position != -1) {
            ArrayList<String> data = dbProfile.getData(vrpId, "electricity", conatct);
            try {
                JSONObject jsonObject = new JSONObject(data.get(3));
                name.setText(jsonObject.getString("name"));
                description.setText(jsonObject.getString("description"));
                Glide.with(ProfileActivity.this).load(jsonObject.getString("image"))
                        .centerCrop()
                        .dontAnimate()
                        .thumbnail(0.5f)
                        .placeholder(R.drawable.profile)
                        .into(image);
                imagePath = jsonObject.getString("image");
                imagetxt.setText("update image");
            } catch (JSONException e) {
                Log.d("xxxxxxxxx", e.toString());
                Toast.makeText(getApplicationContext(), "went wrong", Toast.LENGTH_SHORT).show();
            }
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().length() <= 0 || description.getText().toString().length() <= 0) {
                    Toast.makeText(getApplicationContext(), "Enter all fileds", Toast.LENGTH_SHORT).show();
                } else {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("name", name.getText().toString());
                        jsonObject.put("description", description.getText().toString());
                        jsonObject.put("image", imagePath);
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "went wrong", Toast.LENGTH_SHORT).show();
                    }
                    if (position == -1) {
                        registerProduct(vrpId, "electricity", name.getText().toString()
                                , name.getText().toString(), jsonObject.toString(), false, b, position);
                    } else {
                        registerProduct(vrpId, "electricity", conatct
                                , name.getText().toString(), jsonObject.toString(), true, b, position);
                    }
                }
                b.cancel();
            }
        });
        imagetxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickSetup setup = new PickSetup();
                PickImageDialog.build(setup).setOnPickResult(new IPickResult() {
                    @Override
                    public void onPickResult(PickResult pickResult) {
                        Glide.with(ProfileActivity.this).load(pickResult.getUri())
                                .centerCrop()
                                .dontAnimate()
                                .thumbnail(0.5f)
                                .placeholder(R.drawable.profile)
                                .into(image);
                        imagePath = pickResult.getUri().toString();
                        imagetxt.setText(pickResult.getUri().toString());
                    }
                })
                        .show(ProfileActivity.this);
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickSetup setup = new PickSetup();
                PickImageDialog.build(setup).setOnPickResult(new IPickResult() {
                    @Override
                    public void onPickResult(PickResult pickResult) {
                        Glide.with(ProfileActivity.this).load(pickResult.getUri())
                                .centerCrop()
                                .dontAnimate()
                                .thumbnail(0.5f)
                                .placeholder(R.drawable.profile)
                                .into(image);
                        imagePath = pickResult.getUri().toString();
                        imagetxt.setText(pickResult.getUri().toString());
                    }
                })
                        .show(ProfileActivity.this);
            }
        });
        b.show();
    }

    public void addfinancialinstitutions(final int position, final String conatct, String tittle) {
        imagePath = "";
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ProfileActivity.this);
        LayoutInflater inflater = ProfileActivity.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.common_popup, null);
        final ImageView itemclose = (ImageView) dialogView.findViewById(R.id.itemclose);
        final CustomFontEditText name = (CustomFontEditText) dialogView.findViewById(R.id.name);
        final CustomFontEditText description = (CustomFontEditText) dialogView.findViewById(R.id.description);
        final LinearLayout imagelin = (LinearLayout) dialogView.findViewById(R.id.imglin);
        final CircleImageView image = (CircleImageView) dialogView.findViewById(R.id.image);
        final CustomFontTextView imagetxt = (CustomFontTextView) dialogView.findViewById(R.id.imagetxt);
        CustomFontTextView submit = (CustomFontTextView) dialogView.findViewById(R.id.r_submittxt);
        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();
        b.setCancelable(false);
        itemclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.cancel();
            }
        });
        final CustomFontTextView itemtittle = (CustomFontTextView) dialogView.findViewById(R.id.itemtittle);
        itemtittle.setText(tittle);
        if (position != -1) {
            ArrayList<String> data = dbProfile.getData(vrpId, "financialinstitutions", conatct);
            try {
                JSONObject jsonObject = new JSONObject(data.get(3));
                name.setText(jsonObject.getString("name"));
                description.setText(jsonObject.getString("description"));
                Glide.with(ProfileActivity.this).load(jsonObject.getString("image"))
                        .centerCrop()
                        .dontAnimate()
                        .thumbnail(0.5f)
                        .placeholder(R.drawable.profile)
                        .into(image);
                imagePath = jsonObject.getString("image");
                imagetxt.setText("update image");
            } catch (JSONException e) {
                Log.d("xxxxxxxxx", e.toString());
                Toast.makeText(getApplicationContext(), "went wrong", Toast.LENGTH_SHORT).show();
            }
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().length() <= 0 || description.getText().toString().length() <= 0) {
                    Toast.makeText(getApplicationContext(), "Enter all fileds", Toast.LENGTH_SHORT).show();
                } else {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("name", name.getText().toString());
                        jsonObject.put("description", description.getText().toString());
                        jsonObject.put("image", imagePath);
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "went wrong", Toast.LENGTH_SHORT).show();
                    }
                    if (position == -1) {
                        registerProduct(vrpId, "financialinstitutions", name.getText().toString()
                                , name.getText().toString(), jsonObject.toString(), false, b, position);
                    } else {
                        registerProduct(vrpId, "financialinstitutions", conatct
                                , name.getText().toString(), jsonObject.toString(), true, b, position);
                    }
                }
                b.cancel();
            }
        });
        imagetxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickSetup setup = new PickSetup();
                PickImageDialog.build(setup).setOnPickResult(new IPickResult() {
                    @Override
                    public void onPickResult(PickResult pickResult) {
                        Glide.with(ProfileActivity.this).load(pickResult.getUri())
                                .centerCrop()
                                .dontAnimate()
                                .thumbnail(0.5f)
                                .placeholder(R.drawable.profile)
                                .into(image);
                        imagePath = pickResult.getUri().toString();
                        imagetxt.setText(pickResult.getUri().toString());
                    }
                })
                        .show(ProfileActivity.this);
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickSetup setup = new PickSetup();
                PickImageDialog.build(setup).setOnPickResult(new IPickResult() {
                    @Override
                    public void onPickResult(PickResult pickResult) {
                        Glide.with(ProfileActivity.this).load(pickResult.getUri())
                                .centerCrop()
                                .dontAnimate()
                                .thumbnail(0.5f)
                                .placeholder(R.drawable.profile)
                                .into(image);
                        imagePath = pickResult.getUri().toString();
                        imagetxt.setText(pickResult.getUri().toString());
                    }
                })
                        .show(ProfileActivity.this);
            }
        });
        b.show();
    }

    public void addschools(final int position, final String conatct, String tittle) {
        imagePath = "";
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ProfileActivity.this);
        LayoutInflater inflater = ProfileActivity.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.common_popup, null);
        final ImageView itemclose = (ImageView) dialogView.findViewById(R.id.itemclose);
        final CustomFontEditText name = (CustomFontEditText) dialogView.findViewById(R.id.name);
        final CustomFontEditText description = (CustomFontEditText) dialogView.findViewById(R.id.description);
        final LinearLayout imagelin = (LinearLayout) dialogView.findViewById(R.id.imglin);
        final CircleImageView image = (CircleImageView) dialogView.findViewById(R.id.image);
        final CustomFontTextView imagetxt = (CustomFontTextView) dialogView.findViewById(R.id.imagetxt);
        CustomFontTextView submit = (CustomFontTextView) dialogView.findViewById(R.id.r_submittxt);
        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();
        b.setCancelable(false);
        itemclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.cancel();
            }
        });
        final CustomFontTextView itemtittle = (CustomFontTextView) dialogView.findViewById(R.id.itemtittle);
        itemtittle.setText(tittle);
        if (position != -1) {
            ArrayList<String> data = dbProfile.getData(vrpId, "schools", conatct);
            try {
                JSONObject jsonObject = new JSONObject(data.get(3));
                name.setText(jsonObject.getString("name"));
                description.setText(jsonObject.getString("description"));
                Glide.with(ProfileActivity.this).load(jsonObject.getString("image"))
                        .centerCrop()
                        .dontAnimate()
                        .thumbnail(0.5f)
                        .placeholder(R.drawable.profile)
                        .into(image);
                imagePath = jsonObject.getString("image");
                imagetxt.setText("update image");
            } catch (JSONException e) {
                Log.d("xxxxxxxxx", e.toString());
                Toast.makeText(getApplicationContext(), "went wrong", Toast.LENGTH_SHORT).show();
            }
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().length() <= 0 || description.getText().toString().length() <= 0) {
                    Toast.makeText(getApplicationContext(), "Enter all fileds", Toast.LENGTH_SHORT).show();
                } else {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("name", name.getText().toString());
                        jsonObject.put("description", description.getText().toString());
                        jsonObject.put("image", imagePath);
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "went wrong", Toast.LENGTH_SHORT).show();
                    }
                    if (position == -1) {
                        registerProduct(vrpId, "schools", name.getText().toString()
                                , name.getText().toString(), jsonObject.toString(), false, b, position);
                    } else {
                        registerProduct(vrpId, "schools", conatct
                                , name.getText().toString(), jsonObject.toString(), true, b, position);
                    }
                }
                b.cancel();
            }
        });
        imagetxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickSetup setup = new PickSetup();
                PickImageDialog.build(setup).setOnPickResult(new IPickResult() {
                    @Override
                    public void onPickResult(PickResult pickResult) {
                        Glide.with(ProfileActivity.this).load(pickResult.getUri())
                                .centerCrop()
                                .dontAnimate()
                                .thumbnail(0.5f)
                                .placeholder(R.drawable.profile)
                                .into(image);
                        imagePath = pickResult.getUri().toString();
                        imagetxt.setText(pickResult.getUri().toString());
                    }
                })
                        .show(ProfileActivity.this);
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickSetup setup = new PickSetup();
                PickImageDialog.build(setup).setOnPickResult(new IPickResult() {
                    @Override
                    public void onPickResult(PickResult pickResult) {
                        Glide.with(ProfileActivity.this).load(pickResult.getUri())
                                .centerCrop()
                                .dontAnimate()
                                .thumbnail(0.5f)
                                .placeholder(R.drawable.profile)
                                .into(image);
                        imagePath = pickResult.getUri().toString();
                        imagetxt.setText(pickResult.getUri().toString());
                    }
                })
                        .show(ProfileActivity.this);
            }
        });
        b.show();
    }

    public void addhospitals(final int position, final String conatct, String tittle) {
        imagePath = "";
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ProfileActivity.this);
        LayoutInflater inflater = ProfileActivity.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.common_popup, null);
        final ImageView itemclose = (ImageView) dialogView.findViewById(R.id.itemclose);
        final CustomFontEditText name = (CustomFontEditText) dialogView.findViewById(R.id.name);
        final CustomFontEditText description = (CustomFontEditText) dialogView.findViewById(R.id.description);
        final LinearLayout imagelin = (LinearLayout) dialogView.findViewById(R.id.imglin);
        final CircleImageView image = (CircleImageView) dialogView.findViewById(R.id.image);
        final CustomFontTextView imagetxt = (CustomFontTextView) dialogView.findViewById(R.id.imagetxt);
        CustomFontTextView submit = (CustomFontTextView) dialogView.findViewById(R.id.r_submittxt);
        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();
        b.setCancelable(false);
        itemclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.cancel();
            }
        });
        final CustomFontTextView itemtittle = (CustomFontTextView) dialogView.findViewById(R.id.itemtittle);
        itemtittle.setText(tittle);
        if (position != -1) {
            ArrayList<String> data = dbProfile.getData(vrpId, "hospitals", conatct);
            try {
                JSONObject jsonObject = new JSONObject(data.get(3));
                name.setText(jsonObject.getString("name"));
                description.setText(jsonObject.getString("description"));
                Glide.with(ProfileActivity.this).load(jsonObject.getString("image"))
                        .centerCrop()
                        .dontAnimate()
                        .thumbnail(0.5f)
                        .placeholder(R.drawable.profile)
                        .into(image);
                imagePath = jsonObject.getString("image");
                imagetxt.setText("update image");
            } catch (JSONException e) {
                Log.d("xxxxxxxxx", e.toString());
                Toast.makeText(getApplicationContext(), "went wrong", Toast.LENGTH_SHORT).show();
            }
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().length() <= 0 || description.getText().toString().length() <= 0) {
                    Toast.makeText(getApplicationContext(), "Enter all fileds", Toast.LENGTH_SHORT).show();
                } else {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("name", name.getText().toString());
                        jsonObject.put("description", description.getText().toString());
                        jsonObject.put("image", imagePath);
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "went wrong", Toast.LENGTH_SHORT).show();
                    }
                    if (position == -1) {
                        registerProduct(vrpId, "hospitals", name.getText().toString()
                                , name.getText().toString(), jsonObject.toString(), false, b, position);
                    } else {
                        registerProduct(vrpId, "hospitals", conatct
                                , name.getText().toString(), jsonObject.toString(), true, b, position);
                    }
                }
                b.cancel();
            }
        });
        imagetxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickSetup setup = new PickSetup();
                PickImageDialog.build(setup).setOnPickResult(new IPickResult() {
                    @Override
                    public void onPickResult(PickResult pickResult) {
                        Glide.with(ProfileActivity.this).load(pickResult.getUri())
                                .centerCrop()
                                .dontAnimate()
                                .thumbnail(0.5f)
                                .placeholder(R.drawable.profile)
                                .into(image);
                        imagePath = pickResult.getUri().toString();
                        imagetxt.setText(pickResult.getUri().toString());
                    }
                })
                        .show(ProfileActivity.this);
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickSetup setup = new PickSetup();
                PickImageDialog.build(setup).setOnPickResult(new IPickResult() {
                    @Override
                    public void onPickResult(PickResult pickResult) {
                        Glide.with(ProfileActivity.this).load(pickResult.getUri())
                                .centerCrop()
                                .dontAnimate()
                                .thumbnail(0.5f)
                                .placeholder(R.drawable.profile)
                                .into(image);
                        imagePath = pickResult.getUri().toString();
                        imagetxt.setText(pickResult.getUri().toString());
                    }
                })
                        .show(ProfileActivity.this);
            }
        });
        b.show();
    }

    public void addshops(final int position, final String conatct, String tittle) {
        imagePath = "";
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ProfileActivity.this);
        LayoutInflater inflater = ProfileActivity.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.common_popup, null);
        final ImageView itemclose = (ImageView) dialogView.findViewById(R.id.itemclose);
        final CustomFontEditText name = (CustomFontEditText) dialogView.findViewById(R.id.name);
        final CustomFontEditText description = (CustomFontEditText) dialogView.findViewById(R.id.description);
        final LinearLayout imagelin = (LinearLayout) dialogView.findViewById(R.id.imglin);
        final CircleImageView image = (CircleImageView) dialogView.findViewById(R.id.image);
        final CustomFontTextView imagetxt = (CustomFontTextView) dialogView.findViewById(R.id.imagetxt);
        CustomFontTextView submit = (CustomFontTextView) dialogView.findViewById(R.id.r_submittxt);
        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();
        b.setCancelable(false);
        itemclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.cancel();
            }
        });
        final CustomFontTextView itemtittle = (CustomFontTextView) dialogView.findViewById(R.id.itemtittle);
        itemtittle.setText(tittle);
        if (position != -1) {
            ArrayList<String> data = dbProfile.getData(vrpId, "shops", conatct);
            try {
                JSONObject jsonObject = new JSONObject(data.get(3));
                name.setText(jsonObject.getString("name"));
                description.setText(jsonObject.getString("description"));
                Glide.with(ProfileActivity.this).load(jsonObject.getString("image"))
                        .centerCrop()
                        .dontAnimate()
                        .thumbnail(0.5f)
                        .placeholder(R.drawable.profile)
                        .into(image);
                imagePath = jsonObject.getString("image");
                imagetxt.setText("update image");
            } catch (JSONException e) {
                Log.d("xxxxxxxxx", e.toString());
                Toast.makeText(getApplicationContext(), "went wrong", Toast.LENGTH_SHORT).show();
            }
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().length() <= 0 || description.getText().toString().length() <= 0) {
                    Toast.makeText(getApplicationContext(), "Enter all fileds", Toast.LENGTH_SHORT).show();
                } else {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("name", name.getText().toString());
                        jsonObject.put("description", description.getText().toString());
                        jsonObject.put("image", imagePath);
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "went wrong", Toast.LENGTH_SHORT).show();
                    }
                    if (position == -1) {
                        registerProduct(vrpId, "shops", name.getText().toString()
                                , name.getText().toString(), jsonObject.toString(), false, b, position);
                    } else {
                        registerProduct(vrpId, "shops", conatct
                                , name.getText().toString(), jsonObject.toString(), true, b, position);
                    }
                }
                b.cancel();
            }
        });
        imagetxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickSetup setup = new PickSetup();
                PickImageDialog.build(setup).setOnPickResult(new IPickResult() {
                    @Override
                    public void onPickResult(PickResult pickResult) {
                        Glide.with(ProfileActivity.this).load(pickResult.getUri())
                                .centerCrop()
                                .dontAnimate()
                                .thumbnail(0.5f)
                                .placeholder(R.drawable.profile)
                                .into(image);
                        imagePath = pickResult.getUri().toString();
                        imagetxt.setText(pickResult.getUri().toString());
                    }
                })
                        .show(ProfileActivity.this);
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickSetup setup = new PickSetup();
                PickImageDialog.build(setup).setOnPickResult(new IPickResult() {
                    @Override
                    public void onPickResult(PickResult pickResult) {
                        Glide.with(ProfileActivity.this).load(pickResult.getUri())
                                .centerCrop()
                                .dontAnimate()
                                .thumbnail(0.5f)
                                .placeholder(R.drawable.profile)
                                .into(image);
                        imagePath = pickResult.getUri().toString();
                        imagetxt.setText(pickResult.getUri().toString());
                    }
                })
                        .show(ProfileActivity.this);
            }
        });
        b.show();
    }

    public void addCrop(final int position, final String conatct, String tittle) {
        imagePath = "";
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ProfileActivity.this);
        LayoutInflater inflater = ProfileActivity.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.crops_popup, null);
        final ImageView itemclose = (ImageView) dialogView.findViewById(R.id.itemclose);
        final CustomFontEditText year = (CustomFontEditText) dialogView.findViewById(R.id.year);
        final EditText season = (EditText) dialogView.findViewById(R.id.season);
        final CustomFontEditText perennial = (CustomFontEditText) dialogView.findViewById(R.id.perennial);
        final LinearLayout perenniallin = (LinearLayout) dialogView.findViewById(R.id.perenniallin);
        final LinearLayout rabilin = (LinearLayout) dialogView.findViewById(R.id.rabilin);
        final LinearLayout khariflin = (LinearLayout) dialogView.findViewById(R.id.khariflin);
        final LinearLayout annuallin = (LinearLayout) dialogView.findViewById(R.id.annuallin);
        final LinearLayout zaidlin = (LinearLayout) dialogView.findViewById(R.id.zaidlin);
        final LinearLayout imagelin = (LinearLayout) dialogView.findViewById(R.id.imglin);
        final CustomFontEditText annual = (CustomFontEditText) dialogView.findViewById(R.id.annual);
        final CustomFontEditText rabi = (CustomFontEditText) dialogView.findViewById(R.id.rabi);
        final CustomFontEditText kharif = (CustomFontEditText) dialogView.findViewById(R.id.karif);
        final CustomFontEditText zaid = (CustomFontEditText) dialogView.findViewById(R.id.zaid);
        final CircleImageView image = (CircleImageView) dialogView.findViewById(R.id.image);
        final CustomFontTextView imagetxt = (CustomFontTextView) dialogView.findViewById(R.id.imagetxt);
        CustomFontTextView submit = (CustomFontTextView) dialogView.findViewById(R.id.r_submittxt);
        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();
        b.setCancelable(false);
        itemclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.cancel();
            }
        });
        final CustomFontTextView itemtittle = (CustomFontTextView) dialogView.findViewById(R.id.itemtittle);
        itemtittle.setText(tittle);
        if (position != -1) {
            ArrayList<String> data = dbProfile.getData(vrpId, "crop", conatct);
            try {
                JSONObject jsonObject = new JSONObject(data.get(3));
                year.setText(jsonObject.getString("year"));
                season.setText(jsonObject.getString("season"));
                if (season.getText().toString().trim().equals("rabi")) {
                    rabilin.setVisibility(View.VISIBLE);
                    rabi.setText(jsonObject.getString("cropname"));
                } else if (season.getText().toString().trim().equals("zaid")) {
                    zaidlin.setVisibility(View.VISIBLE);
                    zaid.setText(jsonObject.getString("cropname"));
                } else if (season.getText().toString().trim().equals("kharif")) {
                    khariflin.setVisibility(View.VISIBLE);
                    kharif.setText(jsonObject.getString("cropname"));
                } else if (season.getText().toString().trim().equals("annual")) {
                    annuallin.setVisibility(View.VISIBLE);
                    annual.setText(jsonObject.getString("cropname"));
                } else if (season.getText().toString().trim().equals("perennial")) {
                    perenniallin.setVisibility(View.VISIBLE);
                    perennial.setText(jsonObject.getString("cropname"));
                }
                if (season.getText().toString().length() > 0) {
                    imagelin.setVisibility(View.VISIBLE);
                    Glide.with(ProfileActivity.this).load(jsonObject.getString("image"))
                            .centerCrop()
                            .dontAnimate()
                            .thumbnail(0.5f)
                            .placeholder(R.drawable.profile)
                            .into(image);
                    imagePath = jsonObject.getString("image");
                    imagetxt.setText("update image");
                }

            } catch (JSONException e) {
                Log.d("xxxxxxxxx", e.toString());
                Toast.makeText(getApplicationContext(), "went wrong", Toast.LENGTH_SHORT).show();
            }
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (year.getText().toString().length() <= 0 || season.getText().toString().length() <= 0) {
                    Toast.makeText(getApplicationContext(), "Enter all fileds", Toast.LENGTH_SHORT).show();
                } else {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("year", year.getText().toString());
                        jsonObject.put("season", season.getText().toString());
                        if (season.getText().toString().trim().equals("rabi")) {
                            jsonObject.put("cropname", rabi.getText().toString());
                        } else if (season.getText().toString().trim().equals("zaid")) {
                            jsonObject.put("cropname", zaid.getText().toString());
                        } else if (season.getText().toString().trim().equals("kharif")) {
                            jsonObject.put("cropname", kharif.getText().toString());
                        } else if (season.getText().toString().trim().equals("annual")) {
                            jsonObject.put("cropname", annual.getText().toString());
                        } else if (season.getText().toString().trim().equals("perennial")) {
                            jsonObject.put("cropname", perennial.getText().toString());
                        }
                        if (season.getText().toString().trim().length() > 0) {
                            jsonObject.put("image", imagePath);
                        }
                        if (position == -1) {
                            registerProduct(vrpId, "crop", jsonObject.getString("cropname")
                                    , jsonObject.getString("cropname"), jsonObject.toString(), false, b, position);
                        } else {
                            registerProduct(vrpId, "crop", conatct
                                    , jsonObject.getString("cropname"), jsonObject.toString(), true, b, position);
                        }
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
                b.cancel();
            }
        });
        season.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String choices = "rabi,zaid,kharif,annual,perennial";
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(ProfileActivity.this);
                builderSingle.setTitle("Select " + "Season" + ":-");
                String[] choicesStrings = choices.split(",");
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ProfileActivity.this, android.R.layout.simple_list_item_1);
                for (int i = 0; i < choicesStrings.length; i++) {
                    arrayAdapter.add(choicesStrings[i]);
                }
                builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String strName = arrayAdapter.getItem(which);
                        season.setText(strName);
                        if (season.getText().toString().length() > 0) {
                            imagelin.setVisibility(View.VISIBLE);
                            if (season.getText().toString().trim().equals("rabi")) {
                                rabilin.setVisibility(View.VISIBLE);
                                zaidlin.setVisibility(View.GONE);
                                khariflin.setVisibility(View.GONE);
                                annuallin.setVisibility(View.GONE);
                                perenniallin.setVisibility(View.GONE);
                            } else if (season.getText().toString().trim().equals("zaid")) {
                                rabilin.setVisibility(View.GONE);
                                zaidlin.setVisibility(View.VISIBLE);
                                khariflin.setVisibility(View.GONE);
                                annuallin.setVisibility(View.GONE);
                                perenniallin.setVisibility(View.GONE);
                            } else if (season.getText().toString().trim().equals("kharif")) {
                                rabilin.setVisibility(View.GONE);
                                zaidlin.setVisibility(View.GONE);
                                khariflin.setVisibility(View.VISIBLE);
                                annuallin.setVisibility(View.GONE);
                                perenniallin.setVisibility(View.GONE);
                            } else if (season.getText().toString().trim().equals("annual")) {
                                rabilin.setVisibility(View.GONE);
                                zaidlin.setVisibility(View.GONE);
                                khariflin.setVisibility(View.GONE);
                                annuallin.setVisibility(View.VISIBLE);
                                perenniallin.setVisibility(View.GONE);
                            } else if (season.getText().toString().trim().equals("perennial")) {
                                rabilin.setVisibility(View.GONE);
                                zaidlin.setVisibility(View.GONE);
                                khariflin.setVisibility(View.GONE);
                                annuallin.setVisibility(View.GONE);
                                perenniallin.setVisibility(View.VISIBLE);
                            }
                        } else {
                            rabilin.setVisibility(View.GONE);
                            zaidlin.setVisibility(View.GONE);
                            khariflin.setVisibility(View.GONE);
                            annuallin.setVisibility(View.GONE);
                            perenniallin.setVisibility(View.GONE);
                            imagelin.setVisibility(View.GONE);
                        }
                    }
                });
                builderSingle.show();
            }
        });
        imagetxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickSetup setup = new PickSetup();
                PickImageDialog.build(setup).setOnPickResult(new IPickResult() {
                    @Override
                    public void onPickResult(PickResult pickResult) {
                        Glide.with(ProfileActivity.this).load(pickResult.getUri())
                                .centerCrop()
                                .dontAnimate()
                                .thumbnail(0.5f)
                                .placeholder(R.drawable.profile)
                                .into(image);
                        imagePath = pickResult.getUri().toString();
                        imagetxt.setText(pickResult.getUri().toString());
                    }
                })
                        .show(ProfileActivity.this);
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickSetup setup = new PickSetup();
                PickImageDialog.build(setup).setOnPickResult(new IPickResult() {
                    @Override
                    public void onPickResult(PickResult pickResult) {
                        Glide.with(ProfileActivity.this).load(pickResult.getUri())
                                .centerCrop()
                                .dontAnimate()
                                .thumbnail(0.5f)
                                .placeholder(R.drawable.profile)
                                .into(image);
                        imagePath = pickResult.getUri().toString();
                        imagetxt.setText(pickResult.getUri().toString());
                    }
                })
                        .show(ProfileActivity.this);
            }
        });
        season.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String choices = "rabi,zaid,kharif,annual,perennial";
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(ProfileActivity.this);
                builderSingle.setTitle("Select " + "Season" + ":-");
                String[] choicesStrings = choices.split(",");
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ProfileActivity.this, android.R.layout.simple_list_item_1);
                for (int i = 0; i < choicesStrings.length; i++) {
                    arrayAdapter.add(choicesStrings[i]);
                }
                builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = arrayAdapter.getItem(which);
                        season.setText(strName);
                        if (season.getText().toString().length() > 0) {
                            imagelin.setVisibility(View.VISIBLE);
                            if (season.getText().toString().trim().equals("rabi")) {
                                rabilin.setVisibility(View.VISIBLE);
                                zaidlin.setVisibility(View.GONE);
                                khariflin.setVisibility(View.GONE);
                                annuallin.setVisibility(View.GONE);
                                perenniallin.setVisibility(View.GONE);
                            } else if (season.getText().toString().trim().equals("zaid")) {
                                rabilin.setVisibility(View.GONE);
                                zaidlin.setVisibility(View.VISIBLE);
                                khariflin.setVisibility(View.GONE);
                                annuallin.setVisibility(View.GONE);
                                perenniallin.setVisibility(View.GONE);
                            } else if (season.getText().toString().trim().equals("kharif")) {
                                rabilin.setVisibility(View.GONE);
                                zaidlin.setVisibility(View.GONE);
                                khariflin.setVisibility(View.VISIBLE);
                                annuallin.setVisibility(View.GONE);
                                perenniallin.setVisibility(View.GONE);
                            } else if (season.getText().toString().trim().equals("annual")) {
                                rabilin.setVisibility(View.GONE);
                                zaidlin.setVisibility(View.GONE);
                                khariflin.setVisibility(View.GONE);
                                annuallin.setVisibility(View.VISIBLE);
                                perenniallin.setVisibility(View.GONE);
                            } else if (season.getText().toString().trim().equals("perennial")) {
                                rabilin.setVisibility(View.GONE);
                                zaidlin.setVisibility(View.GONE);
                                khariflin.setVisibility(View.GONE);
                                annuallin.setVisibility(View.GONE);
                                perenniallin.setVisibility(View.VISIBLE);
                            }
                        } else {
                            rabilin.setVisibility(View.GONE);
                            zaidlin.setVisibility(View.GONE);
                            khariflin.setVisibility(View.GONE);
                            annuallin.setVisibility(View.GONE);
                            perenniallin.setVisibility(View.GONE);
                            imagelin.setVisibility(View.GONE);
                        }
                    }
                });
                builderSingle.show();
            }
        });
        season.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (season.getText().toString().trim().length() == 0) {
                    rabilin.setVisibility(View.GONE);
                    zaidlin.setVisibility(View.GONE);
                    khariflin.setVisibility(View.GONE);
                    annuallin.setVisibility(View.GONE);
                    perenniallin.setVisibility(View.GONE);
                    imagelin.setVisibility(View.GONE);
                }
            }
        });
        b.show();
    }

    public void addAnimal(final int position, final String conatct, String tittle) {
        imagePath = "";
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ProfileActivity.this);
        LayoutInflater inflater = ProfileActivity.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.common_popup, null);
        final ImageView itemclose = (ImageView) dialogView.findViewById(R.id.itemclose);
        final CustomFontEditText name = (CustomFontEditText) dialogView.findViewById(R.id.name);
        final CustomFontEditText description = (CustomFontEditText) dialogView.findViewById(R.id.description);
        final LinearLayout imagelin = (LinearLayout) dialogView.findViewById(R.id.imglin);
        final CircleImageView image = (CircleImageView) dialogView.findViewById(R.id.image);
        final CustomFontTextView imagetxt = (CustomFontTextView) dialogView.findViewById(R.id.imagetxt);
        CustomFontTextView submit = (CustomFontTextView) dialogView.findViewById(R.id.r_submittxt);
        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();
        b.setCancelable(false);
        itemclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.cancel();
            }
        });
        final CustomFontTextView itemtittle = (CustomFontTextView) dialogView.findViewById(R.id.itemtittle);
        itemtittle.setText(tittle);
        if (position != -1) {
            ArrayList<String> data = dbProfile.getData(vrpId, "animal", conatct);
            try {
                JSONObject jsonObject = new JSONObject(data.get(3));
                name.setText(jsonObject.getString("name"));
                description.setText(jsonObject.getString("description"));
                Glide.with(ProfileActivity.this).load(jsonObject.getString("image"))
                        .centerCrop()
                        .dontAnimate()
                        .thumbnail(0.5f)
                        .placeholder(R.drawable.profile)
                        .into(image);
                imagePath = jsonObject.getString("image");
                imagetxt.setText("update image");
            } catch (JSONException e) {
                Log.d("xxxxxxxxx", e.toString());
                Toast.makeText(getApplicationContext(), "went wrong", Toast.LENGTH_SHORT).show();
            }
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().length() <= 0 || description.getText().toString().length() <= 0) {
                    Toast.makeText(getApplicationContext(), "Enter all fileds", Toast.LENGTH_SHORT).show();
                } else {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("name", name.getText().toString());
                        jsonObject.put("description", description.getText().toString());
                        jsonObject.put("image", imagePath);
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "went wrong", Toast.LENGTH_SHORT).show();
                    }
                    if (position == -1) {
                        registerProduct(vrpId, "animal", name.getText().toString()
                                , name.getText().toString(), jsonObject.toString(), false, b, position);
                    } else {
                        registerProduct(vrpId, "animal", conatct
                                , name.getText().toString(), jsonObject.toString(), true, b, position);
                    }
                }
                b.cancel();
            }
        });
        imagetxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickSetup setup = new PickSetup();
                PickImageDialog.build(setup).setOnPickResult(new IPickResult() {
                    @Override
                    public void onPickResult(PickResult pickResult) {
                        Glide.with(ProfileActivity.this).load(pickResult.getUri())
                                .centerCrop()
                                .dontAnimate()
                                .thumbnail(0.5f)
                                .placeholder(R.drawable.profile)
                                .into(image);
                        imagePath = pickResult.getUri().toString();
                        imagetxt.setText(pickResult.getUri().toString());
                    }
                })
                        .show(ProfileActivity.this);
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickSetup setup = new PickSetup();
                PickImageDialog.build(setup).setOnPickResult(new IPickResult() {
                    @Override
                    public void onPickResult(PickResult pickResult) {
                        Glide.with(ProfileActivity.this).load(pickResult.getUri())
                                .centerCrop()
                                .dontAnimate()
                                .thumbnail(0.5f)
                                .placeholder(R.drawable.profile)
                                .into(image);
                        imagePath = pickResult.getUri().toString();
                        imagetxt.setText(pickResult.getUri().toString());
                    }
                })
                        .show(ProfileActivity.this);
            }
        });
        b.show();
    }

    public void addFarmmachine(final int position, final String conatct, String tittle) {
        imagePath = "";
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ProfileActivity.this);
        LayoutInflater inflater = ProfileActivity.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.common_popup, null);
        final ImageView itemclose = (ImageView) dialogView.findViewById(R.id.itemclose);
        final CustomFontEditText name = (CustomFontEditText) dialogView.findViewById(R.id.name);
        final CustomFontEditText description = (CustomFontEditText) dialogView.findViewById(R.id.description);
        final LinearLayout imagelin = (LinearLayout) dialogView.findViewById(R.id.imglin);
        final CircleImageView image = (CircleImageView) dialogView.findViewById(R.id.image);
        final CustomFontTextView imagetxt = (CustomFontTextView) dialogView.findViewById(R.id.imagetxt);
        CustomFontTextView submit = (CustomFontTextView) dialogView.findViewById(R.id.r_submittxt);
        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();
        b.setCancelable(false);
        itemclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.cancel();
            }
        });
        final CustomFontTextView itemtittle = (CustomFontTextView) dialogView.findViewById(R.id.itemtittle);
        itemtittle.setText(tittle);
        if (position != -1) {
            ArrayList<String> data = dbProfile.getData(vrpId, "farmmachine", conatct);
            try {
                JSONObject jsonObject = new JSONObject(data.get(3));
                name.setText(jsonObject.getString("name"));
                description.setText(jsonObject.getString("description"));
                Glide.with(ProfileActivity.this).load(jsonObject.getString("image"))
                        .centerCrop()
                        .dontAnimate()
                        .thumbnail(0.5f)
                        .placeholder(R.drawable.profile)
                        .into(image);
                imagePath = jsonObject.getString("image");
                imagetxt.setText("update image");
            } catch (JSONException e) {
                Log.d("xxxxxxxxx", e.toString());
                Toast.makeText(getApplicationContext(), "went wrong", Toast.LENGTH_SHORT).show();
            }
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().length() <= 0 || description.getText().toString().length() <= 0) {
                    Toast.makeText(getApplicationContext(), "Enter all fileds", Toast.LENGTH_SHORT).show();
                } else {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("name", name.getText().toString());
                        jsonObject.put("description", description.getText().toString());
                        jsonObject.put("image", imagePath);
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "went wrong", Toast.LENGTH_SHORT).show();
                    }
                    if (position == -1) {
                        registerProduct(vrpId, "farmmachine", name.getText().toString()
                                , name.getText().toString(), jsonObject.toString(), false, b, position);
                    } else {
                        registerProduct(vrpId, "farmmachine", conatct
                                , name.getText().toString(), jsonObject.toString(), true, b, position);
                    }
                }
                b.cancel();
            }
        });
        imagetxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickSetup setup = new PickSetup();
                PickImageDialog.build(setup).setOnPickResult(new IPickResult() {
                    @Override
                    public void onPickResult(PickResult pickResult) {
                        Glide.with(ProfileActivity.this).load(pickResult.getUri())
                                .centerCrop()
                                .dontAnimate()
                                .thumbnail(0.5f)
                                .placeholder(R.drawable.profile)
                                .into(image);
                        imagePath = pickResult.getUri().toString();
                        imagetxt.setText(pickResult.getUri().toString());
                    }
                })
                        .show(ProfileActivity.this);
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickSetup setup = new PickSetup();
                PickImageDialog.build(setup).setOnPickResult(new IPickResult() {
                    @Override
                    public void onPickResult(PickResult pickResult) {
                        Glide.with(ProfileActivity.this).load(pickResult.getUri())
                                .centerCrop()
                                .dontAnimate()
                                .thumbnail(0.5f)
                                .placeholder(R.drawable.profile)
                                .into(image);
                        imagePath = pickResult.getUri().toString();
                        imagetxt.setText(pickResult.getUri().toString());
                    }
                })
                        .show(ProfileActivity.this);
            }
        });
        b.show();
    }

    public void addBank(final int position, final String conatct, String tittle) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ProfileActivity.this);
        LayoutInflater inflater = ProfileActivity.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_bank_popup, null);
        final CustomFontEditText nametxt = (CustomFontEditText) dialogView.findViewById(R.id.name);
        final CustomFontEditText accounttxt = (CustomFontEditText) dialogView.findViewById(R.id.account);
        final CustomFontEditText ifscnumbertxt = (CustomFontEditText) dialogView.findViewById(R.id.ifscnumber);
        final CustomFontTextView submittxt = (CustomFontTextView) dialogView.findViewById(R.id.r_submittxt);
        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();
        b.setCancelable(false);
        final CustomFontTextView itemtittle = (CustomFontTextView) dialogView.findViewById(R.id.itemtittle);
        itemtittle.setText(tittle);
        final ImageView itemclose = (ImageView) dialogView.findViewById(R.id.itemclose);
        itemclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.cancel();
            }
        });

        if (position != -1) {
            ArrayList<String> data = dbProfile.getData(vrpId, "bank", conatct);
            try {
                JSONObject jsonObject = new JSONObject(data.get(3));
                nametxt.setText(jsonObject.getString("bankname"));
                accounttxt.setText(jsonObject.getString("accno"));
                ifscnumbertxt.setText(jsonObject.getString("ifsc"));
            } catch (JSONException e) {
                Log.d("xxxxxxxxx", e.toString());
                Toast.makeText(getApplicationContext(), "went wrong", Toast.LENGTH_SHORT).show();
            }
        }
        submittxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nametxt.getText().toString().length() <= 0 || accounttxt.getText().toString().length() <= 0 ||
                        ifscnumbertxt.getText().toString().length() <= 0) {
                    Toast.makeText(getApplicationContext(), "Enter all fileds", Toast.LENGTH_SHORT).show();
                } else {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("accno", accounttxt.getText().toString());
                        jsonObject.put("bankname", nametxt.getText().toString());
                        jsonObject.put("ifsc", ifscnumbertxt.getText().toString());
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "went wrong", Toast.LENGTH_SHORT).show();
                    }
                    if (position == -1) {
                        registerProduct(vrpId, "bank", accounttxt.getText().toString()
                                , accounttxt.getText().toString(), jsonObject.toString(), false, b, position);
                    } else {
                        registerProduct(vrpId, "bank", conatct
                                , accounttxt.getText().toString(), jsonObject.toString(), true, b, position);
                    }
                }
                b.cancel();
            }
        });
        b.show();
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
                        if (mname.equals("bank")) {
                            JSONObject jsonObject = new JSONObject(mdata.toString());
                            if (!mupdate) {
                                Plot plot = new Plot("", jsonObject.getString("accno"),
                                        "f", jsonObject.getString("ifsc"), null);
                                bankList.add(plot);
                                mRecyclerAdapterbank.notifyData(bankList);
                                dbProfile.addData(vrpId, "bank", contact, mdata);
                            } else {
                                bankList.get(mposition).setPlotname(jsonObject.getString("accno"));
                                bankList.get(mposition).setPlotarea(jsonObject.getString("ifsc"));
                                dbProfile.updatedata(vrpId, "bank", moldcontact, contact, mdata);
                                mRecyclerAdapterbank.notifyData(bankList);
                            }
                        } else if (mname.equals("crop")) {
                            JSONObject jsonObject = new JSONObject(mdata.toString());
                            if (!mupdate) {
                                Plot plot = new Plot("", jsonObject.getString("season"),
                                        jsonObject.getString("image"), jsonObject.getString("year"), null);
                                cropList.add(plot);
                                mRecyclerAdaptercrop.notifyData(cropList);
                                dbProfile.addData(vrpId, "crop", contact, mdata);
                            } else {
                                cropList.get(mposition).setPlotname(jsonObject.getString("season"));
                                cropList.get(mposition).setPlotarea(jsonObject.getString("year"));
                                cropList.get(mposition).setPlotimage(jsonObject.getString("image"));
                                dbProfile.updatedata(vrpId, "crop", moldcontact, contact, mdata);
                                mRecyclerAdaptercrop.notifyData(cropList);
                            }
                        } else if (mname.equals("animal")) {
                            JSONObject jsonObject = new JSONObject(mdata.toString());
                            if (!mupdate) {
                                Plot plot = new Plot("", jsonObject.getString("name"),
                                        jsonObject.getString("image"), jsonObject.getString("description"), null);
                                animalList.add(plot);
                                mRecyclerAdapteranimal.notifyData(animalList);
                                dbProfile.addData(vrpId, "animal", contact, mdata);
                            } else {
                                animalList.get(mposition).setPlotname(jsonObject.getString("name"));
                                animalList.get(mposition).setPlotarea(jsonObject.getString("description"));
                                animalList.get(mposition).setPlotimage(jsonObject.getString("image"));
                                dbProfile.updatedata(vrpId, "animal", moldcontact, contact, mdata);
                                mRecyclerAdapteranimal.notifyData(animalList);
                            }
                        } else if (mname.equals("farmmachine")) {
                            JSONObject jsonObject = new JSONObject(mdata.toString());
                            if (!mupdate) {
                                Plot plot = new Plot("", jsonObject.getString("name"),
                                        jsonObject.getString("image"), jsonObject.getString("description"), null);
                                farmmachineList.add(plot);
                                mRecyclerAdapterfarmmachine.notifyData(farmmachineList);
                                dbProfile.addData(vrpId, "farmmachine", contact, mdata);
                            } else {
                                farmmachineList.get(mposition).setPlotname(jsonObject.getString("name"));
                                farmmachineList.get(mposition).setPlotarea(jsonObject.getString("description"));
                                farmmachineList.get(mposition).setPlotimage(jsonObject.getString("image"));
                                dbProfile.updatedata(vrpId, "farmmachine", moldcontact, contact, mdata);
                                mRecyclerAdapterfarmmachine.notifyData(farmmachineList);
                            }
                        } else if (mname.equals("labour")) {
                            JSONObject jsonObject = new JSONObject(mdata.toString());
                            if (!mupdate) {
                                Plot plot = new Plot("", jsonObject.getString("name"),
                                        jsonObject.getString("image"), jsonObject.getString("description"), null);
                                labourList.add(plot);
                                mRecyclerAdapterlabour.notifyData(labourList);
                                dbProfile.addData(vrpId, "labour", contact, mdata);
                            } else {
                                labourList.get(mposition).setPlotname(jsonObject.getString("name"));
                                labourList.get(mposition).setPlotarea(jsonObject.getString("description"));
                                labourList.get(mposition).setPlotimage(jsonObject.getString("image"));
                                dbProfile.updatedata(vrpId, "labour", moldcontact, contact, mdata);
                                mRecyclerAdapterlabour.notifyData(labourList);
                            }
                        } else if (mname.equals("soilresources")) {
                            JSONObject jsonObject = new JSONObject(mdata.toString());
                            if (!mupdate) {
                                Plot plot = new Plot("", jsonObject.getString("name"),
                                        jsonObject.getString("image"), jsonObject.getString("description"), null);
                                soilresourcesList.add(plot);
                                mRecyclerAdaptersoilresources.notifyData(soilresourcesList);
                                dbProfile.addData(vrpId, "soilresources", contact, mdata);
                            } else {
                                soilresourcesList.get(mposition).setPlotname(jsonObject.getString("name"));
                                soilresourcesList.get(mposition).setPlotarea(jsonObject.getString("description"));
                                soilresourcesList.get(mposition).setPlotimage(jsonObject.getString("image"));
                                dbProfile.updatedata(vrpId, "soilresources", moldcontact, contact, mdata);
                                mRecyclerAdaptersoilresources.notifyData(soilresourcesList);
                            }
                        } else if (mname.equals("waterresources")) {
                            JSONObject jsonObject = new JSONObject(mdata.toString());
                            if (!mupdate) {
                                Plot plot = new Plot("", jsonObject.getString("name"),
                                        jsonObject.getString("image"), jsonObject.getString("description"), null);
                                waterresourcesList.add(plot);
                                mRecyclerAdapterwaterresources.notifyData(waterresourcesList);
                                dbProfile.addData(vrpId, "waterresources", contact, mdata);
                            } else {
                                waterresourcesList.get(mposition).setPlotname(jsonObject.getString("name"));
                                waterresourcesList.get(mposition).setPlotarea(jsonObject.getString("description"));
                                waterresourcesList.get(mposition).setPlotimage(jsonObject.getString("image"));
                                dbProfile.updatedata(vrpId, "waterresources", moldcontact, contact, mdata);
                                mRecyclerAdapterwaterresources.notifyData(waterresourcesList);
                            }
                        } else if (mname.equals("toilets")) {
                            JSONObject jsonObject = new JSONObject(mdata.toString());
                            if (!mupdate) {
                                Plot plot = new Plot("", jsonObject.getString("name"),
                                        jsonObject.getString("image"), jsonObject.getString("description"), null);
                                toiletsList.add(plot);
                                mRecyclerAdaptertoilets.notifyData(toiletsList);
                                dbProfile.addData(vrpId, "toilets", contact, mdata);
                            } else {
                                toiletsList.get(mposition).setPlotname(jsonObject.getString("name"));
                                toiletsList.get(mposition).setPlotarea(jsonObject.getString("description"));
                                toiletsList.get(mposition).setPlotimage(jsonObject.getString("image"));
                                dbProfile.updatedata(vrpId, "toilets", moldcontact, contact, mdata);
                                mRecyclerAdaptertoilets.notifyData(toiletsList);
                            }
                        } else if (mname.equals("electricity")) {
                            JSONObject jsonObject = new JSONObject(mdata.toString());
                            if (!mupdate) {
                                Plot plot = new Plot("", jsonObject.getString("name"),
                                        jsonObject.getString("image"), jsonObject.getString("description"), null);
                                electricityList.add(plot);
                                mRecyclerAdapterelectricity.notifyData(electricityList);
                                dbProfile.addData(vrpId, "electricity", contact, mdata);
                            } else {
                                electricityList.get(mposition).setPlotname(jsonObject.getString("name"));
                                electricityList.get(mposition).setPlotarea(jsonObject.getString("description"));
                                electricityList.get(mposition).setPlotimage(jsonObject.getString("image"));
                                dbProfile.updatedata(vrpId, "electricity", moldcontact, contact, mdata);
                                mRecyclerAdapterelectricity.notifyData(electricityList);
                            }
                        } else if (mname.equals("financialinstitutions")) {
                            JSONObject jsonObject = new JSONObject(mdata.toString());
                            if (!mupdate) {
                                Plot plot = new Plot("", jsonObject.getString("name"),
                                        jsonObject.getString("image"), jsonObject.getString("description"), null);
                                financialinstitutionsList.add(plot);
                                mRecyclerAdapterfinancialinstitutions.notifyData(financialinstitutionsList);
                                dbProfile.addData(vrpId, "financialinstitutions", contact, mdata);
                            } else {
                                financialinstitutionsList.get(mposition).setPlotname(jsonObject.getString("name"));
                                financialinstitutionsList.get(mposition).setPlotarea(jsonObject.getString("description"));
                                financialinstitutionsList.get(mposition).setPlotimage(jsonObject.getString("image"));
                                dbProfile.updatedata(vrpId, "financialinstitutions", moldcontact, contact, mdata);
                                mRecyclerAdapterfinancialinstitutions.notifyData(financialinstitutionsList);
                            }
                        } else if (mname.equals("schools")) {
                            JSONObject jsonObject = new JSONObject(mdata.toString());
                            if (!mupdate) {
                                Plot plot = new Plot("", jsonObject.getString("name"),
                                        jsonObject.getString("image"), jsonObject.getString("description"), null);
                                schoolsList.add(plot);
                                mRecyclerAdapterschools.notifyData(schoolsList);
                                dbProfile.addData(vrpId, "schools", contact, mdata);
                            } else {
                                schoolsList.get(mposition).setPlotname(jsonObject.getString("name"));
                                schoolsList.get(mposition).setPlotarea(jsonObject.getString("description"));
                                schoolsList.get(mposition).setPlotimage(jsonObject.getString("image"));
                                dbProfile.updatedata(vrpId, "schools", moldcontact, contact, mdata);
                                mRecyclerAdapterschools.notifyData(schoolsList);
                            }
                        } else if (mname.equals("hospitals")) {
                            JSONObject jsonObject = new JSONObject(mdata.toString());
                            if (!mupdate) {
                                Plot plot = new Plot("", jsonObject.getString("name"),
                                        jsonObject.getString("image"), jsonObject.getString("description"), null);
                                hospitalsList.add(plot);
                                mRecyclerAdapterhospitals.notifyData(hospitalsList);
                                dbProfile.addData(vrpId, "hospitals", contact, mdata);
                            } else {
                                hospitalsList.get(mposition).setPlotname(jsonObject.getString("name"));
                                hospitalsList.get(mposition).setPlotarea(jsonObject.getString("description"));
                                hospitalsList.get(mposition).setPlotimage(jsonObject.getString("image"));
                                dbProfile.updatedata(vrpId, "hospitals", moldcontact, contact, mdata);
                                mRecyclerAdapterhospitals.notifyData(hospitalsList);
                            }
                        } else if (mname.equals("shops")) {
                            JSONObject jsonObject = new JSONObject(mdata.toString());
                            if (!mupdate) {
                                Plot plot = new Plot("", jsonObject.getString("name"),
                                        jsonObject.getString("image"), jsonObject.getString("description"), null);
                                shopsList.add(plot);
                                mRecyclerAdaptershops.notifyData(shopsList);
                                dbProfile.addData(vrpId, "shops", contact, mdata);
                            } else {
                                shopsList.get(mposition).setPlotname(jsonObject.getString("name"));
                                shopsList.get(mposition).setPlotarea(jsonObject.getString("description"));
                                shopsList.get(mposition).setPlotimage(jsonObject.getString("image"));
                                dbProfile.updatedata(vrpId, "shops", moldcontact, contact, mdata);
                                mRecyclerAdaptershops.notifyData(shopsList);
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
                        prepareData(false);
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


    public void qrcode(final int position, final String contact, final String tittle) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ProfileActivity.this);
        LayoutInflater inflater = ProfileActivity.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.qrcode_activity, null);
        final ImageView qrimage = (ImageView) dialogView.findViewById(R.id.qrimage);
        final CustomFontTextView submittxt = (CustomFontTextView) dialogView.findViewById(R.id.r_submittxt);
        final CustomFontTextView itemtittle = (CustomFontTextView) dialogView.findViewById(R.id.itemtittle);
        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();
        b.setCancelable(false);
        itemtittle.setText(tittle);
        final ImageView itemclose = (ImageView) dialogView.findViewById(R.id.itemclose);
        final ImageView itemshare = (ImageView) dialogView.findViewById(R.id.itemshare);
        itemshare.setVisibility(View.GONE);
        itemclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.cancel();
            }
        });

        if (position != -1) {
            ArrayList<String> data = dbProfile.getData(vrpId, "bank", contact);
            try {
                JSONObject jsonObject = new JSONObject(data.get(3));
                jsonObject.put("voname", vrp.getVoname());
                QrGenerator.Builder builder = new QrGenerator.Builder();
                builder.content(jsonObject.toString());
                builder.qrSize(250);
                QrGenerator qrGenerator = new QrGenerator(builder);
                qrimage.setImageBitmap(qrGenerator.createQRCode());
                itemshare.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                Log.d("xxxxxxxxx", e.toString());
                Toast.makeText(getApplicationContext(), "went wrong", Toast.LENGTH_SHORT).show();
            }
        }
        itemshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView content = (ImageView) dialogView.findViewById(R.id.qrimage);
                content.setDrawingCacheEnabled(true);
                Bitmap bitmap = content.getDrawingCache();
                File root = Environment.getExternalStorageDirectory();
                File cachePath = new File(root.getAbsolutePath() + "/DCIM/Camera/image.jpg");
                try {
                    cachePath.createNewFile();
                    FileOutputStream ostream = new FileOutputStream(cachePath);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
                    ostream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/*");
                share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(cachePath));
                startActivity(Intent.createChooser(share, "Share via"));
            }
        });
        b.show();
    }


    public void prepareData(boolean fromcloud) {
        if (checkInternetConnection() && fromcloud) {
            getAllProduct();
        } else {
            List<ArrayList<String>> allData = dbProfile.getAllData();
            for (int i = 0; i < allData.size(); i++) {
                ArrayList<String> data = allData.get(i);
                if (data.get(1).equals("bank")) {
                    try {
                        JSONObject dataObject = new JSONObject(data.get(3).toString());
                        Plot plot = new Plot("", dataObject.getString("accno"), "f", dataObject.getString("ifsc"), null);
                        bankList.add(plot);
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "went wrong", Toast.LENGTH_SHORT).show();
                    }
                } else if (data.get(1).equals("crop")) {
                    try {
                        JSONObject dataObject = new JSONObject(data.get(3).toString());
                        Plot plot = new Plot("", dataObject.getString("cropname"),
                                dataObject.getString("image"), dataObject.getString("year"), null);
                        cropList.add(plot);
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "went wrong", Toast.LENGTH_SHORT).show();
                    }
                } else if (data.get(1).equals("animal")) {
                    try {
                        JSONObject dataObject = new JSONObject(data.get(3).toString());
                        Plot plot = new Plot("", dataObject.getString("name"),
                                dataObject.getString("image"), dataObject.getString("description"), null);
                        animalList.add(plot);
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "went wrong", Toast.LENGTH_SHORT).show();
                    }
                } else if (data.get(1).equals("farmmachine")) {
                    try {
                        JSONObject dataObject = new JSONObject(data.get(3).toString());
                        Plot plot = new Plot("", dataObject.getString("name"),
                                dataObject.getString("image"), dataObject.getString("description"), null);
                        farmmachineList.add(plot);
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "went wrong", Toast.LENGTH_SHORT).show();
                    }
                } else if (data.get(1).equals("labour")) {
                    try {
                        JSONObject dataObject = new JSONObject(data.get(3).toString());
                        Plot plot = new Plot("", dataObject.getString("name"),
                                dataObject.getString("image"), dataObject.getString("description"), null);
                        labourList.add(plot);
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "went wrong", Toast.LENGTH_SHORT).show();
                    }
                } else if (data.get(1).equals("soilresources")) {
                    try {
                        JSONObject dataObject = new JSONObject(data.get(3).toString());
                        Plot plot = new Plot("", dataObject.getString("name"),
                                dataObject.getString("image"), dataObject.getString("description"), null);
                        soilresourcesList.add(plot);
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "went wrong", Toast.LENGTH_SHORT).show();
                    }
                } else if (data.get(1).equals("toilets")) {
                    try {
                        JSONObject dataObject = new JSONObject(data.get(3).toString());
                        Plot plot = new Plot("", dataObject.getString("name"),
                                dataObject.getString("image"), dataObject.getString("description"), null);
                        toiletsList.add(plot);
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "went wrong", Toast.LENGTH_SHORT).show();
                    }
                } else if (data.get(1).equals("waterresources")) {
                    try {
                        JSONObject dataObject = new JSONObject(data.get(3).toString());
                        Plot plot = new Plot("", dataObject.getString("name"),
                                dataObject.getString("image"), dataObject.getString("description"), null);
                        waterresourcesList.add(plot);
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "went wrong", Toast.LENGTH_SHORT).show();
                    }
                } else if (data.get(1).equals("electricity")) {
                    try {
                        JSONObject dataObject = new JSONObject(data.get(3).toString());
                        Plot plot = new Plot("", dataObject.getString("name"),
                                dataObject.getString("image"), dataObject.getString("description"), null);
                        electricityList.add(plot);
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "went wrong", Toast.LENGTH_SHORT).show();
                    }
                } else if (data.get(1).equals("financialinstitutions")) {
                    try {
                        JSONObject dataObject = new JSONObject(data.get(3).toString());
                        Plot plot = new Plot("", dataObject.getString("name"),
                                dataObject.getString("image"), dataObject.getString("description"), null);
                        financialinstitutionsList.add(plot);
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "went wrong", Toast.LENGTH_SHORT).show();
                    }
                } else if (data.get(1).equals("schools")) {
                    try {
                        JSONObject dataObject = new JSONObject(data.get(3).toString());
                        Plot plot = new Plot("", dataObject.getString("name"),
                                dataObject.getString("image"), dataObject.getString("description"), null);
                        schoolsList.add(plot);
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "went wrong", Toast.LENGTH_SHORT).show();
                    }
                } else if (data.get(1).equals("hospitals")) {
                    try {
                        JSONObject dataObject = new JSONObject(data.get(3).toString());
                        Plot plot = new Plot("", dataObject.getString("name"),
                                dataObject.getString("image"), dataObject.getString("description"), null);
                        hospitalsList.add(plot);
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "went wrong", Toast.LENGTH_SHORT).show();
                    }
                } else if (data.get(1).equals("shops")) {
                    try {
                        JSONObject dataObject = new JSONObject(data.get(3).toString());
                        Plot plot = new Plot("", dataObject.getString("name"),
                                dataObject.getString("image"), dataObject.getString("description"), null);
                        shopsList.add(plot);
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        mRecyclerAdapterbank.notifyData(bankList);
        mRecyclerAdaptercrop.notifyData(cropList);
        mRecyclerAdapteranimal.notifyData(animalList);
        mRecyclerAdapterfarmmachine.notifyData(farmmachineList);
        mRecyclerAdapterlabour.notifyData(labourList);
        mRecyclerAdaptersoilresources.notifyData(soilresourcesList);
        mRecyclerAdapterwaterresources.notifyData(waterresourcesList);
        mRecyclerAdaptertoilets.notifyData(toiletsList);
        mRecyclerAdapterelectricity.notifyData(electricityList);
        mRecyclerAdapterfinancialinstitutions.notifyData(financialinstitutionsList);
        mRecyclerAdapterschools.notifyData(schoolsList);
        mRecyclerAdapterhospitals.notifyData(hospitalsList);
        mRecyclerAdaptershops
                .notifyData(shopsList);
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
        MarkerOptions marker = new MarkerOptions()
                .position(new LatLng(Double.parseDouble(vrp.getGeotag().split(",")[0]), Double.parseDouble(vrp.getGeotag().split(",")[1])))
                .title(vrp.getVoname())
                .snippet(vrp.getGeotag().split(",")[0].substring(0, 7) + "," + vrp.getGeotag().split(",")[1].substring(0, 7));
        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        googleMap.addMarker(marker).showInfoWindow();
        CameraPosition cameraPosition = new CameraPosition.Builder().target(
                new LatLng(Double.parseDouble(vrp.getGeotag().split(",")[0]), Double.parseDouble(vrp.getGeotag().split(",")[1]))).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}
