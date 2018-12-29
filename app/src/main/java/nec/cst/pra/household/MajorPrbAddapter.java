package nec.cst.pra.household;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import nec.cst.pra.R;

/**
 * Created by arthi on 11/27/2018.
 */

public class MajorPrbAddapter extends RecyclerView.Adapter<MajorPrbAddapter.MyViewHolder> {

        private Context mainActivityUser;
        private ArrayList<MajorPrb> MajorList;
        private OnItemClick onItemClick;
        public class MyViewHolder extends RecyclerView.ViewHolder{

            private TextView problem,possible;
            LinearLayout parentLinear;

            public MyViewHolder(View view){
                super((view));
                problem =(TextView) view.findViewById(R.id.problem);
                possible =(TextView) view.findViewById(R.id.possible);

                parentLinear = (LinearLayout) view.findViewById(R.id.parentLinear);

            }
        }
    public MajorPrbAddapter(Context mainActivityUser, ArrayList<MajorPrb> moviesList, OnItemClick onItemClick) {
            this.MajorList = moviesList;
            this.mainActivityUser = mainActivityUser;
            this.onItemClick = onItemClick;
        }
    public void notifyData(ArrayList<MajorPrb> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.MajorList = myList;
        notifyDataSetChanged();
    }
    public MajorPrbAddapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.majorprb_row, parent, false);

        return new MajorPrbAddapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MajorPrbAddapter.MyViewHolder holder, final int position) {
        MajorPrb bean = MajorList.get(position);
        holder.problem.setText(bean.problem);
        holder.possible.setText(bean.possible);



        if (position % 2 == 0) {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.viewBg));
        } else {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.white));
        }

        holder.parentLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.itemMajorPrbClick(position);
            }
        });
    }
    public  int getItemCount(){
        return MajorList.size();
    }

}
