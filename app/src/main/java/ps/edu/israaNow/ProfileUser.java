package ps.edu.israaNow;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import ps.edu.israaNow.Retrofit.RetrofitClient;
import ps.edu.israaNow.Retrofit.RetroiftGet;
import ps.edu.israaNow.fcm.GenerateFCMService;
import ps.edu.israaNow.models.DataUser;
import ps.edu.israaNow.models.DefaultResponse;
import ps.edu.israaNow.models.LoginResponse;
import ps.edu.israaNow.storge.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileUser extends AppCompatActivity implements View.OnClickListener {

    private ImageView backHome, userImage;
    private EditText userName, editTextCurrentPassword, editTextNewPassword, editTextNewPasswordConf;
    private Button saveChangeProfile, savePassword;
    private Bitmap bitmap;
    private ProgressBar progress_circular, progress_circular_pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);
        initViews();
        initListeners();
        userName.setText(SharedPrefManager.getInstance(this).getUser().getName());
        Picasso.with(this).load(SharedPrefManager.getInstance(this).getUser().getPic()).into(userImage);


    }

    private void initViews() {
        backHome = findViewById(R.id.backHome);
        userImage = findViewById(R.id.edit_user_image);
        userName = findViewById(R.id.edit_user_name);
        saveChangeProfile = findViewById(R.id.saveChange);
        progress_circular = findViewById(R.id.progress_circular);
        progress_circular_pass = findViewById(R.id.progress_circular_pass);
        editTextCurrentPassword = findViewById(R.id.editTextCurrentPassword);
        editTextNewPassword = findViewById(R.id.newPassword);
        editTextNewPasswordConf = findViewById(R.id.newPasswordConf);
        savePassword = findViewById(R.id.saveChangePassword);
    }

    private void initListeners() {
        backHome.setOnClickListener(this);
        userImage.setOnClickListener(this);
        saveChangeProfile.setOnClickListener(this);
        savePassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backHome:
                Intent intent = new Intent(ProfileUser.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.edit_user_image:
                pickImage();
                break;
            case R.id.saveChange:
                updateProfile();
                break;

            case R.id.saveChangePassword:
                updatePassword();
                break;

        }
    }

    private void pickImage() {
        PickImageDialog.build(new PickSetup())
                .setOnPickResult(new IPickResult() {
                    @Override
                    public void onPickResult(PickResult r) {
                        userImage.setImageBitmap(r.getBitmap());
                        bitmap = r.getBitmap();
                    }
                })
                .setOnPickCancel(new IPickCancel() {
                    @Override
                    public void onCancelClick() {
                        Toast.makeText(ProfileUser.this, "لم يتم أختيار صورة شخصية", Toast.LENGTH_SHORT).show();
                    }
                }).show(getSupportFragmentManager());
    }

    public MultipartBody.Part bitmapToMultipartBodyPart(Bitmap bitmap, String name) {
        if (bitmap == null) {
            return null;
        }
        //create a file to write bitmap data
        int iUniqueId = (int) (System.currentTimeMillis() & 0xfffffff);
        File file = new File(getCacheDir(), iUniqueId + ".jpg");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Convert bitmap to byte array
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] bitmapdata = bos.toByteArray();

        //write the bytes in file
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //pass it like this
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        return MultipartBody.Part.createFormData(name, file.getName(), requestFile);
    }

    private void updateProfile() {
        progress_circular.setVisibility(View.VISIBLE);
        BitmapDrawable drawable = (BitmapDrawable) userImage.getDrawable();
        bitmap = drawable.getBitmap();
        String name = userName.getText().toString().trim();
        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        DataUser user = SharedPrefManager.getInstance(this).getUser();
        Call<LoginResponse> call = RetroiftGet.getInstance()
                .getApi()
                .updateProfile(
                        user.getToken(),
                        map,
                        bitmapToMultipartBodyPart(bitmap, "avatar")
                );
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.code() == 200) {
                    progress_circular.setVisibility(View.GONE);
                    Toast.makeText(ProfileUser.this, "تم تحديث البيانات", Toast.LENGTH_SHORT).show();
                    login();
                } else {
                    progress_circular.setVisibility(View.GONE);
                    Log.e("response", response.toString());

                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(ProfileUser.this, "حدث خطأ ما !", Toast.LENGTH_SHORT).show();


            }
        });
    }

    private void login() {
        progress_circular.setVisibility(View.VISIBLE);
        Call<LoginResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .userLogin(String.valueOf(SharedPrefManager.getInstance(this).getUser().getUser_no()), SharedPrefManager.getInstance(this).getUser().getPassword());
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                progress_circular.setVisibility(View.GONE);
                if (response.code() == 200 && loginResponse.getStatusUser().isSuccess()) {
                    SharedPrefManager.getInstance(ProfileUser.this).saveUser(loginResponse.getDataUser(), SharedPrefManager.getInstance(ProfileUser.this).getUser().getPassword());
                    Intent service = new Intent(ProfileUser.this, GenerateFCMService.class);
                    startService(service);
                } else {
                    Toast.makeText(ProfileUser.this, "خطأ في البيانات !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progress_circular.setVisibility(View.GONE);
                Toast.makeText(ProfileUser.this, "حدث خطأ ما !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updatePassword() {
        String currentPassword = editTextCurrentPassword.getText().toString().trim();
        String newPassword = editTextNewPassword.getText().toString().trim();
        String newPasswordConf = editTextNewPasswordConf.getText().toString().trim();

        if (currentPassword.isEmpty()) {
            editTextCurrentPassword.setError("أدخل كلمة المرور");
            editTextCurrentPassword.requestFocus();
            return;
        }
        if (newPassword.isEmpty()) {
            editTextNewPassword.setError("أدخل كلمة المرور الجديدة");
            editTextNewPassword.requestFocus();
            return;
        }
        if (newPassword.length() < 8) {
            editTextNewPassword.setError("يجب أن تتضمن كلمة المرور 8 أحرف");
            editTextNewPassword.requestFocus();
            return;
        }
        if (newPasswordConf.isEmpty()) {
            editTextNewPasswordConf.setError("قم بتأكيد كلمة المرور");
            editTextNewPasswordConf.requestFocus();
            return;
        }
        if (!newPasswordConf.equals(newPassword)) {
            editTextNewPasswordConf.setError("كلمة المرور غير متطابة");
            editTextNewPasswordConf.requestFocus();
            return;
        }
        progress_circular_pass.setVisibility(View.VISIBLE);
        DataUser user = SharedPrefManager.getInstance(ProfileUser.this).getUser();
        Call<DefaultResponse> call = RetroiftGet.getInstance().getApi()
                .updatePassword("Bearer" + user.getToken(),
                        currentPassword,
                        newPassword);

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {

                if (response.code() == 200) {

                    progress_circular_pass.setVisibility(View.GONE);
                    Toast.makeText(ProfileUser.this, "تم تغير كلمة المرور بنجاح", Toast.LENGTH_SHORT).show();
                    editTextCurrentPassword.setText("");
                    editTextNewPassword.setText("");
                    editTextNewPasswordConf.setText("");

                } else {
                    progress_circular_pass.setVisibility(View.GONE);
                    Toast.makeText(ProfileUser.this, "كلمة المرور الحالية خاطئة !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Toast.makeText(ProfileUser.this, "حدث خطأ ما !", Toast.LENGTH_SHORT).show();

            }
        });
    }
}