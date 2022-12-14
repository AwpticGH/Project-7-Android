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

import g10.manga.comicable.R;
import g10.manga.comicable.controller.AuthController;
import g10.manga.comicable.model.AuthModel;

public class RegisterActivity extends AppCompatActivity {

    private EditText inputEmail;
    private EditText inputPassword;
    private EditText inputName;
    private Button btnRegister;

    private AuthModel authModel;
    private FirebaseAuth mAuth;
    private AuthController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        if (controller == null)
            controller = new AuthController(this);

        inputEmail = findViewById(R.id.input_register_email);
        inputPassword = findViewById(R.id.input_register_password);
        inputName = findViewById(R.id.input_register_name);
        btnRegister = findViewById(R.id.button_register);

        btnRegister.setOnClickListener(view -> {
            String email = inputEmail.getText().toString().trim();
            String password = inputPassword.getText().toString().trim();
            String name = inputName.getText().toString().trim();

            authModel = new AuthModel();
            authModel.setEmail(email);
            authModel.setPassword(password);
            authModel.setName(name);

            controller.create(authModel);
        });
    }
}