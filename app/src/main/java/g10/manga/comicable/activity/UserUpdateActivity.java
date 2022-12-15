package g10.manga.comicable.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

import g10.manga.comicable.R;
import g10.manga.comicable.controller.AuthController;
import g10.manga.comicable.model.AuthModel;

public class UserUpdateActivity extends AppCompatActivity {

    private AuthController controller;
    private AuthModel model;

    private TextView tvEmail, tvPassword, tvName;
    private Button btnUpdate, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_update);
        controller = MainActivity.getAuthController();
        model = MainActivity.getAuthModel();

        tvEmail = findViewById(R.id.text_email);
        tvPassword = findViewById(R.id.text_password);
        tvName = findViewById(R.id.text_name);
        btnUpdate = findViewById(R.id.btn_update);
        btnDelete = findViewById(R.id.btn_delete);

        if (model != null)
            setViewAttributes();
        else {
            controller.read(FirebaseAuth.getInstance().getCurrentUser(), new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()) {
                        model = task.getResult().getValue(AuthModel.class);
                        setViewAttributes();
                    }
                    else {
                        Toast.makeText(
                                getApplicationContext(),
                                "Failed Getting Account Information",
                                Toast.LENGTH_SHORT
                        ).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                }
            });
        }

        btnUpdate.setOnClickListener(view -> {
            String email = tvEmail.getText().toString().trim();
            String password = tvPassword.getText().toString().trim();
            String name = tvName.getText().toString().trim();

            model.setEmail(email);
            model.setPassword(password);
            model.setName(name);

            controller.update(model);
        });

        btnDelete.setOnClickListener(view -> {
            controller.delete(model);
        });
    }

    private void setViewAttributes() {
        tvEmail.setText(model.getEmail());
        tvPassword.setText(model.getPassword());
        tvName.setText(model.getName());
    }
}