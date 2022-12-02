package g10.manga.comicable.activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import g10.manga.comicable.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText inputEmail;
    private EditText inputPassword;
    private EditText inputName;
    private Button btnRegister;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        inputEmail = findViewById(R.id.input_register_email);
        inputPassword = findViewById(R.id.input_register_password);
        inputName = findViewById(R.id.input_register_name);
        btnRegister = findViewById(R.id.button_register);

        Intent intent = new Intent(this, LoginActivity.class);

        btnRegister.setOnClickListener(view -> {
            String email = inputEmail.getText().toString().trim();
            String password = inputPassword.getText().toString().trim();
            String name = inputName.getText().toString().trim();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "createUserWithEmailAndPassword:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                startActivity(intent);
                                finish();
                            }
                            else {
                                Log.w(TAG, "createUseWithEmailAndPassword:failure", task.getException());
                                Toast.makeText(
                                        getApplicationContext(),
                                        "Register Failed",
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                        }
                    });
        });
    }
}