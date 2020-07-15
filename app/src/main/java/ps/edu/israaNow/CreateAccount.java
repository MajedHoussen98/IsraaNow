package ps.edu.israaNow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CreateAccount extends AppCompatActivity implements View.OnClickListener {

    private Button createAccountStudentButton, createAccountEmployButton;
    private TextView loginTextCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        initViews();
        initListeners();
    }

    public void initViews() {

        createAccountStudentButton = findViewById(R.id.CreateAccountStudentButton);
        createAccountEmployButton = findViewById(R.id.CreateAccountEmployButton);
        loginTextCreateAccount = findViewById(R.id.LoginTextCreateAccount);
    }

    public void initListeners() {
        createAccountStudentButton.setOnClickListener(this);
        createAccountEmployButton.setOnClickListener(this);
        loginTextCreateAccount.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.CreateAccountStudentButton:
                Intent intent = new Intent(CreateAccount.this, CreateAccountStudent.class);
                startActivity(intent);
                finish();
                break;
            case R.id.CreateAccountEmployButton:
                Intent intent1 = new Intent(CreateAccount.this, CreateAccountEmploy.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.LoginTextCreateAccount:
                Intent intent2 = new Intent(CreateAccount.this, LoginActivity.class);
                startActivity(intent2);
                finish();
                break;
        }
    }
}
