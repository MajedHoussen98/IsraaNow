package ps.edu.israaNow.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DefaultResponse {

    @SerializedName("success")
    @Expose
    boolean success;

    @SerializedName("message")
    @Expose
    String message;

    public DefaultResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
