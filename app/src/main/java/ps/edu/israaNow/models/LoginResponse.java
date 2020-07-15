package ps.edu.israaNow.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("status")
    @Expose
    private StatusResponse statusUser;

    @SerializedName("data")
    @Expose
    private DataUser dataUser;

    public LoginResponse(StatusResponse statusUser, DataUser dataUser) {
        this.statusUser = statusUser;
        this.dataUser = dataUser;
    }

    public StatusResponse getStatusUser() {
        return statusUser;
    }

    public void setStatusUser(StatusResponse statusUser) {
        this.statusUser = statusUser;
    }

    public DataUser getDataUser() {
        return dataUser;
    }

    public void setDataUser(DataUser dataUser) {
        this.dataUser = dataUser;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "statusUser=" + statusUser +
                ", dataUser=" + dataUser +
                '}';
    }
}
