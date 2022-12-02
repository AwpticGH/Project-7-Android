package g10.manga.comicable.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;

import g10.manga.comicable.R;

public class MainActivity extends AppCompatActivity {

    FirebaseUser firebaseAccount;
    GoogleSignInAccount googleAccount;

    Serializable user;

    TextView textResult;
    Button btnLogout;

    Intent intentLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intentLogout = new Intent(this, LoginActivity.class);
        btnLogout = findViewById(R.id.button_logout);
        textResult = findViewById(R.id.text_login_result);

//        if (getIntent().getSerializableExtra("account").getClass().equals(FirebaseUser.class))
//            user = getIntent().getSerializableExtra("account");
//        else if (getIntent().getSerializableExtra("account").getClass().equals(GoogleSignInAccount.class))
//            googleAccount = (GoogleSignInAccount) getIntent().getSerializableExtra("account");
        user = getIntent().getSerializableExtra("account");
        textResult.setText("Name : " + user.getClass().getTypeName());

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!getIntent().hasExtra("account")) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }
}