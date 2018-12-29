package nec.cst.pra.action.workingGroup;

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



    public class WorkingGroupAdapter extends RecyclerView.Adapter<WorkingGroupAdapter.MyViewHolder> {

        public Context mainActivityUser;
        public ArrayList<Workinggroup> workingGroupList;
        public OnItemClick onItemClick;
        public class MyViewHolder extends RecyclerView.ViewHolder{

            private TextView workingGroupSerial,workingGroupName,workingGroupcoordinator,workingGroupDepartment,workingGroupContact,workingGroupAreaOfInterest,workingGroupSubject,workingGroupdistrict;
            LinearLayout parentLinear;

            public MyViewHolder(View view){
                super((view));
                workingGroupSerial =(TextView) view.findViewById(R.id.workingGroupSerial);
                workingGroupName =(TextView) view.findViewById(R.id.workingGroupName);
                workingGroupcoordinator =(TextView) view.findViewById(R.id.workingGroupcoordinator);
                workingGroupDepartment =(TextView) view.findViewById(R.id.workingGroupDepartment);
                workingGroupContact =(TextView) view.findViewById(R.id.workingGroupContact);
                workingGroupAreaOfInterest =(TextView) view.findViewById(R.id.workingGroupAreaOfInterest);
                workingGroupSubject =(TextView) view.findViewById(R.id.workingGroupSubject);
                parentLinear = (LinearLayout) view.findViewById(R.id.parentLinear);

            }
        }
        public WorkingGroupAdapter(Context mainActivityUser, ArrayList<Workinggroup> workingGroupList, OnItemClick onItemClick) {
            this.workingGroupList = workingGroupList;
            this.mainActivityUser = mainActivityUser;
            this.onItemClick = onItemClick;
        }
        public void notifyData(ArrayList<Workinggroup> myList) {
            Log.d("notifyData ", myList.size() + "");
            this.workingGroupList = myList;
            notifyDataSetChanged();
        }
        public WorkingGroupAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.workinggroup_preview, parent, false);

            return new WorkingGroupAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(WorkingGroupAdapter.MyViewHolder holder, final int position) {
            Workinggroup bean = workingGroupList.get(position);
            holder.workingGroupSerial.setText(bean.getWorkingGroupSerial());
            holder.workingGroupName.setText(bean.getWorkingGroupName());
            holder.workingGroupcoordinator.setText(bean.getWorkingGroupcoordinator());
            holder.workingGroupDepartment.setText(bean.getWorkingGroupDepartment());
            holder.workingGroupContact.setText(bean.getWorkingGroupContact());
            holder.workingGroupAreaOfInterest.setText(bean.getWorkingGroupAreaOfInterest());
            holder.workingGroupSubject.setText(bean.getWorkingGroupSubject());



            if (position % 2 == 0) {
                holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.viewBg));
            } else {
                holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.white));
            }

            holder.parentLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.itemWorkingClick(position);
                }
            });
        }
        public  int getItemCount(){
            return workingGroupList.size();
        }


    }
