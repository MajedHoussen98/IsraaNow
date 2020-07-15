package ps.edu.israaNow.ui.Home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import com.google.firebase.iid.FirebaseInstanceId;

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

public class HomeFragment extends Fragment implements PostsAdapter.ListItemClickListener {

    private RecyclerView homeEvents;
    private PostsAdapter postsAdapter;
    private ArrayList<Post> posts = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private View view;
    private ProgressBar progress_circular;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        TextView titleBar = getActivity().findViewById(R.id.title_bar);
        titleBar.setText("الرئيسية");
        initViews();
        return view;
    }


    private void initViews() {
        homeEvents = view.findViewById(R.id.homeEvents);
        progress_circular = view.findViewById(R.id.progress_circular);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        posts = new ArrayList<>();
        getEvent();
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
            case R.id.share:

                break;
        }
    }

    private void getEvent() {
        progress_circular.setVisibility(View.VISIBLE);
        Call<EventResponse> call = RetroiftGet.getInstance().getApi().getEvent(SharedPrefManager.getInstance(getContext()).getUser().getToken());
        call.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {
                if (response.code() == 200) {
                    progress_circular.setVisibility(View.GONE);
                    posts = response.body().getDataEvents();
                    postsAdapter = new PostsAdapter(getActivity(), posts, HomeFragment.this);
                    progress_circular.setVisibility(View.GONE);
                    homeEvents.setAdapter(postsAdapter);
                } else {
                    Toast.makeText(getActivity(), "حدث خطأ ما !", Toast.LENGTH_SHORT).show();
                }

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
                getEvent();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

}
