package ps.edu.israaNow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import ps.edu.israaNow.Retrofit.RetroiftGet;
import ps.edu.israaNow.models.EventResponse;
import ps.edu.israaNow.storge.SharedPrefManager;
import ps.edu.israaNow.ui.adapter.PostsAdapter;
import ps.edu.israaNow.models.Post;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowCollege extends AppCompatActivity implements View.OnClickListener, PostsAdapter.ListItemClickListener {

    private ImageView iconBackCollege, imageLogoCollege;
    private TextView collegeNameTextView;
    private String collegeName, imageLogo;
    private Integer collegeId;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView collegesEvents;
    private PostsAdapter postsAdapter;
    private ProgressBar progress_circular;
    private ArrayList<Post> posts = new ArrayList<>();
    ArrayList<Post> postsFilter = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_college);

        collegeName = getIntent().getExtras().getString("college_name");
        collegeId = getIntent().getExtras().getInt("college_id");
        imageLogo = getIntent().getExtras().getString("imageLogoCollege");
        initViews();
        initListener();
        getEvent();
        refreshData();

    }

    private void initViews() {
        iconBackCollege = findViewById(R.id.icon_back_home);
        imageLogoCollege = findViewById(R.id.image_logo_college);
        collegeNameTextView = findViewById(R.id.collegeName);
        progress_circular = findViewById(R.id.progress_circular);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        collegeNameTextView.setText(collegeName);
        Glide.with(this).load(imageLogo).into(imageLogoCollege);
        collegesEvents = findViewById(R.id.recyclerViewColleges);

    }

    private void initListener() {
        iconBackCollege.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.icon_back_home:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onListItemClicked(int position, int viewId) {
        switch (viewId) {
            case R.id.ll_event:
                int id = postsFilter.get(position).getId();
                Intent intent = new Intent(this, DetailsEvent.class);
                intent.putExtra("event_id", String.valueOf(id));
                startActivity(intent);
                break;
        }
    }

    private void getEvent() {

        Call<EventResponse> call = RetroiftGet.getInstance().getApi().getEvent(SharedPrefManager.getInstance(ShowCollege.this).getUser().getToken());

        call.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {
                posts = response.body().getDataEvents();
                for (int i = 0; i < posts.size(); i++) {
                    if (posts.get(i).getCollege_id().equals(collegeId))
                        postsFilter.add(posts.get(i));
                }
                collegesEvents.setLayoutManager(new LinearLayoutManager(ShowCollege.this));
                postsAdapter = new PostsAdapter(ShowCollege.this, postsFilter, ShowCollege.this);
                progress_circular.setVisibility(View.GONE);
                collegesEvents.setAdapter(postsAdapter);
            }

            @Override
            public void onFailure(Call<EventResponse> call, Throwable t) {
                progress_circular.setVisibility(View.GONE);
                Toast.makeText(ShowCollege.this, "حدث خطأ ما !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void refreshData() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //    getEvent();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

}

