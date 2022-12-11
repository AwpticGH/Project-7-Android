package g10.manga.comicable.helper;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import g10.manga.comicable.R;
import g10.manga.comicable.activity.MainActivity;

public class LoginHelper {

    private Activity activity;

    private FirebaseAuth auth;

    SignInClient oneTapClient;
    BeginSignInRequest signInRequest;

    private String idToken = null;
    private String email = null;
    private String password = null;

    private boolean oneTapUI = true;

    public boolean isOneTapUI() {
        return oneTapUI;
    }

    public void setOneTapUI(boolean status) {
        this.oneTapUI = status;
        if (oneTapUI)
            beginSignInRequest();
    }

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
                    oneTapUI = false;
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
