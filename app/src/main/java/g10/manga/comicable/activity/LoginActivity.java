package g10.manga.comicable.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

import g10.manga.comicable.R;
import g10.manga.comicable.controller.AuthController;

public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail;
    private EditText inputPassword;
    private Button btnLogin;
    private Button btnRegister;

    private FirebaseAuth mAuth;

    private Intent intentLogin;
    private Intent intentRegister;

    private static AuthController controller;

    public static AuthController getLoginHelper() {
        return controller;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        if (controller == null)
            controller = new AuthController(this);

        intentLogin = new Intent(this, MainActivity.class);
        intentRegister = new Intent(this, RegisterActivity.class);

        inputEmail = findViewById(R.id.input_login_email);
        inputPassword = findViewById(R.id.input_login_password);
        btnLogin = findViewById(R.id.button_login);
        btnRegister = findViewById(R.id.button_register_page);

        btnLogin.setOnClickListener(view -> {
            String email = inputEmail.getText().toString().trim();
            String password = inputPassword.getText().toString().trim();

            controller.loginWithEmailAndPassword(email, password);
        });

        btnRegister.setOnClickListener(view -> startActivity(intentRegister));
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (controller.isLoggedIn()) {
            startActivity(intentLogin);
            finish();
        }
    }

    @Override
    public void onActivityResult(int requestCode, @Nullable int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {
            case Activity.RESULT_OK:
                controller.makeToast(R.integer.LOGOUT_SUCCESSFUL);
                break;
        }
    }


}