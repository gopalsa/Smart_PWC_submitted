package nec.cst.pra.action.salient;

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



    public class SalientAdapter extends RecyclerView.Adapter<SalientAdapter.MyViewHolder> {

        public Context mainActivityUser;
        public ArrayList<Salient> salientList;
        public OnItemClick onItemClick;
        public class MyViewHolder extends RecyclerView.ViewHolder{

            private TextView salientSerial,salientProject,salientTimelines,salientSourceOfFund,salientCost,salientAreaOfIntervention,salientExpectedOutcomes;
            LinearLayout parentLinear;

            public MyViewHolder(View view){
                super((view));
                salientSerial =(TextView) view.findViewById(R.id.salientSerial);
                salientProject =(TextView) view.findViewById(R.id.salientProject);
                salientTimelines =(TextView) view.findViewById(R.id.salientTimelines);
                salientSourceOfFund =(TextView) view.findViewById(R.id.salientSourceOfFunding);
                salientCost =(TextView) view.findViewById(R.id.salientCost);
                salientAreaOfIntervention =(TextView) view.findViewById(R.id.salientAreaOfIntervention);
                salientExpectedOutcomes =(TextView) view.findViewById(R.id.salientExpectedOutcomes);
                parentLinear = (LinearLayout) view.findViewById(R.id.parentLinear);

            }
        }
        public SalientAdapter(Context mainActivityUser, ArrayList<Salient> salientList, OnItemClick onItemClick) {
            this.salientList = salientList;
            this.mainActivityUser = mainActivityUser;
            this.onItemClick = onItemClick;
        }
        public void notifyData(ArrayList<Salient> myList) {
            Log.d("notifyData ", myList.size() + "");
            this.salientList = myList;
            notifyDataSetChanged();
        }
        public SalientAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.salient_preview, parent, false);

            return new SalientAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(SalientAdapter.MyViewHolder holder, final int position) {
            Salient bean = salientList.get(position);
            holder.salientSerial.setText(bean.getSalientSerial());
            holder.salientProject.setText(bean.getSalientProject());
            holder.salientTimelines.setText(bean.getSalientTimelines());
            holder.salientSourceOfFund.setText(bean.getSalientSourceOfFund());
            holder.salientCost.setText(bean.getSalientCost());
            holder.salientAreaOfIntervention.setText(bean.getSalientAreaOfIntervention());
            holder.salientExpectedOutcomes.setText(bean.getExpectedOutcomes());

            if (position % 2 == 0) {
                holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.viewBg));
            } else {
                holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.white));
            }

            holder.parentLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.itemSalientClick(position);
                }
            });
        }
        public  int getItemCount(){
            return salientList.size();
        }


    }
