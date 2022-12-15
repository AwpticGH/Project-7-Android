package g10.manga.comicable.model;

import java.io.Serializable;

import g10.manga.comicable.model.manga.ChapterModel;
import g10.manga.comicable.model.manga.InfoModel;
import g10.manga.comicable.model.manga.PopularModel;

public class CheckpointModel implements Serializable {

    private String id;
    private AuthModel user;
    private PopularModel manga;
    private ChapterModel chapter;

    public AuthModel getUser() {
        return user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUser(AuthModel user) {
        this.user = user;
    }

    public PopularModel getManga() {
        return manga;
    }

    public void setManga(PopularModel manga) {
        this.manga = manga;
    }

    public ChapterModel getChapter() {
        return chapter;
    }

    public void setChapter(ChapterModel chapter) {
        this.chapter = chapter;
    }
}
