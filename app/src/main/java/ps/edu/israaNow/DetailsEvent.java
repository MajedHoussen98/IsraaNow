package ps.edu.israaNow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import ps.edu.israaNow.Retrofit.RetroiftGet;
import ps.edu.israaNow.models.AddtoFavResponse;
import ps.edu.israaNow.models.Event;
import ps.edu.israaNow.models.EventResponse;
import ps.edu.israaNow.models.JoinEventResponse;
import ps.edu.israaNow.models.Post;
import ps.edu.israaNow.storge.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ps.edu.israaNow.R.*;

public class DetailsEvent extends AppCompatActivity implements View.OnClickListener {

    private ImageView iconBackHome;
    private ImageView imageViewEvent, joinEventButton;
    private TextView textViewTitle, textViewDescription, textViewLocation, textViewPlace, textViewStartTime, textViewEndTime,
                     textViewStartDate, textViewEndDate, textViewJoin, textViewJoinNumber;
    private boolean joined;
    private String eventTitle, eventImage, descriptionEvent, startTime, endTime, startDate, endDate, location, place;
    private int numberJoin;
    private ProgressBar progressBar;
    private String id;
    private LinearLayout ll2, ll3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_detailes_event);

        initViews();
        initListener();
        id = getIntent().getExtras().getString("event_id");
        getDetailsEvent(id);
        
    }


    private void initViews() {
        iconBackHome = findViewById(R.id.icon_back_home);
        textViewTitle = findViewById(R.id.event_title);
        imageViewEvent = findViewById(R.id.event_image);
        textViewDescription = findViewById(R.id.detailes_description);
        textViewLocation = findViewById(R.id.event_location);
        textViewPlace = findViewById(R.id.event_place);
        textViewStartTime = findViewById(R.id.event_start_time);
        textViewEndTime = findViewById(R.id.event_end_time);
        textViewStartDate = findViewById(R.id.event_start_date);
        textViewEndDate = findViewById(R.id.event_end_date);
        textViewJoin = findViewById(R.id.join_eventText);
        textViewJoinNumber = findViewById(R.id.number_joined);
        joinEventButton = findViewById(R.id.join_eventButton);
        progressBar = findViewById(R.id.progress_circular);
        ll2 = findViewById(R.id.ll2);
        ll3 = findViewById(R.id.ll3);

    }

    private void initListener() {
        iconBackHome.setOnClickListener(this);
        joinEventButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.join_eventButton) {

            if (joined) {
                textViewJoin.setText("إنضمام");
                joined = false;
                --numberJoin;
                disjoinevent(String.valueOf(id), DetailsEvent.this);
                textViewJoinNumber.setText(String.valueOf(numberJoin));

            } else {
                textViewJoin.setText("منضم");
                joined = true;
                ++numberJoin;
                joinEvent(String.valueOf(id), DetailsEvent.this);
                textViewJoinNumber.setText(String.valueOf(numberJoin));
            }

        } else if (v.getId() == R.id.icon_back_home) {

            startActivity(new Intent(DetailsEvent.this, MainActivity.class));
            finish();
        }


    }

    private void joinEvent(String id, final Context context) {
        Call<JoinEventResponse> call = RetroiftGet.getInstance().getApi().joinEvent(SharedPrefManager.getInstance(context).getUser().getToken(), id);
        call.enqueue(new Callback<JoinEventResponse>() {
            @Override
            public void onResponse(Call<JoinEventResponse> call, Response<JoinEventResponse> response) {

                Toast.makeText(context, response.body().getStatusUser().getMessage(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<JoinEventResponse> call, Throwable t) {

            }
        });

    }

    private void disjoinevent(String eventId, final Context context) {

        Call<JoinEventResponse> call = RetroiftGet.getInstance().getApi().disjoinevent(SharedPrefManager.getInstance(context).getUser().getToken(), eventId);
        call.enqueue(new Callback<JoinEventResponse>() {
            @Override
            public void onResponse(Call<JoinEventResponse> call, Response<JoinEventResponse> response) {

                Toast.makeText(context, response.body().getStatusUser().getMessage(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<JoinEventResponse> call, Throwable t) {

                Toast.makeText(DetailsEvent.this, "حدث خطأ ما !", Toast.LENGTH_SHORT).show();

            }
        });

    }


    private void getDetailsEvent(String id) {
        progressBar.setVisibility(View.VISIBLE);

        Call<Event> call = RetroiftGet.getInstance().getApi().getEventDetails(SharedPrefManager.getInstance(this).getUser().getToken(), id);
        call.enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {


                progressBar.setVisibility(View.GONE);
                eventTitle = response.body().getPost().getTitle();
                eventImage = response.body().getPost().getPic();
                descriptionEvent = response.body().getPost().getDescription();
                startTime = response.body().getPost().getStart_time();
                endTime = response.body().getPost().getEnd_time();
                startDate = response.body().getPost().getStart_date();
                endDate = response.body().getPost().getEnd_date();
                numberJoin = response.body().getPost().getNo_join();
                location = response.body().getPost().getLocation();
                place = response.body().getPost().getHall();
                joined = response.body().getPost().isIs_joined();

                if (joined) {
                    textViewJoin.setText("منضم");
                } else {
                    textViewJoin.setText("إنضمام");
                }

                textViewTitle.setText(eventTitle);
                Glide.with(DetailsEvent.this).load(eventImage).into(imageViewEvent);
                textViewDescription.setText(descriptionEvent);
                textViewStartTime.setText(startTime);
                textViewEndTime.setText(endTime);
                textViewStartDate.setText(startDate);
                textViewEndDate.setText(endDate);
                textViewLocation.setText(location);
                textViewPlace.setText(place);
                textViewJoinNumber.setText(String.valueOf(numberJoin));

                textViewTitle.setVisibility(View.VISIBLE);
                ll2.setVisibility(View.VISIBLE);
                ll3.setVisibility(View.VISIBLE);

            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {

            }
        });
    }

}
