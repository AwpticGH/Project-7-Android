package g10.manga.comicable.call;

import android.util.Log;

import g10.manga.comicable.model.manga.ChapterModel;
import g10.manga.comicable.response.ChapterResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChapterCall extends BaseCall {

    private ChapterModel chapter;

    public ChapterCall(String url) {
        super(url);
    }

    public Call<ChapterResponse> getChapterDetail(String endpoint) {
        return api.getChapterDetail(endpoint);
//        call.enqueue(new Callback<ChapterResponse>() {
//            @Override
//            public void onResponse(Call<ChapterResponse> call, Response<ChapterResponse> response) {
//                assert response.body() != null;
//                if (response.body().isSuccess()) {
//                    chapter = response.body().getChapter();
//
//                    Log.d("Call Result(success)", "Title : " + chapter.getTitle());
//                    Log.d("Call Result(success)", "Images : " + chapter.getImages().toString());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ChapterResponse> call, Throwable t) {
//                Log.e("Call Result(fail)", t.getLocalizedMessage());
//            }
//        });
    }
}
