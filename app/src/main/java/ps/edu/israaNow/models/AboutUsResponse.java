package ps.edu.israaNow.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AboutUsResponse {

    @SerializedName("status")
    @Expose
    private StatusResponse statusAboutUs;

    @SerializedName("data")
    @Expose
    private AboutUsData aboutUsData;


    public AboutUsResponse(StatusResponse statusAboutUs, AboutUsData aboutUsData) {
        this.statusAboutUs = statusAboutUs;
        this.aboutUsData = aboutUsData;
    }


    public StatusResponse getStatusAboutUs() {
        return statusAboutUs;
    }

    public void setStatusAboutUs(StatusResponse statusAboutUs) {
        this.statusAboutUs = statusAboutUs;
    }

    public AboutUsData getAboutUsData() {
        return aboutUsData;
    }

    public void setAboutUsData(AboutUsData aboutUsData) {
        this.aboutUsData = aboutUsData;
    }
}
