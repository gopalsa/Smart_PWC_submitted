package nec.cst.pra.survey;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.google.gson.Gson;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import nec.cst.pra.R;
import nec.cst.pra.app.AppConfig;
import nec.cst.pra.app.AppController;
import nec.cst.pra.app.HeaderFooterPageEvent;
import nec.cst.pra.household.Mainbean;
import nec.cst.pra.household.SurveyItem;
import nec.cst.pra.survey.adapters.BaseStudentAdapter;
import nec.cst.pra.survey.adapters.StudentAdapterByName;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class PieChartActivity extends AppCompatActivity implements SurveyItemClick {

    private static final int CAMERA_GALLERY_CODE = 100;

    static private ArrayList<Mainbean> surveyArrayList = new ArrayList<>();
    public static final String mypreference = "mypref";
    public static final String buStudentId = "buStudentIdKey";
    SharedPreferences sharedpreferences;


    static List<DataEntry> data = new ArrayList<>();
    static String title = "Smart survey";
    static int invalidResponse = 0;
    static List<nec.cst.pra.survey.adapters.Response> responses = new ArrayList<>();
    static Map<String, Integer> departmentCountMap = new HashMap<>();
    private SurveyQuestionsAdapter mRecyclerAdapterMaster;
    private RecyclerView mastersList;
    ArrayList<SurveyItem> surveyItems = new ArrayList<>();
    static boolean isGraph = false;
    static NestedScrollView scrollview;
    static boolean isPrint = false;
    static PrintSurveyItem[] printSurveyItems = null;
    static int questionItem = 0;
    static String questionName;
    static String studentId;
    static ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);


        if (!checkPermission(new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE})) {
            requestPermission(new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, CAMERA_GALLERY_CODE);
        }

        getSupportActionBar().setTitle("Smart Survey");

        sharedpreferences = this.getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        studentId = sharedpreferences.getString(buStudentId, "");
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        surveyItems = AppConfig.loadHouseQuestions(this);
        mastersList = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerAdapterMaster = new SurveyQuestionsAdapter(this, surveyItems, this);
        final LinearLayoutManager thirdManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mastersList.setLayoutManager(thirdManager);
        mastersList.setAdapter(mRecyclerAdapterMaster);


        getAllData();

    }

    private void getAllData() {
        String tag_string_req = "req_register";
        // showDialog();

        String url = null;
        boolean newString;
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            newString = false;
            url = AppConfig.URL_GET_All_SURVEY;
        } else {
            newString = extras.getBoolean("isUser");
            if (newString) {
                url = AppConfig.URL_GET_USER_SURVEY;
            } else {
                url = AppConfig.URL_GET_All_SURVEY;
            }
        }


        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Register Response: ", response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        JSONArray jsonArray = jObj.getJSONArray("survey");
                        surveyArrayList = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject dataObject = jsonArray.getJSONObject(i);
                                Mainbean survey = new Gson().fromJson(dataObject.getString("data"), Mainbean.class);
                                survey.setId(dataObject.getString("id"));
                                survey.setStudentid(dataObject.getString("studentid"));
                                surveyArrayList.add(survey);
                            } catch (Exception e) {
                                Log.e("xxxxxxxxxx", String.valueOf(i) + "        " + e.toString());
                            }
                        }
                        loadHomeFragment(surveyItems.get(1).question, "age");
                    }
                } catch (JSONException e) {
                    Log.e("xxxxxxxxxxx", e.toString());
                    Toast.makeText(getApplicationContext(), "Some Network Error.Try after some time", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Registration Error: ", error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Some Network Error.Try after some time", Toast.LENGTH_LONG).show();
                finish();
            }
        }) {
            protected Map<String, String> getParams() {
                HashMap localHashMap = new HashMap();
                localHashMap.put("key", sharedpreferences.getString(buStudentId, ""));
                return localHashMap;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    private void loadHomeFragment(String question, String surveyName) {


        questionName = surveyName;
        invalidResponse = 0;
        data = new ArrayList<>();
        responses = new ArrayList<>();
        Map<String, Long> stringLongMap = new HashMap<>();
        for (int i = 0; i < surveyArrayList.size(); i++) {
            nec.cst.pra.survey.adapters.Response response = new nec.cst.pra.survey.adapters.Response();
            Mainbean survey = surveyArrayList.get(i);
            response.setStudent(survey.id);
            response.setGps("0.0");

            String value = null;
            try {
                Class<?> clazz = survey.getClass();
                Field field = clazz.getField(surveyName); //Note, this can throw an exception if the field doesn't exist.
                value = (String) field.get(survey);
                value = value.replaceAll(("\n"), "");
                if (value != null && value.length() > 0) {
                    if (surveyName.equals("age")) {
                        int age = Integer.parseInt(value);
                        if (age > 0 && age <= 10) {
                            value = "0-10";
                        } else if (age >= 11 && age <= 14) {
                            value = "11-14";
                        } else if (age >= 15 && age <= 18) {
                            value = "15-18";
                        } else if (age >= 19 && age <= 30) {
                            value = "19-30";
                        } else if (age >= 31 && age <= 40) {
                            value = "31-40";
                        } else if (age >= 41 && age <= 50) {
                            value = "41-50";
                        } else if (age >= 51 && age <= 60) {
                            value = "51-60";
                        } else if (age >= 61 && age <= 100) {
                            value = "61-100";
                        }
                    }
                } else {
                    value = null;
                }
            } catch (Exception e) {
                Log.e("xxxxxxxxx", e.toString());
            }

            if (value == null) {
                value = "Skipped";
                invalidResponse = invalidResponse + 1;
                response.setIsValid("Invalid");
            } else {
                response.setIsValid("Valid");
            }

            String[] vaStrings = value.split(",");
            for (int k = 0; k < vaStrings.length; k++) {
                String valueString = vaStrings[k];
                response.setResponse(valueString);
                responses.add(response);
                if (stringLongMap.containsKey(valueString)) {
                    long valueLong = stringLongMap.get(valueString);
                    stringLongMap.put(valueString, valueLong + 1);
                } else {
                    stringLongMap.put(valueString, (long) 1);
                }
            }
        }


        Iterator it = stringLongMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            data.add(new ValueDataEntry(String.valueOf(pair.getKey()), (Number) pair.getValue()));
            it.remove(); // avoids a ConcurrentModificationException
        }
        title = question;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame, new ExampleFragment(), question);
        fragmentTransaction.commitAllowingStateLoss();


    }

    @Override
    public void itemClick(int position) {

        if (surveyArrayList.size() <= 0) {
            Toast.makeText(getApplicationContext(), "No survey found.Please wait", Toast.LENGTH_SHORT).show();
        } else {
            isGraph = surveyItems.get(position).isGraph;
            loadHomeFragment(surveyItems.get(position).question, surveyItems.get(position).field);
        }

    }


    public static class ExampleFragment extends Fragment implements BaseStudentAdapter.OnItemClickListener {

        private AnyChartView anyChartView;
        private View view;
        private StudentAdapterByName mSectionedRecyclerAdapter;
        private FloatingActionButton printImg;
        private PieChartActivity activity;
        private EditText relevanceToTheSurvey;
        private EditText infer;
        private Button btnSubmit;
        private ProgressBar progressInfer;
        private LinearLayout inferLayout;
        private TextView noGraphQues;
        private NestedScrollView mainLayout;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            view = inflater.inflate(R.layout.activity_pie_chart_frag, container, false);
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            scrollview = (NestedScrollView) view.findViewById(R.id.scrollview);

            activity = (PieChartActivity) getActivity();

            anyChartView = view.findViewById(R.id.any_chart_view);
            printImg = view.findViewById(R.id.printImg);
            noGraphQues = view.findViewById(R.id.noGraphQues);
            TextView titleText = view.findViewById(R.id.title);
            TextView exttitle = view.findViewById(R.id.exttitle);
            infer = view.findViewById(R.id.infer);
            btnSubmit = view.findViewById(R.id.btnSubmit);
            progressInfer = view.findViewById(R.id.progressInfer);
            inferLayout = view.findViewById(R.id.inferLayout);
            mainLayout = view.findViewById(R.id.mainLayout);

            infer = view.findViewById(R.id.infer);
            relevanceToTheSurvey = view.findViewById(R.id.relevanceToTheSurvey);
            TextView subtitle = view.findViewById(R.id.subtitle);
            anyChartView.setProgressBar(view.findViewById(R.id.progress_bar));

            getUser();

            int validResponse = surveyArrayList.size() - invalidResponse;
            departmentCountMap.put("Invalid", invalidResponse);
            departmentCountMap.put("Valid", validResponse);
            String sub = "Total Survey : " + String.valueOf(surveyArrayList.size()) + "\nValid Responses : " + String.valueOf(validResponse)
                    + "\nInvalid or Skipped : " + String.valueOf(invalidResponse);
            titleText.setText("Bharathiyar University UBA 2.0 Household survey\n" + title);
            exttitle.setVisibility(View.GONE);

            if (isGraph) {
                exttitle.setVisibility(View.GONE);
                exttitle.setText("No Graph for this Question");
                anyChartView.setVisibility(View.GONE);
                noGraphQues.setVisibility(View.VISIBLE);
            } else {
                anyChartView.setVisibility(View.VISIBLE);
                noGraphQues.setVisibility(View.GONE);
            }
            subtitle.setText(sub);


            if (studentId.equals("7339277826") || studentId.equals("9626047547")
                    || studentId.equals("9080593193") || studentId.equals("7812031342")) {

                infer.setEnabled(true);
                relevanceToTheSurvey.setEnabled(true);
                btnSubmit.setVisibility(View.VISIBLE);
            } else {
                infer.setEnabled(false);
                relevanceToTheSurvey.setEnabled(false);
                btnSubmit.setVisibility(View.GONE);
            }


            Pie pie = AnyChart.pie();
            pie.data(data);
            pie.labels().position("outside");
            pie.legend().title().enabled(false);
//            pie.getLegend().getTitle()
//                    .setText()
//                    .setPadding(0d, 0d, 10d, 0d);
            pie.legend()
                    .position("bottom")
                    .itemsLayout(LegendLayout.HORIZONTAL_EXPANDABLE)
                    .align(Align.RIGHT);

            anyChartView.setChart(pie);


            anyChartView.setOnRenderedListener(new AnyChartView.OnRenderedListener() {
                @Override
                public void onRendered() {
                    if (isPrint) {


                        Thread logoTimer = new Thread() {
                            public void run() {
                                try {
                                    sleep(4000);

                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            // This code will always run on the UI thread, therefore is safe to modify UI elements.
                                            if (isPrint) {
                                                printImg.setVisibility(View.GONE);
                                                inferLayout.setVisibility(View.GONE);
                                                int height = scrollview.getChildAt(0).getHeight();
                                                int width = scrollview.getChildAt(0).getWidth();
                                                if (height > 0 && width > 0) {
                                                    PrintSurveyItem printSurveyItem = new PrintSurveyItem(
                                                            titleText.getText().toString(),
                                                            "Survey Responses: Valid: " + String.valueOf(validResponse)
                                                                    + "  Invalid: " + String.valueOf(invalidResponse) +
                                                                    "  Total: " + String.valueOf(surveyArrayList.size()),
                                                            createBitmap(mainLayout), infer.getText().toString(),
                                                            relevanceToTheSurvey.getText().toString());
                                                    printSurveyItems[questionItem - 1] = printSurveyItem;
                                                }
                                                printImg.setVisibility(View.VISIBLE);
                                                inferLayout.setVisibility(View.VISIBLE);
                                                activity.callPrintMethod();

                                            }
                                        }
                                    });

                                } catch (Exception e) {
                                    Log.e("Error", e.toString());
                                } finally {

                                }
                            }
                        };
                        logoTimer.start();
                    }
                }
            });

            printImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    printSurveyItems = new PrintSurveyItem[1];
                    printImg.setVisibility(View.GONE);
                    inferLayout.setVisibility(View.GONE);
                    int height = scrollview.getChildAt(0).getHeight();
                    int width = scrollview.getChildAt(0).getWidth();
                    if (height > 0 && width > 0) {
                        PrintSurveyItem printSurveyItem = new PrintSurveyItem(
                                titleText.getText().toString(),
                                "Survey Responses: Valid: " + String.valueOf(validResponse)
                                        + "  Invalid: " + String.valueOf(invalidResponse) +
                                        "  Total: " + String.valueOf(surveyArrayList.size())
                                , createBitmap(mainLayout), infer.getText().toString(),
                                relevanceToTheSurvey.getText().toString());
                        printSurveyItems[0] = printSurveyItem;
                    }
                    printImg.setVisibility(View.VISIBLE);
                    inferLayout.setVisibility(View.VISIBLE);
                    activity.printFunction();

