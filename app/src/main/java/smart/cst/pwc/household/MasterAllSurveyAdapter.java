package smart.cst.pwc.household;

import android.content.Context;
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

import smart.cst.pwc.R;


public class MasterAllSurveyAdapter extends RecyclerView.Adapter<MasterAllSurveyAdapter.MyViewHolder> {

    private Context mainActivityUser;
    private ArrayList<Mainbean> moviesList;
    OnSurveyClick onSurveyClick;
    SharedPreferences sharedpreferences;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView village, wardNo, district;
        ImageView editImage, locationImage;
        LinearLayout parentLinear;

        public MyViewHolder(View view) {
            super(view);
            village = (TextView) view.findViewById(R.id.village);
            wardNo = (TextView) view.findViewById(R.id.wardNo);
            district = (TextView) view.findViewById(R.id.district);
            editImage = (ImageView) view.findViewById(R.id.editImage);
            district = (TextView) view.findViewById(R.id.district);
            locationImage = (ImageView) view.findViewById(R.id.locationImage);

            parentLinear = (LinearLayout) view.findViewById(R.id.parentLinear);
        }
    }


    public MasterAllSurveyAdapter(Context mainActivityUser,
                                  ArrayList<Mainbean> moviesList, OnSurveyClick onSurveyClick) {
        this.moviesList = moviesList;
        this.mainActivityUser = mainActivityUser;
        this.onSurveyClick = onSurveyClick;

    }

    public void notifyData(ArrayList<Mainbean> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.moviesList = myList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.survey_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Mainbean bean = moviesList.get(position);
        holder.village.setText(bean.village);
        //holder.age.setText(bean.getGeoTag());
        //holder.education.setText(bean.getId());
        holder.wardNo.setText(bean.wardNo);
        holder.district.setText(bean.district);

        if (position % 2 == 0) {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.viewBg));
        } else {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.white));
        }

        holder.editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             onSurveyClick.onEditClick(position);
            }
        });

        holder.locationImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSurveyClick.onPrintClick(position);
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
