package g10.manga.comicable.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.akshay.library.CurveBottomBar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

import g10.manga.comicable.R;
import g10.manga.comicable.controller.AuthController;
import g10.manga.comicable.fragment.FavoriteFragment;
import g10.manga.comicable.fragment.GenreFragment;
import g10.manga.comicable.fragment.HomeFragment;
import g10.manga.comicable.model.AuthModel;
import g10.manga.comicable.utils.BottomBarBehavior;

public class MainActivity extends AppCompatActivity {

    private static AuthController authController;
    private static AuthModel authModel;
    private TextView tvToolbar;
    private ImageView ivLogo;

    private Fragment fragment;
    private CurveBottomBar navigation;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (authController == null)
            authController = new AuthController(this);

        authController.read(FirebaseAuth.getInstance().getCurrentUser(), new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                authModel = task.getResult().getValue(AuthModel.class);

                navigation = findViewById(R.id.curveBottomBar);
                navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

                CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
                layoutParams.setBehavior(new BottomBarBehavior());

                fab = findViewById(R.id.fab);
                fab.setOnClickListener(view -> {
                    fragment = new FavoriteFragment();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frameContainer, fragment, fragment.getClass().getSimpleName())
                            .commit();
                    navigation.setSelectedItemId(R.id.navigation_null);
                });

                tvToolbar = findViewById(R.id.text_toolbar);
                String greeting = "Halo, \n" + authModel.getName();
                tvToolbar.setText(greeting);

                ivLogo = findViewById(R.id.ivLogo);
                ivLogo.setOnClickListener(v -> {
                    startActivity(new Intent(getApplicationContext(), SettingActivity.class));
                });

                if (savedInstanceState == null){
                    navigation.setSelectedItemId(R.id.navigation_home);
                }
            }
        });
    }

    public static AuthController getAuthController() {
        return authController;
    }

    public static AuthModel getAuthModel() {
        return authModel;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (!authController.isLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    private final CurveBottomBar.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new CurveBottomBar.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = new HomeFragment();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frameContainer, fragment, fragment.getClass().getSimpleName())
                            .commit();
                    return true;
                case R.id.navigation_list:
                    fragment = new GenreFragment();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frameContainer, fragment, fragment.getClass().getSimpleName())
                            .commit();
                    return true;


            }
            return false;
        }
    };
}