//                    pie.print(PaperSize.A4, false);

                }
            });
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            Comparator<nec.cst.pra.survey.adapters.Response> movieComparator =
                    (o1, o2) -> o1.getIsValid().compareTo(o2.getIsValid());
            Collections.sort(responses, movieComparator);
            mSectionedRecyclerAdapter = new StudentAdapterByName(responses, getContext(), departmentCountMap);
            mSectionedRecyclerAdapter.setOnItemClickListener(this);
            recyclerView.setAdapter(mSectionedRecyclerAdapter);
            mSectionedRecyclerAdapter.collapseAllSections();

            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    registerUser();
                }
            });


            return view;
        }

        @Override
        public void onItemClicked(nec.cst.pra.survey.adapters.Response task) {

        }

        @Override
        public void onSubheaderClicked(int position) {
            if (mSectionedRecyclerAdapter.isSectionExpanded(mSectionedRecyclerAdapter.getSectionIndex(position))) {
                mSectionedRecyclerAdapter.collapseSection(mSectionedRecyclerAdapter.getSectionIndex(position));
            } else {
                mSectionedRecyclerAdapter.expandSection(mSectionedRecyclerAdapter.getSectionIndex(position));
            }
        }


        private void registerUser() {
            String tag_string_req = "req_register";
            progressInfer.setVisibility(View.VISIBLE);
            inferLayout.setVisibility(View.GONE);
            // showDialog();
            StringRequest strReq = new StringRequest(Request.Method.POST,
                    AppConfig.URL_CREATE_SURVEYANS, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("Register Response: ", response.toString());
                    progressInfer.setVisibility(View.GONE);
                    inferLayout.setVisibility(View.VISIBLE);
                    Toast.makeText(activity, "Successfully updated", Toast.LENGTH_SHORT).show();

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    // Log.e("Registration Error: ", error.getMessage());
                    Toast.makeText(activity,
                            "Some Network Error.Try after some time", Toast.LENGTH_LONG).show();
                    progressInfer.setVisibility(View.GONE);
                    inferLayout.setVisibility(View.VISIBLE);
                }
            }) {
                protected Map<String, String> getParams() {
                    HashMap localHashMap = new HashMap();
                    localHashMap.put("name", questionName);
                    localHashMap.put("infer", infer.getText().toString());
                    localHashMap.put("relevance", relevanceToTheSurvey.getText().toString());
                    return localHashMap;
                }
            };
            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
        }

        private void getUser() {
            String tag_string_req = "req_register";
            progressInfer.setVisibility(View.VISIBLE);
            inferLayout.setVisibility(View.GONE);
            // showDialog();
            StringRequest strReq = new StringRequest(Request.Method.POST,
                    AppConfig.URL_GET_All_SURVEYANS, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("Register Response: ", response.toString());
                    progressInfer.setVisibility(View.GONE);
                    inferLayout.setVisibility(View.VISIBLE);

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (!jsonObject.getBoolean("error")) {
                            JSONObject dataObject = jsonObject.getJSONArray("survey").getJSONObject(0);

                            infer.setText(dataObject.getString("infer"));
                            relevanceToTheSurvey.setText(dataObject.getString("relevance"));


                        }
                    } catch (JSONException e) {
                        Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    // Log.e("Registration Error: ", error.getMessage());
                    Toast.makeText(activity,
                            "Some Network Error.Try after some time", Toast.LENGTH_LONG).show();
                    progressInfer.setVisibility(View.GONE);
                    inferLayout.setVisibility(View.VISIBLE);
                }
            }) {
                protected Map<String, String> getParams() {
                    HashMap localHashMap = new HashMap();
                    localHashMap.put("key", questionName);
                    return localHashMap;
                }
            };
            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
        }

    }

    private static Bitmap createBitmap(NestedScrollView z) {
        View u = z;
        int height = z.getChildAt(0).getHeight();
        int width = z.getChildAt(0).getWidth();
        Bitmap b = AppConfig.getBitmapFromView(u, height, width);
        return Bitmap.createScaledBitmap(b, (int) PageSize.A6.getWidth(), (int) PageSize.A6.getHeight(), true);

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
            printSurveyItems = new PrintSurveyItem[surveyItems.size()];
            questionItem = 0;
            callPrintMethod();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void callPrintMethod() {
        if (questionItem < surveyItems.size() - 1) {

            progressDialog.setMessage("Converting Graphs into Pdf " + String.valueOf(questionItem + 1)
                    + "/" + String.valueOf(surveyItems.size()));
            if (questionItem == 0) {
                progressDialog.show();
            }
            isGraph = surveyItems.get(questionItem).isGraph;
            isPrint = true;
            loadHomeFragment(surveyItems.get(questionItem).question, surveyItems.get(questionItem).field);

        } else

        {
            isPrint = false;
            printFunction();


        }

        questionItem++;
    }


    private void printFunction() {

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
            AppConfig.addContent(document, printSurveyItems);
            document.close();


        } catch (Error | Exception e) {
            e.printStackTrace();
        }
        progressDialog.hide();

        Uri photoURI = FileProvider.getUriForFile(getApplicationContext(),
                getApplicationContext().getPackageName() + ".smart.cst.pra.provider",
                new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/PDF/" + "demo" + ".pdf"));

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(photoURI
                , "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);

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

    private void requestPermission(String[] permissions, int code) {
        ActivityCompat.requestPermissions(this, permissions, code);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == CAMERA_GALLERY_CODE) {
            if (grantResults.length > 0) {
                boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (locationAccepted && cameraAccepted) {
                    Toast.makeText(getApplicationContext(), "Permission Granted, Now you can access Storage.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied, You cannot access Storage", Toast.LENGTH_LONG).show();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(CAMERA)) {
                            showMessageOKCancel("Permission Denied, You cannot access Storage",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE},
                                                        CAMERA_GALLERY_CODE);
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


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(PieChartActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
}




