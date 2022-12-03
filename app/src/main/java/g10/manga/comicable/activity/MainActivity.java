package g10.manga.comicable.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;

import g10.manga.comicable.R;
import g10.manga.comicable.helper.LoginHelper;

public class MainActivity extends AppCompatActivity {


    FirebaseUser user;
    LoginHelper helper;

    TextView textResult;
    Button btnLogout;

    Intent intentLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper = LoginActivity.getLoginHelper();
        user = helper.getCurrentUser();

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