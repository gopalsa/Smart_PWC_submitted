package nec.cst.pra.action.technologyCustomisation;

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



    public class TechnologyCustomisationAdapter extends RecyclerView.Adapter<TechnologyCustomisationAdapter.MyViewHolder> {

        public Context mainActivityUser;
        public ArrayList<TechnologyCustomisation> technologyCustomisationList;
        public OnItemClick onItemClick;
        public class MyViewHolder extends RecyclerView.ViewHolder{

            private TextView technologyCustomisationSerial,technologyCustomisationTitle,technologyCustomisationStatus,technologyCustomisationComments;
            LinearLayout parentLinear;

            public MyViewHolder(View view){
                super((view));
                technologyCustomisationSerial =(TextView) view.findViewById(R.id.technologyCustomisationSerial);
                technologyCustomisationTitle =(TextView) view.findViewById(R.id.technologyCustomisationtTitle);
                technologyCustomisationStatus =(TextView) view.findViewById(R.id.technologyCustomisationStatus);
                technologyCustomisationComments =(TextView) view.findViewById(R.id.technologyCustomisationComments);
                parentLinear = (LinearLayout) view.findViewById(R.id.parentLinear);

            }
        }
        public TechnologyCustomisationAdapter(Context mainActivityUser, ArrayList<TechnologyCustomisation> technologyCustomisationList, OnItemClick onItemClick) {
            this.technologyCustomisationList = technologyCustomisationList;
            this.mainActivityUser = mainActivityUser;
            this.onItemClick = onItemClick;
        }
        public void notifyData(ArrayList<TechnologyCustomisation> myList) {
            Log.d("notifyData ", myList.size() + "");
            this.technologyCustomisationList = myList;
            notifyDataSetChanged();
        }
        public TechnologyCustomisationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.technologycustomisation_preview, parent, false);

            return new TechnologyCustomisationAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(TechnologyCustomisationAdapter.MyViewHolder holder, final int position) {
            TechnologyCustomisation bean = technologyCustomisationList.get(position);
            holder.technologyCustomisationSerial.setText(bean.getTechnologyCustomisationSerial());
            holder.technologyCustomisationTitle.setText(bean.getTechnologyCustomisationtTitle());
            holder.technologyCustomisationStatus.setText(bean.getTechnologyCustomisationtTitle());
            holder.technologyCustomisationComments.setText(bean.getTechnologyCustomisationComments());


            if (position % 2 == 0) {
                holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.viewBg));
            } else {
                holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.white));
            }

            holder.parentLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.itemTechnologyCustomisationClick(position);
                }
            });
        }
        public  int getItemCount(){
            return technologyCustomisationList.size();
        }


    }
