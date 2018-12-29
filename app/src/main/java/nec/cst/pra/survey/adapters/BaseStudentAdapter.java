package nec.cst.pra.survey.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.CallSuper;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import nec.cst.pra.R;
import nec.cst.pra.survey.sectionedrecyclerview.SectionedRecyclerViewAdapter;


/**
 * @author Vladislav Zhukov (https://github.com/zhukic)
 */

public abstract class BaseStudentAdapter extends SectionedRecyclerViewAdapter<BaseStudentAdapter.SubheaderHolder, BaseStudentAdapter.MovieViewHolder> {

    public interface OnItemClickListener {
        void onItemClicked(Response task);

        void onSubheaderClicked(int position);
    }

    List<Response> taskList;
    Map<String, Integer> departmentCountMap;
    Context context;
    OnItemClickListener onItemClickListener;

    static class SubheaderHolder extends RecyclerView.ViewHolder {

        private static Typeface meduiumTypeface = null;

        TextView mSubheaderText, countBadge;
        ImageView mArrow;

        SubheaderHolder(View itemView) {
            super(itemView);
            this.mSubheaderText = (TextView) itemView.findViewById(R.id.subheaderText);
            this.mArrow = (ImageView) itemView.findViewById(R.id.arrow);
            this.countBadge = (TextView) itemView.findViewById(R.id.countBadge);

            if (meduiumTypeface == null) {
             //  meduiumTypeface = Typeface.createFromAsset(itemView.getContext().getAssets(), "Roboto-Medium.ttf");
            }
            //this.mSubheaderText.setTypeface(meduiumTypeface);
        }

    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView gps;
        TextView response;
        TextView isValid;
        LinearLayout parentLinear;

        MovieViewHolder(View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.name);
            this.gps = (TextView) itemView.findViewById(R.id.gps);
            this.response = (TextView) itemView.findViewById(R.id.response);
            this.isValid = (TextView) itemView.findViewById(R.id.isValid);
            this.parentLinear = (LinearLayout) itemView.findViewById(R.id.parentLinear);

        }
    }

    BaseStudentAdapter(List<Response> itemList, Context context, Map<String, Integer> departmentCountMap) {
        super();
        this.taskList = itemList;
        this.context = context;
        this.departmentCountMap = departmentCountMap;
    }

    @Override
    public MovieViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new MovieViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.survey_response_item, parent, false));
    }

    @Override
    public SubheaderHolder onCreateSubheaderViewHolder(ViewGroup parent, int viewType) {
        return new SubheaderHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item_header, parent, false));
    }

    @Override
    @CallSuper
    public void onBindSubheaderViewHolder(SubheaderHolder subheaderHolder, int nextItemPosition) {

        boolean isSectionExpanded = isSectionExpanded(getSectionIndex(subheaderHolder.getAdapterPosition()));

        if (isSectionExpanded) {
            subheaderHolder.mArrow.setImageDrawable(ContextCompat.getDrawable(subheaderHolder.itemView.getContext(), R.drawable.ic_add_box_black_24dp));
        } else {
            subheaderHolder.mArrow.setImageDrawable(ContextCompat.getDrawable(subheaderHolder.itemView.getContext(), R.drawable.ic_indeterminate_check_box_black_24dp));
        }

        subheaderHolder.itemView.setOnClickListener(v -> onItemClickListener.onSubheaderClicked(subheaderHolder.getAdapterPosition()));

    }

    @Override
    public int getItemSize() {
        return taskList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
