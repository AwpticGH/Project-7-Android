package g10.manga.comicable.response;

import java.util.ArrayList;
import java.util.List;

import g10.manga.comicable.model.manga.InfoModel;

public class InfoResponse {

    private List<InfoModel> infos;
    private InfoModel info;

    public InfoResponse() {
        infos = new ArrayList<InfoModel>();
    }

    public InfoModel getInfo(String name) {


        return info;
    }
}
