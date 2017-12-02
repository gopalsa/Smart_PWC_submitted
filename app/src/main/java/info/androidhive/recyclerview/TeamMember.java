package info.androidhive.recyclerview;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import info.androidhive.recyclerview.db.DbMember;
import info.androidhive.recyclerview.db.DbVrp;

/**
 * Created by Gopal on 18-11-2017.
 */

public class TeamMember extends AppCompatActivity {

    private ArrayList<Member> memberList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TeamMemberAdapter mAdapter;
    private LinearLayout emptylayout;
    private String imagePath;
    DbMember dbMember;
    DbVrp dbVrp;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String vrpid = "vrpidKey";
    public static final String update = "updateKey";
    String vrpId = "";
    private Vrp vrp;
    GPSTracker gps;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_member);
        getSupportActionBar().setTitle("Team members");
        gps = new GPSTracker(TeamMember.this);

        dbMember = new DbMember(this);
        dbVrp = new DbVrp(this);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(vrpid)) {
            vrpId = sharedpreferences.getString(vrpid, "").trim();
        }

        vrp = new Gson().fromJson(ConvertUtils.sample(dbVrp.getDataByvrpid(vrpId).get(1)), Vrp.class);
        emptylayout = (LinearLayout) findViewById(R.id.emptylayout);
        emptylayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMemberDialog(-1, "", "Add team member");
            }
        });
        TextView submittxt = (TextView) findViewById(R.id.r_submittxt);
        submittxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMemberDialog(-1, "", "Add team member");
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mAdapter = new TeamMemberAdapter(TeamMember.this, memberList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                final CharSequence[] items = {
                        "Edit", "Delete"
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(TeamMember.this);
                builder.setTitle("Make your selection");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            showMemberDialog(position, memberList.get(position).getName(), "Update team member");
                        } else if (item == 1) {
                            dbMember.deleteData(vrpId, memberList.get(position).getName(),
                                    memberList.get(position).getName(), "");
                        }
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        prepareData();
    }


    public void showMemberDialog(final int position, final String conatct, String tittle) {
        imagePath = "";
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(TeamMember.this);
        LayoutInflater inflater = TeamMember.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.team_member_registration, null);
        final ImageView itemclose = (ImageView) dialogView.findViewById(R.id.itemclose);
        final CustomFontEditText name = (CustomFontEditText) dialogView.findViewById(R.id.membername);
        final CustomFontEditText designation = (CustomFontEditText) dialogView.findViewById(R.id.designation);
        final CustomFontEditText designationinpra = (CustomFontEditText) dialogView.findViewById(R.id.designationinpra);
        final CustomFontEditText address = (CustomFontEditText) dialogView.findViewById(R.id.address);
        final CustomFontEditText phone = (CustomFontEditText) dialogView.findViewById(R.id.phone);
        final CustomFontEditText geotag = (CustomFontEditText) dialogView.findViewById(R.id.geotag);
        final ImageView georefresh = (ImageView) dialogView.findViewById(R.id.refresh);
        final CustomFontEditText pincode = (CustomFontEditText) dialogView.findViewById(R.id.pincode);
        final CustomFontEditText education = (CustomFontEditText) dialogView.findViewById(R.id.education);
        final CustomFontEditText experience = (CustomFontEditText) dialogView.findViewById(R.id.experience);
        final CustomFontEditText socialmedia = (CustomFontEditText) dialogView.findViewById(R.id.socialmedia);
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
        itemtittle.setText(tittle);
        if (position != -1) {
            ArrayList<String> data = dbMember.getData(vrpId, "member", conatct);
            JsonParser parser = new JsonParser();
            JsonObject o = parser.parse(data.get(3)).getAsJsonObject();
            Member member = new Gson()
                    .fromJson(o, Member.class);
            name.setText(member.getName());
            designation.setText(member.getDesignation());
            designationinpra.setText(member.getDesignationinpra());
            address.setText(member.getAddress());
            phone.setText(member.getPhone());
            education.setText(member.getEducation());
            experience.setText(member.getExperience());
            socialmedia.setText(member.getSocialmedia());
            geotag.setText(member.getGeotag());
            pincode.setText(member.getGeotag());
            Glide.with(TeamMember.this).load(member.getImage())
                    .centerCrop()
                    .dontAnimate()
                    .thumbnail(0.5f)
                    .placeholder(R.drawable.profile)
                    .into(image);
            imagePath = member.getImage();
            imagetxt.setText("update image");
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().length() <= 0
                        || designation.getText().toString().length() <= 0
                        || designationinpra.getText().toString().length() <= 0
                        || address.getText().toString().length() <= 0
                        || phone.getText().toString().length() <= 0
                        || education.getText().toString().length() <= 0
                        || experience.getText().toString().length() <= 0
                        || socialmedia.getText().toString().length() <= 0
                        ) {
                    Toast.makeText(getApplicationContext(), "Enter all fileds", Toast.LENGTH_SHORT).show();
                } else {
                    Member member = new Member(imagePath, name.getText().toString(),
                            designation.getText().toString(),
                            designationinpra.getText().toString(),
                            address.getText().toString(),
                            geotag.getText().toString(),
                            pincode.getText().toString(),
                            phone.getText().toString(),
                            education.getText().toString(),
                            experience.getText().toString(),
                            socialmedia.getText().toString());
                    if (position == -1) {
                        memberList.add(member);
                        mAdapter.notifyData(memberList);
                        dbMember.addData(vrpId, "member", name.getText().toString(), new Gson().toJson(member));
                        b.cancel();
                        emptylayout.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    } else {
                        memberList.get(position).setName(member.getName());
                        memberList.get(position).setDesignation(member.getDesignation());
                        memberList.get(position).setDesignationinpra(member.getDesignationinpra());
                        memberList.get(position).setAddress(member.getAddress());
                        memberList.get(position).setPhone(member.getPhone());
                        memberList.get(position).setEducation(member.getEducation());
                        memberList.get(position).setExperience(member.getExperience());
                        memberList.get(position).setSocialmedia(member.getSocialmedia());
                        memberList.get(position).setImage(member.getImage());
                        dbMember.updatedata(vrpId, "member", conatct
                                , name.getText().toString(), new Gson().toJson(member));
                        mAdapter.notifyData(memberList);
                        b.cancel();
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
                        Glide.with(TeamMember.this).load(pickResult.getUri())
                                .centerCrop()
                                .dontAnimate()
                                .thumbnail(0.5f)
                                .placeholder(R.drawable.profile)
                                .into(image);
                        imagePath = pickResult.getUri().toString();
                        imagetxt.setText(pickResult.getUri().toString());
                    }
                })
                        .show(TeamMember.this);
            }
        });

        georefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check if GPS enabled
                gps = new GPSTracker(TeamMember.this);
                if (gps.canGetLocation()) {

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

                    geotag.setText(latitude + "," + longitude);

                    Geocoder geocoder;
                    List<Address> addresses;
                    geocoder = new Geocoder(TeamMember.this, Locale.getDefault());
                    try {
                        addresses = geocoder.getFromLocation(latitude
                                , longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                        String addressGeo = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                        String postalCode = addresses.get(0).getPostalCode();
                        address.setText(addressGeo);
                        pincode.setText(postalCode);
                    } catch (Exception e) {
                        Log.d("Team member", e.toString());
                    }
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
        if (position==-1&&gps.canGetLocation()) {
            gps = new GPSTracker(TeamMember.this);
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            geotag.setText(latitude + "," + longitude);
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(TeamMember.this, Locale.getDefault());
            try {
                addresses = geocoder.getFromLocation(latitude
                        , longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                String addressGeo = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String postalCode = addresses.get(0).getPostalCode();
                address.setText(addressGeo);
                pincode.setText(postalCode);
            } catch (Exception e) {
                Log.d("Team member", e.toString());
            }
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


    private void prepareData() {
        List<ArrayList<String>> memList = new ArrayList<>();
        memberList = new ArrayList<>();
        memList = dbMember.getAllData();
        for (int i = 0; i < memList.size(); i++) {
            try {
                JsonParser parser = new JsonParser();
                JsonObject o = parser.parse(memList.get(i).get(3)).getAsJsonObject();
                Member member = new Gson().fromJson(o, Member.class);
                memberList.add(member);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        if (memberList.size() > 0) {
            emptylayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            emptylayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
        mAdapter.notifyData(memberList);
    }


}
