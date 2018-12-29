package nec.cst.pra.action.miscellaneous;

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



    public class MiscellaneousAdapter extends RecyclerView.Adapter<MiscellaneousAdapter.MyViewHolder> {

        public Context mainActivityUser;
        public ArrayList<Miscellaneous> miscellaneousList;
        public OnItemClick onItemClick;

    public void notifyData(MiscellaneousAdapter miscellaneousAdapter) {
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

            private TextView miscellaneousSerial,miscellaneousTitle,miscellaneousStatus,miscellaneousComments;
            LinearLayout parentLinear;

            public MyViewHolder(View view){
                super((view));
                miscellaneousSerial =(TextView) view.findViewById(R.id.miscellaneousSerial);
                miscellaneousTitle =(TextView) view.findViewById(R.id.miscellaneousTitle);
                miscellaneousStatus =(TextView) view.findViewById(R.id.miscellaneousStatus);
                miscellaneousComments =(TextView) view.findViewById(R.id.miscellaneousComments);
                parentLinear = (LinearLayout) view.findViewById(R.id.parentLinear);

            }
        }
        public MiscellaneousAdapter(Context mainActivityUser, ArrayList<Miscellaneous> miscellaneousList, OnItemClick onItemClick) {
            this.miscellaneousList = miscellaneousList;
            this.mainActivityUser = mainActivityUser;
            this.onItemClick = onItemClick;
        }
        public void notifyData(ArrayList<Miscellaneous> myList) {
            Log.d("notifyData ", myList.size() + "");
            this.miscellaneousList = myList;
            notifyDataSetChanged();
        }
        public MiscellaneousAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.miscellaneous_preview, parent, false);

            return new MiscellaneousAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MiscellaneousAdapter.MyViewHolder holder, final int position) {
            Miscellaneous bean = miscellaneousList.get(position);
            holder.miscellaneousSerial.setText(bean.getMiscellaneousSerial());
            holder.miscellaneousTitle.setText(bean.getMiscellaneousTitle());
            holder.miscellaneousStatus.setText(bean.getMiscellaneousStatus());
            holder.miscellaneousComments.setText(bean.getMiscellaneousComments());


            if (position % 2 == 0) {
                holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.viewBg));
            } else {
                holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.white));
            }

            holder.parentLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.itemMiscellaneousClick(position);
                }
            });
        }
        public  int getItemCount(){
            return miscellaneousList.size();
        }


    }
