package ps.edu.israaNow.fcm;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.HashMap;
import java.util.Map;

import ps.edu.israaNow.Retrofit.RetroiftGet;
import ps.edu.israaNow.models.StatusResponse;
import ps.edu.israaNow.storge.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GenerateFCMService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        generateFCMToken(getApplicationContext());
        return super.onStartCommand(intent, flags, startId);
    }

    public void generateFCMToken(final Context context) {
        FirebaseApp.initializeApp(context);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            GenerateFCMService.this.stopSelf();
                            return;
                        }
                        String token = task.getResult().getToken();
                        GenerateFCMService.this.stopSelf();
                        GenerateFCMService.this.changeFCM(token);
                    }
                });
    }

    private void changeFCM(String token) {
        Map<String, String> map = new HashMap<>();
        map.put("fcm_token", token);
        Call<StatusResponse> call = RetroiftGet.getInstance().getApi()
                .changeFCM(SharedPrefManager.getInstance(this).getUser().getToken(), map);
        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {

            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {

            }
        });
    }
}
