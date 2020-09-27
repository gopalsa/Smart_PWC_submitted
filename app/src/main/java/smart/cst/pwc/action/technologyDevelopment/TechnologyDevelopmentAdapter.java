package smart.cst.pwc.action.technologyDevelopment;

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



    public class TechnologyDevelopmentAdapter extends RecyclerView.Adapter<TechnologyDevelopmentAdapter.MyViewHolder> {

        public Context mainActivityUser;
        public ArrayList<TechnologyDevelopment> technologyDevelopmentList;
        public OnItemClick onItemClick;
        public class MyViewHolder extends RecyclerView.ViewHolder{

            private TextView technologyDevelopmentSerial,technologyDevelopmentTitle,technologyDevelopmentStatus,technologyDevelopmentComments;
            LinearLayout parentLinear;

            public MyViewHolder(View view){
                super((view));
                technologyDevelopmentSerial =(TextView) view.findViewById(R.id.technologyDevelopmentSerial);
                technologyDevelopmentTitle =(TextView) view.findViewById(R.id.technologyDevelopmentTitle);
                technologyDevelopmentStatus =(TextView) view.findViewById(R.id.technologyDevelopmentStatus);
                technologyDevelopmentComments =(TextView) view.findViewById(R.id.technologyDevelopmentComments);
                parentLinear = (LinearLayout) view.findViewById(R.id.parentLinear);

            }
        }
        public TechnologyDevelopmentAdapter(Context mainActivityUser, ArrayList<TechnologyDevelopment> technologyDevelopmentList, OnItemClick onItemClick) {
            this.technologyDevelopmentList = technologyDevelopmentList;
            this.mainActivityUser = mainActivityUser;
            this.onItemClick = onItemClick;
        }
        public void notifyData(ArrayList<TechnologyDevelopment> myList) {
            Log.d("notifyData ", myList.size() + "");
            this.technologyDevelopmentList = myList;
            notifyDataSetChanged();
        }
        public TechnologyDevelopmentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.technologydevelopment_preview, parent, false);

            return new TechnologyDevelopmentAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(TechnologyDevelopmentAdapter.MyViewHolder holder, final int position) {
            TechnologyDevelopment bean = technologyDevelopmentList.get(position);
            holder.technologyDevelopmentSerial.setText(bean.getTechnologyDevelopmentSerial());
            holder.technologyDevelopmentTitle.setText(bean.getTechnologyDevelopmentTitle());
            holder.technologyDevelopmentStatus.setText(bean.getTechnologyDevelopmentStatus());
            holder.technologyDevelopmentComments.setText(bean.getTechnologyDevelopmentComments());


            if (position % 2 == 0) {
                holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.viewBg));
            } else {
                holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.white));
            }

            holder.parentLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.itemTechnologyDevelopmentClick(position);
                }
            });
        }
        public  int getItemCount(){
            return technologyDevelopmentList.size();
        }


    }
