package nec.cst.pra.action.activitydetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import nec.cst.pra.action.OnItemClick;
import nec.cst.pra.R;

import java.util.ArrayList;

/**
 * Created by SanAji on 30-11-2018.
 */



    public class ActivityDetailAdapter extends RecyclerView.Adapter<ActivityDetailAdapter.MyViewHolder>  {

        public Context mainActivityUser;
        public ArrayList<ActivityDetail> activitydetailList;
        public OnItemClick onItemClick;

    public static void notifyData(EditText activityDetailActivity) {
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

            private TextView activityDetailSerialNo,activityDetailDate,activityDetailVillage,activityDetailActivity,activityDetailFaculty;
            LinearLayout parentLinear;

            public MyViewHolder(View view){
                super((view));
                activityDetailSerialNo =(TextView) view.findViewById(R.id.activityDetailSerialNo);
                activityDetailDate =(TextView) view.findViewById(R.id.activityDetailDate);
                activityDetailVillage =(TextView) view.findViewById(R.id.activityDetailVillage);
                activityDetailActivity =(TextView) view.findViewById(R.id.activityDetailDate);
                activityDetailFaculty =(TextView) view.findViewById(R.id.activityDetailFaculty);
                parentLinear = (LinearLayout) view.findViewById(R.id.parentLinear);

            }
        }
        public ActivityDetailAdapter(Context mainActivityUser, ArrayList<ActivityDetail> activitydetailList, OnItemClick onItemClick) {
            this.activitydetailList = activitydetailList;
            this.mainActivityUser = mainActivityUser;
            this.onItemClick = onItemClick;
        }
        public void notifyData(ArrayList<ActivityDetail> myList) {
            Log.d("notifyData ", myList.size() + "");
            this.activitydetailList = myList;
            notifyDataSetChanged();
        }
        public ActivityDetailAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activitydetail_preview, parent, false);

            return new ActivityDetailAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ActivityDetailAdapter.MyViewHolder holder, final int position) {
            ActivityDetail bean = activitydetailList.get(position);
            holder.activityDetailSerialNo.setText(bean.getActivityDetailSerialNo());
            holder.activityDetailDate.setText(bean.getActivityDetailDate());
            holder.activityDetailVillage.setText(bean.getActivityDetailVillage());
            holder.activityDetailActivity.setText(bean.getActivityDetailActivity());
            holder.activityDetailFaculty.setText(bean.getActivityDetailFaculty());


            if (position % 2 == 0) {
                holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.viewBg));
            } else {
                holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.white));
            }

            holder.parentLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.itemAcademicClick(position);
                }
            });
        }
        public  int getItemCount(){
            return activitydetailList.size();
        }


    }
