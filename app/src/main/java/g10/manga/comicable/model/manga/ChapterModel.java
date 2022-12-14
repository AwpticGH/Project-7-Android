package g10.manga.comicable.model.manga;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ChapterModel extends BaseModel {

    private String title;
    @SerializedName("image")
    private ArrayList<String> images;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }
}
