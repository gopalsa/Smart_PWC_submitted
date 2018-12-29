package nec.cst.pra;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.TextView;

import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import nec.cst.pra.db.DbImage;
import nec.cst.pra.db.DbReport;

/**
 * Created by Gopal on 18-11-2017.
 */

public class FinalReport extends AppCompatActivity {

    DbReport dbReport;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String tittle = "tittleKey";
    public static final String vrpid = "vrpidKey";
    String tittleString;
    String vrpString;
    DbImage dbImage;
    private MultiSnapRecyclerView mRecyclerViewhistory;
    private ProfileAdapter mRecyclerAdapterhistory;
    private ArrayList<Plot> historyList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_report);
        dbReport = new DbReport(this);
        dbImage = new DbImage(this);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(tittle)) {
            tittleString = sharedpreferences.getString(tittle, "").trim();
            vrpString = sharedpreferences.getString(vrpid, "").trim();
        }
        getSupportActionBar().setTitle(tittleString);
        TextView toolreport = (TextView) findViewById(R.id.toolreport);
        TextView additionalinfo = (TextView) findViewById(R.id.additionalinfo);
        TextView observation = (TextView) findViewById(R.id.observation);

        final TextView date = (TextView) findViewById(R.id.date);
        final TextView time = (TextView) findViewById(R.id.time);
        final TextView fmalecount = (TextView) findViewById(R.id.fmalecount);
        final TextView ffemalecount = (TextView) findViewById(R.id.ffemalecount);
        final TextView pmalecount = (TextView) findViewById(R.id.pmalecount);
        final TextView pfemalecount = (TextView) findViewById(R.id.pfemalecount);
        try {
            String string = dbReport.getData(vrpString, tittleString, getResources().getString(R.string.attendance));
            if (string != null) {
                JSONObject attendanceObject = new JSONObject(string);
                date.setText(attendanceObject.getString("date"));
                time.setText(attendanceObject.getString("time"));
                fmalecount.setText(attendanceObject.getString("fmalecount"));
                ffemalecount.setText(attendanceObject.getString("ffemalecount"));
                pmalecount.setText(attendanceObject.getString("pmalecount"));
                pfemalecount.setText(attendanceObject.getString("pfemalecount"));
            }
        } catch (Exception e) {
            Log.d("Tool Report ", e.toString());
        }
        try {
            String string = dbReport.getData(vrpString, tittleString, getResources().getString(R.string.toolreport));
            if (string != null)
                toolreport.setText(string);
        } catch (Exception e) {
            Log.d("Tool Report ", e.toString());
        }
        try {
            String string = dbReport.getData(vrpString, tittleString, getResources().getString(R.string.additionalInfo));
            if (string != null)
                additionalinfo.setText(string);
        } catch (Exception e) {
            Log.d("Additional Info ", e.toString());
        }
        try {
            String string = dbReport.getData(vrpString, tittleString, getResources().getString(R.string.observation));
            if (string != null)
                observation.setText(string);
        } catch (Exception e) {
            Log.d("Observation ", e.toString());
        }


        mRecyclerViewhistory = (MultiSnapRecyclerView) findViewById(R.id.historylist);
        mRecyclerAdapterhistory = new ProfileAdapter(FinalReport.this, historyList);
        final LinearLayoutManager thirdManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewhistory.setLayoutManager(thirdManager);
        mRecyclerViewhistory.setAdapter(mRecyclerAdapterhistory);
        prepareData();
    }

    private void prepareData() {
        historyList = new ArrayList<>();
        List<String> history = dbImage.getAllData(vrpString, tittleString);
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

}
