package g10.manga.comicable.api;

import g10.manga.comicable.response.InfoResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface InfoApi {

    @GET("info/{endpoint}")
    Call<InfoResponse> getComicInfo(@Path("endpoint")String endpoint);
}
