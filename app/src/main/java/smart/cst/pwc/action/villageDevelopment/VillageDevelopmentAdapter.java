package smart.cst.pwc.action.villageDevelopment;

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


    public class VillageDevelopmentAdapter extends RecyclerView.Adapter<VillageDevelopmentAdapter.MyViewHolder> {

        public Context mainActivityUser;
        public ArrayList<VillageDevelopment> villageDevelopmentList;
        public OnItemClick onItemClick;



    public class MyViewHolder extends RecyclerView.ViewHolder{

            private TextView villageDevelopmentSerial,villageDevelopmentName,villageDevelopmentStatus;
            LinearLayout parentLinear;

            public MyViewHolder(View view){
                super((view));
                villageDevelopmentSerial =(TextView) view.findViewById(R.id.villageDevelopmentSerial);
                villageDevelopmentName =(TextView) view.findViewById(R.id.villageDevelopmentName);
                villageDevelopmentStatus =(TextView) view.findViewById(R.id.villageDevelopmentStatus);
                parentLinear = (LinearLayout) view.findViewById(R.id.parentLinear);

            }
        }
        public VillageDevelopmentAdapter(Context mainActivityUser, ArrayList<VillageDevelopment> villageDevelopmentList, OnItemClick onItemClick) {
            this.villageDevelopmentList = villageDevelopmentList;
            this.mainActivityUser = mainActivityUser;
            this.onItemClick = onItemClick;
        }
    public void notifyData(ArrayList<VillageDevelopment> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.villageDevelopmentList = myList;
        notifyDataSetChanged();
    }
        
        public VillageDevelopmentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.villagedevelopment_preview, parent, false);

            return new VillageDevelopmentAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(VillageDevelopmentAdapter.MyViewHolder holder, final int position) {
            VillageDevelopment bean = villageDevelopmentList.get(position);
            holder.villageDevelopmentSerial.setText(bean.getVillageDevelopmentSerial());
            holder.villageDevelopmentName.setText(bean.getVillageDevelopmentName());
            holder.villageDevelopmentStatus.setText(bean.getVillageDevelopmentStatus());


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
            return villageDevelopmentList.size();
        }


    }
