package g10.manga.comicable.api;

import g10.manga.comicable.response.ChapterResponse;
import g10.manga.comicable.response.InfoResponse;
import g10.manga.comicable.response.ListResponse;
import g10.manga.comicable.response.PopularResponse;
import g10.manga.comicable.response.RecommendedResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MangaApi {

    // Get All Comics
    @GET("list")
    Call<ListResponse> getAllComics();

    @GET("list")
    Call<ListResponse> getAllComicsWithFilter(@Query("filter")String filter);

    // Get Popular Comics
    @GET("popular/page/{pageNumber}")
    Call<PopularResponse> getPopularComics(@Path("pageNumber") int pageNumber);

    // Get Recommended Comics
    @GET("recommended/page/{pageNumber}")
    Call<RecommendedResponse> getRecommendedComics(@Path("pageNumber") int pageNumber);

    // Get Comic Info
    @GET("info{endpoint}")
    Call<InfoResponse> getComicInfo(@Path(value = "endpoint", encoded = true) String infoEndpoint);

    // Get Chapter Detail
    @GET("chapter{endpoint}")
    Call<ChapterResponse> getChapterDetail(@Path(value = "endpoint", encoded = true) String chapterEndpoint);
}
