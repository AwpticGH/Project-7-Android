package g10.manga.comicable.call;

import g10.manga.comicable.api.MangaApi;
import g10.manga.comicable.helper.RetrofitHelper;

public class BaseCall {

    protected MangaApi api;

    protected BaseCall(String url) {
        api = RetrofitHelper.getInstance(url).create(MangaApi.class);
    }
}
