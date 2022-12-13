package g10.manga.comicable.model.manga;

import com.google.gson.annotations.SerializedName;

public class RecommendedModel extends BaseModel {

    private String title;
    private String image;
    @SerializedName("desc") private String description;
    private String type;
    private String endpoint;

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getEndpoint() {
        return endpoint;
    }
}
