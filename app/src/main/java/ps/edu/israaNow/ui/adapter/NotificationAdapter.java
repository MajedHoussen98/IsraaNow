package ps.edu.israaNow.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import ps.edu.israaNow.R;
import ps.edu.israaNow.models.Post;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    Context context;
    ArrayList<Post> list;
    final private ListItemClickListener mOnClickListener;


    public interface ListItemClickListener {
        void onListItemClicked(int position, int viewId);
    }

    public NotificationAdapter(Context context, ArrayList<Post> list, ListItemClickListener mOnClickListener1) {

        this.context = context;
        this.list = list;
        this.mOnClickListener = mOnClickListener1;
    }

    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_notification, viewGroup, false);
        return new NotificationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Post notification = list.get(position);
        Picasso.with(context).load(notification.getCollege().getPic()).into(holder.college_image);
        holder.college_name.setText(notification.getCollege().getName());
        holder.notification_time.setText(notification.getStart_time());
        holder.event_title.setText(notification.getTitle());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CircleImageView college_image;
        private TextView college_name, notification_time, event_title;
        private ConstraintLayout ll_notification;

        public ViewHolder(View itemView) {
            super(itemView);
            college_image = itemView.findViewById(R.id.college_image_notification);
            college_name = itemView.findViewById(R.id.college_name_notification);
            notification_time = itemView.findViewById(R.id.notification_time);
            event_title = itemView.findViewById(R.id.event_title_notification);
            ll_notification = itemView.findViewById(R.id.ll_notification);
            ll_notification.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnClickListener.onListItemClicked(getAdapterPosition(), v.getId());


        }
    }
}
