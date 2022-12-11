package g10.manga.comicable.api;

import java.util.List;

import g10.manga.comicable.model.manga.InfoModel;
import g10.manga.comicable.model.manga.ListModel;
import g10.manga.comicable.response.ListResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ListApi {

    @GET("list")
    Call<ListResponse> getAllComics();

    @GET("list")
    Call<ListResponse> getAllComicsWithFilter(@Query("filter")String filter);

    @GET("popular/page/{pageNumber}")
    Call<List<InfoModel>> getPopularComics(@Path("pageNumber")int pageNumber);

    @GET("recommended/page/{pageNumber}")
    Call<List<InfoModel>> getRecommendedComics(@Path("pageNumber")int pageNumber);
}
