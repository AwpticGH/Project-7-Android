package g10.manga.comicable.controller;

import android.os.Build;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import g10.manga.comicable.model.AuthModel;
import g10.manga.comicable.model.CheckpointModel;

public class CheckpointController {

    private final String DB_URL = "https://console.firebase.google.com/u/1/project/ccit-project-c68fc/database/ccit-project-c68fc-default-rtdb/data/~2F";
    private final String DB_REFERENCE = "Checkpoint";
    private List<CheckpointModel> checkpoints;
    private CheckpointModel checkpoint;
    private DatabaseReference dbReference;

    public CheckpointController() {
        dbReference = FirebaseDatabase.getInstance(DB_URL).getReference(DB_REFERENCE);
    }

    public void create(AuthModel user, CheckpointModel checkpoint) {
        String pushId = dbReference.push().getKey();
        checkpoint.setId(pushId);

        dbReference.child(user.getId()).child(checkpoint.getId()).setValue(checkpoint);
    }

    public List<CheckpointModel> readAll(AuthModel user) {
        dbReference.child(user.getId()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        task.getResult().getChildren().forEach(new Consumer<DataSnapshot>() {
                            @Override
                            public void accept(DataSnapshot dataSnapshot) {
                                checkpoints = new ArrayList<>();
                                checkpoints.add(dataSnapshot.getValue(CheckpointModel.class));
                            }
                        });
                    }
                }
            }
        });

        return checkpoints;
    }

    public CheckpointModel read(AuthModel user, String id) {
        dbReference.child(user.getId()).child(id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful())
                    checkpoint = task.getResult().getValue(CheckpointModel.class);
            }
        });

        return checkpoint;
    }

    public void update(AuthModel user, CheckpointModel checkpoint) {
        dbReference.child(user.getId()).child(checkpoint.getId()).setValue(checkpoint);
    }

    public void delete(AuthModel user, CheckpointModel checkpoint) {
        dbReference.child(user.getId()).child(checkpoint.getId()).removeValue();
    }
}
