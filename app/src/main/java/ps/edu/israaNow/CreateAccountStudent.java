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

import okhttp3.ResponseBody;
import ps.edu.israaNow.Retrofit.RetrofitClient;
import ps.edu.israaNow.Retrofit.RetroiftGet;
import ps.edu.israaNow.dialogs.SuccessfulRegister;
import ps.edu.israaNow.models.StatusResponse;
import ps.edu.israaNow.storge.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateAccountStudent extends AppCompatActivity implements View.OnClickListener {

    private EditText studentName, studentNumber, password, password_confirmation;
    private TextView loginTextStudent;
    private Button createAccountStudentButton;
    private ProgressBar progress_circular;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_student);

        initViews();
        initListener();
    }


    public void initViews() {
        studentName = findViewById(R.id.student_name);
        studentNumber = findViewById(R.id.student_number);
        password = findViewById(R.id.editText_password);
        password_confirmation = findViewById(R.id.password_confirmation);
        loginTextStudent = findViewById(R.id.LoginTextStudent);
        createAccountStudentButton = findViewById(R.id.btnS);
        progress_circular = findViewById(R.id.progress_circular);

    }

    public void initListener() {
        loginTextStudent.setOnClickListener(this);
        createAccountStudentButton.setOnClickListener(this);
    }

    private void createAccountStudent() {
        String type = "student";
        String name = studentName.getText().toString();
        String number = String.valueOf(studentNumber.getText());
        String pass = password.getText().toString();
        String pass_conf = password_confirmation.getText().toString();


        if (name.isEmpty()) {
            studentName.setError("أدخل أسم الطالب");
            studentName.requestFocus();
            return;
        }
        if (number.isEmpty()) {
            studentNumber.setError("أدخل الرقم الجامعي");
            studentNumber.requestFocus();
            return;
        }
        if (pass.isEmpty()) {
            password.setError("أدخل كلمة المرور");
            password.requestFocus();
            return;
        }
        if (pass.length() < 8) {
            password.setError("يجب أن تتضمن كلمة المرور 8 أحرف");
            password.requestFocus();
            return;
        }
        if (pass_conf.isEmpty()) {
            password_confirmation.setError("قم بتأكيد كلمة المرور");
            password_confirmation.requestFocus();
            return;
        }
        if (!pass.equals(pass_conf)) {
            password_confirmation.setError("كلمة المرور ليست متطابقة");
            password_confirmation.requestFocus();
            return;
        }
        progress_circular.setVisibility(View.VISIBLE);
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .SendDataRegister(type, name, number, pass, pass_conf);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 201) {
                    progress_circular.setVisibility(View.GONE);
                    openDialog();
                    studentName.setText("");
                    studentNumber.setText("");
                    password.setText("");
                    password_confirmation.setText("");
                }else {
                    progress_circular.setVisibility(View.GONE);
                   Toast.makeText(CreateAccountStudent.this, "هذا المستخدم موجود !" +"\n"+
                            "الرجاء أدخال بيانات صحيحة", Toast.LENGTH_SHORT).show();
               }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                progress_circular.setVisibility(View.GONE);
                Toast.makeText(CreateAccountStudent.this, "حدث خطأ ما !", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.LoginTextStudent:
                Intent intent = new Intent(CreateAccountStudent.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btnS:
                createAccountStudent();
                break;
        }
    }

    public void openDialog() {
        SuccessfulRegister successfulRegister = new SuccessfulRegister();
        successfulRegister.show(getSupportFragmentManager(), "successfulRegister");
    }
}
