package ps.edu.israaNow.ui.ComingEvent;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

import ps.edu.israaNow.DetailsEvent;
import ps.edu.israaNow.R;
import ps.edu.israaNow.Retrofit.RetroiftGet;
import ps.edu.israaNow.models.EventResponse;
import ps.edu.israaNow.storge.SharedPrefManager;
import ps.edu.israaNow.ui.Home.HomeFragment;
import ps.edu.israaNow.ui.adapter.PostsAdapter;
import ps.edu.israaNow.models.Post;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComingEventsFragment extends Fragment implements PostsAdapter.ListItemClickListener {

    private RecyclerView comingEvents;
    private PostsAdapter postsAdapter;
    private ArrayList<Post> posts;
    private ProgressBar progress_circular;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View view;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_coming_event, container, false);
        TextView titleBar = getActivity().findViewById(R.id.title_bar);
        titleBar.setText("فعاليات قادمة");
        initViews();
        return view;
    }

    private void initViews() {
        comingEvents = view.findViewById(R.id.comingEvent);
        progress_circular = view.findViewById(R.id.progress_circular);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        posts = new ArrayList<>();
        getComingEvent();
        refreshData();
    }

    @Override
    public void onListItemClicked(int position, int viewId) {
        switch (viewId) {
            case R.id.ll_event:
                int id = posts.get(position).getId();
                Intent intent = new Intent(getActivity(), DetailsEvent.class);
                intent.putExtra("event_id", String.valueOf(id));
                startActivity(intent);
                break;
        }
    }

    private void getComingEvent() {
        Call<EventResponse> call = RetroiftGet.getInstance().getApi().getCommingEvent(SharedPrefManager.getInstance(getContext()).getUser().getToken());
        call.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {
                posts = response.body().getDataEvents();
                postsAdapter = new PostsAdapter(getActivity(), posts, ComingEventsFragment.this);
                progress_circular.setVisibility(View.GONE);
                comingEvents.setAdapter(postsAdapter);
            }

            @Override
            public void onFailure(Call<EventResponse> call, Throwable t) {
                progress_circular.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "حدث خطأ ما !", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void refreshData() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getComingEvent();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
