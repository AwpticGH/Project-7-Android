package g10.manga.comicable.model.manga;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InfoModel {

    private String thumbnail;
    private String title;
    private String type;
    private String author;
    private String status;
    private String rating;
    private List<String> genre;
    @SerializedName("chapter_list")
    private List<ChapterListModel> chapterList;

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

    public List<String> getGenre() {
        return genre;
    }

    public List<ChapterListModel> getChapterList() {
        return chapterList;
    }
}