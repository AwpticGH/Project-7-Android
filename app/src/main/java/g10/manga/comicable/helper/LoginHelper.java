package g10.manga.comicable.helper;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import g10.manga.comicable.R;
import g10.manga.comicable.activity.MainActivity;
import g10.manga.comicable.controller.AuthController;
import g10.manga.comicable.model.AuthModel;

public class LoginHelper {

    private Activity activity;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private SignInClient oneTapClient;
    private BeginSignInRequest signInRequest;

    private String idToken = null;
    private String email = null;
    private String password = null;

    public boolean isLoggedIn() {
        return auth.getCurrentUser() != null;
    }

    public FirebaseAuth getAuth() {
        return this.auth;
    }

    public LoginHelper(Activity activity, FirebaseAuth auth) {
        this.activity = activity;
        this.auth = auth;
    }

    public void beginSignInRequest() {
        oneTapClient = Identity.getSignInClient(activity);
        signInRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(
                        BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        .setServerClientId(activity.getString(R.string.SERVER_CLIENT_ID))
                        .setFilterByAuthorizedAccounts(true)
                        .build()
                )
                .setPasswordRequestOptions(
                        BeginSignInRequest.PasswordRequestOptions.builder()
                        .setSupported(true)
                        .build()
                )
                .setAutoSelectEnabled(true)
                .build();

        oneTapClient.beginSignIn(signInRequest)
                .addOnSuccessListener(result -> {
                    try {
                        activity.startIntentSenderForResult(
                                result.getPendingIntent().getIntentSender(),
                                activity.getResources().getInteger(R.integer.REQUEST_ONE_TAP_UI),
                                null, 0, 0, 0
                            );
                    }
                    catch (IntentSender.SendIntentException e) {
                        Log.e(TAG, "Couldn't start one tap UI due to code : " + e.getLocalizedMessage());
                    }
                })
                .addOnFailureListener(exception -> {
                    Log.d(TAG, "Attempt to beginSignIn failed due to code : " + exception.getLocalizedMessage());
                    Toast.makeText(
                            activity,
                            activity.getString(R.string.ONE_TAP_UI_ERROR),
                            Toast.LENGTH_LONG
                    ).show();
                });
    }

    public void register(AuthModel model) {
        auth.createUserWithEmailAndPassword(model.getEmail(), model.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    user = auth.getCurrentUser();
                    AuthController controller = new AuthController(model);
                    controller.create();
                }
            }
        });
    }

    public void loginWithEmailAndPassword(String email, String password) {
        Log.d("Status Login", "LoginHelper(Before Sign-in) : " + isLoggedIn());
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

    public void makeToast(int loginStatus) {
        String text = null;
        switch (loginStatus) {
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
        }

        Toast.makeText(
                activity,
                text,
                Toast.LENGTH_SHORT
        ).show();
    }

    public FirebaseUser getCurrentUser() {
        return auth.getCurrentUser();
    }
}
