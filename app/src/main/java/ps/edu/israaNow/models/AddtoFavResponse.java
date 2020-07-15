package ps.edu.israaNow.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddtoFavResponse {

    @SerializedName("status")
    @Expose
    private StatusResponse statusUser;

    public AddtoFavResponse(StatusResponse statusUser) {
        this.statusUser = statusUser;
    }

    public StatusResponse getStatusUser() {
        return statusUser;
    }

    public void setStatusUser(StatusResponse statusUser) {
        this.statusUser = statusUser;
    }
}
