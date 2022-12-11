package g10.manga.comicable.activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import g10.manga.comicable.R;
import g10.manga.comicable.helper.LoginHelper;

public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail;
    private EditText inputPassword;
    private Button btnLogin;
    private Button btnRegister;

    private FirebaseAuth mAuth;

    private Intent intentLogin;
    private Intent intentRegister;

    private static LoginHelper helper;

    public static LoginHelper getLoginHelper() {
        return helper;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        if (helper == null)
            helper = new LoginHelper(this, mAuth);

        intentLogin = new Intent(this, MainActivity.class);
        intentRegister = new Intent(this, RegisterActivity.class);

        inputEmail = findViewById(R.id.input_login_email);
        inputPassword = findViewById(R.id.input_login_password);
        btnLogin = findViewById(R.id.button_login);
        btnRegister = findViewById(R.id.button_register_page);

        btnLogin.setOnClickListener(view -> {
            String email = inputEmail.getText().toString().trim();
            String password = inputPassword.getText().toString().trim();

            helper.loginWithEmailAndPassword(email, password);
        });

        btnRegister.setOnClickListener(view -> startActivity(intentRegister));
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (helper.isLoggedIn()) {
            startActivity(intentLogin);
            finish();
        }
    }

    @Override
    public void onActivityResult(int requestCode, @Nullable int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {
            case Activity.RESULT_OK:
                helper.makeToast(R.integer.LOGOUT_SUCCESSFUL);
                break;
        }
    }


}