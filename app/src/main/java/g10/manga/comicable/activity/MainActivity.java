package g10.manga.comicable.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import g10.manga.comicable.R;
import g10.manga.comicable.api.ListApi;
import g10.manga.comicable.helper.LoginHelper;
import g10.manga.comicable.helper.RetrofitHelper;
import g10.manga.comicable.model.manga.ListModel;
import g10.manga.comicable.response.ListResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    FirebaseUser user;
    LoginHelper helper;

    TextView textResult;
    Button btnLogout;

    Intent intentLogout;

    ListApi listApi;
    List<ListModel> lists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper = LoginActivity.getLoginHelper();
        user = helper.getCurrentUser();

        listApi = RetrofitHelper.getInstance(getString(R.string.MANGA_API_BASE_URL)).create(ListApi.class);

        Call<ListResponse> callList = listApi.getAll();
        callList.enqueue(new Callback<ListResponse>() {
            @Override
            public void onResponse(Call<ListResponse> call, Response<ListResponse> response) {
                lists = response.body().getLists();
                for (ListModel list : lists) {
                    Log.d("Call Result(Success)", "Title : " + list.getTitle());
                    Log.d("Call Result(Success)", "Endpoint : " + list.getEndpoint());
                    Log.d("Call Result(Success)", "Image : " + list.getImage());
                }
            }

            @Override
            public void onFailure(Call<ListResponse> call, Throwable t) {
                Log.w("Call Result(Fail)", t.getLocalizedMessage());
            }
        });

        textResult = findViewById(R.id.text_login_result);
        btnLogout = findViewById(R.id.button_logout);

        intentLogout = new Intent(this, LoginActivity.class);

        textResult.setText(String.format("Name : %s", user.getEmail()));

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