package g10.manga.comicable.call;

import android.util.Log;

import java.util.List;

import g10.manga.comicable.api.MangaApi;
import g10.manga.comicable.helper.RetrofitHelper;
import g10.manga.comicable.model.manga.ListModel;
import g10.manga.comicable.response.ListResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListCall extends BaseCall {

    private List<ListModel> lists;

    public ListCall(String url) {
        super(url);
    }

    public List<ListModel> getAllComics() {
        Call<ListResponse> call = api.getAllComics();
        call.enqueue(new Callback<ListResponse>() {
            @Override
            public void onResponse(Call<ListResponse> call, Response<ListResponse> response) {
                assert response.body() != null;
                if (response.body().isSuccess()) {
                    Log.d("Call Result Boolean", "Result : " + response.body().isSuccess());
                    lists = response.body().getLists();
                    for (ListModel list : lists) {
                        Log.d("Call Result(Success)", "Title : " + list.getTitle());
                        Log.d("Call Result(Success)", "Endpoint : " + list.getEndpoint());
                        Log.d("Call Result(Success)", "Image : " + list.getImage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ListResponse> call, Throwable t) {
                Log.e("Call Result(Fail)", t.getLocalizedMessage());
            }
        });

        return lists;
    }

}
