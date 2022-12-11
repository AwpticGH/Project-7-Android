package g10.manga.comicable.response;

import java.util.List;

public class BaseResponse {

    protected boolean success;
    protected String message;
    protected int code;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
