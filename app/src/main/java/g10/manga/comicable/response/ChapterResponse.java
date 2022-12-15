package g10.manga.comicable.response;

import com.google.gson.annotations.SerializedName;

import g10.manga.comicable.model.manga.ChapterModel;

public class ChapterResponse extends BaseResponse {

    @SerializedName("data")
    private ChapterModel chapter;

    public ChapterModel getChapter() {
        return chapter;
    }
}
