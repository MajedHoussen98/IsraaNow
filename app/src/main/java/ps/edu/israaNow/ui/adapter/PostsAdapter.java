package ps.edu.israaNow.ui.adapter;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ps.edu.israaNow.R;
import ps.edu.israaNow.Retrofit.RetroiftGet;
import ps.edu.israaNow.models.AddtoFavResponse;
import ps.edu.israaNow.models.Post;
import ps.edu.israaNow.models.StatusResponse;
import ps.edu.israaNow.storge.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    Context context;
    List<Post> list;
    final private ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onListItemClicked(int position, int viewId);
    }

    public PostsAdapter(Context context, List<Post> list, PostsAdapter.ListItemClickListener mOnClickListener) {
        this.context = context;
        this.list = list;
        this.mOnClickListener = mOnClickListener;
    }

    @NonNull
    @Override
    public PostsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_design, viewGroup, false);
        return new PostsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsAdapter.ViewHolder viewHolder, int i) {
        final Post post = list.get(i);
        Picasso.with(context).load(post.getCollege().getPic()).into(viewHolder.college_image);
        Picasso.with(context).load(post.getPic()).into(viewHolder.event_image);
        viewHolder.college_name.setText(post.getCollege().getName());
        viewHolder.event_date.setText(post.getStart_date1());
        viewHolder.fav_num.setText(String.valueOf(post.getNo_favourite()));
        viewHolder.event_title.setText(post.getTitle());

        if (post.isIs_fav()) {
            viewHolder.fav.setImageResource(R.drawable.ic_favorite_red_24dp);
        } else {
            viewHolder.fav.setImageResource(R.drawable.icons_like_card);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CircleImageView college_image;
        private LinearLayout ll_event;
        private TextView college_name, event_date, fav_num, event_title;
        private ImageView event_image, share, fav;


        public ViewHolder(View itemView) {
            super(itemView);
            college_image = itemView.findViewById(R.id.college_image);
            ll_event = itemView.findViewById(R.id.ll_event);
            college_name = itemView.findViewById(R.id.college_name);
            event_date = itemView.findViewById(R.id.event_date);
            fav_num = itemView.findViewById(R.id.fav_num);
            event_title = itemView.findViewById(R.id.event_title);
            event_image = itemView.findViewById(R.id.event_image);
            share = itemView.findViewById(R.id.share);
            fav = itemView.findViewById(R.id.fav);
            fav.setOnClickListener(this);
            share.setOnClickListener(this);
            ll_event.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.fav) {

                int numFav = list.get(getAdapterPosition()).getNo_favourite();
                if (list.get(getAdapterPosition()).isIs_fav()) {
                    fav.setImageResource(R.drawable.icons_like_card);
                    list.get(getAdapterPosition()).setIs_fav(false);
                    --numFav;
                    addFav(String.valueOf(list.get(getAdapterPosition()).getId()), context);
                    fav_num.setText(String.valueOf(numFav));
                } else {
                    fav.setImageResource(R.drawable.ic_favorite_red_24dp);
                    list.get(getAdapterPosition()).setIs_fav(true);
                    ++numFav;
                    list.get(getAdapterPosition()).setNo_favourite(numFav);
                    addFav(String.valueOf(list.get(getAdapterPosition()).getId()), context);
                    fav_num.setText(String.valueOf(numFav));
                }

            } else if (v.getId() == R.id.share) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                final String appPackageName = itemView.getContext().getPackageName();
                String strAppLink = "";
                try {
                    strAppLink = "https://play.google.com/store/apps/details?id=" + appPackageName;
                } catch (android.content.ActivityNotFoundException e) {
                    strAppLink = "https://play.google.com/store/apps/details?id=" + appPackageName;
                }
                intent.setType("text/link");
                String shareBody = "قم بتحميل التطبيق للتعرف على الحدث" + "\n" + "" + strAppLink;
                String shareSub = "APP NAME/ TITLE";
                intent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                intent.putExtra(Intent.EXTRA_TEXT, shareBody);
                context.startActivity(Intent.createChooser(intent, "مشاركة بأستخدام"));

            }
            mOnClickListener.onListItemClicked(getAdapterPosition(), v.getId());
        }
    }

    public void addFav(String eventId, final Context context) {
        Log.e("evenId", eventId);
        Call<AddtoFavResponse> call = RetroiftGet.getInstance().getApi().addFav(SharedPrefManager.getInstance(context).getUser().getToken(), eventId);
        call.enqueue(new Callback<AddtoFavResponse>() {
            @Override
            public void onResponse(Call<AddtoFavResponse> call, Response<AddtoFavResponse> response) {

                Toast.makeText(context, response.body().getStatusUser().getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<AddtoFavResponse> call, Throwable t) {

            }
        });

    }
}