package ps.edu.israaNow.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class CollegeResponse {

    @SerializedName("status")
    @Expose
    private StatusResponse statusCollege;

    @SerializedName("data")
    @Expose
    private ArrayList<DataCollege> dataCollege;


    public CollegeResponse(StatusResponse statusCollege, ArrayList<DataCollege> dataCollege) {
        this.statusCollege = statusCollege;
        this.dataCollege = dataCollege;
    }

    public StatusResponse getStatusCollege() {
        return statusCollege;
    }

    public void setStatusCollege(StatusResponse statusCollege) {
        this.statusCollege = statusCollege;
    }

    public ArrayList<DataCollege> getDataCollege() {
        return dataCollege;
    }

    public void setDataCollege(ArrayList<DataCollege> dataCollege) {
        this.dataCollege = dataCollege;
    }
}
