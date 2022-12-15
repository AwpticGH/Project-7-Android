package g10.manga.comicable.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import g10.manga.comicable.R;
import g10.manga.comicable.controller.AuthController;
import g10.manga.comicable.model.AuthModel;

public class SettingActivity extends AppCompatActivity {

    private AuthController controller;
    private AuthModel model;

    private TextView textResult;
    private Button btnLogout, btnUpdate;

    private Intent intentLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        controller = MainActivity.getAuthController();
        model = MainActivity.getAuthModel();

        textResult = findViewById(R.id.text_user_name);
        btnLogout = findViewById(R.id.button_logout);
        btnUpdate = findViewById(R.id.button_update);

        intentLogout = new Intent(this, LoginActivity.class);

        textResult.setText((MainActivity.getAuthModel() != null)
                ? MainActivity.getAuthModel().getName()
                : FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

        btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(intentLogout);
            finish();
            controller.makeToast(R.integer.LOGOUT_SUCCESSFUL);
        });

        btnUpdate.setOnClickListener(v -> {
            startActivity(new Intent(this, UserUpdateActivity.class));
        });
    }
}