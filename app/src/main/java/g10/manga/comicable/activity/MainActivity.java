package g10.manga.comicable.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import g10.manga.comicable.R;
import g10.manga.comicable.adapter.ListAdapter;
import g10.manga.comicable.api.InfoApi;
import g10.manga.comicable.api.ListApi;
import g10.manga.comicable.helper.LoginHelper;
import g10.manga.comicable.helper.RetrofitHelper;
import g10.manga.comicable.model.manga.InfoModel;
import g10.manga.comicable.model.manga.ListModel;
import g10.manga.comicable.response.InfoResponse;
import g10.manga.comicable.response.ListResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ListAdapter.ItemClickListener {

    FirebaseUser user;
    LoginHelper helper;

    RecyclerView recyclerView;
    TextView textResult;
    Button btnLogout;

    Intent intentLogout;

    ListApi listApi;
    List<ListModel> mangaLists;
    ListAdapter adapter;

    InfoApi infoApi;
    InfoModel mangaInfo;

    ListAdapter.ItemClickListener itemClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper = LoginActivity.getLoginHelper();
        user = helper.getCurrentUser();

        // User Email and Logout Button
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
        // --------------------------------------------

        // Api Call
        recyclerView = findViewById(R.id.recycler_view_main);
        itemClickListener = this::onItemClick;

        listApi = RetrofitHelper.getInstance(getString(R.string.MANGA_API_BASE_URL)).create(ListApi.class);
        infoApi = RetrofitHelper.getInstance(getString(R.string.MANGA_API_BASE_URL)).create(InfoApi.class);

        Call<ListResponse> callList = listApi.getAll();
        callList.enqueue(new Callback<ListResponse>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<ListResponse> call, Response<ListResponse> response) {
                mangaLists = response.body().getLists();

                int columns = mangaLists.size() / 2;
                recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), columns));
                adapter = new ListAdapter(getApplicationContext(), mangaLists);
                adapter.setItemClickListener(itemClickListener);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ListResponse> call, Throwable t) {
                Log.w("CallList Result(Fail)", t.getLocalizedMessage());
            }
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

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, InfoActivity.class);
        intent.putExtra("endpoint", adapter.getItem(position).getEndpoint());
        startActivity(intent);
    }
}