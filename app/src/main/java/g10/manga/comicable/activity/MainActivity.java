package g10.manga.comicable.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import g10.manga.comicable.R;
import g10.manga.comicable.api.MangaApi;
import g10.manga.comicable.call.ListCall;
import g10.manga.comicable.call.PopularCall;
import g10.manga.comicable.call.RecommendedCall;
import g10.manga.comicable.helper.LoginHelper;
import g10.manga.comicable.model.manga.ListModel;

public class MainActivity extends AppCompatActivity {

    FirebaseUser user;
    LoginHelper helper;

    RecyclerView recyclerView;
    TextView textResult;
    Button btnLogout;

    Intent intentLogout;

    ListCall listCall;
    List<ListModel> lists;

    PopularCall popularCall;

    RecommendedCall recommendedCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper = LoginActivity.getLoginHelper();
        user = helper.getCurrentUser();

        listCall = new ListCall(getString(R.string.MANGA_API_BASE_URL));
        popularCall = new PopularCall(getString(R.string.MANGA_API_BASE_URL));
        recommendedCall = new RecommendedCall(getString(R.string.MANGA_API_BASE_URL));

//        lists = listCall.getAllComics();
        popularCall.getPopulars(1);

        textResult = findViewById(R.id.text_login_result);
        btnLogout = findViewById(R.id.button_logout);

        intentLogout = new Intent(this, LoginActivity.class);

        textResult.setText(user.getEmail());

        btnLogout.setOnClickListener(view -> {
            helper.getAuth().signOut();
            helper.setOneTapUI(false);
            startActivity(intentLogout);
            finish();
            helper.makeToast(R.integer.LOGOUT_SUCCESSFUL);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (user == null) {
            startActivity(intentLogout);
            finish();
        }
    }
}