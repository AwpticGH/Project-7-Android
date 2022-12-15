package g10.manga.comicable.call;

import android.util.Log;

import java.util.List;

import g10.manga.comicable.model.manga.PopularModel;
import g10.manga.comicable.response.PopularResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularCall extends BaseCall {

    private List<PopularModel> populars;

    public PopularCall(String url) {
        super(url);
    }

    public Call<PopularResponse> getCall(int page) {
        return api.getPopularComics(page);
//        call.enqueue(new Callback<PopularResponse>() {
//            @Override
//            public void onResponse(Call<PopularResponse> call, Response<PopularResponse> response) {
//                assert response.body() != null;
//                if (response.body().isSuccess()) {
//                    populars = response.body().getPopulars();
//
//                    for (PopularModel popular : populars) {
//                        Log.d("Call Result(success)", "Title : " + popular.getTitle());
//                        Log.d("Call Result(success)", "Type : " + popular.getType());
//                        Log.d("Call Result(success)", "Description : " + popular.getDescription());
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<PopularResponse> call, Throwable t) {
//                Log.e("Call Result(fail)", t.getLocalizedMessage());
//            }
//        });
    }
}
