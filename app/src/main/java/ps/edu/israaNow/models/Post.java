package ps.edu.israaNow.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post {

    @SerializedName("id")
    @Expose
    Integer id;

    @SerializedName("college_id")
    @Expose
    Integer college_id;

    @SerializedName("title")
    @Expose
    String title;

    @SerializedName("description")
    @Expose
    String description;

    @SerializedName("location")
    @Expose
    String location;

    @SerializedName("hall")
    @Expose
    String hall;

    @SerializedName("pic")
    @Expose
    String pic;

    @SerializedName("start_date")
    @Expose
    String start_date;

    @SerializedName("end_date")
    @Expose
    String end_date;

    @SerializedName("start_time")
    @Expose
    String start_time;

    @SerializedName("end_time")
    @Expose
    String end_time;

    @SerializedName("is_joined")
    @Expose
    boolean is_joined;

    @SerializedName("is_fav")
    @Expose
    boolean is_fav;

    @SerializedName("start_date1")
    @Expose
    String start_date1;

    @SerializedName("created_at")
    @Expose
    String created_at;

    @SerializedName("no_join")
    @Expose
    Integer no_join;

    @SerializedName("no_favourite")
    @Expose
    Integer no_favourite;

    @SerializedName("college")
    @Expose
    DataCollege college;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCollege_id() {
        return college_id;
    }

    public void setCollege_id(Integer college_id) {
        this.college_id = college_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getHall() {
        return hall;
    }

    public void setHall(String hall) {
        this.hall = hall;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public boolean isIs_joined() {
        return is_joined;
    }

    public void setIs_joined(boolean is_joined) {
        this.is_joined = is_joined;
    }

    public boolean isIs_fav() {
        return is_fav;
    }

    public void setIs_fav(boolean is_fav) {
        this.is_fav = is_fav;
    }

    public String getStart_date1() {
        return start_date1;
    }

    public void setStart_date1(String start_date1) {
        this.start_date1 = start_date1;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Integer getNo_join() {
        return no_join;
    }

    public void setNo_join(Integer no_join) {
        this.no_join = no_join;
    }

    public Integer getNo_favourite() {
        return no_favourite;
    }

    public void setNo_favourite(Integer no_favourite) {
        this.no_favourite = no_favourite;
    }

    public DataCollege getCollege() {
        return college;
    }

    public void setCollege(DataCollege college) {
        this.college = college;
    }

}
