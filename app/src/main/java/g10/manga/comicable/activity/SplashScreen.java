package g10.manga.comicable.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import g10.manga.comicable.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        ImageView ivComicable = findViewById(R.id.iv_splash_screen);
        TextView tvComicable = findViewById(R.id.tv_splash_screen);

        Animation slide = AnimationUtils.loadAnimation(this, R.anim.side_slide);
        ivComicable.startAnimation(slide);
        tvComicable.startAnimation(slide);

        Handler handler = new Handler();
        handler.postDelayed(this::startMainActivity, 3000);
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}