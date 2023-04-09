package g10.manga.comicable.call;

import android.util.Log;

import java.util.jar.JarOutputStream;

import g10.manga.comicable.model.manga.ChapterModel;
import g10.manga.comicable.response.ChapterResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChapterCall extends BaseCall {


    public ChapterCall(String url) {
        super(url);
    }

    public Call<ChapterResponse> getChapterDetail(String endpoint) {
        return api.getChapterDetail(endpoint);
    }
}
