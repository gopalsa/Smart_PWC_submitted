package nec.cst.pra.action.major;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import nec.cst.pra.action.OnItemClick;
import nec.cst.pra.R;

import java.util.ArrayList;

/**
 * Created by SanAji on 30-11-2018.
 */


    public class MajorAdapter extends RecyclerView.Adapter<MajorAdapter.MyViewHolder> {

        public Context mainActivityUser;
        public ArrayList<Major> MajorList;
        public OnItemClick onItemClick;
        public class MyViewHolder extends RecyclerView.ViewHolder{

            private TextView issue,proposed,formulate;
            LinearLayout parentLinear;

            public MyViewHolder(View view){
                super((view));
                issue =(TextView) view.findViewById(R.id.issue);
                proposed =(TextView) view.findViewById(R.id.proposed);
                formulate =(TextView) view.findViewById(R.id.formulate);
                parentLinear = (LinearLayout) view.findViewById(R.id.parentLinear);

            }
        }
        public MajorAdapter(Context mainActivityUser, ArrayList<Major> MajorList, OnItemClick onItemClick) {
            this.MajorList = MajorList;
            this.mainActivityUser = mainActivityUser;
            this.onItemClick = onItemClick;
        }
        public void notifyData(ArrayList<Major> myList) {
            Log.d("notifyData ", myList.size() + "");
            this.MajorList = myList;
            notifyDataSetChanged();
        }
        public MajorAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.major_preview, parent, false);

            return new MajorAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MajorAdapter.MyViewHolder holder, final int position) {
            Major bean = MajorList.get(position);
            holder.issue.setText(bean.getIssue());
            holder.proposed.setText(bean.getProposed());
            holder.formulate.setText(bean.getFormulate());


            if (position % 2 == 0) {
                holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.viewBg));
            } else {
                holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.white));
            }

            holder.parentLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.itemMajorClick(position);
                }
            });
        }
        public  int getItemCount(){
            return MajorList.size();
        }

    }