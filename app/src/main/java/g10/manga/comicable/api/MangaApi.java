package g10.manga.comicable.api;

import java.util.List;

import g10.manga.comicable.model.manga.InfoModel;
import g10.manga.comicable.model.manga.ListModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MangaApi {

    @GET("/list")
    Call<List<ListModel>> getAll();

    @GET("/list")
    Call<List<ListModel>> getAllWithFilter(@Query("filter")String filter);

    @GET("/popular/page/{pageNumber}")
    Call<List<InfoModel>> getPopularComics(@Path("pageNumber")int pageNumber);

    @GET("/recommended/page/{pageNumber}")
    Call<List<InfoModel>> getRecommendedComics(@Path("pageNumber")int pageNumber);

    @GET("/info/manga{endpoint}")
    Call<List<InfoModel>> getComicInfo(@Path("endpoint")String endpoint, String manga);
}
