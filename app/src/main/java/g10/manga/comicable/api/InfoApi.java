package g10.manga.comicable.api;

import g10.manga.comicable.model.manga.InfoModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface InfoApi {

    @GET("/info/manga{endpoint}")
    Call<InfoModel> getComicInfo(@Path("endpoint")String endpoint, String manga);
}
