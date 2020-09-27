package smart.cst.pwc.action;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import smart.cst.pwc.R;
import smart.cst.pwc.action.academic.Academic;
import smart.cst.pwc.action.academic.AcademicAdapter;
import smart.cst.pwc.action.activitydetail.ActivityDetail;
import smart.cst.pwc.action.activitydetail.ActivityDetailAdapter;
import smart.cst.pwc.action.basevillage.Base;
import smart.cst.pwc.action.basevillage.BaseVillageAdapter;
import smart.cst.pwc.action.household.HouseHold;
import smart.cst.pwc.action.household.HouseHoldAdapter;
import smart.cst.pwc.action.major.Major;
import smart.cst.pwc.action.major.MajorAdapter;
import smart.cst.pwc.action.meeting.Meeting;
import smart.cst.pwc.action.meeting.MeetingAdapter;
import smart.cst.pwc.action.miscellaneous.Miscellaneous;
import smart.cst.pwc.action.miscellaneous.MiscellaneousAdapter;
import smart.cst.pwc.action.salient.Salient;
import smart.cst.pwc.action.salient.SalientAdapter;
import smart.cst.pwc.action.technologies.Technologies;
import smart.cst.pwc.action.technologies.TechnologiesAdapter;
import smart.cst.pwc.action.technologyCustomisation.TechnologyCustomisation;
import smart.cst.pwc.action.technologyCustomisation.TechnologyCustomisationAdapter;
import smart.cst.pwc.action.technologyDevelopment.TechnologyDevelopment;
import smart.cst.pwc.action.technologyDevelopment.TechnologyDevelopmentAdapter;
import smart.cst.pwc.action.uba.Uba;
import smart.cst.pwc.action.uba.UbaAdapter;
import smart.cst.pwc.action.vdp.Vdp;
import smart.cst.pwc.action.vdp.VdpAdapter;
import smart.cst.pwc.action.village.Village;
import smart.cst.pwc.action.village.VillageAdapter;
import smart.cst.pwc.action.villageDevelopment.VillageDevelopment;
import smart.cst.pwc.action.villageDevelopment.VillageDevelopmentAdapter;
import smart.cst.pwc.action.workingGroup.WorkingGroupAdapter;
import smart.cst.pwc.action.workingGroup.Workinggroup;
import smart.cst.pwc.app.AppController;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivityAction extends AppCompatActivity implements OnItemClick {

    ProgressDialog pDialog;


    private RecyclerView majorList;
    private ArrayList<Major> majorFrmPowerArrayList = new ArrayList<>();
    MajorAdapter majorAdapter;

    private RecyclerView ubaList;
    private ArrayList<Uba> ubaArrayList = new ArrayList<>();
    UbaAdapter UbaAdapter;

    private RecyclerView villagefarmPowerList;
    private ArrayList<Village> villageArrayList = new ArrayList<>();
    VillageAdapter VillageAdapter;

    private RecyclerView workingGroupfarmPowerList;
    private ArrayList<Workinggroup> workinggroupArrayList = new ArrayList<>();
    WorkingGroupAdapter workingGroupAdapter;

    private RecyclerView academicFarmPowerList;
    private ArrayList<Academic> academicArrayList = new ArrayList<>();
    AcademicAdapter academicAdapter;

    private RecyclerView activityDetailFarmPowerList;
    private ArrayList<ActivityDetail> activityDetailArrayList = new ArrayList<>();
    ActivityDetailAdapter activityDetailAdapter;

    private RecyclerView baseVillageFarmPowerList;
    private ArrayList<Base> baseAdapterArrayList = new ArrayList<>();
    BaseVillageAdapter baseVillageAdapter;

    private RecyclerView householdFarmPowerList;
    private ArrayList<HouseHold> householdArrayList = new ArrayList<>();
    HouseHoldAdapter houseHoldAdapter;

    private RecyclerView meetingFarmPowerList;
    private ArrayList<Meeting> meetingsArrayList = new ArrayList<>();
    MeetingAdapter meetingAdapter;

    private RecyclerView villageDevelopmentFarmPowerList;
    private ArrayList<VillageDevelopment> villageDevelopmentArrayList = new ArrayList<>();
    VillageDevelopmentAdapter villageDevelopmentAdapter;

    private RecyclerView salientFarmPowerList;
    private ArrayList<Salient> salientArrayList = new ArrayList<>();
    SalientAdapter salientAdapter;

    private RecyclerView technologiesFarmPowerList;
    private ArrayList<Technologies> technologiesArrayList = new ArrayList<>();
    TechnologiesAdapter technologiesAdapter;

    private RecyclerView vdpFarmPowerList;
    private ArrayList<Vdp> vdpArrayList = new ArrayList<>();
    VdpAdapter vdpAdapter;

    private RecyclerView technologyDevelopmentFarmPowerList;
    private ArrayList<TechnologyDevelopment> technologyDevelopmentArrayList = new ArrayList<>();
    TechnologyDevelopmentAdapter technologyDevelopmentAdapter;

    private RecyclerView technologycustomisationFarmPowerList;
    private ArrayList<TechnologyCustomisation> technologyCustomisationArrayList = new ArrayList<>();
    TechnologyCustomisationAdapter technologycustomisationAdapter;

    private RecyclerView miscellaneousarmPowerList;
    private ArrayList<Miscellaneous> miscellaneousArrayList = new ArrayList<>();
    MiscellaneousAdapter miscellaneousAdapter;

    ActionPlan actionPlan = null;
    private String TAG = getClass().getSimpleName();

    EditText institutionparicipatingInstitution;
    EditText institutioncoordinate;
    EditText institutionEmail;
    EditText institutionContact;
    EditText institutionVillage;
    EditText institutionDistrict;
    EditText activityStudent;
    EditText activityStatus;
    EditText activitystudentRemark;
    EditText activityMeeting;
    EditText activityMeetingStatus;
    EditText activityMeetingRemark;
    EditText activityInteraction;
    EditText activityInteractionStatus;
    EditText activityInteractionRemark;
    EditText activityAwareness;
    EditText activityAwarenessStatus;
    EditText activityAwarenessRemark;
    EditText reportingPeriod;
    EditText holy;
    EditText ExtraCurricular;
    EditText interactionWithRural;
    EditText interactionWithDistrict;
    EditText activityPotential;
    EditText activityChallenges;
    EditText activityAnyOther;
    Button submit;
    ActionPlan mainbean = null;

    public static final String mypreference = "mypref";
    public static final String buSurveyerId = "buSurveyerIdKey";
    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_action);

        setContentView(R.layout.activity_main_action);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        sharedpreferences = this.getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        institutionparicipatingInstitution = (EditText) findViewById(R.id.institutionparicipatingInstitution);
        institutioncoordinate = (EditText) findViewById(R.id.institutioncoordinate);
        institutionEmail = (EditText) findViewById(R.id.institutionEmail);
        institutionContact = (EditText) findViewById(R.id.institutionContact);
        institutionVillage = (EditText) findViewById(R.id.institutionVillage);
        institutionDistrict = (EditText) findViewById(R.id.institutionDistrict);
        activityStudent = (EditText) findViewById(R.id.activityStudent);
        activitystudentRemark = (EditText) findViewById(R.id.activitystudentRemark);
        activityStatus = (EditText) findViewById(R.id.activityStatus);
        activityMeeting = (EditText) findViewById(R.id.activityMeeting);
        activityMeetingStatus = (EditText) findViewById(R.id.activityMeetingStatus);
        activityMeetingRemark = (EditText) findViewById(R.id.activityMeetingRemark);
        activityInteraction = (EditText) findViewById(R.id.activityInteraction);
        activityInteractionStatus = (EditText) findViewById(R.id.activityInteractionStatus);
        activityInteractionRemark = (EditText) findViewById(R.id.activityInteractionRemark);
        activityAwareness = (EditText) findViewById(R.id.activityAwareness);
        activityAwarenessStatus = (EditText) findViewById(R.id.activityAwarenessStatus);
        activityAwarenessRemark = (EditText) findViewById(R.id.activityAwarenessRemark);
        reportingPeriod = (EditText) findViewById(R.id.reportingPeriod);
        holy = (EditText) findViewById(R.id.holy);
        ExtraCurricular = (EditText) findViewById(R.id.ExtraCurricular);
        interactionWithRural = (EditText) findViewById(R.id.interactionWithRural);
        interactionWithDistrict = (EditText) findViewById(R.id.interactionWithDistrict);
        activityPotential=(EditText) findViewById(R.id.activityPotential);
        activityChallenges=(EditText) findViewById(R.id.activityChallenges);
        activityAnyOther=(EditText) findViewById(R.id.activityAnyOther);
        submit=(Button)findViewById(R.id.submit);

        majorList = (RecyclerView) findViewById(R.id.majorList);
        majorAdapter = new MajorAdapter(this, majorFrmPowerArrayList, this);
        final LinearLayoutManager major = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        majorList.setLayoutManager(major);
        majorList.setAdapter(majorAdapter);
        TextView addField = (TextView) findViewById(R.id.addField);
        addField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMajorDialog(-1);
            }
        });


        ubaList = (RecyclerView) findViewById(R.id.ubaFarmPowerList);
        UbaAdapter = new UbaAdapter(this, ubaArrayList, this);
        final LinearLayoutManager uba = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        ubaList.setLayoutManager(uba);
        ubaList.setAdapter(UbaAdapter);
        TextView ubaAddRow = (TextView) findViewById(R.id.ubaAddRow);
        ubaAddRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showubaDialog(-1);
            }
        });


        villagefarmPowerList = (RecyclerView) findViewById(R.id.villagefarmPowerList);
        VillageAdapter = new VillageAdapter(this, villageArrayList, this);
        final LinearLayoutManager village = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        villagefarmPowerList.setLayoutManager(village);
        villagefarmPowerList.setAdapter(VillageAdapter);
        TextView villageAddField = (TextView) findViewById(R.id.villageAddField);
        villageAddField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showvillageDialog(-1);
            }
        });

        workingGroupfarmPowerList = (RecyclerView) findViewById(R.id.workingGroupfarmPowerList);
        workingGroupAdapter = new WorkingGroupAdapter(this, workinggroupArrayList, this);
        final LinearLayoutManager workinggroup = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        workingGroupfarmPowerList.setLayoutManager(workinggroup);
        workingGroupfarmPowerList.setAdapter(workingGroupAdapter);
        TextView detailAddField = (TextView) findViewById(R.id.detailAddField);
        detailAddField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showworkinggroupDialog(-1);
            }
        });

        academicFarmPowerList = (RecyclerView) findViewById(R.id.academicFarmPowerList);
        academicAdapter = new AcademicAdapter(this, academicArrayList, this);
        final LinearLayoutManager academic = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        academicFarmPowerList.setLayoutManager(academic);
        academicFarmPowerList.setAdapter(academicAdapter);
        TextView academicAddField = (TextView) findViewById(R.id.academicAddField);
        academicAddField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showacademicDialog(-1);
            }
        });

        activityDetailFarmPowerList = (RecyclerView) findViewById(R.id.activityDetailFarmPowerList);
        activityDetailAdapter = new ActivityDetailAdapter(this, activityDetailArrayList, this);
        final LinearLayoutManager activityDetail = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        activityDetailFarmPowerList.setLayoutManager(activityDetail);
        activityDetailFarmPowerList.setAdapter(activityDetailAdapter);
        TextView activitiesAddField = (TextView) findViewById(R.id.activitiesAddField);
        activitiesAddField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showactivityDetailDialog(-1);
            }
        });

        baseVillageFarmPowerList = (RecyclerView) findViewById(R.id.baseVillageFarmPowerList);
        baseVillageAdapter  = new BaseVillageAdapter(this, baseAdapterArrayList, this);
        final LinearLayoutManager Base = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        baseVillageFarmPowerList.setLayoutManager(Base);
        baseVillageFarmPowerList.setAdapter(baseVillageAdapter);
        TextView baseVillageAddRow = (TextView) findViewById(R.id.baseVillageAddRow);
        baseVillageAddRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBaseDialog(-1);
            }
        });

        householdFarmPowerList = (RecyclerView) findViewById(R.id.householdFarmPowerList);
        houseHoldAdapter = new HouseHoldAdapter(this, householdArrayList, this);
        final LinearLayoutManager household = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        householdFarmPowerList.setLayoutManager(household);
        householdFarmPowerList.setAdapter(houseHoldAdapter);
        TextView baseHouseAddRow = (TextView) findViewById(R.id.baseHouseAddRow);
        baseHouseAddRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showhouseHoldDialog(-1);
            }
        });

        meetingFarmPowerList = (RecyclerView) findViewById(R.id.meetingFarmPowerList);
        meetingAdapter = new MeetingAdapter(this, meetingsArrayList, this);
        final LinearLayoutManager meeting = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        meetingFarmPowerList.setLayoutManager(meeting);
        meetingFarmPowerList.setAdapter(meetingAdapter);
        TextView meetingAddRow = (TextView) findViewById(R.id.meetingAddRow);
        meetingAddRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showmeetingDialog(-1);
            }
        });

        villageDevelopmentFarmPowerList = (RecyclerView) findViewById(R.id.villageDevelopmentFarmPowerList);
        villageDevelopmentAdapter = new VillageDevelopmentAdapter(this, villageDevelopmentArrayList, this);
        final LinearLayoutManager villageDevelopment = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        villageDevelopmentFarmPowerList.setLayoutManager(villageDevelopment);
        villageDevelopmentFarmPowerList.setAdapter(villageDevelopmentAdapter);
        TextView villageDevelopmentAddRow = (TextView) findViewById(R.id.villageDevelopmentAddRow);
        villageDevelopmentAddRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showvillageDevelopmentDialog(-1);
            }
        });

        salientFarmPowerList = (RecyclerView) findViewById(R.id.salientFarmPowerList);
        salientAdapter = new SalientAdapter(this, salientArrayList, this);
        final LinearLayoutManager salient = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        salientFarmPowerList.setLayoutManager(salient);
        salientFarmPowerList.setAdapter(salientAdapter);
        TextView salientAddRow = (TextView) findViewById(R.id.salientAddRow);
        salientAddRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showsalientDialog(-1);
            }
        });

        technologiesFarmPowerList = (RecyclerView) findViewById(R.id.technologiesFarmPowerList);
        technologiesAdapter = new TechnologiesAdapter(this, technologiesArrayList, this);
        final LinearLayoutManager technologies = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        technologiesFarmPowerList.setLayoutManager(technologies);
        technologiesFarmPowerList.setAdapter(technologiesAdapter);
        TextView technologiesAddRow = (TextView) findViewById(R.id.technologiesAddRow);
        technologiesAddRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showtechnologiesDialog(-1);
            }
        });

        vdpFarmPowerList = (RecyclerView) findViewById(R.id.vdpFarmPowerList);
        vdpAdapter = new VdpAdapter(this, vdpArrayList, this);
        final LinearLayoutManager vdp = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        vdpFarmPowerList.setLayoutManager(vdp);
        vdpFarmPowerList.setAdapter(vdpAdapter);
        TextView vdpAddplace = (TextView) findViewById(R.id.vdpAddplace);
        vdpAddplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showvdpDialog(-1);
            }
        });

        technologyDevelopmentFarmPowerList = (RecyclerView) findViewById(R.id.technologyDevelopmentFarmPowerList);
        technologyDevelopmentAdapter = new TechnologyDevelopmentAdapter(this, technologyDevelopmentArrayList, this);
        final LinearLayoutManager technologyDevelopment = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        technologyDevelopmentFarmPowerList.setLayoutManager(technologyDevelopment);
        technologyDevelopmentFarmPowerList.setAdapter(technologyDevelopmentAdapter);
        final TextView technologyDevelopmentFarmPowerList = (TextView) findViewById(R.id.technologyDevelopmentAddField);
        technologyDevelopmentFarmPowerList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showtechnologyDevelopmentDialog(-1);
            }
        });

        technologycustomisationFarmPowerList = (RecyclerView) findViewById(R.id.technologycustomisationFarmPowerList);
        technologycustomisationAdapter = new TechnologyCustomisationAdapter(this, technologyCustomisationArrayList, this);
        final LinearLayoutManager technologycustomisation = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        technologycustomisationFarmPowerList.setLayoutManager(technologycustomisation);
        technologycustomisationFarmPowerList.setAdapter(technologycustomisationAdapter);
        TextView technologyAddAction = (TextView) findViewById(R.id.technologyAddAction);
        technologyAddAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showtechnologycustomisationDialog(-1);
            }
        });

        miscellaneousarmPowerList = (RecyclerView) findViewById(R.id.miscellaneousarmPowerList);
        miscellaneousAdapter = new MiscellaneousAdapter(this, miscellaneousArrayList, this);
        final LinearLayoutManager miscellaneous = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        miscellaneousarmPowerList.setLayoutManager(miscellaneous);
        miscellaneousarmPowerList.setAdapter(miscellaneousAdapter);
        TextView miscellaneousAddAction = (TextView) findViewById(R.id.miscellaneousAddAction);
        miscellaneousAddAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showmiscellaneousDialog(-1);
            }
        });

        final Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ActionPlan tempmainBean = new ActionPlan(
                        majorFrmPowerArrayList,
                        ubaArrayList,
                        villageArrayList,
                        workinggroupArrayList,
                        academicArrayList,
                        activityDetailArrayList,
                        baseAdapterArrayList,
                        householdArrayList,
                        meetingsArrayList,
                        villageDevelopmentArrayList,
                        salientArrayList,
                        technologiesArrayList,
                        vdpArrayList,
                        technologyDevelopmentArrayList,
                        technologyCustomisationArrayList,
                        miscellaneousArrayList,
                        institutionparicipatingInstitution.getText().toString(),
                        institutioncoordinate.getText().toString(),
                        institutionEmail.getText().toString(),
                        institutionContact.getText().toString(),
                        institutionVillage.getText().toString(),
                        institutionDistrict.getText().toString(),
                        activityStudent.getText().toString(),
                        activityStatus.getText().toString(),
                        activitystudentRemark.getText().toString(),
                        activityMeeting.getText().toString(),
                        activityMeetingStatus.getText().toString(),
                        activityMeetingRemark.getText().toString(),
                        activityInteraction.getText().toString(),
                        activityInteractionStatus.getText().toString(),
                        activityInteractionRemark.getText().toString(),
                        activityAwareness.getText().toString(),
                        activityAwarenessStatus.getText().toString(),
                        activityAwarenessRemark.getText().toString(),
                        reportingPeriod.getText().toString(),
                        holy.getText().toString(),
                        ExtraCurricular.getText().toString(),
                        interactionWithRural.getText().toString(),
                        interactionWithDistrict.getText().toString(),
                        activityPotential.getText().toString(),
                        activityChallenges.getText().toString(),
                        activityAnyOther.getText().toString()
                        );
                String jsonVal = new Gson().toJson(tempmainBean);
                Log.e("xxxxxxxxxxxxx", jsonVal);
                if (mainbean == null) {
                    tempmainBean.setId(String.valueOf(System.currentTimeMillis()));
                } else {
                    tempmainBean.setId(mainbean.id);
                }
                getCreateTest(tempmainBean.id,  sharedpreferences.getString(buSurveyerId, ""), jsonVal);

            }
        });

    }


    public void showMajorDialog(final int position) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivityAction.this);
        LayoutInflater inflater = MainActivityAction.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.majord, null);

        final TextView submit = (TextView) dialogView.findViewById(R.id.submit);
        final EditText issue = (EditText) dialogView.findViewById(R.id.issue);
        final EditText proposed = (EditText) dialogView.findViewById(R.id.proposed);
        final EditText formulate = (EditText) dialogView.findViewById(R.id.formulate);

        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();


        if (position != -1) {
            submit.setText("UPDATE");
            Major bean = majorFrmPowerArrayList.get(position);
            issue.setText(bean.getIssue());
            proposed.setText(bean.getProposed());
            formulate.setText(bean.getFormulate());

        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == -1) {
                    Major bean = new Major(issue.getText().toString(),
                            proposed.getText().toString(), formulate.getText().toString()

                    );
                    majorFrmPowerArrayList.add(bean);
                } else {
                    majorFrmPowerArrayList.get(position).setIssue(issue.getText().toString());
                    majorFrmPowerArrayList.get(position).setProposed(proposed.getText().toString());
                    majorFrmPowerArrayList.get(position).setFormulate(formulate.getText().toString());
                }
                majorAdapter.notifyData(majorFrmPowerArrayList);
                b.cancel();
            }
        });
        b.show();
    }

    public void showubaDialog(final int position) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivityAction.this);
        LayoutInflater inflater = MainActivityAction.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.ubad, null);

        final TextView submit = (TextView) dialogView.findViewById(R.id.submit);
        final EditText ubaPostal = (EditText) dialogView.findViewById(R.id.ubaPostal);
        final EditText ubaTelephone = (EditText) dialogView.findViewById(R.id.ubaTelephone);
        final EditText ubaMobile = (EditText) dialogView.findViewById(R.id.ubaMobile);
        final EditText ubaFax = (EditText) dialogView.findViewById(R.id.ubaFax);
        final EditText ubaEmail = (EditText) dialogView.findViewById(R.id.ubaEmail);


        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();


        if (position != -1) {
            submit.setText("UPDATE");
            Uba bean = ubaArrayList.get(position);
            ubaPostal.setText(bean.getubaPostal());
            ubaTelephone.setText(bean.getUbaTelephone());
            ubaMobile.setText(bean.getUbaMobile());
            ubaFax.setText(bean.getUbaFax());
            ubaEmail.setText(bean.getUbaEmail());

        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == -1) {
                    Uba bean = new Uba(ubaPostal.getText().toString(),
                            ubaTelephone.getText().toString(),
                            ubaMobile.getText().toString(),
                            ubaFax.getText().toString(),
                            ubaEmail.getText().toString()

                    );
                    ubaArrayList.add(bean);
                } else {
                    ubaArrayList.get(position).setUbaPostal(ubaPostal.getText().toString());
                    ubaArrayList.get(position).setUbaTelephone(ubaTelephone.getText().toString());
                    ubaArrayList.get(position).setUbaMobile(ubaMobile.getText().toString());
                    ubaArrayList.get(position).setUbaFax(ubaFax.getText().toString());
                    ubaArrayList.get(position).setUbaEmail(ubaEmail.getText().toString());
                }
                UbaAdapter.notifyData(ubaArrayList);
                b.cancel();
            }
        });
        b.show();
    }

    public void showvillageDialog(final int position) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivityAction.this);
        LayoutInflater inflater = MainActivityAction.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.villaged, null);

        final TextView submit = (TextView) dialogView.findViewById(R.id.submit);
        final EditText villageSeial = (EditText) dialogView.findViewById(R.id.villageSerial);
        final EditText villageBlock = (EditText) dialogView.findViewById(R.id.villageBlock);
        final EditText villageDistrict = (EditText) dialogView.findViewById(R.id.villageDistrict);
        final EditText villageHeadOfVillage = (EditText) dialogView.findViewById(R.id.villageHeadOfVillage);
        final EditText villageKeyContact = (EditText) dialogView.findViewById(R.id.villageKeyContact);

        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();


        if (position != -1) {
            submit.setText("UPDATE");
            Village bean = villageArrayList.get(position);
            villageSeial.setText(bean.getVillageSerial());
            villageBlock.setText(bean.getVillageBlock());
            villageDistrict.setText(bean.getVillageDistrict());
            villageHeadOfVillage.setText(bean.getVillageHeadOfVillage());
            villageKeyContact.setText(bean.getVillageKeyContact());

        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == -1) {
                    Village bean = new Village(villageSeial.getText().toString(),
                            villageBlock.getText().toString(), villageDistrict.getText().toString(), villageHeadOfVillage.getText().toString(), villageKeyContact.getText().toString()

                    );
                    villageArrayList.add(bean);
                } else {
                    villageArrayList.get(position).setVillageDevelopmentSerial(villageSeial.getText().toString());
                    villageArrayList.get(position).setVillageDevelopmentName(villageBlock.getText().toString());
                    villageArrayList.get(position).setVillageDevelopmentStatus(villageDistrict.getText().toString());
                    villageArrayList.get(position).setVillageDevelopmentStatus(villageHeadOfVillage.getText().toString());
                    villageArrayList.get(position).setVillageDevelopmentStatus(villageKeyContact.getText().toString());
                }
                VillageAdapter.notifyData(villageArrayList);
                b.cancel();
            }
        });
        b.show();
    }

    public void showworkinggroupDialog(final int position) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivityAction.this);
        LayoutInflater inflater = MainActivityAction.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.working_groupd, null);

        final TextView submit = (TextView) dialogView.findViewById(R.id.submit);
        final EditText workingGroupSerial = (EditText) dialogView.findViewById(R.id.workingGroupSerial);
        final EditText workingGroupName = (EditText) dialogView.findViewById(R.id.workingGroupName);
        final EditText workingGroupcoordinator = (EditText) dialogView.findViewById(R.id.workingGroupcoordinator);
        final EditText workingGroupContact = (EditText) dialogView.findViewById(R.id.workingGroupContact);
        final EditText workingGroupSubject = (EditText) dialogView.findViewById(R.id.workingGroupSubject);
        final EditText workingGroupDepartment = (EditText) dialogView.findViewById(R.id.workingGroupDepartment);
        final EditText workingGroupAreaOfInterest = (EditText) dialogView.findViewById(R.id.workingGroupAreaOfInterest);

        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();


        if (position != -1) {
            submit.setText("UPDATE");
            Workinggroup bean = workinggroupArrayList.get(position);
            workingGroupSerial.setText(bean.getWorkingGroupSerial());
            workingGroupName.setText(bean.getWorkingGroupName());
            workingGroupcoordinator.setText(bean.getWorkingGroupcoordinator());
            workingGroupDepartment.setText(bean.getWorkingGroupDepartment());
            workingGroupContact.setText(bean.getWorkingGroupContact());
            workingGroupAreaOfInterest.setText(bean.getWorkingGroupAreaOfInterest());
            workingGroupSubject.setText(bean.getWorkingGroupSubject());


        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == -1) {
                    Workinggroup bean = new Workinggroup(workingGroupSerial.getText().toString(),
                            workingGroupName.getText().toString(), workingGroupcoordinator.getText().toString(), workingGroupDepartment.getText().toString(), workingGroupContact.getText().toString(), workingGroupAreaOfInterest.getText().toString(), workingGroupSubject.getText().toString()

                    );

                    workinggroupArrayList.add(bean);
                } else {
                    workinggroupArrayList.get(position).setWorkingGroupSerial(workingGroupSerial.getText().toString());
                    workinggroupArrayList.get(position).setWorkingGroupName(workingGroupName.getText().toString());
                    workinggroupArrayList.get(position).setWorkingGroupcoordinator(workingGroupcoordinator.getText().toString());
                    workinggroupArrayList.get(position).setWorkingGroupDepartment(workingGroupDepartment.getText().toString());
                    workinggroupArrayList.get(position).setWorkingGroupContact(workingGroupContact.getText().toString());
                    workinggroupArrayList.get(position).setWorkingGroupSubject(workingGroupSubject.getText().toString());
                    workinggroupArrayList.get(position).setWorkingGroupAreaOfInterest(workingGroupAreaOfInterest.getText().toString());
                }
                workingGroupAdapter.notifyData(workinggroupArrayList);
                b.cancel();
            }
        });
        b.show();
    }

    public void showacademicDialog(final int position) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivityAction.this);
        LayoutInflater inflater = MainActivityAction.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.academicd, null);

        final TextView submit = (TextView) dialogView.findViewById(R.id.submit);
        final EditText academicSerial = (EditText) dialogView.findViewById(R.id.academicSerial);
        final EditText academicAcademicActivity = (EditText) dialogView.findViewById(R.id.academicAcademicActivity);
        final EditText academicFaculty = (EditText) dialogView.findViewById(R.id.academicFaculty);
        final EditText academicTopic = (EditText) dialogView.findViewById(R.id.academicTopic);
        final EditText academicKey = (EditText) dialogView.findViewById(R.id.academicKey);

        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();


        if (position != -1) {
            submit.setText("UPDATE");
            Academic bean = academicArrayList.get(position);
            academicSerial.setText(bean.getAcademicSerial());
            academicAcademicActivity.setText(bean.getAcademicAcademicActivity());
            academicFaculty.setText(bean.getAcademicFaculty());
            academicTopic.setText(bean.getAcademicTopic());
            academicKey.setText(bean.getAcademicKey());
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == -1) {
                    Academic bean = new Academic(academicSerial.getText().toString(),
                            academicAcademicActivity.getText().toString(), academicFaculty.getText().toString(), academicTopic.getText().toString(), academicTopic.getText().toString(), academicKey.getText().toString()

                    );

                    academicArrayList.add(bean);
                } else {
                    academicArrayList.get(position).setAcademicSerial(academicSerial.getText().toString());
                    academicArrayList.get(position).setAcademicAcademicActivity(academicAcademicActivity.getText().toString());
                    academicArrayList.get(position).setAcademicFaculty(academicFaculty.getText().toString());
                    academicArrayList.get(position).setAcademicTopic(academicTopic.getText().toString());
                    academicArrayList.get(position).setAcademicKey(academicKey.getText().toString());

                }
                academicAdapter.notifyData(academicArrayList);
                b.cancel();
            }
        });
        b.show();
    }

    public void showactivityDetailDialog(final int position) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivityAction.this);
        LayoutInflater inflater = MainActivityAction.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_detaild, null);

        final TextView submit = (TextView) dialogView.findViewById(R.id.submit);
        final EditText activityDetailSerialNo = (EditText) dialogView.findViewById(R.id.activityDetailSerialNo);
        final EditText activityDetailDate = (EditText) dialogView.findViewById(R.id.activityDetailDate);
        final EditText activityDetailVillage = (EditText) dialogView.findViewById(R.id.activityDetailVillage);
        final EditText activityDetailActivity = (EditText) dialogView.findViewById(R.id.activityDetailActivity);
        final EditText activityDetailFaculty = (EditText) dialogView.findViewById(R.id.activityDetailFaculty);


        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();


        if (position != -1) {
            submit.setText("UPDATE");
            ActivityDetail bean = activityDetailArrayList.get(position);
            activityDetailSerialNo.setText(bean.getActivityDetailSerialNo());
            activityDetailDate.setText(bean.getActivityDetailDate());
            activityDetailVillage.setText(bean.getActivityDetailVillage());
            activityDetailActivity.setText(bean.getActivityDetailActivity());
            activityDetailFaculty.setText(bean.getActivityDetailFaculty());
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == -1) {
                    ActivityDetail bean = new ActivityDetail(activityDetailSerialNo.getText().toString(),
                            activityDetailDate.getText().toString(), activityDetailVillage.getText().toString(), activityDetailActivity.getText().toString(), activityDetailFaculty.getText().toString()

                    );

                    activityDetailArrayList.add(bean);
                } else {
                    activityDetailArrayList.get(position).setActivityDetailSerialNo(activityDetailSerialNo.getText().toString());
                    activityDetailArrayList.get(position).setActivityDetailDate(activityDetailDate.getText().toString());
                    activityDetailArrayList.get(position).setActivityDetailVillage(activityDetailVillage.getText().toString());
                    activityDetailArrayList.get(position).setActivityDetailActivity(activityDetailActivity.getText().toString());
                    activityDetailArrayList.get(position).setActivityDetailFaculty(activityDetailFaculty.getText().toString());
                }
                activityDetailAdapter.notifyData(activityDetailArrayList);
                b.cancel();
            }
        });
        b.show();
    }

    public void showBaseDialog(final int position) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivityAction.this);
        LayoutInflater inflater = MainActivityAction.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.base_villaged, null);

        final TextView submit = (TextView) dialogView.findViewById(R.id.submit);
        final EditText baseVillageSerial = (EditText) dialogView.findViewById(R.id.baseVillageSerial);
        final EditText baseVillageVillage = (EditText) dialogView.findViewById(R.id.baseVillageVillage);
        final EditText baseVillageComplete = (EditText) dialogView.findViewById(R.id.baseVillageComplete);


        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();


        if (position != -1) {
            submit.setText("UPDATE");
            Base bean = baseAdapterArrayList.get(position);
            baseVillageSerial.setText(bean.getBaseVillageSerial());
            baseVillageVillage.setText(bean.getBaseVillageVillage());
            baseVillageComplete.setText(bean.getbaseVillageComplete());

        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == -1) {
                    Base bean = new Base(baseVillageSerial.getText().toString(),
                            baseVillageVillage.getText().toString(), baseVillageComplete.getText().toString()

                    );

                    baseAdapterArrayList.add(bean);
                } else {
                    baseAdapterArrayList.get(position).setBaseVillageSerial(baseVillageSerial.getText().toString());
                    baseAdapterArrayList.get(position).setBaseVillageVillage(baseVillageVillage.getText().toString());
                    baseAdapterArrayList.get(position).setbaseVillageComplete(baseVillageComplete.getText().toString());

                }
                baseVillageAdapter.notifyData(baseAdapterArrayList);
                b.cancel();
            }
        });
        b.show();
    }

    public void showhouseHoldDialog(final int position) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivityAction.this);
        LayoutInflater inflater = MainActivityAction.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.householdd, null);

        final TextView submit = (TextView) dialogView.findViewById(R.id.submit);
        final EditText householdSerial = (EditText) dialogView.findViewById(R.id.householdVillage);
        final EditText householdVillage = (EditText) dialogView.findViewById(R.id.householdVillage);
        final EditText householdComplete = (EditText) dialogView.findViewById(R.id.householdComplete);
        final EditText householdToComplete = (EditText) dialogView.findViewById(R.id.householdToComplete);

        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();


        if (position != -1) {
            submit.setText("UPDATE");
            HouseHold bean = householdArrayList.get(position);
            householdSerial.setText(bean.getHouseholdSerial());
            householdVillage.setText(bean.getHouseholdVillage());
            householdComplete.setText(bean.getHouseholdComplete());
            householdToComplete.setText(bean.getHouseholdToComplete());

        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == -1) {
                    HouseHold bean = new HouseHold(householdSerial.getText().toString(),
                            householdVillage.getText().toString(), householdComplete.getText().toString(), householdToComplete.getText().toString()

                    );

                    householdArrayList.add(bean);
                } else {
                    householdArrayList.get(position).setHouseholdSerial(householdSerial.getText().toString());
                    householdArrayList.get(position).setHouseholdVillage(householdVillage.getText().toString());
                    householdArrayList.get(position).setHouseholdComplete(householdComplete.getText().toString());
                    householdArrayList.get(position).setHouseholdToComplete(householdToComplete.getText().toString());
                }
                houseHoldAdapter.notifyData(householdArrayList);
                b.cancel();
            }
        });
        b.show();
    }

    public void showmeetingDialog(final int position) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivityAction.this);
        LayoutInflater inflater = MainActivityAction.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.meetingd, null);

        final TextView submit = (TextView) dialogView.findViewById(R.id.submit);
        final EditText meetingSeial = (EditText) dialogView.findViewById(R.id.meetingSerial);
        final EditText meetingName = (EditText) dialogView.findViewById(R.id.meetingName);
        final EditText meetingDate = (EditText) dialogView.findViewById(R.id.meetingDate);
        final EditText meetingNoOfParticipants = (EditText) dialogView.findViewById(R.id.meetingNoOfParticipants);
        final EditText meetingKeyOutcomes = (EditText) dialogView.findViewById(R.id.meetingKeyOutcomes);

        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();


        if (position != -1) {
            submit.setText("UPDATE");
            Meeting bean = meetingsArrayList.get(position);
            meetingSeial.setText(bean.getMeetingSerial());
            meetingName.setText(bean.getMeetingName());
            meetingDate.setText(bean.getMeetingDate());
            meetingNoOfParticipants.setText(bean.getMeetingNoOfParticipants());
            meetingKeyOutcomes.setText(bean.getMeetingKeyOutcomes());

        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == -1) {
                    Meeting bean = new Meeting(meetingSeial.getText().toString(),
                            meetingName.getText().toString(), meetingDate.getText().toString(), meetingNoOfParticipants.getText().toString(), meetingKeyOutcomes.getText().toString()

                    );

                    meetingsArrayList.add(bean);
                } else {
                    meetingsArrayList.get(position).setMeetingSerial(meetingSeial.getText().toString());
                    meetingsArrayList.get(position).setMeetingName(meetingName.getText().toString());
                    meetingsArrayList.get(position).setMeetingDate(meetingDate.getText().toString());
                    meetingsArrayList.get(position).setMeetingNoOfParticipants(meetingNoOfParticipants.getText().toString());
                    meetingsArrayList.get(position).getMeetingKeyOutcomes(meetingKeyOutcomes.getText().toString());
                }
                meetingAdapter.notifyData(meetingsArrayList);
                b.cancel();
            }
        });
        b.show();
    }

    public void showvillageDevelopmentDialog(final int position) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivityAction.this);
        LayoutInflater inflater = MainActivityAction.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.village_developmentd, null);

        final TextView submit = (TextView) dialogView.findViewById(R.id.submit);
        final EditText villageDevelopmentSerial = (EditText) dialogView.findViewById(R.id.villageDevelopmentSerial);
        final EditText villageDevelopmentName = (EditText) dialogView.findViewById(R.id.villageDevelopmentName);
        final EditText villageDevelopmentStatus = (EditText) dialogView.findViewById(R.id.villageDevelopmentStatus);

        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();


        if (position != -1) {
            submit.setText("UPDATE");
            VillageDevelopment bean = villageDevelopmentArrayList.get(position);
            villageDevelopmentSerial.setText(bean.getVillageDevelopmentSerial());
            villageDevelopmentName.setText(bean.getVillageDevelopmentName());
            villageDevelopmentStatus.setText(bean.getVillageDevelopmentStatus());
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == -1) {
                    VillageDevelopment bean = new VillageDevelopment(villageDevelopmentSerial.getText().toString(),
                            villageDevelopmentSerial.getText().toString(), villageDevelopmentStatus.getText().toString()
                    );

                    villageDevelopmentArrayList.add(bean);
                } else {
                    villageDevelopmentArrayList.get(position).setVillageDevelopmentSerial(villageDevelopmentSerial.getText().toString());
                    villageDevelopmentArrayList.get(position).setVillageDevelopmentName(villageDevelopmentName.getText().toString());
                    villageDevelopmentArrayList.get(position).setVillageDevelopmentStatus(villageDevelopmentStatus.getText().toString());
                }
                villageDevelopmentAdapter.notifyData(villageDevelopmentArrayList);
                b.cancel();
            }
        });
        b.show();
    }

    public void showsalientDialog(final int position) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivityAction.this);
        LayoutInflater inflater = MainActivityAction.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.salientd, null);

        final TextView submit = (TextView) dialogView.findViewById(R.id.submit);
        final EditText salientSerial = (EditText) dialogView.findViewById(R.id.salientSerial);
        final EditText salientProject = (EditText) dialogView.findViewById(R.id.salientProject);
        final EditText salientTimelines = (EditText) dialogView.findViewById(R.id.salientTimelines);
        final EditText salientSourceOfFund = (EditText) dialogView.findViewById(R.id.salientSourceOfFunding);
        final EditText salientCost = (EditText) dialogView.findViewById(R.id.salientCost);
        final EditText salientAreaOfIntervention = (EditText) dialogView.findViewById(R.id.salientAreaOfIntervention);
        final EditText salientExpectedOutcomes = (EditText) dialogView.findViewById(R.id.salientExpectedOutcomes);

        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();


        if (position != -1) {
            submit.setText("UPDATE");
            Salient bean = salientArrayList.get(position);
            salientSerial.setText(bean.getSalientSerial());
            salientProject.setText(bean.getSalientProject());
            salientTimelines.setText(bean.getSalientTimelines());
            salientSourceOfFund.setText(bean.getSalientSourceOfFund());
            salientCost.setText(bean.getSalientCost());
            salientAreaOfIntervention.setText(bean.getSalientAreaOfIntervention());
            salientExpectedOutcomes.setText(bean.getExpectedOutcomes());


        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == -1) {
                    Salient bean = new Salient(salientSerial.getText().toString(),
                            salientProject.getText().toString(), salientTimelines.getText().toString(), salientSourceOfFund.getText().toString(), salientCost.getText().toString(), salientAreaOfIntervention.getText().toString(), salientExpectedOutcomes.getText().toString()

                    );

                    salientArrayList.add(bean);
                } else {
                    salientArrayList.get(position).setSalientSerial(salientSerial.getText().toString());
                    salientArrayList.get(position).setSalientProject(salientProject.getText().toString());
                    salientArrayList.get(position).setSalientTimelines(salientTimelines.getText().toString());
                    salientArrayList.get(position).setSalientSourceOfFund(salientSourceOfFund.getText().toString());
                    salientArrayList.get(position).setSalientCost(salientCost.getText().toString());
                    salientArrayList.get(position).setSalientAreaOfIntervention(salientAreaOfIntervention.getText().toString());
                    salientArrayList.get(position).salientExpectedOutcomes(salientExpectedOutcomes.getText().toString());
                }
                salientAdapter.notifyData(salientArrayList);
                b.cancel();
            }
        });
        b.show();
    }

    public void showtechnologiesDialog(final int position) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivityAction.this);
        LayoutInflater inflater = MainActivityAction.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.technologiesd, null);

        final TextView submit = (TextView) dialogView.findViewById(R.id.submit);
        final EditText technologiesSerial = (EditText) dialogView.findViewById(R.id.technologiesSerial);
        final EditText technologiesTechnology = (EditText) dialogView.findViewById(R.id.technologiesTechnology);
        final EditText technologiesSubjectArea = (EditText) dialogView.findViewById(R.id.technologiesSubjectArea);
        final EditText technologiesWhetherTranferred = (EditText) dialogView.findViewById(R.id.technologiesWhetherTransferred);
        final EditText technologiesImpact = (EditText) dialogView.findViewById(R.id.technologiesImpact);

        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();


        if (position != -1) {
            submit.setText("UPDATE");
            Technologies bean = technologiesArrayList.get(position);
            technologiesSerial.setText(bean.getTechnologiesSerial());
            technologiesTechnology.setText(bean.getTechnologiesTechnology());
            technologiesSubjectArea.setText(bean.getTechnologiesSubjectArea());
            technologiesWhetherTranferred.setText(bean.getTechnologiesWhetherTransferred());
            technologiesImpact.setText(bean.getTechnologiesImpact());


        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == -1) {
                    Technologies bean = new Technologies(technologiesSerial.getText().toString(),
                            technologiesTechnology.getText().toString(), technologiesSubjectArea.getText().toString(), technologiesWhetherTranferred.getText().toString(), technologiesImpact.getText().toString()

                    );

                    technologiesArrayList.add(bean);
                } else {
                    technologiesArrayList.get(position).setTechnologiesSerial(technologiesSerial.getText().toString());
                    technologiesArrayList.get(position).setTechnologiesTechnology(technologiesTechnology.getText().toString());
                    technologiesArrayList.get(position).setTechnologiesSubjectArea(technologiesSubjectArea.getText().toString());
                    technologiesArrayList.get(position).setTechnologiesWhetherTransferred(technologiesWhetherTranferred.getText().toString());
                    technologiesArrayList.get(position).setTechnologiesImpact(technologiesImpact.getText().toString());
                }
                technologiesAdapter.notifyData(technologiesArrayList);
                b.cancel();
            }
        });
        b.show();
    }

    public void showvdpDialog(final int position) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivityAction.this);
        LayoutInflater inflater = MainActivityAction.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.vdpd, null);

        final TextView submit = (TextView) dialogView.findViewById(R.id.submit);
        final EditText vdpAddPlace = (EditText) dialogView.findViewById(R.id.vdpAddplace);
        final EditText vdpAction = (EditText) dialogView.findViewById(R.id.vdpAction);

        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();


        if (position != -1) {
            submit.setText("UPDATE");
            Vdp bean = vdpArrayList.get(position);
            vdpAddPlace.setText(bean.getVdpAddplace());
            vdpAction.setText(bean.getVdpAction());
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == -1) {
                    Vdp bean = new Vdp(vdpAddPlace.getText().toString(),
                            vdpAction.getText().toString()
                    );

                    vdpArrayList.add(bean);
                } else {
                    vdpArrayList.get(position).setVdpAddplace(vdpAddPlace.getText().toString());
                    vdpArrayList.get(position).setVdpAction(vdpAction.getText().toString());
                }
                vdpAdapter.notifyData(vdpArrayList);
                b.cancel();
            }
        });
        b.show();
    }

    public void showtechnologyDevelopmentDialog(final int position) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivityAction.this);
        LayoutInflater inflater = MainActivityAction.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.technology_developmentd, null);

        final TextView submit = (TextView) dialogView.findViewById(R.id.submit);
        final EditText technologyDevelopmentSerial = (EditText) dialogView.findViewById(R.id.technologyDevelopmentSerial);
        final EditText technologyDevelopmentTitle = (EditText) dialogView.findViewById(R.id.technologyDevelopmentTitle);
        final EditText technologyDevelopmentStatus = (EditText) dialogView.findViewById(R.id.technologyDevelopmentStatus);
        final EditText technologyDevelopmentComments = (EditText) dialogView.findViewById(R.id.technologyDevelopmentComments);

        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();


        if (position != -1) {
            submit.setText("UPDATE");
            TechnologyDevelopment bean = technologyDevelopmentArrayList.get(position);
            technologyDevelopmentSerial.setText(bean.getTechnologyDevelopmentSerial());
            technologyDevelopmentTitle.setText(bean.getTechnologyDevelopmentTitle());
            technologyDevelopmentStatus.setText(bean.getTechnologyDevelopmentStatus());
            technologyDevelopmentComments.setText(bean.getTechnologyDevelopmentStatus());

        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == -1) {
                    TechnologyDevelopment bean = new TechnologyDevelopment(technologyDevelopmentSerial.getText().toString(),
                            technologyDevelopmentTitle.getText().toString(), technologyDevelopmentStatus.getText().toString(), technologyDevelopmentComments.getText().toString()

                    );

                    technologyDevelopmentArrayList.add(bean);
                } else {
                    technologyDevelopmentArrayList.get(position).setTechnologyDevelopmentSerial(technologyDevelopmentSerial.getText().toString());
                    technologyDevelopmentArrayList.get(position).setTechnologyDevelopmentTitle(technologyDevelopmentTitle.getText().toString());
                    technologyDevelopmentArrayList.get(position).setTechnologyDevelopmentStatus(technologyDevelopmentStatus.getText().toString());
                    technologyDevelopmentArrayList.get(position).setTechnologyDevelopmentComments(technologyDevelopmentComments.getText().toString());

                }
                technologyDevelopmentAdapter.notifyData(technologyDevelopmentArrayList);
                b.cancel();
            }
        });
        b.show();
    }

    public void showtechnologycustomisationDialog(final int position) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivityAction.this);
        LayoutInflater inflater = MainActivityAction.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.technology_customisationd, null);

        final TextView submit = (TextView) dialogView.findViewById(R.id.submit);
        final EditText technologyCustomisationSerial = (EditText) dialogView.findViewById(R.id.technologyCustomisationSerial);
        final EditText technologyCustomisationTitle = (EditText) dialogView.findViewById(R.id.technologyCustomisationtTitle);
        final EditText technologyCustomisationStatus = (EditText) dialogView.findViewById(R.id.technologyCustomisationStatus);
        final EditText technologyCustomisationComments = (EditText) dialogView.findViewById(R.id.technologyCustomisationComments);
        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();


        if (position != -1) {
            submit.setText("UPDATE");
            TechnologyCustomisation bean = technologyCustomisationArrayList.get(position);
            technologyCustomisationSerial.setText(bean.getTechnologyCustomisationSerial());
            technologyCustomisationTitle.setText(bean.getTechnologyCustomisationtTitle());
            technologyCustomisationStatus.setText(bean.getTechnologyCustomisationStatus());
            technologyCustomisationComments.setText(bean.getTechnologyCustomisationComments());
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == -1) {
                    TechnologyCustomisation bean = new TechnologyCustomisation(technologyCustomisationSerial.getText().toString(),
                            technologyCustomisationTitle.getText().toString(), technologyCustomisationStatus.getText().toString(), technologyCustomisationComments.getText().toString()

                    );

                    technologyCustomisationArrayList.add(bean);
                } else {
                    technologyCustomisationArrayList.get(position).setTechnologyCustomisationSerial(technologyCustomisationSerial.getText().toString());
                    technologyCustomisationArrayList.get(position).setTechnologyCustomisationtTitle(technologyCustomisationTitle.getText().toString());
                    technologyCustomisationArrayList.get(position).setTechnologyCustomisationStatus(technologyCustomisationStatus.getText().toString());
                    technologyCustomisationArrayList.get(position).setTechnologyCustomisationComments(technologyCustomisationComments.getText().toString());
                }
                technologycustomisationAdapter.notifyData(technologyCustomisationArrayList);
                b.cancel();
            }
        });
        b.show();
    }

    public void showmiscellaneousDialog(final int position) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivityAction.this);
        LayoutInflater inflater = MainActivityAction.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.miscellaneousd, null);

        final TextView submit = (TextView) dialogView.findViewById(R.id.submit);
        final EditText miscellaneousSerial = (EditText) dialogView.findViewById(R.id.miscellaneousSerial);
        final EditText miscellaneousTitle = (EditText) dialogView.findViewById(R.id.miscellaneousTitle);
        final EditText miscellaneousStatus = (EditText) dialogView.findViewById(R.id.miscellaneousStatus);
        final EditText miscellaneousComments = (EditText) dialogView.findViewById(R.id.miscellaneousComments);

        dialogBuilder.setView(dialogView);
        final AlertDialog b = dialogBuilder.create();


        if (position != -1) {
            submit.setText("UPDATE");
            Miscellaneous bean = miscellaneousArrayList.get(position);
            miscellaneousSerial.setText(bean.getMiscellaneousSerial());
            miscellaneousTitle.setText(bean.getMiscellaneousTitle());
            miscellaneousStatus.setText(bean.getMiscellaneousStatus());
            miscellaneousComments.setText(bean.getMiscellaneousComments());
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == -1) {
                    Miscellaneous bean = new Miscellaneous(miscellaneousSerial.getText().toString(),
                            miscellaneousTitle.getText().toString(), miscellaneousStatus.getText().toString(), miscellaneousComments.getText().toString()
                    );

                    miscellaneousArrayList.add(bean);
                } else {
                    miscellaneousArrayList.get(position).setMiscellaneousSerial(miscellaneousSerial.getText().toString());
                    miscellaneousArrayList.get(position).setMiscellaneousTitle(miscellaneousTitle.getText().toString());
                    miscellaneousArrayList.get(position).setMiscellaneousStatus(miscellaneousStatus.getText().toString());
                    miscellaneousArrayList.get(position).setMiscellaneousComments(miscellaneousComments.getText().toString());
                }
                miscellaneousAdapter.notifyData(miscellaneousArrayList);
                b.cancel();
            }
        });
        b.show();
    }


    @Override
    public void itemMajorClick(int position) {
showMajorDialog(position);
    }

    @Override
    public void itemUbaClick(int position) {
        showubaDialog(position);

    }

    @Override
    public void itemVillageClick(int position) {
        showvillageDialog(position);

    }

    @Override
    public void itemWorkingClick(int position) {
        showworkinggroupDialog(position);

    }

    @Override
    public void itemAcademicClick(int position) {
        showacademicDialog(position);

    }

    @Override
    public void itemActivityDetailClick(int position) {
        showactivityDetailDialog(position);

    }

    @Override
    public void itemBaseVillageClick(int position) {
        showBaseDialog(position);

    }

    @Override
    public void itemHouseholdClick(int position) {
        showhouseHoldDialog(position);

    }

    @Override
    public void itemMeetingsClick(int position) {
        showmeetingDialog(position);

    }

    @Override
    public void itemSalientClick(int position) {
        showsalientDialog(position);
    }

    @Override
    public void itemTechnologiesClick(int position) {
        showtechnologiesDialog(position);
    }

    @Override
    public void itemVdpClick(int position) {
        showvdpDialog(position);
    }

    @Override
    public void itemTechnologyDevelopmentClick(int position) {
        showtechnologyDevelopmentDialog(position);
    }

    @Override
    public void itemTechnologyCustomisationClick(int position) {
        showtechnologycustomisationDialog(position);
    }

    @Override
    public void itemMiscellaneousClick(int position) {
        showmiscellaneousDialog(position);
    }

    private void getCreateTest(final String mId, final String surveyer, final String data) {
        this.pDialog.setMessage("Creating...");
        showDialog();
        StringRequest local16 = new StringRequest(1, "http://coconutfpo.smartfpo.com/breedcontest/UBA/common.php", new Response.Listener<String>() {
            public void onResponse(String paramString) {
                Log.d("tag", "Register Response: " + paramString.toString());
                hideDialog();
                try {
                    JSONObject localJSONObject1 = new JSONObject(paramString);
                    String str = localJSONObject1.getString("message");
                    if (localJSONObject1.getInt("success") == 1) {
                        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                    return;
                } catch (JSONException localJSONException) {
                    localJSONException.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError paramVolleyError) {
                Log.e("tag", "Fetch Error: " + paramVolleyError.getMessage());
                Toast.makeText(getApplicationContext(), paramVolleyError.getMessage(), Toast.LENGTH_SHORT).show();
                hideDialog();
            }
        }) {
            protected Map<String, String> getParams() {
                HashMap<String, String> localHashMap = new HashMap<String, String>();
                if (mId != null) {
                    localHashMap.put("id", mId);
                }
                localHashMap.put("formId", mId);
                localHashMap.put("surveyer", surveyer);
                localHashMap.put("data", data);


                return localHashMap;
            }
        };
        AppController.getInstance().addToRequestQueue(local16, TAG);
    }

    private void hideDialog() {

        if (this.pDialog.isShowing()) this.pDialog.dismiss();
    }

    private void showDialog() {

        if (!this.pDialog.isShowing()) this.pDialog.show();
    }
}