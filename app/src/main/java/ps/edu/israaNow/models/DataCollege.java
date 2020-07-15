package ps.edu.israaNow.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataCollege {

    @SerializedName("id")
    @Expose
    Integer id;

    @SerializedName("image")
    @Expose
    String pic;

    @SerializedName("name")
    @Expose
    String name;

    public DataCollege(Integer id, String image, String name) {
        this.id = id;
        this.pic = image;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DataCollege{" +
                "id=" + id +
                ", pic='" + pic + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
