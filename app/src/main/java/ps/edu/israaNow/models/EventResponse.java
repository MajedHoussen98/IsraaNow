package ps.edu.israaNow.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class EventResponse {

    @SerializedName("status")
    @Expose
    private StatusResponse statusResponse;

    @SerializedName("data")
    @Expose
    private ArrayList<Post> posts;


    public StatusResponse getStatusResponse() {
        return statusResponse;
    }


    public ArrayList<Post> getDataEvents() {
        return posts;
    }

    @Override
    public String toString() {
        return "EventResponse{" +
                "statusResponse=" + statusResponse +
                ", posts=" + posts +
                '}';
    }
}
