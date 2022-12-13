package com.azhar.komik.networking;

/**
 * Created by Azhar Rivaldi on 22-12-2019.
 */

public class ApiEndpoint {

    public static String BASEURL = "https://komiku-api.fly.dev/api/comic/newest/page/1";
    public static String ALSOURL = "https://komiku-api.fly.dev/api/comic/recommended/page/1";
    public static String GENREURL = "https://mangamint.glitch.me/api/genres";
    public static String DETAILMANGAURL = "https://komiku-api.fly.dev/api/comic/info/{endpoint}";
    public static String CHAPTERURL = "https://komiku-api.fly.dev/api/comic/chapter{endpoint chapter}";
    public static String GENREDETAIL = "https://mangamint.glitch.me/api/genres/{endpoint}";

}
