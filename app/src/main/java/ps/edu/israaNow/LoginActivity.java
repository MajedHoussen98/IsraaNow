package ps.edu.israaNow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.HashMap;
import java.util.Map;

import ps.edu.israaNow.Retrofit.RetrofitClient;
import ps.edu.israaNow.Retrofit.RetroiftGet;
import ps.edu.israaNow.fcm.GenerateFCMService;
import ps.edu.israaNow.models.LoginResponse;
import ps.edu.israaNow.models.StatusResponse;
import ps.edu.israaNow.storge.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button signInButton;
    private TextView createAccountLogin, forgotPassword;
    private EditText editTextUserNumber, editTextPassword;
    private ProgressBar progress_circular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        initViews();
        initListener();
    }

    public void initViews() {
        editTextUserNumber = findViewById(R.id.user_number);
        editTextPassword = findViewById(R.id.editText_password);
        progress_circular = findViewById(R.id.progress_circular);
        signInButton = findViewById(R.id.SignInButton);
        createAccountLogin = findViewById(R.id.CreateTextLogin);
    }

    public void initListener() {

        signInButton.setOnClickListener(this);
        createAccountLogin.setOnClickListener(this);
    }

    private void userLogin() {
        String number = String.valueOf(editTextUserNumber.getText());
        final String pass = editTextPassword.getText().toString().trim();

        if (number.isEmpty()) {
            editTextUserNumber.setError("أدخل رقم المستخدم");
            editTextUserNumber.requestFocus();
            return;
        }
        if (pass.isEmpty()) {
            editTextPassword.setError("أدخل كلمة المرور");
            editTextPassword.requestFocus();
            return;
        }

        progress_circular.setVisibility(View.VISIBLE);
        Call<LoginResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .userLogin(number, pass);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                progress_circular.setVisibility(View.GONE);

                if (response.code() == 200 && loginResponse.getStatusUser().isSuccess()) {
                    SharedPrefManager.getInstance(LoginActivity.this).saveUser(loginResponse.getDataUser(), pass);
                    Intent service = new Intent(LoginActivity.this, GenerateFCMService.class);
                    startService(service);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "خطأ في البيانات !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progress_circular.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, "حدث خطأ ما !", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.SignInButton:
                userLogin();
                break;
            case R.id.CreateTextLogin:
                Intent intent1 = new Intent(LoginActivity.this, CreateAccount.class);
                startActivity(intent1);
                break;
        }
    }
}
