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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import g10.manga.comicable.R;

public class LoginHelper {

    private Activity activity;

    private FirebaseAuth auth;
    private FirebaseUser user;

    SignInClient oneTapClient;
    BeginSignInRequest signInRequest;

    private String idToken = null;
    private String email = null;
    private String password = null;

    private boolean oneTapUI = true;


    public LoginHelper(Activity activity, FirebaseAuth auth) {
        this.activity = activity;
        this.auth = auth;
        user = auth.getCurrentUser();
    }

    public boolean isOneTapUI() {
        return oneTapUI;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void beginSignInRequest() {
        oneTapClient = Identity.getSignInClient(activity);
        System.out.println("oneTapClient : " + oneTapClient);
        signInRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        .setServerClientId(activity.getString(R.string.server_client_id))
                        .setFilterByAuthorizedAccounts(true)
                        .build()
                ).setPasswordRequestOptions(BeginSignInRequest.PasswordRequestOptions.builder()
                        .setSupported(true)
                        .build()
                ).setAutoSelectEnabled(true)
                .build();
        System.out.println("signInRequest : " + signInRequest);
        System.out.println("googleIdTokenRequestOptions : " + signInRequest.getGoogleIdTokenRequestOptions());
        System.out.println("serverClientId : " + signInRequest.getGoogleIdTokenRequestOptions().getServerClientId());
        System.out.println("passwordRequestOptions : " + signInRequest.getPasswordRequestOptions());
        oneTapClient.beginSignIn(signInRequest)
                .addOnSuccessListener(result -> {
                    try {
                        activity.startIntentSenderForResult(
                                result.getPendingIntent().getIntentSender(),
                                R.integer.request_code_one_tap_ui,
                                null, 0, 0, 0
                            );
                    }
                    catch (IntentSender.SendIntentException e) {
                        Log.e(TAG, "Couldn't start one tap UI due to code : " + e.getLocalizedMessage());
                    }
                })
                .addOnFailureListener(exception -> {
                    Log.d(TAG, "Attempt to beginSignIn failed due to code : " + exception.getLocalizedMessage());
                });
    }

    public boolean loginWithEmailAndPassword(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "loginWilEmailAndPassword : Success");
                        user = auth.getCurrentUser();
                    }
                    else Log.w(TAG, "loginWithEmailAndPassword : Fail");
                });
        return user != null;
    }

    private boolean loginWithCredential(String idToken) {
        AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(firebaseCredential)
                .addOnCompleteListener(task -> {
                   if (task.isSuccessful()) {
                       Log.d(TAG, "loginWithCredential : Success");
                       user = auth.getCurrentUser();
                   }
                   else Log.w(TAG, "loginWithCredential : Fail");
                });
        return user != null;
    }

    public boolean loginWithGoogle(Intent intent) {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(intent);

        try {
            GoogleSignInAccount googleAccount = task.getResult(ApiException.class); // Masalahnya di sini
            System.out.println("account : " + googleAccount);
            idToken = googleAccount.getIdToken();
            System.out.println("idToken : " + idToken);
        }
        catch (ApiException e) {
            Log.w(TAG, "loginWithGoogle : Fail");
            activity.recreate();
        }

        return loginWithCredential(idToken);
    }

    public boolean loginWithOneTap(@Nullable Intent data) {
        try {
            SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(data);
            idToken = credential.getGoogleIdToken();
            email = credential.getId();
            password = credential.getPassword();
        }
        catch (ApiException e) {
            switch (e.getStatusCode()) {
                case CommonStatusCodes.CANCELED:
                    Log.d(TAG, "One Tap Dialog Closed");
                    oneTapUI = false;
                    break;
                case CommonStatusCodes.NETWORK_ERROR:
                    Log.d(TAG, "One Tap Encountered an Error");
                    makeToast(false);
                    break;
                default:
                    Log.d(TAG, "Failed Getting Credential");
                    break;
            }
        }

        return loginWithCredential(idToken) || loginWithEmailAndPassword(email, password);
    }

    public void makeToast(boolean loginStatus) {
        if (loginStatus)
            Toast.makeText(
                 activity,
                 "Login Successful",
                 Toast.LENGTH_SHORT
            ).show();
        else
            Toast.makeText(
                    activity,
                    "Login Failed, Please Try Again!",
                    Toast.LENGTH_SHORT
            ).show();
    }
}
