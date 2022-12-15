package g10.manga.comicable.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import g10.manga.comicable.R;
import g10.manga.comicable.adapter.ChapterAdapter;
import g10.manga.comicable.call.ChapterCall;
import g10.manga.comicable.model.manga.ChapterModel;
import g10.manga.comicable.response.ChapterResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChapterActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private Toolbar toolbar;
    private TextView tvChapterName;
    private ImageView imgChapter;
    private ViewPager viewPager;
    private Button btnNext, btnPrev;

    private ChapterCall call;
    private ChapterModel model;
    private ChapterAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Mohon Tunggu");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Sedang menampilkan gambar");

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
//        setSupportActionBar(toolbar);
//        assert getSupportActionBar() != null;
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvChapterName = findViewById(R.id.tvTitle);
        tvChapterName.setSelected(true);

        viewPager = findViewById(R.id.viewPager);
        btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = viewPager.getCurrentItem();
                viewPager.setCurrentItem(currentItem + 1);
            }
        });

        btnPrev = findViewById(R.id.btnPrev);
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = viewPager.getCurrentItem();
                viewPager.setCurrentItem(currentItem - 1);
            }
        });

        call = new ChapterCall(getString(R.string.MANGA_API_BASE_URL));
        String endpoint = getIntent().getStringExtra("endpoint");
        Log.d(getLocalClassName(), "Chapter Endpoint : " + endpoint);
        progressDialog.show();
        call.getChapterDetail(endpoint).enqueue(new Callback<ChapterResponse>() {
            @Override
            public void onResponse(Call<ChapterResponse> call, Response<ChapterResponse> response) {
                assert response.body() != null;
                if (response.body().isSuccess()) {
                    model = response.body().getChapter();
                    tvChapterName.setText(model.getTitle());
                    setImage(model);

                    progressDialog.dismiss();

                }
                Log.d("Call Result(success)", "Title : " + model.getTitle());
                Log.d("Call Result(success)", "Images : " + model.getImages().toString());
            }

            @Override
            public void onFailure(Call<ChapterResponse> call, Throwable t) {
                Toast.makeText(
                        getApplicationContext(),
                        "Failed Loading Resources",
                        Toast.LENGTH_SHORT
                ).show();
                finish();
            }
        });
    }

    private void setImage(ChapterModel model) {
        adapter = new ChapterAdapter(model, this, R.layout.list_item_detail_chapter, R.id.tvPagination, R.id.imgPhoto);
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
