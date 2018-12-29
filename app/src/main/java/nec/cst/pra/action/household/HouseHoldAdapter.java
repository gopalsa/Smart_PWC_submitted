package nec.cst.pra.action.household;

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



    public class HouseHoldAdapter extends RecyclerView.Adapter<HouseHoldAdapter.MyViewHolder>  {
        public Context mainActivityUser;
        public ArrayList<HouseHold> householdList;
        public OnItemClick onItemClick;
        public class MyViewHolder extends RecyclerView.ViewHolder{

            private TextView householdSerial,householdVillage,householdComplete,householdToComplete;
            LinearLayout parentLinear;

            public MyViewHolder(View view){
                super((view));
                householdSerial =(TextView) view.findViewById(R.id.householdSerial);
                householdVillage =(TextView) view.findViewById(R.id.householdVillage);
                householdComplete =(TextView) view.findViewById(R.id.householdComplete);
                householdToComplete =(TextView) view.findViewById(R.id.householdToComplete);
                parentLinear = (LinearLayout) view.findViewById(R.id.parentLinear);

            }
        }
        public HouseHoldAdapter(Context mainActivityUser, ArrayList<HouseHold> householdList, OnItemClick onItemClick) {
            this.householdList = householdList;
            this.mainActivityUser = mainActivityUser;
            this.onItemClick = onItemClick;
        }
        public void notifyData(ArrayList<HouseHold> myList) {
            Log.d("notifyData ", myList.size() + "");
            this.householdList = myList;
            notifyDataSetChanged();
        }
        public HouseHoldAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.household_preview, parent, false);

            return new HouseHoldAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(HouseHoldAdapter.MyViewHolder holder, final int position) {
            HouseHold bean = householdList.get(position);
            holder.householdSerial.setText(bean.getHouseholdSerial());
            holder.householdVillage.setText(bean.getHouseholdVillage());
            holder. householdComplete.setText(bean.getHouseholdComplete());
            holder. householdComplete.setText(bean.getHouseholdToComplete());


            if (position % 2 == 0) {
                holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.viewBg));
            } else {
                holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.white));
            }

            holder.parentLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.itemHouseholdClick(position);
                }
            });
        }
        public  int getItemCount(){
            return householdList.size();
        }


    }
