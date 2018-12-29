package nec.cst.pra.action;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import nec.cst.pra.R;


public class MasterAllSurveyAdapterAction extends RecyclerView.Adapter<MasterAllSurveyAdapterAction.MyViewHolder> {

    private Context mainActivityUser;
    private ArrayList<ActionPlan> moviesList;
    SharedPreferences sharedpreferences;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView institutionparicipatingInstitution,institutioncoordinate,institutionEmail;
        ImageView editImage, locationImage;
        LinearLayout parentLinear;

        public MyViewHolder(View view) {
            super(view);
            institutionparicipatingInstitution = (TextView) view.findViewById(R.id.institutionparicipatingInstitution);
            institutioncoordinate = (TextView) view.findViewById(R.id.institutioncoordinate);
            institutionEmail = (TextView) view.findViewById(R.id.institutionEmail);
            parentLinear = (LinearLayout) view.findViewById(R.id.parentLinear);
        }
    }


    public MasterAllSurveyAdapterAction(Context mainActivityUser, ArrayList<ActionPlan> moviesList) {
        this.moviesList = moviesList;
        this.mainActivityUser = mainActivityUser;

    }

    public void notifyData(ArrayList<ActionPlan> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.moviesList = myList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.survey_list_row_action, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        ActionPlan mainbean = moviesList.get(position);
        holder.institutionparicipatingInstitution.setText(mainbean.getInstitutionparicipatingInstitution());
        //holder.age.setText(bean.getGeoTag());
        //holder.education.setText(bean.getId());
        holder.institutioncoordinate.setText(mainbean.getInstitutioncoordinate());
        holder.institutionEmail.setText(mainbean.getInstitutionEmail());

        if (position % 2 == 0) {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.viewBg));
        } else {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.white));
        }

        holder.parentLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainActivityUser, MainActivityAction.class);
                intent.putExtra("object", moviesList.get(position));
                mainActivityUser.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public static String round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return String.valueOf((double) tmp / factor);
    }

}
