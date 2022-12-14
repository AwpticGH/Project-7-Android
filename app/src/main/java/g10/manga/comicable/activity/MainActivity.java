package g10.manga.comicable.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.akshay.library.CurveBottomBar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

import java.util.List;

import g10.manga.comicable.R;
import g10.manga.comicable.call.ListCall;
import g10.manga.comicable.call.PopularCall;
import g10.manga.comicable.call.RecommendedCall;
import g10.manga.comicable.controller.AuthController;
import g10.manga.comicable.fragment.GenreFragment;
import g10.manga.comicable.fragment.HomeFragment;
import g10.manga.comicable.model.AuthModel;
import g10.manga.comicable.model.manga.ListModel;
import g10.manga.comicable.utils.BottomBarBehavior;

public class MainActivity extends AppCompatActivity {

//    AuthController controller;
//    AuthModel model;
//
//    RecyclerView recyclerView;
//    TextView textResult;
//    Button btnLogout;
//
//    Intent intentLogout;
//
//    ListCall listCall;
//    List<ListModel> lists;
//
//    PopularCall popularCall;
//
//    RecommendedCall recommendedCall;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        controller = new AuthController(this);
//
//        listCall = new ListCall(getString(R.string.MANGA_API_BASE_URL));
//        popularCall = new PopularCall(getString(R.string.MANGA_API_BASE_URL));
//        recommendedCall = new RecommendedCall(getString(R.string.MANGA_API_BASE_URL));
//
////        lists = listCall.getAllComics();
//        popularCall.getPopulars(1);
//
//        textResult = findViewById(R.id.text_login_result);
//        btnLogout = findViewById(R.id.button_logout);
//
//        intentLogout = new Intent(this, LoginActivity.class);
//
//        controller.read(FirebaseAuth.getInstance().getCurrentUser(), new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                model = task.getResult().getValue(AuthModel.class);
//                textResult.setText((model != null)
//                        ? model.getName()
//                        : FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
//            }
//        });
//
//        btnLogout.setOnClickListener(view -> {
//            FirebaseAuth.getInstance().signOut();
//            startActivity(intentLogout);
//            finish();
//            controller.makeToast(R.integer.LOGOUT_SUCCESSFUL);
//        });
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
//            startActivity(intentLogout);
//            finish();
//        }
//    }

    CurveBottomBar navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigation = findViewById(R.id.curveBottomBar);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        layoutParams.setBehavior(new BottomBarBehavior());

        if (savedInstanceState == null){
            navigation.setSelectedItemId(R.id.navigation_home);
        }

    }

    private final CurveBottomBar.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new CurveBottomBar.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;

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