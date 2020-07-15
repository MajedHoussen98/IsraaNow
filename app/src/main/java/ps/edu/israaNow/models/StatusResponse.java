package ps.edu.israaNow.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatusResponse {

    @SerializedName("code")
    @Expose
    private Integer code;

    @SerializedName("success")
    @Expose
    private boolean success;

    @SerializedName("message")
    @Expose
    private String message;

    public StatusResponse(Integer code, boolean success, String message) {
        this.code = code;
        this.success = success;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "StatusResponse{" +
                "code=" + code +
                ", success=" + success +
                ", message='" + message + '\'' +
                '}';
    }
}
