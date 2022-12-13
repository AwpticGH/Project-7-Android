package g10.manga.comicable.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import g10.manga.comicable.model.manga.PopularModel;

public class PopularResponse extends BaseResponse {

    @SerializedName("data") private List<PopularModel> populars;

    public List<PopularModel> getPopulars() {
        return populars;
    }
}
