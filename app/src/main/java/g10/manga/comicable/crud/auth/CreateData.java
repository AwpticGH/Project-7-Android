package g10.manga.comicable.crud.auth;

import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;

import java.util.Random;

import g10.manga.comicable.model.AuthModel;

public class CreateData extends BaseData {

    public CreateData() {
        super();
    }

    public CreateData(FirebaseUser user, AuthModel model) {
        super(user, model);
    }

    public void create(TextView tvEmail, TextView tvPassword, TextView tvName) {
        if (getModel() == null) setModel(new AuthModel());

        getModel().setId(super.getId());
        getModel().setEmail(tvEmail.getText().toString().trim());
        getModel().setPassword(tvPassword.getText().toString().trim());
        getModel().setName(tvName.getText().toString().trim());

        super.create(getModel());
    }

    public void create(AuthModel model) {
        model.setId(super.getId());
        super.create(model);
    }

    public void create(String email, String password, String name) {
        if (getModel() == null) setModel(new AuthModel());

        getModel().setId(super.getId());
        getModel().setEmail(email);
        getModel().setPassword(password);
        getModel().setName(name);

        super.create(getModel());
    }

}
