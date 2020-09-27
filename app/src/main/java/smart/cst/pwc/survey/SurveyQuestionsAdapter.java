package smart.cst.pwc.survey;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;
import smart.cst.pwc.R;
import smart.cst.pwc.household.SurveyItem;


public class SurveyQuestionsAdapter extends RecyclerView.Adapter<SurveyQuestionsAdapter.MyViewHolder> {

    private Context mainActivityUser;
    private ArrayList<SurveyItem> moviesList;
    SharedPreferences sharedpreferences;
    int row_index = -1;
    SurveyItemClick surveyItemClick;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        FloatingTextButton question;

        public MyViewHolder(View view) {
            super(view);
            question = (FloatingTextButton) view.findViewById(R.id.question);
        }
    }


    public SurveyQuestionsAdapter(Context mainActivityUser, ArrayList<SurveyItem> moviesList, SurveyItemClick surveyItemClick) {
        this.moviesList = moviesList;
        this.mainActivityUser = mainActivityUser;
        this.surveyItemClick = surveyItemClick;

    }

    public void notifyData(ArrayList<SurveyItem> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.moviesList = myList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.survey_question_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        SurveyItem bean = moviesList.get(position);
        holder.question.setTitle(bean.question.substring(0, bean.question.indexOf(" ")-1));
       // holder.question.setTitle(String.valueOf(position + 1));

        holder.question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index = position;
                notifyDataSetChanged();
                surveyItemClick.itemClick(position);
            }
        });
        if (row_index == position) {
            holder.question.setBackgroundColor(Color.parseColor("#00cc66"));
        } else {
            holder.question.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.colorPrimary));
        }

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
