package ps.edu.israaNow.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataUser {
    @Override
    public String toString() {
        return "DataUser{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", user_no=" + user_no +
                ", name='" + name + '\'' +
                ", pic='" + pic + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    public String getToken() {
        return "Bearer " + token;
    }

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("user_no")
    @Expose
    private int user_no;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("pic")
    @Expose
    private String pic;
    private String password;

    public DataUser(int id, int user_no, String name, String pic, String token, String password) {
        this.id = id;
        this.token = token;
        this.type = type;
        this.user_no = user_no;
        this.name = name;
        this.pic = pic;
        this.password = password;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getUser_no() {
        return user_no;
    }

    public void setUser_no(int user_no) {
        this.user_no = user_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPassword() {
        return password;
    }
}
