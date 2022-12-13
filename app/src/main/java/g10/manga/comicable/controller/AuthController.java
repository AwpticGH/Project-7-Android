package g10.manga.comicable.controller;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import g10.manga.comicable.model.AuthModel;

public class AuthController {

    private final String DB_URL = "https://console.firebase.google.com/u/1/project/ccit-project-c68fc/database/ccit-project-c68fc-default-rtdb/data/~2F";
    private final String DB_REFERENCE = "Authentication";
    private AuthModel model;
    private DatabaseReference dbReference;

    public AuthController() {
        dbReference = FirebaseDatabase.getInstance(DB_URL).getReference(DB_REFERENCE);
    }

    public AuthController(AuthModel model) {
        dbReference = FirebaseDatabase.getInstance(DB_URL).getReference(DB_REFERENCE);
        this.model = model;
    }

    public AuthController(FirebaseUser user) {
        dbReference = FirebaseDatabase.getInstance(DB_URL).getReference(DB_REFERENCE);
        this.model = this.read(user);
    }

    public void create() {
        String pushId = dbReference.push().getKey();
        dbReference.child(pushId).setValue(model);
    }

    public void create(AuthModel model) {
        String pushId = dbReference.push().getKey();
        dbReference.child(pushId).setValue(model);
    }

    public AuthModel read(FirebaseUser user) {
        dbReference.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                model = snapshot.getValue(AuthModel.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return model;
    }

    public void update(AuthModel model) {
        dbReference.child(model.getId()).setValue(model);
    }

}
