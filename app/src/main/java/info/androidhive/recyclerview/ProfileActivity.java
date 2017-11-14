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

        bankDetail();
        cropDetail();
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

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "went wrong", Toast.LENGTH_SHORT).show();
                    }
                    if (position == -1) {
                        registerProduct(vrpId, "crop", year.getText().toString()
                                , year.getText().toString(), jsonObject.toString(), false, b, position);
                    } else {
                        registerProduct(vrpId, "crop", conatct
                                , year.getText().toString(), jsonObject.toString(), true, b, position);
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
//                        if (mname.equals("leadfarmer")) {
//                            JSONObject jsonObject = new JSONObject(mdata.toString());
//                            if (!mupdate) {
//                                Plot plot = new Plot("", jsonObject.getString("name"),
//                                        "f", jsonObject.getString("contact"), null);
//                                farmerList.add(plot);
//                                mRecyclerAdapterFarmer.notifyData(farmerList);
//                                dbProfile.addData(farmerId, "leadfarmer", contact, mdata);
//                            } else {
//                                farmerList.get(mposition).setPlotname(jsonObject.getString("name"));
//                                farmerList.get(mposition).setPlotarea(jsonObject.getString("contact"));
//                                dbProfile.updatedata(farmerId, "leadfarmer", moldcontact, contact, mdata);
//                                mRecyclerAdapterFarmer.notifyData(farmerList);
//                            }
//                        } else if (mname.equals("expertsupport")) {
//                            JSONObject jsonObject = new JSONObject(mdata.toString());
//                            if (!mupdate) {
//                                Plot plot = new Plot("", jsonObject.getString("name"),
//                                        "f", jsonObject.getString("contact"), null);
//                                expertList.add(plot);
//                                mRecyclerAdapterExpert.notifyData(expertList);
//                                dbProfile.addData(farmerId, "expertsupport", contact, mdata);
//                            } else {
//                                expertList.get(mposition).setPlotname(jsonObject.getString("name"));
//                                expertList.get(mposition).setPlotarea(jsonObject.getString("contact"));
//                                dbProfile.updatedata(farmerId, "expertsupport", moldcontact, contact, mdata);
//                                mRecyclerAdapterExpert.notifyData(expertList);
//                            }
//                        } else if (mname.equals("agroservices")) {
//                            JSONObject jsonObject = new JSONObject(mdata.toString());
//                            if (!mupdate) {
//                                Plot plot = new Plot("", jsonObject.getString("name"),
//                                        "f", jsonObject.getString("contact"), null);
//                                agrolist.add(plot);
//                                mRecyclerAdapteAgro.notifyData(agrolist);
//                                dbProfile.addData(farmerId, "agroservices", contact, mdata);
//                            } else {
//                                agrolist.get(mposition).setPlotname(jsonObject.getString("name"));
//                                agrolist.get(mposition).setPlotarea(jsonObject.getString("contact"));
//                                dbProfile.updatedata(farmerId, "agroservices", moldcontact, contact, mdata);
//                                mRecyclerAdapteAgro.notifyData(agrolist);
//                            }
//                        }
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
                                dbProfile.updatedata(vrpId, "agent", moldcontact, contact, mdata);
                                mRecyclerAdaptercrop.notifyData(cropList);
                            }
                        }
//                        else if (mname.equals("service")) {
//                            JSONObject jsonObject = new JSONObject(mdata.toString());
//                            if (!mupdate) {
//                                Plot plot = new Plot("", jsonObject.getString("name"),
//                                        jsonObject.getString("image"), jsonObject.getString("contact"), null);
//                                serviceList.add(plot);
//                                mRecyclerAdapterService.notifyData(serviceList);
//                                dbProfile.addData(farmerId, "service", contact, mdata);
//                            } else {
//                                serviceList.get(mposition).setPlotname(jsonObject.getString("name"));
//                                serviceList.get(mposition).setPlotarea(jsonObject.getString("contact"));
//                                serviceList.get(mposition).setPlotimage(jsonObject.getString("image"));
//                                dbProfile.updatedata(farmerId, "service", moldcontact, contact, mdata);
//                                mRecyclerAdapterService.notifyData(serviceList);
//                            }
//                        }
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
                        Plot plot = new Plot("", dataObject.getString("season"), dataObject.getString("image"), dataObject.getString("year"), null);
                        cropList.add(plot);
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
// else if (data.get(1).equals("service")) {
//                    try {
//                        JSONObject dataObject = new JSONObject(data.get(3).toString());
//                        Plot plot = new Plot("", dataObject.getString("name"), dataObject.getString("image"), dataObject.getString("contact"), null);
//                        serviceList.add(plot);
//                    } catch (JSONException e) {
//                        Toast.makeText(getApplicationContext(), "went wrong", Toast.LENGTH_SHORT).show();
//                    }
//                } else if (data.get(1).equals("leadfarmer")) {
//                    try {
//                        JSONObject dataObject = new JSONObject(data.get(3).toString());
//                        Plot plot = new Plot("", dataObject.getString("name"), "noimage", dataObject.getString("contact"), null);
//                        farmerList.add(plot);
//                    } catch (JSONException e) {
//                        Toast.makeText(getApplicationContext(), "went wrong", Toast.LENGTH_SHORT).show();
//                    }
//                } else if (data.get(1).equals("expertsupport")) {
//                    try {
//                        JSONObject dataObject = new JSONObject(data.get(3).toString());
//                        Plot plot = new Plot("", dataObject.getString("name"), "noimage", dataObject.getString("contact"), null);
//                        expertList.add(plot);
//                    } catch (JSONException e) {
//                        Toast.makeText(getApplicationContext(), "went wrong", Toast.LENGTH_SHORT).show();
//                    }
//                } else if (data.get(1).equals("agroservices")) {
//                    try {
//                        JSONObject dataObject = new JSONObject(data.get(3).toString());
//                        Plot plot = new Plot("", dataObject.getString("name"), "noimage", dataObject.getString("contact"), null);
//                        agrolist.add(plot);
//                    } catch (JSONException e) {
//                        Toast.makeText(getApplicationContext(), "went wrong", Toast.LENGTH_SHORT).show();
//                    }
//                }
            }
        }
        mRecyclerAdapterbank.notifyData(bankList);
        mRecyclerAdaptercrop.notifyData(cropList);
//        mRecyclerAdapteAgro.notifyData(agrolist);
//        mRecyclerAdapterExpert.notifyData(expertList);
//        mRecyclerAdapterFarmer.notifyData(farmerList);
//        mRecyclerAdapter.notifyData(agentList);
    }
}
