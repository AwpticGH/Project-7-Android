package g10.manga.comicable.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import g10.manga.comicable.R;
//import g10.manga.comicable.databinding.ActivityInfoBinding;
import g10.manga.comicable.api.MangaApi;
import g10.manga.comicable.model.manga.InfoModel;
import g10.manga.comicable.response.InfoResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoActivity extends AppCompatActivity {

//    private ActivityInfoBinding binding;
    private TextView tvTitle;

    private InfoModel info;
    private MangaApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        binding = ActivityInfoBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
//        ViewPager viewPager = binding.viewPager;
//        viewPager.setAdapter(sectionsPagerAdapter);
//        TabLayout tabs = binding.tabs;
//        tabs.setupWithViewPager(viewPager);
//        FloatingActionButton fab = binding.fab;

//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        tvTitle = findViewById(R.id.title);
        String endpoint = getIntent().getStringExtra("endpoint");

        Call<InfoResponse> callInfo = api.getComicInfo(endpoint);
        callInfo.enqueue(new Callback<InfoResponse>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<InfoResponse> call, Response<InfoResponse> response) {
                info = response.body().getInfo();
                tvTitle.setText(info.getTitle());
            }

            @Override
            public void onFailure(Call<InfoResponse> call, Throwable t) {
                Log.w("CallInfo Result(fail)", t.getLocalizedMessage());
            }
        });
    }
}