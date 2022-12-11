package g10.manga.comicable.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import g10.manga.comicable.model.manga.RecommendedModel;

public class RecommendedResponse extends BaseResponse {

    @SerializedName("data") private List<RecommendedModel> recommendeds;

    public List<RecommendedModel> getRecommendeds() {
        return recommendeds;
    }
}
