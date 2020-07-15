package ps.edu.israaNow;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import ps.edu.israaNow.storge.SharedPrefManager;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView closeNav, iconsMenu, iconSearch, userImage;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ImageView bottomFavoriteImage;
    private ImageView bottomComingEvents;
    private ImageView bottomHomeImage;
    private ImageView bottomCollegeImage;
    private TextView userNumber, nameUser;
    private ImageView bottomNotificationImage;
    private NavController navController;


    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navigationView, navController);

        initViews();
        initListeners();

        nameUser.setText(SharedPrefManager.getInstance(this).getUser().getName());
        Picasso.with(this).load(SharedPrefManager.getInstance(this).getUser().getPic()).into(userImage);
        userNumber.setText(String.valueOf(SharedPrefManager.getInstance(this).getUser().getUser_no()));
    }

    private void initViews() {

        View header = navigationView.getHeaderView(0);
        closeNav = header.findViewById(R.id.close_nav);
        userImage = header.findViewById(R.id.userImageView);
        userNumber = header.findViewById(R.id.user_number);
        nameUser = header.findViewById(R.id.nameUser);
        iconsMenu = findViewById(R.id.icons_menu);
        iconSearch = findViewById(R.id.icon_search);
        bottomFavoriteImage = findViewById(R.id.bottom_favorite_image);
        bottomComingEvents = findViewById(R.id.bottom_coming_events_image);
        bottomHomeImage = findViewById(R.id.bottom_home_image);
        bottomCollegeImage = findViewById(R.id.bottom_college_image);
        bottomNotificationImage = findViewById(R.id.bottom_notification_image);
    }

    private void initListeners() {

        closeNav.setOnClickListener(this);
        iconsMenu.setOnClickListener(this);
        iconSearch.setOnClickListener(this);
        bottomFavoriteImage.setOnClickListener(this);
        bottomComingEvents.setOnClickListener(this);
        bottomHomeImage.setOnClickListener(this);
        bottomCollegeImage.setOnClickListener(this);
        bottomNotificationImage.setOnClickListener(this);
        userImage.setOnClickListener(this);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("WrongConstant")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.nav_logout) {
                    SharedPrefManager.getInstance(MainActivity.this).clear();
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();

                } else if (menuItem.getItemId() == R.id.nav_fav) {
                    changeBottomBackgrounds(bottomFavoriteImage, R.drawable.ic_favorite_selected);
                    navController.navigate(R.id.nav_fav);

                } else if (menuItem.getItemId() == R.id.nav_home) {
                    changeBottomBackgrounds(bottomHomeImage, R.drawable.ic_home_selected);
                    navController.navigate(R.id.nav_home);

                } else if (menuItem.getItemId() == R.id.nav_about_us) {
                    changeBottomBackgrounds(bottomFavoriteImage, R.drawable.ic_favorite);
                    changeBottomBackgrounds(bottomComingEvents, R.drawable.ic_event_available_black_24dp);
                    changeBottomBackgrounds(bottomHomeImage, R.drawable.ic_home);
                    changeBottomBackgrounds(bottomCollegeImage, R.drawable.ic_college);
                    changeBottomBackgrounds(bottomNotificationImage, R.drawable.ic_notifications);
                    navController.navigate(R.id.nav_about_us);

                } else if (menuItem.getItemId() == R.id.nav_college) {
                    changeBottomBackgrounds(bottomCollegeImage, R.drawable.ic_college_selected);
                    navController.navigate(R.id.nav_college);

                } else if (menuItem.getItemId() == R.id.nav_coming_event) {
                    changeBottomBackgrounds(bottomComingEvents, R.drawable.ic_event_available_ss);
                    navController.navigate(R.id.nav_coming_event);

                } else if (menuItem.getItemId() == R.id.nav_notification) {
                    changeBottomBackgrounds(bottomNotificationImage, R.drawable.ic_notifications_selected);
                    navController.navigate(R.id.nav_notification);

                } else if (menuItem.getItemId() == R.id.nav_running_event) {
                    changeBottomBackgrounds(bottomFavoriteImage, R.drawable.ic_favorite);
                    changeBottomBackgrounds(bottomComingEvents, R.drawable.ic_event_available_black_24dp);
                    changeBottomBackgrounds(bottomHomeImage, R.drawable.ic_home);
                    changeBottomBackgrounds(bottomCollegeImage, R.drawable.ic_college);
                    changeBottomBackgrounds(bottomNotificationImage, R.drawable.ic_notifications);
                    navController.navigate(R.id.nav_running_event);
                } else if (menuItem.getItemId() == R.id.nav_share) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    final String appPackageName = getApplicationContext().getPackageName();
                    String strAppLink = "";
                    try {
                        strAppLink = "https://play.google.com/store/apps/details?id=" + appPackageName;
                    } catch (android.content.ActivityNotFoundException e) {
                        strAppLink = "https://play.google.com/store/apps/details?id=" + appPackageName;
                    }
                    intent.setType("text/link");
                    String shareBody = "قم بتحميل التطبيق الأن" + "\n" + "" + strAppLink;
                    String shareSub = "APP NAME/ TITLE";
                    intent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                    intent.putExtra(Intent.EXTRA_TEXT, shareBody);
                    startActivity(Intent.createChooser(intent, "مشاركة بأستخدام"));
                } else if (menuItem.getItemId() == R.id.nav_archive_event) {
                    changeBottomBackgrounds(bottomFavoriteImage, R.drawable.ic_favorite);
                    changeBottomBackgrounds(bottomComingEvents, R.drawable.ic_event_available_black_24dp);
                    changeBottomBackgrounds(bottomHomeImage, R.drawable.ic_home);
                    changeBottomBackgrounds(bottomCollegeImage, R.drawable.ic_college);
                    changeBottomBackgrounds(bottomNotificationImage, R.drawable.ic_notifications);
                    navController.navigate(R.id.nav_archive_event);
                }
                drawer.closeDrawer(Gravity.END);
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.close_nav:
                drawer.closeDrawer(Gravity.RIGHT);
                break;
            case R.id.icons_menu:
                drawer.openDrawer(Gravity.RIGHT);
                break;
            case R.id.icon_search:
                Intent intent = new Intent(MainActivity.this, Search.class);
                startActivity(intent);
                break;
            case R.id.bottom_favorite_image:
                changeBottomBackgrounds(bottomFavoriteImage, R.drawable.ic_favorite_selected);
                navController.navigate(R.id.nav_fav);
                break;
            case R.id.bottom_coming_events_image:
                changeBottomBackgrounds(bottomComingEvents, R.drawable.ic_event_available_ss);
                navController.navigate(R.id.nav_coming_event);
                break;
            case R.id.bottom_home_image:
                changeBottomBackgrounds(bottomHomeImage, R.drawable.ic_home_selected);
                navController.navigate(R.id.nav_home);
                break;
            case R.id.bottom_college_image:
                changeBottomBackgrounds(bottomCollegeImage, R.drawable.ic_college_selected);
                navController.navigate(R.id.nav_college);
                break;
            case R.id.bottom_notification_image:
                changeBottomBackgrounds(bottomNotificationImage, R.drawable.ic_notifications_selected);
                navController.navigate(R.id.nav_notification);
                break;
            case R.id.userImageView:
                Intent intentImage = new Intent(MainActivity.this, ProfileUser.class);
                startActivity(intentImage);
                break;
        }
    }

    private void changeBottomBackgrounds(ImageView imageToChange, int resId) {
        bottomFavoriteImage.setImageResource(R.drawable.ic_favorite);
        bottomComingEvents.setImageResource(R.drawable.ic_event_available_black_24dp);
        bottomHomeImage.setImageResource(R.drawable.ic_home);
        bottomCollegeImage.setImageResource(R.drawable.ic_college);
        bottomNotificationImage.setImageResource(R.drawable.ic_notifications);
        imageToChange.setImageResource(resId);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
