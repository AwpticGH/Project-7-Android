package g10.manga.comicable.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import g10.manga.comicable.model.manga.ListModel;

public class ListResponse extends BaseResponse {

    @SerializedName("data")
    private List<ListModel> lists;

    public List<ListModel> getLists() {
        return lists;
    }
}
