package nec.cst.pra.action.village;

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


    public class VillageAdapter extends RecyclerView.Adapter<VillageAdapter.MyViewHolder> {

        public Context mainActivityUser;
        public ArrayList<Village> villageList;
        public OnItemClick onItemClick;


    public class MyViewHolder extends RecyclerView.ViewHolder{

            private TextView villageSerial,villageBlock,villageDistrict,villageHeadOfVillage,villageKeyContact;
            LinearLayout parentLinear;

            public MyViewHolder(View view){
                super((view));
                villageSerial =(TextView) view.findViewById(R.id.villageSerial);
                villageBlock =(TextView) view.findViewById(R.id.villageBlock);
                villageDistrict =(TextView) view.findViewById(R.id.villageDistrict);
                villageHeadOfVillage =(TextView) view.findViewById(R.id.villageHeadOfVillage);
                villageKeyContact =(TextView) view.findViewById(R.id.villageKeyContact);
                parentLinear = (LinearLayout) view.findViewById(R.id.parentLinear);

            }
        }
        public VillageAdapter(Context mainActivityUser, ArrayList<Village> villageList, OnItemClick onItemClick) {
            this.villageList = villageList;
            this.mainActivityUser = mainActivityUser;
            this.onItemClick = onItemClick;
        }
        public void notifyData(ArrayList<Village> myList) {
            Log.d("notifyData ", myList.size() + "");
            this.villageList = myList;
            notifyDataSetChanged();
        }
        public VillageAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.village_preview, parent, false);

            return new VillageAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(VillageAdapter.MyViewHolder holder, final int position) {
            Village bean = villageList.get(position);
            holder.villageSerial.setText(bean.getVillageSerial());
            holder.villageBlock.setText(bean.getVillageBlock());
            holder.villageDistrict.setText(bean.getVillageDistrict());
            holder.villageHeadOfVillage.setText(bean.getVillageHeadOfVillage());
            holder.villageKeyContact.setText(bean.getVillageKeyContact());


            if (position % 2 == 0) {
                holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.viewBg));
            } else {
                holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.white));
            }

            holder.parentLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.itemVillageClick(position);
                }
            });
        }
        public  int getItemCount(){
            return villageList.size();
        }


    }
