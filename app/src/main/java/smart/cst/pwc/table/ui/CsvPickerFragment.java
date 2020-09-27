package smart.cst.pwc.table.ui;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import smart.cst.pwc.R;
import smart.cst.pwc.table.utils.FileUtils;
import smart.cst.pwc.table.utils.PermissionHelper;
import smart.cst.pwc.table.utils.UriHelper;

import static android.content.Intent.EXTRA_MIME_TYPES;

public class CsvPickerFragment extends Fragment implements View.OnClickListener {
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String tittle = "tittleKey";
    public static final String filename = "filenameKey";
    String filenameString;
    private static final int REQUEST_CODE_PERMISSION_READ_EXTERNAL_STORAGE = 1;
    private static final int REQUEST_CODE_PERMISSION_READ_EXTERNAL_STORAGE_DEMO = 3;
    private static final int REQUEST_CODE_PICK_CSV = 2;
    private TextView tvPickFile;
    private ProgressDialog pDialog;
    public static CsvPickerFragment newInstance() {
        Bundle args = new Bundle();
        CsvPickerFragment fragment = new CsvPickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_csv_picker, container, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        pDialog = new ProgressDialog(this.getContext());
        pDialog.setCancelable(false);
        view.findViewById(R.id.bPickFile).setOnClickListener(this);
        tvPickFile = (TextView) view.findViewById(R.id.tvPickFile);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedpreferences = this.getContext().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(filename)) {
            filenameString = sharedpreferences.getString(filename, "").trim();
        }
        if (PermissionHelper.checkOrRequest(CsvPickerFragment.this, REQUEST_CODE_PERMISSION_READ_EXTERNAL_STORAGE_DEMO,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            createDemoFile();
        }

//        SpannableString ss = new SpannableString(getString(R.string.pick_csv_or_demo_file));
//        ClickableSpan clickableSpan = new ClickableSpan() {
//            @Override
//            public void onClick(View textView) {
//                if (PermissionHelper.checkOrRequest(CsvPickerFragment.this, REQUEST_CODE_PERMISSION_READ_EXTERNAL_STORAGE_DEMO,
//                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
//                    createDemoFile();
//                }
//            }
//
//            @Override
//            public void updateDrawState(TextPaint ds) {
//                super.updateDrawState(ds);
//                ds.setUnderlineText(false);
//            }
//        };
//        ss.setSpan(clickableSpan, 32, 36, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//        tvPickFile.setText(ss);
//        tvPickFile.setMovementMethod(LinkMovementMethod.getInstance());
//        tvPickFile.setHighlightColor(Color.TRANSPARENT);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSION_READ_EXTERNAL_STORAGE
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            pickCsvFile();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_CSV && data != null) {
            Log.e("filepath", UriHelper.getPath(getContext(), data.getData()));
            Activity activity = getActivity();
            if (activity instanceof OnCsvFileSelectedListener) {
                ((OnCsvFileSelectedListener) activity).onCsvFileSelected(
                        UriHelper.getPath(getContext(), data.getData()));
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (PermissionHelper.checkOrRequest(this, REQUEST_CODE_PERMISSION_READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            pickCsvFile();
        }
    }

    private void pickCsvFile() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        intent.setType("*/*");
        String[] mimetypes = {"text/comma-separated-values", "text/csv"};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.putExtra(EXTRA_MIME_TYPES, mimetypes);
        }

        startActivityForResult(Intent.createChooser(intent, getString(R.string.pick_file)), REQUEST_CODE_PICK_CSV);
    }

    private void createDemoFile() {
        File file = null;
        try {
            DownloadFiles("http://coconutfpo.smartfpo.com/pra/vo/villagename/"+filenameString, filenameString);
            file = createDemoTempFile(filenameString);
        } catch (Exception e) {
            Log.e("ioException", e.toString());
        }
        try {
            if (!file.exists() && file.createNewFile()) {
                InputStream inputStream = getContext().getAssets().open("example.csv");
                FileUtils.copy(inputStream, file);
            }
        } catch (IOException e) {
            e.printStackTrace();
            file = null;
        }

        String path = file == null ? "" : file.getPath();

        if (!TextUtils.isEmpty(path)) {
            Activity activity = getActivity();
            if (activity instanceof OnCsvFileSelectedListener) {
                ((OnCsvFileSelectedListener) activity).onCsvFileSelected(path);
            }
        }
    }

    public File createDemoTempFile(String filename) {
        return new File(Environment.getExternalStorageDirectory() + "/" + "data/", filename);
    }

    interface OnCsvFileSelectedListener {
        void onCsvFileSelected(String file);
    }


    public void DownloadFiles(String url, String name) {
         pDialog.setMessage("Loading ...");
         showDialog();
        try {
            URL u = new URL(url);
            InputStream is = u.openStream();

            DataInputStream dis = new DataInputStream(is);

            byte[] buffer = new byte[1024];
            int length;

            FileOutputStream fos = new FileOutputStream(new File(Environment.getExternalStorageDirectory() + "/" + "data/" + name));
            while ((length = dis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }

        } catch (MalformedURLException mue) {
            Log.e("SYNC getUpdate", "malformed url error", mue);
        } catch (IOException ioe) {
            Log.e("SYNC getUpdate", "io error", ioe);
        } catch (SecurityException se) {
            Log.e("SYNC getUpdate", "security error", se);
        }
        hideDialog();
    }
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


}
