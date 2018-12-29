package nec.cst.pra.action.meeting;

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


    public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.MyViewHolder> {

        public Context mainActivityUser;
        public ArrayList<Meeting> meetingList;
        public OnItemClick onItemClick;
        public class MyViewHolder extends RecyclerView.ViewHolder{

            private TextView meetingSerial,meetingName,meetingDate,meetingNoOfParticipants,meetingKeyOutcomes;
            LinearLayout parentLinear;

            public MyViewHolder(View view){
                super((view));
                meetingSerial =(TextView) view.findViewById(R.id.meetingSerial);
                meetingName =(TextView) view.findViewById(R.id.meetingName);
                meetingDate =(TextView) view.findViewById(R.id.meetingDate);
                meetingNoOfParticipants =(TextView) view.findViewById(R.id.meetingNoOfParticipants);
                meetingKeyOutcomes =(TextView) view.findViewById(R.id.meetingKeyOutcomes);
                parentLinear = (LinearLayout) view.findViewById(R.id.parentLinear);

            }
        }
        public MeetingAdapter(Context mainActivityUser, ArrayList<Meeting> meetingList, OnItemClick onItemClick) {
            this.meetingList = meetingList;
            this.mainActivityUser = mainActivityUser;
            this.onItemClick = onItemClick;
        }
        public void notifyData(ArrayList<Meeting> myList) {
            Log.d("notifyData ", myList.size() + "");
            this.meetingList = myList;
            notifyDataSetChanged();
        }
        public MeetingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.meeting_preview, parent, false);

            return new MeetingAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MeetingAdapter.MyViewHolder holder, final int position) {
            Meeting bean = meetingList.get(position);
            holder.meetingSerial.setText(bean.getMeetingSerial());
            holder.meetingName.setText(bean.getMeetingName());
            holder.meetingDate.setText(bean.getMeetingDate());
            holder.meetingNoOfParticipants.setText(bean.getMeetingNoOfParticipants());
            holder.meetingKeyOutcomes.setText(bean.getMeetingKeyOutcomes());


            if (position % 2 == 0) {
                holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.viewBg));
            } else {
                holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.white));
            }

            holder.parentLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.itemMeetingsClick(position);
                }
            });
        }
        public  int getItemCount(){
            return meetingList.size();
        }


    }
