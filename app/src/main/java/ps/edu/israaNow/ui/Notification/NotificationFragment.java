package ps.edu.israaNow.ui.Notification;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ps.edu.israaNow.DetailsEvent;
import ps.edu.israaNow.R;
import ps.edu.israaNow.Retrofit.RetroiftGet;
import ps.edu.israaNow.models.EventResponse;
import ps.edu.israaNow.models.Post;
import ps.edu.israaNow.storge.SharedPrefManager;
import ps.edu.israaNow.ui.adapter.NotificationAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NotificationFragment extends Fragment implements NotificationAdapter.ListItemClickListener {

    private RecyclerView notificationR;
    private NotificationAdapter notificationAdapter;
    private ArrayList<Post> notifications = new ArrayList<>();
    private View view;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        TextView titleBar = getActivity().findViewById(R.id.title_bar);
        titleBar.setText("الإشعارات");
        view = inflater.inflate(R.layout.fragment_notification, container, false);
        initViews();
        getNotification();
        RefreshNotification();
        return view;
    }
    public void initViews() {
        notificationR = view.findViewById(R.id.notificationR);
        progressBar = view.findViewById(R.id.progress_circular);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
    }
    @Override
    public void onListItemClicked(int position, int viewId) {
        switch (viewId) {
            case R.id.ll_notification:
                int id = notifications.get(position).getId();
                String imageEvent = notifications.get(position).getPic();
                String titleEvent = notifications.get(position).getTitle();
                String descriptionEvent = notifications.get(position).getDescription();
                String location = notifications.get(position).getLocation();
                String place = notifications.get(position).getHall();
                String startTime = notifications.get(position).getStart_time();
                String endTime = notifications.get(position).getEnd_time();
                String startDate = notifications.get(position).getStart_date();
                boolean isJoin = notifications.get(position).isIs_joined();
                String endDate = notifications.get(position).getEnd_date();
                Integer numberJoin = notifications.get(position).getNo_join();
                Intent intent = new Intent(getActivity(), DetailsEvent.class);
                intent.putExtra("event_id", id);
                intent.putExtra("event_title", titleEvent);
                intent.putExtra("event_image", imageEvent);
                intent.putExtra("descriptionEvent", descriptionEvent);
                intent.putExtra("location", location);
                intent.putExtra("hall", place);
                intent.putExtra("startTime", startTime);
                intent.putExtra("endTime", endTime);
                intent.putExtra("startDate", startDate);
                intent.putExtra("endDate", endDate);
                intent.putExtra("numberJoin", numberJoin);
                intent.putExtra("isJoin", isJoin);
                startActivity(intent);
                break;
        }
    }
    private void getNotification() {
        Call<EventResponse> call = RetroiftGet.getInstance().getApi().getEvent(SharedPrefManager.getInstance(getContext()).getUser().getToken());
        call.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {
                if (response.code() == 200) {
                    progressBar.setVisibility(View.GONE);
                    notifications = response.body().getDataEvents();
                    notificationAdapter = new NotificationAdapter(getActivity(), notifications, NotificationFragment.this);
                    notificationR.setAdapter(notificationAdapter);
                } else {
                    Toast.makeText(getActivity(), "حدث خطأ ما !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EventResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "حدث خطأ ما !", Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void RefreshNotification(){

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNotification();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
