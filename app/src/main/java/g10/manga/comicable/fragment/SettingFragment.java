package g10.manga.comicable.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

import g10.manga.comicable.R;
import g10.manga.comicable.activity.LoginActivity;
import g10.manga.comicable.activity.MainActivity;
import g10.manga.comicable.activity.UserUpdateActivity;
import g10.manga.comicable.controller.AuthController;
import g10.manga.comicable.model.AuthModel;

public class SettingFragment extends Fragment {

    private TextView textResult;
    private Button btnLogout;
    private Button btnUpdate;

    private Intent intentLogout;

    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textResult = view.findViewById(R.id.text_user_name);
        btnLogout = view.findViewById(R.id.button_logout);
        btnUpdate = view.findViewById(R.id.button_update);

        intentLogout = new Intent(view.getContext(), LoginActivity.class);

        textResult.setText((MainActivity.getAuthModel() != null)
                ? MainActivity.getAuthModel().getName()
                : FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

        btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(intentLogout);
            getActivity().finish();
            MainActivity.getAuthController().makeToast(R.integer.LOGOUT_SUCCESSFUL);
        });

        btnUpdate.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), UserUpdateActivity.class));
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }
}