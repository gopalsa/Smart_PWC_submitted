package smart.cst.pwc;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.hardware.fingerprint.FingerprintManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import smart.cst.pwc.app.AppConfig;
import smart.cst.pwc.app.AppController;
import smart.cst.pwc.db.DbVrp;

public class FingerprintActivity extends AppCompatActivity {

    private KeyStore keyStore;
    // Variable used for storing the key in the Android Keystore container
    private static final String KEY_NAME = "androidHive";
    private Cipher cipher;
    private TextView textView;


    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String update = "updateKey";
    public static final String vrpid = "vrpidKey";
    public static final String buSurveyerId = "buSurveyerIdKey";

    private DbVrp dbVrp;
    public static final String villageIntro = "villageIntroKey";
    public static final String villageMeet = "villageMeetKey";

    private ProgressDialog pDialog;
    private static final String TAG = FingerprintActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerprint);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        dbVrp = new DbVrp(this);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/maven.ttf");
        final EditText username = (EditText) findViewById(R.id.username);
        final EditText password = (EditText) findViewById(R.id.password);
        final TextInputLayout usernameLayout = (TextInputLayout) findViewById(R.id.user);
        final TextInputLayout passwordLayout = (TextInputLayout) findViewById(R.id.pass);
        CustomFontTextView addvrp = (CustomFontTextView) findViewById(R.id.addfarmer);
        final CustomFontTextView login = (CustomFontTextView) findViewById(R.id.login);
        username.setTypeface(custom_font);
        password.setTypeface(custom_font);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().length() <= 0 || password.getText().toString().length() <= 0) {
                    Toast.makeText(getApplicationContext(), "Enter all fields", Toast.LENGTH_SHORT).show();
                } else {
//                    List<ArrayList<String>> vrpList = dbVrp.getAllData();
//                    boolean trig = false;
//                    for (int i = 0; i < vrpList.size(); i++) {
//                        Vrp vrp = new Gson().fromJson(ConvertUtils.sample(vrpList.get(i).get(1)), Vrp.class);
//                        if (username.getText().toString().equals(vrp.getVoname())) {
//                            if (password.getText().toString().equals(vrpList.get(i).get(2))) {
//                                trig = true;
//                                SharedPreferences.Editor editor = sharedpreferences.edit();
//                                editor.putString(vrpid, vrpList.get(i).get(0));
//                                editor.putString(buSurveyerId, username.getText().toString() + "_" + password.getText().toString());
//                                editor.commit();
//                                Intent io = new Intent(FingerprintActivity.this,
//                                        MainActivity.class);
//                                startActivity(io);
//                                finish();
//                            }
//                        }
//                    }
//                    if (!trig && checkInternetConnection()) {
//                        LoginUser(username.getText().toString(), password.getText().toString());
//                    } else if (!trig) {
//                        Toast.makeText(getApplicationContext(), "No vrps found", Toast.LENGTH_SHORT).show();
//                    }

                    LoginUser(username.getText().toString(), password.getText().toString());

                }
            }
        });
        addvrp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.remove(update);
                editor.commit();
                Intent io = new Intent(FingerprintActivity.this, VRPRegistration.class);
                startActivity(io);
            }
        });

        textView = (TextView) findViewById(R.id.errorText);

        try {
            // Initializing both Android Keyguard Manager and Fingerprint Manager
            KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

            FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);


            // Check whether the device has a Fingerprint sensor.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!fingerprintManager.isHardwareDetected()) {
                    /**
                     * An error message will be displayed if the device does not contain the fingerprint hardware.
                     * However if you plan to implement a default authentication method,
                     * you can redirect the user to a default authentication activity from here.
                     * Example:
                     * Intent intent = new Intent(this, DefaultAuthenticationActivity.class);
                     * startActivity(intent);
                     */
                    textView.setText("Your Device does not have a Fingerprint Sensor");
                } else {
                    // Checks whether fingerprint permission is set on manifest
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                        textView.setText("Fingerprint authentication permission not enabled");
                    } else {
                        // Check whether at least one fingerprint is registered
                        if (!fingerprintManager.hasEnrolledFingerprints()) {
                            textView.setText("Register at least one fingerprint in Settings");
                        } else {
                            // Checks whether lock screen security is enabled or not
                            if (!keyguardManager.isKeyguardSecure()) {
                                textView.setText("Lock screen security not enabled in Settings");
                            } else {
                                generateKey();

                                if (cipherInit()) {
                                    FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(cipher);
                                    FingerprintHandler helper = new FingerprintHandler(this);
                                    helper.startAuth(fingerprintManager, cryptoObject);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Error|Exception e) {
            Log.e("xxxxxxxxxxx", "error");
            textView.setText("Your Device does not have a Fingerprint Sensor");
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    protected void generateKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (Exception e) {
            e.printStackTrace();
        }

        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new RuntimeException("Failed to get KeyGenerator instance", e);
        }

        try {
            keyStore.load(null);
            keyGenerator.init(new
                    KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException |
                InvalidAlgorithmParameterException
                | CertificateException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean cipherInit() {
        try {
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }

        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                    null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException | CertificateException | UnrecoverableKeyException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_lan, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.language) {
            AppController.languageSwitcher.showChangeLanguageDialog(this);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    private void LoginUser(final String username,
                           final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Login ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    int success = jObj.getInt("success");
                    if (success == 1) {

                        dbVrp.deleteAll();
                        JSONArray vrp = jObj.getJSONArray("vrp");
                        String mvrpid = vrp.getJSONObject(0).getString("vrpid");
                        String data = vrp.getJSONObject(0).getString("data");
                        String pass = vrp.getJSONObject(0).getString("password");

                        dbVrp.addData(mvrpid, new Gson().toJson(data));
                        dbVrp.updatePassByvrpid(mvrpid, pass);


                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(vrpid, mvrpid);
                        editor.putString(buSurveyerId, username + "_" + password);

                        editor.commit();
                        Intent io = new Intent(FingerprintActivity.this,
                                MainActivity.class);
                        startActivity(io);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);

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

}