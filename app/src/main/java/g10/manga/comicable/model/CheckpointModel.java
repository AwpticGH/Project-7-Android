package g10.manga.comicable.model;

import java.io.Serializable;

import g10.manga.comicable.model.manga.ChapterListModel;
import g10.manga.comicable.model.manga.InfoModel;

public class CheckpointModel implements Serializable {

    private String id;
    private AuthModel user;
    private InfoModel manga;
    private ChapterListModel chapter;

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

    public InfoModel getManga() {
        return manga;
    }

    public void setManga(InfoModel manga) {
        this.manga = manga;
    }

    public ChapterListModel getChapter() {
        return chapter;
    }

    public void setChapter(ChapterListModel chapter) {
        this.chapter = chapter;
    }
}
