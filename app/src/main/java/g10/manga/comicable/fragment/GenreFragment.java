package g10.manga.comicable.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.islamkhsh.CardSliderViewPager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import g10.manga.comicable.R;
import g10.manga.comicable.activity.InfoActivity;
import g10.manga.comicable.adapter.ListAdapter;
import g10.manga.comicable.call.ListCall;
import g10.manga.comicable.model.manga.ListModel;
import g10.manga.comicable.response.ListResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenreFragment extends Fragment implements ListAdapter.OnObjectSelected {

    private RecyclerView recyclerView;
    private ListAdapter adapter;
    private ProgressDialog progressDialog;
    private CardSliderViewPager cardSliderViewPager;

    private List<ListModel> models;
    private ListCall call;

    public GenreFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_genre, container, false);
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

        recyclerView = rootView.findViewById(R.id.rvGenre);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        call = new ListCall(getString(R.string.MANGA_API_BASE_URL));
        progressDialog.show();
        call.getAllComics().enqueue(new Callback<ListResponse>() {
            @Override
            public void onResponse(Call<ListResponse> call, Response<ListResponse> response) {
                assert response.body() != null;
                if (response.body().isSuccess()) {
                    models = response.body().getLists();
                    setAdapter(models);
                    progressDialog.dismiss();
                }
                else {
                    progressDialog.dismiss();
                    Toast.makeText(
                            getActivity(),
                            "Failed Getting Resources",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }

            @Override
            public void onFailure(Call<ListResponse> call, Throwable t) {
                Log.e("PopularCall", "Fail : [" + t.getLocalizedMessage() + "]");
            }
        });
    }

    private void setAdapter(List<ListModel> models) {
        adapter = new ListAdapter(models, getActivity().getApplicationContext(), this,
                R.id.cvList, R.id.tvTitle, R.id.ivThumbnail);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onObjectSelected(ListModel model) {
        Intent intent = new Intent(getActivity(), InfoActivity.class);
        intent.putExtra("endpoint", model.getEndpoint());
        startActivity(intent);
    }
}
