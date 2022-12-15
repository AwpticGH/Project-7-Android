package g10.manga.comicable.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.islamkhsh.CardSliderViewPager;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import g10.manga.comicable.R;
import g10.manga.comicable.activity.InfoActivity;
import g10.manga.comicable.activity.MainActivity;
import g10.manga.comicable.adapter.PopularAdapter;
import g10.manga.comicable.adapter.SliderAdapter;
import g10.manga.comicable.call.PopularCall;
import g10.manga.comicable.model.SliderModel;
import g10.manga.comicable.model.manga.PopularModel;
import g10.manga.comicable.response.PopularResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements PopularAdapter.OnObjectSelected {

    private RecyclerView recyclerView;
    private SliderAdapter sliderAdapter;
    private PopularAdapter popularAdapter;
//    private AdapterKomik adapterKomik;
    private ProgressDialog progressDialog;
    private CardSliderViewPager cardSliderViewPager;
    private List<PopularModel> popularModels = new ArrayList<>();
    private List<SliderModel> sliderModels = new ArrayList<>();
    private PopularCall popularCall;
//    private List<ModelKomik> modelKomik = new ArrayList<>();
//    private List<ModelSlider> modelSlider = new ArrayList<>();
    private TextView greetText;
    private Spinner spPage;
    private String[] numberPage = {"1", "2", "3", "4", "5"
            , "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"
            , "16", "17", "18", "19", "110"};
    private String page;

    public HomeFragment() {

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

        greetText = rootView.findViewById(R.id.tvGreeting);
        cardSliderViewPager = rootView.findViewById(R.id.viewPager);
        spPage = rootView.findViewById(R.id.spPage);

        ArrayAdapter<String> adpWisata = new ArrayAdapter<>((getActivity()), android.R.layout.simple_spinner_item, numberPage);
        adpWisata.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPage.setAdapter(adpWisata);

        spPage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object itemDB = parent.getItemAtPosition(pos);
                page = itemDB.toString();
            }

            public void onNothingSelected(AdapterView<?> parent) { }
        });

        recyclerView = rootView.findViewById(R.id.rvTerbaru);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        popularCall = new PopularCall(getString(R.string.MANGA_API_BASE_URL));

        getGreeting();
        getCall();
    }

    @SuppressLint("SetTextI18n")
    private void getGreeting() {
        String[] name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName().split(" ");
        String firstName = name[0];
        Calendar calendar = Calendar.getInstance();
        int timeOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 0 && timeOfDay < 12) {
            greetText.setText("Selamat Pagi " + firstName);
        }
        else if (timeOfDay >= 12 && timeOfDay < 15) {
            greetText.setText("Selamat Siang " + firstName);
        }
        else if (timeOfDay >= 15 && timeOfDay < 18) {
            greetText.setText("Selamat Sore " + firstName);
        }
        else if (timeOfDay >= 18 && timeOfDay < 24) {
            greetText.setText("Selamat Malam " + firstName);
        }
    }

    private void getCall() {
        progressDialog.show();

        popularCall.getCall(1).enqueue(new Callback<PopularResponse>() {
            @Override
            public void onResponse(Call<PopularResponse> call, Response<PopularResponse> response) {
                popularModels = response.body().getPopulars();
                if (popularModels != null) {
                    // Image Slider
                    for (PopularModel model : popularModels) {
                        SliderModel slider = new SliderModel();
                        slider.setThumb(model.getImage());
                        sliderModels.add(slider);
                    }
                    sliderAdapter = new SliderAdapter(sliderModels);
                    cardSliderViewPager.setAdapter(sliderAdapter);

                    // Popular Comics
                    getComics();

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
            public void onFailure(Call<PopularResponse> call, Throwable t) {
                Log.e("PopularCall", "Fail : [" + t.getLocalizedMessage() + "]");
            }
        });
    }

    private void getComics() {
        popularAdapter = new PopularAdapter(popularModels, getActivity(), this,
                R.id.cvTerbaru, R.id.tvTitle, R.id.imgPhoto, R.id.tvDate, R.id.tvType);
        recyclerView.setAdapter(popularAdapter);
        popularAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSelected(PopularModel model) {
        Intent intent = new Intent(getActivity(), InfoActivity.class);
        intent.putExtra("endpoint", model.getEndpoint());
        startActivity(intent);
    }
}
