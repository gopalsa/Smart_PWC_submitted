package smart.cst.pwc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import smart.cst.pwc.app.AppConfig;
import smart.cst.pwc.app.HeaderFooterPageEvent;
import smart.cst.pwc.db.DbImage;
import smart.cst.pwc.db.DbReport;

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

    TextView toolreport;
    TextView additionalinfo;
    TextView observation;

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
        toolreport = (TextView) findViewById(R.id.toolreport);
        additionalinfo = (TextView) findViewById(R.id.additionalinfo);
        observation = (TextView) findViewById(R.id.observation);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_print, menu);
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
            StringBuilder printLists = new StringBuilder();
            printLists.append("Tool Report\n");
            if (toolreport.getText().toString() != null &&
                    toolreport.getText().toString().length() > 0) {
                printLists.append(toolreport.getText().toString()+"\n");
            } else {
                printLists.append("Tool Report shown here...\n");
            }
            printLists.append("Additional Information\n");
            if (additionalinfo.getText().toString() != null &&
                    additionalinfo.getText().toString().length() > 0) {
                printLists.append(additionalinfo.getText().toString()+"\n");
            } else {
                printLists.append("Additional Information shown here...\n");
            }
            printLists.append("Observation\n");
            if (observation.getText().toString() != null &&
                    observation.getText().toString().length() > 0) {
                printLists.append(observation.getText().toString()+"\n");
            } else {
                printLists.append("Observation shown here...\n");
            }
            printFunction(printLists.toString());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void printFunction(String strings) {

        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/PDF";
            File dir = new File(path);
            if (!dir.exists())
                dir.mkdirs();
            Log.d("PDFCreator", "PDF Path: " + path);
            File file = new File(dir, "demo" + ".pdf");
            FileOutputStream fOut = new FileOutputStream(file);
            Document document = new Document();
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
            AppConfig.addToolReport(document, strings);
            document.close();


        } catch (Error | Exception e) {
            e.printStackTrace();
        }

        Uri photoURI = FileProvider.getUriForFile(getApplicationContext(),
                getApplicationContext().getPackageName() + AppConfig.packageName+".provider",
                new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/PDF/" + "demo" + ".pdf"));

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(photoURI
                , "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);

    }

}
