package smart.cst.pwc.action.academic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import smart.cst.pwc.action.OnItemClick;
import smart.cst.pwc.R;

import java.util.ArrayList;

/**
 * Created by SanAji on 30-11-2018.
 */



    public class AcademicAdapter extends RecyclerView.Adapter<AcademicAdapter.MyViewHolder> {

        public Context mainActivityUser;
        public ArrayList<Academic> academicList;
        public OnItemClick onItemClick;

    public void notifyData(AcademicAdapter activityDetailAdapter) {
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

            private TextView academicSerial,academicAcademicActivity,academicFaculty,academicTopic,academicKey;
            LinearLayout parentLinear;

            public MyViewHolder(View view){
                super((view));
                academicSerial =(TextView) view.findViewById(R.id.academicSerial);
                academicAcademicActivity =(TextView) view.findViewById(R.id.academicAcademicActivity);
                academicFaculty =(TextView) view.findViewById(R.id.academicFaculty);
                academicTopic =(TextView) view.findViewById(R.id.academicTopic);
                academicKey =(TextView) view.findViewById(R.id.academicKey);
                parentLinear = (LinearLayout) view.findViewById(R.id.parentLinear);

            }
        }
        public AcademicAdapter(Context mainActivityUser, ArrayList<Academic> academicList, OnItemClick onItemClick) {
            this.academicList = academicList;
            this.mainActivityUser = mainActivityUser;
            this.onItemClick = onItemClick;
        }
        public void notifyData(ArrayList<Academic> myList) {
            Log.d("notifyData ", myList.size() + "");
            this.academicList = myList;
            notifyDataSetChanged();
        }
        public AcademicAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.academic_preview, parent, false);

            return new AcademicAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(AcademicAdapter.MyViewHolder holder, final int position) {
            Academic bean = academicList.get(position);
            holder.academicSerial.setText(bean.getAcademicSerial());
            holder.academicAcademicActivity.setText(bean.getAcademicAcademicActivity());
            holder.academicFaculty.setText(bean.getAcademicFaculty());
            holder.academicTopic.setText(bean.getAcademicTopic());
            holder.academicKey.setText(bean.getAcademicKey());


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
            return academicList.size();
        }


    }
