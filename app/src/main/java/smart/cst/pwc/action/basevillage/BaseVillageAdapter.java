package smart.cst.pwc.action.basevillage;

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


    public class BaseVillageAdapter extends RecyclerView.Adapter<BaseVillageAdapter.MyViewHolder>  {

        public Context mainActivityUser;
        public ArrayList<Base> BaseList;
        public OnItemClick onItemClick;
        public class MyViewHolder extends RecyclerView.ViewHolder{

            private TextView baseVillageSerial,baseVillageVillage,baseVillageComplete;
            LinearLayout parentLinear;

            public MyViewHolder(View view){
                super((view));
                baseVillageSerial =(TextView) view.findViewById(R.id.baseVillageSerial);
                baseVillageVillage =(TextView) view.findViewById(R.id.baseVillageVillage);
                baseVillageComplete =(TextView) view.findViewById(R.id.baseVillageComplete);
                parentLinear = (LinearLayout) view.findViewById(R.id.parentLinear);

            }
        }
        public BaseVillageAdapter(Context mainActivityUser, ArrayList<Base> BaseList, OnItemClick onItemClick) {
            this.BaseList = BaseList;
            this.mainActivityUser = mainActivityUser;
            this.onItemClick = onItemClick;
        }
        public void notifyData(ArrayList<Base> myList) {
            Log.d("notifyData ", myList.size() + "");
            this.BaseList = myList;
            notifyDataSetChanged();
        }
        public BaseVillageAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.basevillage_preview, parent, false);

            return new BaseVillageAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(BaseVillageAdapter.MyViewHolder holder, final int position) {
            Base bean = BaseList.get(position);
            holder.baseVillageSerial.setText(bean.getBaseVillageSerial());
            holder.baseVillageVillage.setText(bean.baseVillageVillage);
            holder.baseVillageComplete.setText(bean.getbaseVillageComplete());


            if (position % 2 == 0) {
                holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.viewBg));
            } else {
                holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.white));
            }

            holder.parentLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.itemBaseVillageClick(position);
                }
            });
        }
        public  int getItemCount(){
            return BaseList.size();
        }


    }
