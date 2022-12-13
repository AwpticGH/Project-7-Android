package g10.manga.comicable.crud.auth;

import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;

import g10.manga.comicable.model.AuthModel;

public class UpdateData extends BaseData {

    public UpdateData() {
    }

    public UpdateData(FirebaseUser user) {
        super(user);
    }

    public UpdateData(FirebaseUser user, AuthModel model) {
        super(user, model);
    }

    public void update(TextView tvEmail, TextView tvPassword, TextView tvName) {
        if (getModel() == null) setModel(super.read(getUser()));

        getModel().setEmail(tvEmail.getText().toString().trim());
        getModel().setPassword(tvPassword.getText().toString().trim());
        getModel().setName(tvName.getText().toString().trim());

        super.update(getModel());
    }

    public void update(String email, String password, String name) {
        if (getModel() == null) setModel(super.read(getUser()));

        getModel().setEmail(email);
        getModel().setPassword(password);
        getModel().setName(name);

        super.update(getModel());
    }
}
