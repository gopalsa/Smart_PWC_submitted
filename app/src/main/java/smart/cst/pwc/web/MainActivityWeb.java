package smart.cst.pwc.web;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;


import smart.cst.pwc.R;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivityWeb extends Activity {

    private WebView webView = null;
    private static final int FINE_LOCATION_CODE = 199;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_webview);

        this.webView = (WebView) findViewById(R.id.webview);

        if (!checkPermission(new String[]{ACCESS_FINE_LOCATION})) {
            requestPermission(new String[]{ACCESS_FINE_LOCATION}, FINE_LOCATION_CODE);
        } else {
            loadWebsite();
        }
          //webView.loadData("<html><body>Hello, world!</body></html>", "text/html", "UTF-8");
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && this.webView.canGoBack()) {
            this.webView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    private void loadWebsite(){
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        WebViewClientImpl webViewClient = new WebViewClientImpl(this);
        webView.setWebViewClient(webViewClient);

        Bundle extras = getIntent().getExtras();
        webView.loadUrl(extras.getString("link"));

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
      if (requestCode == FINE_LOCATION_CODE) {
            if (grantResults.length > 0) {
                boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                if (locationAccepted) {
                    Toast.makeText(getApplicationContext(), "Permission Granted, Now you can access location data.", Toast.LENGTH_LONG).show();
                    loadWebsite();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied, You cannot access location data", Toast.LENGTH_LONG).show();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                            showMessageOKCancel(getResources().getString(R.string.locationPermissionAlert),
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


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivityWeb.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


}