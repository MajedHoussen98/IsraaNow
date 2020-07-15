package ps.edu.israaNow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import ps.edu.israaNow.Retrofit.RetroiftGet;
import ps.edu.israaNow.models.EventResponse;
import ps.edu.israaNow.models.Post;
import ps.edu.israaNow.storge.SharedPrefManager;
import ps.edu.israaNow.ui.adapter.CollegesAdapter;
import ps.edu.israaNow.ui.adapter.PostsAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Search extends AppCompatActivity implements View.OnClickListener, CollegesAdapter.ListItemClickListener, PostsAdapter.ListItemClickListener {

    private ImageView closeSearchImage;
    private EditText search_editText;
    private ProgressBar progress_circular;
    private ArrayList<Post> searchedArrayList;
    private RecyclerView homeEvents;
    private PostsAdapter postsAdapter;
    private ArrayList<Post> posts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initViews();
        initListener();
    }


    private void initViews() {
        searchedArrayList = new ArrayList<>();
        closeSearchImage = findViewById(R.id.CloseSearch);
        search_editText = findViewById(R.id.search_editText);
        homeEvents = findViewById(R.id.searchR);
        progress_circular = findViewById(R.id.progress_circular);
        posts = new ArrayList<>();

        search_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchedArrayList.clear();
                if (!String.valueOf(s).isEmpty()) {
                    homeEvents.setVisibility(View.VISIBLE);
                    getSearch(String.valueOf(s));
                } else {
                    homeEvents.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initListener() {
        closeSearchImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.CloseSearch) {
            Intent intent = new Intent(Search.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void onListItemClicked(int position, int viewId) {
        switch (viewId) {
            case R.id.ll_event:
                int id = posts.get(position).getId();
                Intent intent = new Intent(Search.this, DetailsEvent.class);
                intent.putExtra("event_id", String.valueOf(id));
                startActivity(intent);
                break;
        }
    }


    private void getSearch(String name) {
        progress_circular.setVisibility(View.VISIBLE);
        Call<EventResponse> call = RetroiftGet.getInstance().getApi().search(SharedPrefManager.getInstance(Search.this).getUser().getToken(), name);
        call.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {
                if (response.code() == 200) {
                    Log.e("response", response.body().toString());
                    progress_circular.setVisibility(View.GONE);
                    posts = response.body().getDataEvents();
                    postsAdapter = new PostsAdapter(Search.this, posts, Search.this);
                    progress_circular.setVisibility(View.GONE);
                    homeEvents.setAdapter(postsAdapter);

                    postsAdapter = new PostsAdapter(Search.this, posts, Search.this);
                    GridLayoutManager manager = new GridLayoutManager(Search.this, 1);
                    homeEvents.setLayoutManager(manager);
                    homeEvents.setAdapter(postsAdapter);

                } else {
                    Toast.makeText(Search.this, "حدث خطأ ما !", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<EventResponse> call, Throwable t) {
                progress_circular.setVisibility(View.GONE);
                Toast.makeText(Search.this, "حدث خطأ ما !", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

