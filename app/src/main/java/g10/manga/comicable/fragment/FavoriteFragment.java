package g10.manga.comicable.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.github.islamkhsh.CardSliderViewPager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

import g10.manga.comicable.R;
import g10.manga.comicable.activity.InfoActivity;
import g10.manga.comicable.activity.MainActivity;
import g10.manga.comicable.adapter.PopularAdapter;
import g10.manga.comicable.adapter.SliderAdapter;
import g10.manga.comicable.call.PopularCall;
import g10.manga.comicable.controller.CheckpointController;
import g10.manga.comicable.model.CheckpointModel;
import g10.manga.comicable.model.manga.PopularModel;

public class FavoriteFragment extends Fragment implements PopularAdapter.OnObjectSelected {

    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private CardSliderViewPager cardSliderViewPager;

    private PopularAdapter popularAdapter;
    private List<PopularModel> models;

    private CheckpointController checkpointController;
    private List<CheckpointModel> checkpoints;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View rootView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Mohon Tunggu");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Sedang menampilkan data");

        cardSliderViewPager = rootView.findViewById(R.id.viewPager);

        recyclerView = rootView.findViewById(R.id.rvTerbaru);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        checkpointController = new CheckpointController();
        checkpointController.readAll(MainActivity.getAuthModel())
                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            checkpoints = new ArrayList<>();
                            for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                                checkpoints.add(dataSnapshot.getValue(CheckpointModel.class));
                            }

                            models = new ArrayList<>();
                            for (CheckpointModel checkpoint : checkpoints) {
                                models.add(checkpoint.getManga());
                            }

                            setAdapter(models);
                        }
                    }
                });

    }

    private void setAdapter(List<PopularModel> models) {
        popularAdapter = new PopularAdapter(models, getActivity(), this,
                R.id.cvTerbaru, R.id.tvTitle, R.id.imgPhoto, R.id.tvDate, R.id.tvType);
        recyclerView.setAdapter(popularAdapter);
        popularAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSelected(PopularModel model) {
        Intent intent = new Intent(getActivity(), InfoActivity.class);
        intent.putExtra("endpoint", model.getEndpoint());
        intent.putExtra("comic", model);
        startActivity(intent);
    }
}