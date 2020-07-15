package ps.edu.israaNow.ui.Favriote;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ps.edu.israaNow.DetailsEvent;
import ps.edu.israaNow.R;
import ps.edu.israaNow.Retrofit.RetroiftGet;
import ps.edu.israaNow.models.EventResponse;
import ps.edu.israaNow.storge.SharedPrefManager;
import ps.edu.israaNow.ui.adapter.PostsAdapter;
import ps.edu.israaNow.models.Post;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavrioteFragment extends Fragment implements PostsAdapter.ListItemClickListener {

    private RecyclerView favEvents;
    private PostsAdapter postsAdapter;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<Post> mPosts = new ArrayList<>();
    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        TextView titleBar = getActivity().findViewById(R.id.title_bar);
        titleBar.setText("المفضلة");
        view = inflater.inflate(R.layout.fragment_favriote, container, false);
        initViews();
        getFavEvent();
        refreshData();
        removeFav();
        return view;
    }

    private void initViews() {
        favEvents = view.findViewById(R.id.favEvents);
        progressBar = view.findViewById(R.id.progress_circular);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
    }

    @Override
    public void onListItemClicked(int position, int viewId) {
        switch (viewId) {
            case R.id.ll_event:
                int id = mPosts.get(position).getId();
                Intent intent = new Intent(getContext(), DetailsEvent.class);
                intent.putExtra("event_id", String.valueOf(id));
                startActivity(intent);
                break;
        }
    }

    private void getFavEvent() {
        Call<EventResponse> call = RetroiftGet.getInstance().getApi().getEventFav(SharedPrefManager.getInstance(getContext()).getUser().getToken());
        call.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {

                mPosts = response.body().getDataEvents();
                postsAdapter = new PostsAdapter(getContext(), mPosts, FavrioteFragment.this);
                progressBar.setVisibility(View.GONE);
                favEvents.setAdapter(postsAdapter);
            }

            @Override
            public void onFailure(Call<EventResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "حدث خطأ ما !", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void refreshData() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFavEvent();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void removeFav() {
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder posts, int direction) {

                int position = posts.getAdapterPosition();
                mPosts.remove(position);
                postsAdapter.notifyDataSetChanged();
            }
        });
        helper.attachToRecyclerView(favEvents);
    }

}
