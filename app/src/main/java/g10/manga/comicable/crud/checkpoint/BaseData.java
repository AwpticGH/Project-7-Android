package g10.manga.comicable.crud.checkpoint;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import g10.manga.comicable.model.AuthModel;
import g10.manga.comicable.model.CheckpointModel;

public class BaseData {

    private final String DB_URL = "https://console.firebase.google.com/u/1/project/ccit-project-c68fc/database/ccit-project-c68fc-default-rtdb/data/~2F";
    private final String DB_REFERENCE = "Checkpoint";
    private List<CheckpointModel> checkpoints;
    private CheckpointModel checkpoint;
    private DatabaseReference dbReference;

    protected BaseData() {
        dbReference = FirebaseDatabase.getInstance(DB_URL).getReference(DB_REFERENCE);
    }

    protected CheckpointModel read(AuthModel user) {
        dbReference.child(user.getId()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    checkpoint = task.getResult().getValue(CheckpointModel.class);
                }
            }
        });

        return checkpoint;
    }

    protected void create(AuthModel user, CheckpointModel checkpoint) {
        String pushId = dbReference.push().getKey();
        checkpoint.setId(pushId);

        dbReference.child(user.getId()).child(checkpoint.getId()).setValue(checkpoint);
    }

    protected void update(AuthModel user, CheckpointModel checkpoint) {
        dbReference.child(user.getId()).child(checkpoint.getId()).setValue(checkpoint);
    }

    protected void delete(AuthModel user, CheckpointModel checkpoint) {
        dbReference.child(user.getId()).child(checkpoint.getId()).removeValue();
    }
}
