package g10.manga.comicable.crud.auth;

import android.content.Intent;

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

public class BaseData {

    private final String DB_URL = "https://console.firebase.google.com/u/1/project/ccit-project-c68fc/database/ccit-project-c68fc-default-rtdb/data/~2F";
    private final String DB_REFERENCE = "Authentication";
    private AuthModel model;
    private DatabaseReference dbReference;
    private String id;
    private FirebaseUser user;

    protected BaseData() {
        dbReference = FirebaseDatabase.getInstance(DB_URL).getReference(DB_REFERENCE);
        id = dbReference.push().getKey();
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    protected BaseData(FirebaseUser user) {
        dbReference = FirebaseDatabase.getInstance(DB_URL).getReference(DB_REFERENCE);
        id = dbReference.push().getKey();
        this.user = user;
    }

    protected BaseData(FirebaseUser user, AuthModel model) {
        dbReference = FirebaseDatabase.getInstance(DB_URL).getReference(DB_REFERENCE);
        this.model = model;
        id = dbReference.push().getKey();
        this.user = user;
    }

    protected AuthModel getModel() {
        return this.model;
    }

    protected void setModel(AuthModel model) {
        this.model = model;
    }

    protected FirebaseUser getUser() {
        return this.user;
    }

    protected String getId() {
        return id;
    }

    protected DatabaseReference getDbReference() {
        return this.dbReference;
    }

    protected void create(AuthModel model) {
        dbReference.child(user.getUid()).setValue(model);
    }

    protected AuthModel read(FirebaseUser user) {
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

    protected void update(AuthModel model) {
        dbReference.child(user.getUid()).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // Setting new display name
                    UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                            .setDisplayName(model.getName())
                            .build();
                    user.updateEmail(model.getEmail());
                    user.updatePassword(model.getPassword());
                    user.updateProfile(profileUpdate);
                }
            }
        });
    }

}
