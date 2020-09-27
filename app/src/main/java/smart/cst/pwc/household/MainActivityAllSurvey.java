package smart.cst.pwc.household;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import smart.cst.pwc.R;
import smart.cst.pwc.app.AppConfig;
import smart.cst.pwc.app.AppController;
import smart.cst.pwc.app.HeaderFooterPageEvent;


public class MainActivityAllSurvey extends AppCompatActivity implements OnSurveyClick {


    private ProgressDialog pDialog;
    private RecyclerView mastersList;
    private MasterAllSurveyAdapter mRecyclerAdapterMaster;
    private ArrayList<Mainbean> itemArrayList = new ArrayList<>();


    public static final String mypreference = "mypref";
    public static final String buSurveyerId = "buSurveyerIdKey";
    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_all_survey);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        getSupportActionBar().setTitle(" Survey");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        sharedpreferences = this.getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);


        FloatingActionButton addMaster = (FloatingActionButton) findViewById(R.id.addSurvey);
        addMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityAllSurvey.this, MainActivityHouseHold.class);
                startActivity(intent);
            }
        });
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        mastersList = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerAdapterMaster = new MasterAllSurveyAdapter(this, itemArrayList, this);
        final LinearLayoutManager thirdManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mastersList.setLayoutManager(thirdManager);
        mastersList.setAdapter(mRecyclerAdapterMaster);


        //   getAllData();
    }

    private void getAllData() {
        String tag_string_req = "req_register";
        pDialog.setMessage("Validating ...");
        showDialog();
        // showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                "http://coconutfpo.smartfpo.com/AMBank/get_all_uba.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Register Response: ", response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        JSONArray jsonArray = jObj.getJSONArray("uba");
                        itemArrayList = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject dataObject = jsonArray.getJSONObject(i);
                                Mainbean mainbean = new Gson().fromJson(dataObject.getString("data"), Mainbean.class);
                                mainbean.setId(dataObject.getString("formid"));
                                itemArrayList.add(mainbean);
                            } catch (Exception e) {
                                Log.e("xxxxxxxxxxx", e.toString());
                                //   Toast.makeText(getApplicationContext(), "Some Network Error.Try after some time", Toast.LENGTH_SHORT).show();

                            }
                        }
                        getSupportActionBar().setSubtitle(String.valueOf(itemArrayList.size()) + " Surveys");
                        mRecyclerAdapterMaster.notifyData(itemArrayList);
                    }
                } catch (Exception e) {
                    Log.e("xxxxxxxxxxx", e.toString());
                    //     Toast.makeText(getApplicationContext(), "Some Network Error.Try after some time", Toast.LENGTH_SHORT).show();

                }
                hideDialog();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Registration Error: ", error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Some Network Error.Try after some time", Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            protected Map<String, String> getParams() {
                HashMap localHashMap = new HashMap();
                localHashMap.put("key", sharedpreferences.getString(buSurveyerId, ""));
                localHashMap.put("db", "nec");
                return localHashMap;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    private void hideDialog() {
        if (this.pDialog.isShowing())
            this.pDialog.dismiss();
    }


    private void showDialog() {
        if (!this.pDialog.isShowing())
            this.pDialog.show();
    }

    @Override
    public void onStart() {
        super.onStart();

        getAllData();
        //if offline

        //  dbHelperSurvey.getAllNotes();
//        itemArrayList = new ArrayList<>();
//        ArrayList<Survey> surveys = dbHelperSurvey.getAllNotes(sharedpreferences.getString(buStudentId, ""));
//        itemArrayList = dbHelperSurvey.getStudentsNotes(sharedpreferences.getString(buStudentId, ""));
//        mRecyclerAdapterMaster.notifyData(itemArrayList);

        //online

    }


    private boolean isValidString(String string) {

        if (string == null) {
            return false;
        } else if (string.length() <= 0) {
            return false;
        } else if (string.startsWith("http")) {
            return false;
        }

        return true;
    }

    public String getfilename_from_path(String path) {
        return path.substring(path.lastIndexOf('/') + 1, path.length());

    }


    @Override
    public void onEditClick(int position) {
        Intent intent = new Intent(MainActivityAllSurvey.this, MainActivityHouseHold.class);
        intent.putExtra("object", itemArrayList.get(position));
        startActivity(intent);
    }

    @Override
    public void onPrintClick(int position) {
        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/PDF";
            File dir = new File(path);
            if (!dir.exists())
                dir.mkdirs();
            Log.d("PDFCreator", "PDF Path: " + path);
            File file = new File(dir, itemArrayList.get(position).name + ".pdf");
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
            AppConfig.addContent(document, itemArrayList.get(position), MainActivityAllSurvey.this);
            document.close();


        } catch (Error | Exception e) {
            e.printStackTrace();
        }
        Uri photoURI = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".smart.cst.pra.provider",

                new File(Environment.getExternalStorageDirectory()
                        .getAbsolutePath() + "/PDF/" + itemArrayList.get(position).name + ".pdf"));

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(photoURI
                , "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);
    }
}


