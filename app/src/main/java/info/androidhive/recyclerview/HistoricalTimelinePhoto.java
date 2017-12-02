package info.androidhive.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import info.androidhive.recyclerview.db.DbImage;

/**
 * Created by Gopal on 08-11-2017.
 */

public class HistoricalTimelinePhoto extends AppCompatActivity {
    private MultiSnapRecyclerView mRecyclerViewhistory;
    private ProfileAdapter mRecyclerAdapterhistory;
    private ArrayList<Plot> historyList = new ArrayList<>();
    private String imagePath;
    GPSTracker gps;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String tittle = "tittleKey";
    public static final String filename = "filenameKey";
    public static final String vrpid = "vrpidKey";
    String tittleString;
    String vrpString;
    DbImage dbImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historical_timeline_photo);
        gps = new GPSTracker(HistoricalTimelinePhoto.this);
        dbImage = new DbImage(this);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(tittle)) {
            tittleString = sharedpreferences.getString(tittle, "").trim();
            vrpString = sharedpreferences.getString(vrpid, "").trim();
        }

        ImageView addimage = (ImageView) findViewById(R.id.historyaddimage);
        addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addImage(-1, "", "Add Image");
            }
        });
        mRecyclerViewhistory = (MultiSnapRecyclerView) findViewById(R.id.historylist);
        mRecyclerAdapterhistory = new ProfileAdapter(HistoricalTimelinePhoto.this, historyList);
        final LinearLayoutManager thirdManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewhistory.setLayoutManager(thirdManager);
        mRecyclerViewhistory.setAdapter(mRecyclerAdapterhistory);
        mRecyclerViewhistory.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerViewhistory, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                addImage(position, historyList.get(position).getTag(), "Update images");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        TextView submit = (TextView) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent io = new Intent(HistoricalTimelinePhoto.this, AdditionalInformation.class);
                startActivity(io);
            }
        });

        prepareData();
    }

    private void prepareData() {
        historyList = new ArrayList<>();
        List<String> history = dbImage.getAllData(vrpString, tittleString,getResources().getString(R.string.history));
        for (int i = 0; i < history.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject = new JSONObject(history.get(i));
                Plot plot = new Plot(jsonObject.getString("geotag"), jsonObject.getString("name"),
                        jsonObject.getString("image"), jsonObject.getString("description"), null);
                historyList.add(plot);
            } catch (JSONException e) {
                Log.d("HistoricalTimelinePhoto", e.toString());
            }
        }
        mRecyclerAdapterhistory.notifyData(historyList);
    }


    public void addImage(final int position, final String name, String tittle) {
        imagePath = "";
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(HistoricalTimelinePhoto.this);
        LayoutInflater inflater = HistoricalTimelinePhoto.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.addimage_popup, null);
        final ImageView itemclose = (ImageView) dialogView.findViewById(R.id.itemclose);
        final CustomFontEditText description = (CustomFontEditText) dialogView.findViewById(R.id.description);
        final LinearLayout imagelin = (LinearLayout) dialogView.findViewById(R.id.imglin);
        final CircleImageView image = (CircleImageView) dialogView.findViewById(R.id.image);
        final CustomFontEditText geotag = (CustomFontEditText) dialogView.findViewById(R.id.geotag);
        final ImageView georefresh = (ImageView) dialogView.findViewById(R.id.refresh);
        final CustomFontEditText imagetxt = (CustomFontEditText) dialogView.findViewById(R.id.imagetxt);
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
            String data = dbImage.getData(vrpString, tittleString, getResources().getString(R.string.history), name);
            try {
                JSONObject jsonObject = new JSONObject(data);
                geotag.setText(jsonObject.getString("geotag"));
                description.setText(jsonObject.getString("description"));
                Glide.with(HistoricalTimelinePhoto.this).load(jsonObject.getString("image"))
                        .centerCrop()
                        .dontAnimate()
                        .thumbnail(0.5f)
                        .placeholder(R.drawable.profile)
                        .into(image);
                imagePath = jsonObject.getString("image");
                imagetxt.setText(jsonObject.getString("name"));
            } catch (JSONException e) {
                Log.d("HistoricalTimelinePhoto", e.toString());
            }
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (description.getText().toString().length() <= 0
                        || imagetxt.getText().toString().length() <= 0
                        || geotag.getText().toString().length() <= 0
                        || description.getText().toString().length() <= 0) {
                    Toast.makeText(getApplicationContext(), "Enter all fileds", Toast.LENGTH_SHORT).show();
                } else {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("geotag", geotag.getText().toString());
                        jsonObject.put("description", description.getText().toString());
                        jsonObject.put("image", imagePath);
                        jsonObject.put("name", imagetxt.getText().toString());
                        if (position == -1) {
                            dbImage.addData(vrpString, tittleString, getResources().getString(R.string.history)
                                    , geotag.getText().toString(), jsonObject.toString());
                            Plot plot = new Plot(jsonObject.getString("geotag"), jsonObject.getString("name"),
                                    jsonObject.getString("image"), jsonObject.getString("description"), null);
                            historyList.add(plot);
                            mRecyclerAdapterhistory.notifyData(historyList);
                        } else {
                            dbImage.updatedata(vrpString, tittleString, getResources().getString(R.string.history)
                                    , name, geotag.getText().toString(), jsonObject.toString());
                            historyList.get(position).setPlotname(imagetxt.getText().toString());
                            historyList.get(position).setTag(geotag.getText().toString());
                            historyList.get(position).setPlotarea(description.getText().toString());
                            historyList.get(position).setPlotimage(imagePath);
                            mRecyclerAdapterhistory.notifyData(historyList);
                        }
                    } catch (JSONException e) {
                        Log.d("HistoricalTimelinePhoto", e.toString());
                    }
                }
                b.cancel();
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickSetup setup = new PickSetup();
                PickImageDialog.build(setup).setOnPickResult(new IPickResult() {
                    @Override
                    public void onPickResult(PickResult pickResult) {
                        Glide.with(HistoricalTimelinePhoto.this).load(pickResult.getUri())
                                .centerCrop()
                                .dontAnimate()
                                .thumbnail(0.5f)
                                .placeholder(R.drawable.profile)
                                .into(image);
                        imagePath = pickResult.getUri().toString();
                    }
                })
                        .show(HistoricalTimelinePhoto.this);
            }
        });

        georefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check if GPS enabled
                gps = new GPSTracker(HistoricalTimelinePhoto.this);
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
            gps = new GPSTracker(HistoricalTimelinePhoto.this);
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
        b.show();
    }

}
