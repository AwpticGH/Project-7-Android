package g10.manga.comicable.crud.auth;

import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;

import g10.manga.comicable.model.AuthModel;

public class ReadData extends BaseData {

    public ReadData() {
        super();
    }

    public ReadData(FirebaseUser user) {
        super(user);
    }

    public void getData(TextView tvEmail, TextView tvPassword, TextView tvName) {
        AuthModel model = super.read(getUser());

        tvEmail.setText(model.getEmail());
        tvPassword.setText(model.getPassword());
        tvName.setText(model.getName());
    }
}
