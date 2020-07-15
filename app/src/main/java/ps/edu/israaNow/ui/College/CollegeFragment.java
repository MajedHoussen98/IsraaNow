package ps.edu.israaNow.ui.College;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
import java.util.List;

import ps.edu.israaNow.R;
import ps.edu.israaNow.Retrofit.RetrofitClient;
import ps.edu.israaNow.Retrofit.RetroiftGet;
import ps.edu.israaNow.ShowCollege;
import ps.edu.israaNow.models.DataCollege;
import ps.edu.israaNow.models.CollegeResponse;
import ps.edu.israaNow.storge.SharedPrefManager;
import ps.edu.israaNow.ui.adapter.CollegesAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CollegeFragment extends Fragment implements CollegesAdapter.ListItemClickListener {

    private RecyclerView colleges;
    private CollegesAdapter collegesAdapter;
    private ArrayList<DataCollege> collegesArray;
    private View view;
    private ProgressBar progress_circular;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        TextView titleBar = getActivity().findViewById(R.id.title_bar);
        titleBar.setText("الكليات");
        view = inflater.inflate(R.layout.fragment_college, container, false);
        initViews();
        return view;
    }

    private void initViews() {
        progress_circular = view.findViewById(R.id.progress_circular);
        colleges = view.findViewById(R.id.colleges);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        collegesArray = new ArrayList<>();
        getCollege();
        refreshData();

    }

    @Override
    public void onListItemClicked(int position, int viewId) {
        switch (viewId){
            case R.id.ll_college:
                Integer id = collegesArray.get(position).getId();
                String name = collegesArray.get(position).getName();
                String imageLogoCollege = collegesArray.get(position).getPic();
                Intent intent = new Intent(getActivity(), ShowCollege.class);
                intent.putExtra("college_id", id);
                intent.putExtra("college_name", name);
                intent.putExtra("imageLogoCollege", imageLogoCollege);
                startActivity(intent);
                break;
        }
    }


    private void getCollege(){

        Call<CollegeResponse> call = RetroiftGet.getInstance().getApi().getCollege(SharedPrefManager.getInstance(getContext()).getUser().getToken());
        call.enqueue(new Callback<CollegeResponse>() {
            @Override
            public void onResponse(Call<CollegeResponse> call, Response<CollegeResponse> response) {
                collegesArray = response.body().getDataCollege();
                collegesAdapter = new CollegesAdapter(getActivity(), collegesArray, CollegeFragment.this);
                GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
                colleges.setLayoutManager(manager);

                manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        if (collegesArray.size() % 2 != 0) {
                            return (position == collegesArray.size() - 1) ? 2 : 1;
                        } else {
                            return 1;
                        }
                    }
                });
                colleges.setAdapter(collegesAdapter);
                    progress_circular.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<CollegeResponse> call, Throwable t) {
                progress_circular.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "حدث خطأ ما !", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void refreshData(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCollege();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
