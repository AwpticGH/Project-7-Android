package g10.manga.comicable.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import g10.manga.comicable.R;
//import g10.manga.comicable.databinding.ActivityInfoBinding;
import g10.manga.comicable.adapter.ChapterListAdapter;
import g10.manga.comicable.api.MangaApi;
import g10.manga.comicable.call.InfoCall;
import g10.manga.comicable.model.manga.ChapterListModel;
import g10.manga.comicable.model.manga.InfoModel;
import g10.manga.comicable.response.InfoResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoActivity extends AppCompatActivity implements ChapterListAdapter.OnObjectSelected {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private TextView tvTitle, tvType, tvAuthor, tvStatus, tvRating, tvGenre;
    private ImageView imgViewThumbnail;

    private String infoEndpoint;
    private InfoCall infoCall;
    private InfoModel infoModel;
    private ChapterListAdapter chapterListAdapter;
    private ProgressDialog progressDialog;
    private List<ChapterListModel> chapterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Mohon Tunggu");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Sedang menampilkan info");

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
//        setSupportActionBar(toolbar);
//        assert getSupportActionBar() != null;

        imgViewThumbnail = findViewById(R.id.imgPhoto);
        tvTitle = findViewById(R.id.tvName);
        tvAuthor = findViewById(R.id.tvNameAuthor);
        tvType = findViewById(R.id.tvType);
        tvStatus = findViewById(R.id.tvStatus);
        tvRating = findViewById(R.id.tvRating);
        tvGenre = findViewById(R.id.tvStatus2);

        recyclerView = findViewById(R.id.rvChapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        infoEndpoint = getIntent().getStringExtra("endpoint");
        Log.d("InfoActivity", "info endpoint : " + infoEndpoint);

        if (infoEndpoint != null) {
            progressDialog.show();
            infoCall = new InfoCall(getString(R.string.MANGA_API_BASE_URL));
            infoCall.getComicInfo(infoEndpoint).enqueue(new Callback<InfoResponse>() {
                @Override
                public void onResponse(Call<InfoResponse> call, Response<InfoResponse> response) {
                    assert response.body() != null;
                    if (response.body().isSuccess()) {
                        infoModel = response.body().getInfo();
                        tvTitle.setText(infoModel.getTitle());
                        tvAuthor.setText(infoModel.getAuthor());
                        tvType.setText(infoModel.getType());
                        tvStatus.setText(infoModel.getStatus());
                        tvRating.setText(infoModel.getRating());
                        tvStatus.setText(infoModel.getStatus());
                        tvGenre.setText((infoModel.getGenres() != null) ? infoModel.getGenres().toString() : "Not Available");

                        Glide.with(getApplicationContext())
                                .load(infoModel.getThumbnail())
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(imgViewThumbnail);

                        chapterList = infoModel.getChapterList();
                        if (chapterList == null) {
                            chapterList = new ArrayList<>();
                            ChapterListModel model = new ChapterListModel();
                            model.setName("Not Available");
                            chapterList.add(model);
                        }

                        Log.d("InfoActivity", "Title : " + infoModel.getTitle());
                        Log.d("InfoActivity", "Author : " + infoModel.getAuthor());
                        Log.d("InfoActivity", "Type : " + infoModel.getType());
                        Log.d("InfoActivity", "Status : " + infoModel.getStatus());
                        Log.d("InfoActivity", "Rating : " + infoModel.getRating());
                        Log.d("InfoActivity", "Thumbnail : " + infoModel.getThumbnail());

                        getChapters(chapterList);
                    }
                    else {
                        Toast.makeText(
                                getApplicationContext(),
                                "Comic Info Not Available",
                                Toast.LENGTH_SHORT
                        ).show();
                        startMainActivity();
                    }
                }

                @Override
                public void onFailure(Call<InfoResponse> call, Throwable t) {
                    Toast.makeText(
                            getApplicationContext(),
                            "Failed Getting Resources",
                            Toast.LENGTH_SHORT
                    ).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            });
        }
        else {
            Toast.makeText(
                    getApplicationContext(),
                    "Comic Info Not Available",
                    Toast.LENGTH_SHORT
            ).show();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }

    private void getChapters(List<ChapterListModel> model) {
        chapterListAdapter = new ChapterListAdapter(model, getApplicationContext(), this, R.id.llChapter, R.id.btnChapter);
        recyclerView.setAdapter(chapterListAdapter);
        progressDialog.dismiss();
    }

    private void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onSelected(ChapterListModel model) {
        Intent intent = new Intent(this, (model.getName().equals("Not Available"))
                                                        ? MainActivity.class
                                                        : ChapterActivity.class);
        if (!model.getName().equals("Not Available"))
            intent.putExtra("endpoint", model.getEndpoint());
        startActivity(intent);
    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams winParams = window.getAttributes();
        if (on)
            winParams.flags |= bits;
        else
            winParams.flags &= ~bits;
        window.setAttributes(winParams);
    }
}