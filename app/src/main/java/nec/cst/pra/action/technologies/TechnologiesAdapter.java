package nec.cst.pra.action.technologies;

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



    public class TechnologiesAdapter extends RecyclerView.Adapter<TechnologiesAdapter.MyViewHolder> {

        public Context mainActivityUser;
        public ArrayList<Technologies> technologiesList;
        public OnItemClick onItemClick;

    public void add(Technologies bean) {
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

            private TextView technologiesSerrial,technologiesTechnology,technologiesSubjectArea,technologiesWhetherTransfrred,technologiesImpact;
            LinearLayout parentLinear;

            public MyViewHolder(View view){
                super((view));
                technologiesSerrial =(TextView) view.findViewById(R.id.technologiesSerial);
                technologiesTechnology =(TextView) view.findViewById(R.id.technologiesTechnology);
                technologiesSubjectArea =(TextView) view.findViewById(R.id.technologiesSubjectArea);
                technologiesWhetherTransfrred =(TextView) view.findViewById(R.id.technologiesWhetherTransferred);
                technologiesImpact =(TextView) view.findViewById(R.id.technologiesImpact);
                parentLinear = (LinearLayout) view.findViewById(R.id.parentLinear);

            }
        }
        public TechnologiesAdapter(Context mainActivityUser, ArrayList<Technologies> technologiesList, OnItemClick onItemClick) {
            this.technologiesList = technologiesList;
            this.mainActivityUser = mainActivityUser;
            this.onItemClick = onItemClick;
        }
        public void notifyData(ArrayList<Technologies> myList) {
            Log.d("notifyData ", myList.size() + "");
            this.technologiesList = myList;
            notifyDataSetChanged();
        }
        public TechnologiesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.technologies_preview, parent, false);

            return new TechnologiesAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(TechnologiesAdapter.MyViewHolder holder, final int position) {
            Technologies bean = technologiesList.get(position);
            holder.technologiesSerrial.setText(bean.getTechnologiesSerial());
            holder.technologiesTechnology.setText(bean.getTechnologiesTechnology());
            holder.technologiesSubjectArea.setText(bean.getTechnologiesSubjectArea());
            holder.technologiesWhetherTransfrred.setText(bean.getTechnologiesWhetherTransferred());
            holder.technologiesImpact.setText(bean.getTechnologiesImpact());


            if (position % 2 == 0) {
                holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.viewBg));
            } else {
                holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.white));
            }

            holder.parentLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.itemTechnologiesClick(position);
                }
            });
        }
        public  int getItemCount(){
            return technologiesList.size();
        }


    }
