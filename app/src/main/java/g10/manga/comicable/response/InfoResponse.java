package g10.manga.comicable.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import g10.manga.comicable.model.manga.InfoModel;

public class InfoResponse extends BaseResponse {

    @SerializedName("data")
    private InfoModel info;

    public InfoModel getInfo() {
        return info;
    }
}
