package g10.manga.comicable.model.manga;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InfoModel extends BaseModel {

    private String thumbnail;
    private String title;
    private String type;
    private String author;
    private String status;
    private String rating;
    @SerializedName("genre") private List<String> genres;
    @SerializedName("chapter_list") private List<ChapterListModel> chapters;

    public String getThumbnail() {
        return thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getAuthor() {
        return author;
    }

    public String getStatus() {
        return status;
    }

    public String getRating() {
        return rating;
    }

    public List<String> getGenres() {
        return genres;
    }

    public List<ChapterListModel> getChapterList() {
        return chapters;
    }
}