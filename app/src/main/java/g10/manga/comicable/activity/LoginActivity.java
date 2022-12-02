package g10.manga.comicable.activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import g10.manga.comicable.R;

public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail;
    private EditText inputPassword;
    private Button btnLogin;
    private Button btnRegister;
    private SignInButton btnLoginWithGoogle;

    private FirebaseAuth mAuth;
    private GoogleSignInClient gsc;
    private GoogleSignInOptions gso;

    private Intent intentLogin;
    private Intent intentRegister;
    private Intent intentLoginWithGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

        intentLogin = new Intent(this, MainActivity.class);
        intentRegister = new Intent(this, RegisterActivity.class);

        inputEmail = findViewById(R.id.input_login_email);
        inputPassword = findViewById(R.id.input_login_password);
        btnLogin = findViewById(R.id.button_login);
        btnRegister = findViewById(R.id.button_register_page);
        btnLoginWithGoogle = findViewById(R.id.button_login_with_google);
        btnLoginWithGoogle.setSize(SignInButton.SIZE_STANDARD);

        btnLogin.setOnClickListener(view -> {
            String email = inputEmail.getText().toString().trim();
            String password = inputPassword.getText().toString().trim();

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "signInWithEmailAndPassword:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                intentLogin.putExtra("account", user);
                                startActivity(intentLogin);
                                finish();
                            }
                            else {
                                Log.w(TAG, "signInWithEmailAndPassword:failure", task.getException());
                                Toast.makeText(
                                        getApplicationContext(),
                                        "Authentication Failed!",
                                        Toast.LENGTH_SHORT
                                ).show();

                            }
                        }
                    });
        });

        btnRegister.setOnClickListener(view -> startActivity(intentRegister));

        btnLoginWithGoogle.setOnClickListener(view -> {
            intentLoginWithGoogle = gsc.getSignInIntent();
            startActivityForResult(intentLoginWithGoogle, R.integer.request_code_google_sign_in);
        });
    }

    @Override
    public void onActivityResult(int requestCode, @Nullable int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == R.integer.request_code_google_sign_in) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            intentLogin.putExtra("account", account);
            startActivity(intentLogin);
            finish();
        }
        catch (ApiException e) {
            Log.w(TAG, "signInResult : failed \n code : " + e.getStatusCode());
            Toast.makeText(
                    this,
                    "Login Failed, Please Try Again!",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}