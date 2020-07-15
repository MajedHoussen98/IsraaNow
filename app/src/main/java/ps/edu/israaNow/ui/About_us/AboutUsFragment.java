package ps.edu.israaNow.ui.About_us;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import ps.edu.israaNow.R;
import ps.edu.israaNow.Retrofit.RetroiftGet;
import ps.edu.israaNow.models.AboutUsResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutUsFragment extends Fragment {

    private View view;
    private TextView titleBar;
    private TextView descriptionTxtView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_about_us, container, false);
        initViews();
        titleBar.setText("من نحن ؟");
        getDescription();
        return view;
    }

    public void initViews() {
        titleBar = getActivity().findViewById(R.id.title_bar);
        descriptionTxtView = view.findViewById(R.id.describtionText);
    }

    private void getDescription() {

        Call<AboutUsResponse> call = RetroiftGet.getInstance().getApi().getDescription();
        call.enqueue(new Callback<AboutUsResponse>() {
            @Override
            public void onResponse(Call<AboutUsResponse> call, Response<AboutUsResponse> response) {
                AboutUsResponse aboutUsResponse = response.body();

                if (response.code() == 200 && aboutUsResponse.getStatusAboutUs().isSuccess()) {

                    descriptionTxtView.setText(aboutUsResponse.getAboutUsData().getDescription());
                }
            }

            @Override
            public void onFailure(Call<AboutUsResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "حدث خطأ ما !", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

