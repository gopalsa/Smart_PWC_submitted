package nec.cst.pra.village;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import nec.cst.pra.ConvertUtils;
import nec.cst.pra.CustomFontEditText;
import nec.cst.pra.CustomFontTextView;
import nec.cst.pra.GPSTracker;
import nec.cst.pra.MainActivity;
import nec.cst.pra.R;
import nec.cst.pra.Vrp;
import nec.cst.pra.app.GlideApp;
import nec.cst.pra.app.Imageutils;
import nec.cst.pra.db.DbMeet;
import nec.cst.pra.db.DbProfile;
import nec.cst.pra.db.DbVrp;

/**
 * Created by vidhushi.g on 8/10/17.
 */

public class VillageMeetingActivity extends AppCompatActivity implements
        BaseClick, MemberClick, Imageutils.ImageAttachmentListener {
    DbVrp dbVrp;
    DbProfile dbProfile;
    DbMeet dbMeet;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String vrpid = "vrpidKey";
    public static final String update = "updateKey";
    public static final String tittle = "tittleKey";
    public static final String villageIntro = "villageIntroKey";
    public static final String villageReference = "villageReferenceKey";
    private Vrp vrp;
    String vrpId = "";
    String meetId = null;
    private ProgressDialog pDialog;


    private ArrayList<ImportantPerson> memberArrayList = new ArrayList<>();
    private RecyclerView memberList;
    private ImportantMemberAdapter mAdapter;


    private ArrayList<Base> baseArrayList = new ArrayList<>();
    private RecyclerView photoList;
    private AttachmentBaseAdapter attachmentBaseAdapter;
    GPSTracker gps;

    Imageutils imageutils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.village_meeting);

        imageutils = new Imageutils(VillageMeetingActivity.this);

        dbVrp = new DbVrp(this);
        dbProfile = new DbProfile(this);
        dbMeet = new DbMeet(this);

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(vrpid)) {
            vrpId = sharedpreferences.getString(vrpid, "").trim();
        }
        vrp = new Gson().fromJson(ConvertUtils.sample(dbVrp.getDataByvrpid(vrpId).get(1)), Vrp.class);
        getSupportActionBar().setTitle("Village Meeting Module");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        gps = new GPSTracker(VillageMeetingActivity.this);


        CustomFontEditText date = (CustomFontEditText) findViewById(R.id.date);
        CustomFontEditText time = (CustomFontEditText) findViewById(R.id.time);
        EditText pmalecount = (EditText) findViewById(R.id.pmalecount);
        EditText pfemalecount = (EditText) findViewById(R.id.pfemalecount);
        EditText fmalecount = (EditText) findViewById(R.id.fmalecount);
        EditText ffemalecount = (EditText) findViewById(R.id.ffemalecount);
        CustomFontEditText consentRecived = (CustomFontEditText) findViewById(R.id.consentRecived);
        CustomFontEditText agenda = (CustomFontEditText) findViewById(R.id.agenda);
        TextView submit = (TextView) findViewById(R.id.submit);

        memberArrayList.add(new ImportantPerson("", "Add Member", "Click to add"));
        memberList = (RecyclerView) findViewById(R.id.memberList);
        mAdapter = new ImportantMemberAdapter(VillageMeetingActivity.this, memberArrayList, this);
        memberList.setHasFixedSize(true);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
        memberList.setLayoutManager(mLayoutManager);
        memberList.setItemAnimator(new DefaultItemAnimator());
        memberList.setAdapter(mAdapter);


        baseArrayList = new ArrayList<>();
        baseArrayList.add(new Base(null, null));
        photoList = (RecyclerView) findViewById(R.id.photoList);
        attachmentBaseAdapter = new AttachmentBaseAdapter(this, baseArrayList, this);
        photoList.setLayoutManager(new GridLayoutManager(this, 3));
        photoList.setAdapter(attachmentBaseAdapter);


        try {
            List<ArrayList<String>> datas = dbMeet.getAllData();
            String dataString = datas.get(0).get(4);

            VillageMeeting villageMeeting = new Gson().fromJson(dataString, VillageMeeting.class);
            if (villageMeeting.importantPeoples != null && villageMeeting.importantPeoples.size() > 0) {
                memberArrayList = villageMeeting.importantPeoples;
                mAdapter.notifyData(memberArrayList);
            }
            date.setText(villageMeeting.date);
            time.setText(villageMeeting.time);
            pmalecount.setText(villageMeeting.pmalecount);
            pfemalecount.setText(villageMeeting.pfemalecount);
            fmalecount.setText(villageMeeting.fmalecount);
            ffemalecount.setText(villageMeeting.ffemalecount);
            agenda.setText(villageMeeting.agenda);
            consentRecived.setText(villageMeeting.consent);
            if (villageMeeting.bases != null && villageMeeting.bases.size() > 0) {
                baseArrayList = villageMeeting.bases;
                attachmentBaseAdapter.notifyData(baseArrayList);
            }
            meetId = villageMeeting.id;

        } catch (Exception e) {

        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VillageMeeting villageMeeting = new VillageMeeting(
                        sharedpreferences.getString(villageIntro, ""),
                        sharedpreferences.getString(villageReference, ""),
                        memberArrayList,
                        date.getText().toString(),
                        time.getText().toString(),
                        pmalecount.getText().toString(),
                        pfemalecount.getText().toString(),
                        fmalecount.getText().toString(),
                        ffemalecount.getText().toString(),
                        agenda.getText().toString(),
                        consentRecived.getText().toString(),
                        baseArrayList
                );
                if (meetId != null) {
                    villageMeeting.setId(meetId);
                    dbMeet.updatedata(vrpId, "meet", meetId, new Gson().toJson(villageMeeting));
                } else {
                    villageMeeting.setId(String.valueOf(System.currentTimeMillis()));
                    dbMeet.addData(vrpId, "meet", villageMeeting.getId(), new Gson().toJson(villageMeeting));
                }

                Intent intent = new Intent(VillageMeetingActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    public void showMemberDialog(final int position) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(VillageMeetingActivity.this);
        LayoutInflater inflater = VillageMeetingActivity.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.import_re_register, null);
        final ImageView itemclose = (ImageView) dialogView.findViewById(R.id.itemclose);

        final CustomFontEditText name = (CustomFontEditText) dialogView.findViewById(R.id.membername);
        final CustomFontEditText fatherName = (CustomFontEditText) dialogView.findViewById(R.id.fatherName);
        final CustomFontEditText age = (CustomFontEditText) dialogView.findViewById(R.id.age);
        final CustomFontEditText gender = (CustomFontEditText) dialogView.findViewById(R.id.gender);
        final CustomFontEditText designation = (CustomFontEditText) dialogView.findViewById(R.id.designation);
        final CustomFontEditText geoTag = (CustomFontEditText) dialogView.findViewById(R.id.geotag);
        final CustomFontEditText yearFrom = (CustomFontEditText) dialogView.findViewById(R.id.yearFrom);
        final CustomFontEditText yearTo = (CustomFontEditText) dialogView.findViewById(R.id.yearTo);
        final CustomFontEditText electedOrSelected = (CustomFontEditText) dialogView.findViewById(R.id.electedOrSelected);

        final ImageView refresh = (ImageView) dialogView.findViewById(R.id.refresh);

        final LinearLayout imagelin = (LinearLayout) dialogView.findViewById(R.id.imglin);
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
        if (position != 0) {
            itemtittle.setText("Update Member");
            ImportantPerson member = memberArrayList.get(position);

            name.setText(member.name);
            fatherName.setText(member.fatherName);
            age.setText(member.age);
            gender.setText(member.gender);
            designation.setText(member.designation);
            geoTag.setText(member.geoTag);
            yearFrom.setText(member.yearFrom);
            yearTo.setText(member.yearTo);
            electedOrSelected.setText(member.electedOrSelected);

            GlideApp.with(VillageMeetingActivity.this).load(member.getImage())
                    .centerCrop()
                    .dontAnimate()
                    .thumbnail(0.5f)
                    .placeholder(R.drawable.profile)
                    .into(image);
            image.setMfilePath(member.getImage());
            image.setIsImage("true");
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().length() <= 0
                        || designation.getText().toString().length() <= 0
                        ) {
                    Toast.makeText(getApplicationContext(), "Enter all fileds", Toast.LENGTH_SHORT).show();
                } else {
                    ImportantPerson member = new ImportantPerson(
                            image.getMfilePath(),
                            name.getText().toString(),
                            fatherName.getText().toString(),
                            age.getText().toString(),
                            gender.getText().toString(),
                            designation.getText().toString(),
                            geoTag.getText().toString(),
                            yearFrom.getText().toString(),
                            yearTo.getText().toString(),
                            electedOrSelected.getText().toString()
                    );
                    if (position == -0) {
                        memberArrayList.add(member);
                        mAdapter.notifyData(memberArrayList);
                        b.cancel();
                    } else {
                        memberArrayList.get(position).setImage(member.image);
                        memberArrayList.get(position).setName(member.name);
                        memberArrayList.get(position).setFatherName(member.fatherName);
                        memberArrayList.get(position).setAge(member.age);
                        memberArrayList.get(position).setGender(member.gender);
                        memberArrayList.get(position).setDesignation(member.designation);
                        memberArrayList.get(position).setGeoTag(member.geoTag);
                        memberArrayList.get(position).setYearFrom(member.yearFrom);
                        memberArrayList.get(position).setYearTo(member.yearTo);
                        memberArrayList.get(position).setElectedOrSelected(member.electedOrSelected);
                        mAdapter.notifyData(memberArrayList);
                        b.cancel();
                    }
                }

            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickSetup setup = new PickSetup();
                PickImageDialog.build(setup).setOnPickResult(new IPickResult() {
                    @Override
                    public void onPickResult(PickResult pickResult) {
                        GlideApp.with(VillageMeetingActivity.this).load(pickResult.getUri())
                                .centerCrop()
                                .dontAnimate()
                                .thumbnail(0.5f)
                                .placeholder(R.drawable.profile)
                                .into(image);
                        image.setMfilePath(pickResult.getUri().toString());
                        image.setIsImage("true");
                        imagetxt.setText(pickResult.getUri().toString());
                    }
                })
                        .show(VillageMeetingActivity.this);
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check if GPS enabled
                gps = new GPSTracker(VillageMeetingActivity.this);
                if (gps.canGetLocation()) {

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

                    geoTag.setText(latitude + "," + longitude);

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

        b.show();
    }


    @Override
    public void onBaseClick(int position) {

        if (position == 0) {
            imageutils.imagepicker(1);
            imageutils.setImageAttachmentListener(new Imageutils.ImageAttachmentListener() {
                @Override
                public void image_attachment(int from, String filename, Bitmap file, Uri uri) {
                    String path = Environment.getExternalStorageDirectory() + File.separator + "ImageAttach" + File.separator;
                    Base base = new Base();
                    base.setUrl(imageutils.getPath(uri));
                    base.setIsImage("false");
                    if (filename != null) {
                        base.setIsImage("true");
                        imageutils.createImage(file, filename, path, false);
                    }
                    baseArrayList.add(base);
                    attachmentBaseAdapter.notifyDataItem(baseArrayList, baseArrayList.size() + 1);
                }
            });
        } else {
            Intent localIntent = new Intent(VillageMeetingActivity.this, ActivityMediaOnline.class);
            localIntent.putExtra("filePath", baseArrayList.get(position).getUrl());
            localIntent.putExtra("isImage", Boolean.parseBoolean(baseArrayList.get(position).getIsImage()));
            startActivity(localIntent);
        }

    }

    @Override
    public void onDeleteClick(int position) {
        baseArrayList.remove(position);
        attachmentBaseAdapter.notifyData(baseArrayList);
    }

    @Override
    public void onMemberClick(int position) {
        showMemberDialog(position);
    }

    @Override
    public void onDeleteMemClick(int position) {
        memberArrayList.remove(position);
        mAdapter.notifyData(memberArrayList);
    }

    @Override
    public void image_attachment(int from, String filename, Bitmap file, Uri uri) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        imageutils.request_permission_result(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageutils.onActivityResult(requestCode, resultCode, data);
    }


}