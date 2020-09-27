package smart.cst.pwc.household;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import smart.cst.pwc.R;

/**
 * Created by arthi on 11/27/2018.
 */

public class FamilyInfoAddapter extends RecyclerView.Adapter<FamilyInfoAddapter.MyViewHolder> {

        private Context mainActivityUser;
        private ArrayList<FamilyInfo> FamilyList;
        private OnItemClick onItemClick;
        public class MyViewHolder extends RecyclerView.ViewHolder{

            private TextView name,age,sex;
            LinearLayout parentLinear;

            public MyViewHolder(View view){
                super((view));
                name =(TextView) view.findViewById(R.id.name);
                age =(TextView) view.findViewById(R.id.age);
                sex =(TextView) view.findViewById(R.id.sex);



                parentLinear = (LinearLayout) view.findViewById(R.id.parentLinear);

            }
        }
    public FamilyInfoAddapter(Context mainActivityUser, ArrayList<FamilyInfo> moviesList, OnItemClick onItemClick) {
            this.FamilyList = moviesList;
            this.mainActivityUser = mainActivityUser;
            this.onItemClick = onItemClick;
        }
    public void notifyData(ArrayList<FamilyInfo> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.FamilyList = myList;
        notifyDataSetChanged();
    }
    public FamilyInfoAddapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.familyinfo_row, parent, false);

        return new FamilyInfoAddapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FamilyInfoAddapter.MyViewHolder holder, final int position) {
        FamilyInfo bean = FamilyList.get(position);
        holder.name.setText(bean.familyName);
        holder.age.setText(bean.familyAge);
        holder.sex.setText(bean.familySex);





        if (position % 2 == 0) {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.viewBg));
        } else {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.white));
        }

        holder.parentLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.itemFamilyIfoClick(position);
            }
        });
    }
    public  int getItemCount(){
        return FamilyList.size();
    }

}
