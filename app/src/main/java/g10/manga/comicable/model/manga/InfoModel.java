package g10.manga.comicable.model.manga;

import com.google.gson.annotations.SerializedName;

public class InfoModel {

    private String thumbnail;
    private String title;
    private String type;
    private String author;
    private String status;
    private String rating;
    private String genre;
    @SerializedName("chapter_list")
    private ChapterListModel chapterList;

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public ChapterListModel getChapterList() {
        return chapterList;
    }

    public void setChapterList(ChapterListModel chapterList) {
        this.chapterList = chapterList;
    }
}