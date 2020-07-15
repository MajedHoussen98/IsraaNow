package ps.edu.israaNow.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AboutUsData {

    @SerializedName("id")
    @Expose
    Integer id;

    @SerializedName("title")
    @Expose
    String title;

    @SerializedName("description")
    @Expose
    String description;

    @SerializedName("pic")
    @Expose
    String pic;

    public AboutUsData(Integer id, String title, String description, String pic) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.pic = pic;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPic() {
        return pic;
    }
}
