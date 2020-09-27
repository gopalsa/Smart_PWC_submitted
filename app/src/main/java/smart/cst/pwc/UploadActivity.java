package smart.cst.pwc;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import smart.cst.pwc.app.AndroidMultiPartEntity;
import smart.cst.pwc.app.AppConfig;
import smart.cst.pwc.app.AppController;
import smart.cst.pwc.db.DbImage;
import smart.cst.pwc.db.DbMember;
import smart.cst.pwc.db.DbPlot;
import smart.cst.pwc.db.DbProfile;
import smart.cst.pwc.db.DbReport;

public class UploadActivity extends AppCompatActivity {


    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String update = "updateKey";
    public static final String vrpid = "vrpidKey";

    private ProgressDialog pDialog;

    List<ArrayList<String>> dbImages = new ArrayList<>();
    int globalPosition = 0;

    DbImage dbImage;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_activity);
        Button upload = (Button) findViewById(R.id.upload);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dbImage = new DbImage(UploadActivity.this);
                dbImages = dbImage.getAllData();

                DbMember dbmembers = new DbMember(UploadActivity.this);
                List<ArrayList<String>> dbMembers = dbmembers.getAllUploadData();

                DbPlot dbPlot = new DbPlot(UploadActivity.this);
                List<ArrayList<String>> dbplots = dbPlot.getAllData();

                DbProfile dbProfile = new DbProfile(UploadActivity.this);
                List<ArrayList<String>> dbProfiles = dbProfile.getAllUploadData();

                DbReport dbReport = new DbReport(UploadActivity.this);
                List<ArrayList<String>> dbReports = dbReport.getAllData();

                dbImages.addAll(dbMembers);
//                dbImages.addAll(dbplots);
//                dbImages.addAll(dbProfiles);
//                dbImages.addAll(dbReports);

                globalPosition = 0;
                new UploadImagesToServer().execute(dbImages.get(0));

            }
        });

    }


    private class UploadImagesToServer extends AsyncTask<ArrayList<String>, Integer, String> {
        ArrayList<String> survey;
        Map<String, String> filePosition = new HashMap<>();
        JSONObject dataObject = null;
        public long totalSize = 0;

        @Override
        protected void onPreExecute() {
            // setting progress bar to zero
            showDialog();
            super.onPreExecute();

        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            pDialog.setMessage("Uploading..." + (String.valueOf(progress[0])));
        }

        @Override
        protected String doInBackground(ArrayList<String>... params) {
            survey = params[0];
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(AppConfig.URL_UPLOAD_IMAGES);
            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                        new AndroidMultiPartEntity.ProgressListener() {

                            @Override
                            public void transferred(long num) {
                                publishProgress((int) ((num / (float) totalSize) * 100));
                            }
                        });

                filePosition = new HashMap<>();
                if (survey.get(5).equals("image") && survey.get(4) != null && survey.get(4).length() > 0) {
                    try {
                        dataObject = new JSONObject(survey.get(4));
                        if (!dataObject.isNull("image") && dataObject.getString("image").length() > 0
                                && !dataObject.getString("image").startsWith("http")) {
                            File sourceFile = new File(dataObject.getString("image"));
                            entity.addPart("image[]", new FileBody(sourceFile));
                            filePosition.put(getfilename_from_path(dataObject.getString("image")), "profileImage");
                        }
                    } catch (Exception e) {
                        Log.e("xxxxxxxxxxx", e.toString());
                    }
                }


                // Adding file data to http body
                totalSize = entity.getContentLength();
                if (totalSize == 0) {

                }
                httppost.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);

                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;

                }

            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }

            return responseString;

        }

        @Override
        protected void onPostExecute(String result) {
            Log.e("Response from server: ", result);
            hideDialog();
            try {
                JSONObject jsonObject = new JSONObject(result.toString());
                Iterator<String> keys = jsonObject.keys();
                if (!jsonObject.getBoolean("error")) {
                    while (keys.hasNext()) {
                        String key = keys.next();
                        if (!key.equals("error")) {
                            if (jsonObject.get(key) instanceof Boolean) {
                                boolean value = jsonObject.getBoolean(key);
                                if (value) {
                                    if (filePosition.containsKey(key)) {
                                        String position = filePosition.get(key);
                                        if (position.equals("profileImage")) {
                                            dataObject.put("image", AppConfig.imagePath + key);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (dbImages.get(5).equals("image")) {
                        dbImage.updatedataAlone(survey.get(0), survey.get(1), survey.get(2)
                                , survey.get(3), dataObject.toString());
                    }
                }
                registerUser(survey, dataObject.toString());

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Images not uploaded", Toast.LENGTH_SHORT).show();
                registerUser(survey, dataObject.toString());
            }

            // showing the server response in an alert dialog
            //showAlert(result);


            super.onPostExecute(result);
        }

    }


    private void registerUser(final ArrayList<String> dbData, String mData) {
        String tag_string_req = "req_register";
        pDialog.setMessage("Processing " + String.valueOf(globalPosition) + "...");
        showDialog();
        // showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_CREATE_OFFLINE_SURVEY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Register Response: ", response.toString());
                hideDialog();
                try {
                    globalPosition = globalPosition + 1;
                    if (globalPosition < dbImages.size()) {
                        new UploadImagesToServer().execute(dbImages.get(globalPosition));
                    } else {
                        Toast.makeText(getApplicationContext(), "All details updated", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "All details updated", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Log.e("Registration Error: ", error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Some Network Error.Try after some time", Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            protected Map<String, String> getParams() {
                HashMap localHashMap = new HashMap();
                localHashMap.put("vrpid", dbData.get(0));
                localHashMap.put("toolname", dbData.get(1));
                localHashMap.put("pagename", dbData.get(2));
                localHashMap.put("dataname", dbData.get(3));
                localHashMap.put("data", mData);
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

    public String getfilename_from_path(String path) {
        return path.substring(path.lastIndexOf('/') + 1, path.length());

    }

}
