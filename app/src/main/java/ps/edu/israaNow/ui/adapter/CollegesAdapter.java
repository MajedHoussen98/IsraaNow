package ps.edu.israaNow.ui.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ps.edu.israaNow.R;
import ps.edu.israaNow.models.DataCollege;

public class CollegesAdapter extends RecyclerView.Adapter<CollegesAdapter.ViewHolder> {
    Context context;
    List<DataCollege> list;
    final private ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onListItemClicked(int position, int viewId);
    }

    public CollegesAdapter(Context context, ArrayList<DataCollege> list, ListItemClickListener mOnClickListener) {
        this.context = context;
        this.list = list;
        this.mOnClickListener = mOnClickListener;
    }

    @NonNull
    @Override
    public CollegesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_college, viewGroup, false);
        return new CollegesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CollegesAdapter.ViewHolder viewHolder, int i) {
        final DataCollege college = list.get(i);
        viewHolder.collegeName.setText(college.getName());
        Glide.with(context).load(college.getPic()).into(viewHolder.collegeImage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView collegeImage;
        private TextView collegeName;
        private LinearLayout ll_college;

        public ViewHolder(View itemView) {

            super(itemView);
            collegeImage = itemView.findViewById(R.id.college_image);
            collegeName = itemView.findViewById(R.id.college_name);
            ll_college = itemView.findViewById(R.id.ll_college);
            ll_college.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnClickListener.onListItemClicked(getAdapterPosition(), v.getId());
        }
    }
}