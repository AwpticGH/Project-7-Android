package g10.manga.comicable.call;

import android.util.Log;

import g10.manga.comicable.model.manga.ChapterListModel;
import g10.manga.comicable.model.manga.InfoModel;
import g10.manga.comicable.response.InfoResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoCall extends BaseCall{

    private InfoModel info;

    public InfoCall(String url) {
        super(url);
    }

    public InfoModel getComicInfo(String endpoint) {
        Call<InfoResponse> call = api.getComicInfo(endpoint);
        call.enqueue(new Callback<InfoResponse>() {
            @Override
            public void onResponse(Call<InfoResponse> call, Response<InfoResponse> response) {
                assert response.body() != null;
                if (response.body().isSuccess()) {
                    info = response.body().getInfo();

                    Log.d("Call Result(success)", "Author : " + info.getAuthor());
                    Log.d("Call Result(success)", "Title : " + info.getTitle());
                    Log.d("Call Result(success)", "Genre : " + info.getGenres().toString());
                    for (ChapterListModel chapter : info.getChapterList()) {
                        Log.d("Call Result(success)", "Chapter : " + chapter.getName());
                        Log.d("Call Result(success)", "Chapter Endpoint : " + chapter.getEndpoint());
                    }
                }
            }

            @Override
            public void onFailure(Call<InfoResponse> call, Throwable t) {
                Log.e("Call Result(fail)", t.getLocalizedMessage());
            }
        });

        return info;
    }
}
