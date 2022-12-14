package g10.manga.comicable.controller;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import g10.manga.comicable.R;
import g10.manga.comicable.activity.LoginActivity;
import g10.manga.comicable.activity.MainActivity;
import g10.manga.comicable.model.AuthModel;

public class AuthController {

    private final String DB_URL = "https://ccit-project-c68fc-default-rtdb.asia-southeast1.firebasedatabase.app/";
    private final String DB_REFERENCE = "Authentication";
    private AuthModel model;
    private DatabaseReference dbReference;
    private Task<DataSnapshot> task;
    private Activity activity;
    private boolean status;

    public AuthController() {
        dbReference = FirebaseDatabase.getInstance(DB_URL).getReference(DB_REFERENCE);
        this.status = false;
    }

    public AuthController(Activity activity) {
        dbReference = FirebaseDatabase.getInstance(DB_URL).getReference(DB_REFERENCE);
        this.activity = activity;
        this.status = false;
    }

    public AuthController(AuthModel model) {
        dbReference = FirebaseDatabase.getInstance(DB_URL).getReference(DB_REFERENCE);
        this.model = model;
        this.status = false;
    }

    public AuthController(FirebaseUser user) {
        dbReference = FirebaseDatabase.getInstance(DB_URL).getReference(DB_REFERENCE);
        this.task = read(user, new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                model = task.getResult().getValue(AuthModel.class);
            }
        });
        this.status = false;
    }

    public Task<DataSnapshot> getTask() {
        return task;
    }

    public boolean isLoggedIn() {
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }

    public void create(AuthModel model) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(model.getEmail(), model.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("AuthController", "Create Result : Success");
                    String id = auth.getCurrentUser().getUid();
                    model.setId(id);
                    dbReference.child(id).setValue(model);
                    activity.startActivity(new Intent(activity, LoginActivity.class));
                }
                else {
                    Log.w(TAG, "createUseWithEmailAndPassword:failure", task.getException());
                    Toast.makeText(
                            activity,
                            "Register Failed",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });
    }

    public Task<DataSnapshot> read(FirebaseUser user, OnCompleteListener<DataSnapshot> onCompleteListener) {
        return dbReference.child(user.getUid()).get().addOnCompleteListener(onCompleteListener);
    }

    public void update(AuthModel model) {
        dbReference.child(model.getId()).setValue(model)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                            makeToast(R.integer.UPDATE_USER_SUCCESSFUL);
                        else
                            makeToast(R.integer.UPDATE_USER_FAILED);
                    }
                });
    }

    public void loginWithEmailAndPassword(String email, String password) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "loginWilEmailAndPassword : Success");
                        activity.startActivity(new Intent(activity, MainActivity.class));
                        makeToast(R.integer.LOGIN_SUCCESSFUL);
                        activity.finish();
                    }
                    else {
                        Log.w(TAG, "loginWithEmailAndPassword : Fail");
                        makeToast(R.integer.LOGIN_FAILED);
                    }
                });
    }

    public void makeToast(int statusCode) {
        String text = null;
        switch (statusCode) {
            case R.integer.LOGIN_SUCCESSFUL:
                text = activity.getString(R.string.LOGIN_SUCCESSFUL);
                break;

            case R.integer.LOGIN_CANCELED:
                text = activity.getString(R.string.LOGIN_CANCELED);
                break;

            case R.integer.LOGOUT_SUCCESSFUL:
                text = activity.getString(R.string.LOGOUT_SUCCESSFUL);
                break;

            case R.integer.NETWORK_ERROR:
                text = activity.getString(R.string.NETWORK_ERROR);
                break;

            case R.integer.LOGIN_FAILED:
                text = activity.getString(R.string.LOGIN_FAILED);
                break;

            case R.integer.UPDATE_USER_SUCCESSFUL:
                text = activity.getString(R.string.UPDATE_USER_SUCCESSFUL);
                break;

            case R.integer.UPDATE_USER_FAILED:
                text = activity.getString(R.string.UPDATE_USER_FAILED);
                break;
        }

        Toast.makeText(
                activity,
                text,
                Toast.LENGTH_SHORT
        ).show();
    }

}
