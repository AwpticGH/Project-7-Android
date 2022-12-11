package g10.manga.comicable.call;

import android.util.Log;

import java.util.List;

import g10.manga.comicable.model.manga.RecommendedModel;
import g10.manga.comicable.response.RecommendedResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecommendedCall extends BaseCall {

    private List<RecommendedModel> recommendeds;

    public RecommendedCall(String url) {
        super(url);
    }

    public List<RecommendedModel> getRecommendeds(int page) {
        Call<RecommendedResponse> call = api.getRecommendedComics(page);
        call.enqueue(new Callback<RecommendedResponse>() {
            @Override
            public void onResponse(Call<RecommendedResponse> call, Response<RecommendedResponse> response) {
                assert response.body() != null;
                if (response.body().isSuccess()) {
                    recommendeds = response.body().getRecommendeds();

                    for (RecommendedModel recommended : recommendeds) {
                        Log.d("Call Result(success)", "Title : " + recommended.getTitle());
                        Log.d("Call Result(success)", "Type : " + recommended.getType());
                        Log.d("Call Result(success)", "Description : " + recommended.getDescription());
                    }
                }
            }

            @Override
            public void onFailure(Call<RecommendedResponse> call, Throwable t) {
                Log.e("Call Result(fail)", t.getLocalizedMessage());
            }
        });

        return recommendeds;
    }
}
