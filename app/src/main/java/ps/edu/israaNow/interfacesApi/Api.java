package ps.edu.israaNow.interfacesApi;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import ps.edu.israaNow.models.AboutUsResponse;
import ps.edu.israaNow.models.AddtoFavResponse;
import ps.edu.israaNow.models.CollegeResponse;
import ps.edu.israaNow.models.DefaultResponse;
import ps.edu.israaNow.models.Event;
import ps.edu.israaNow.models.EventResponse;
import ps.edu.israaNow.models.JoinEventResponse;
import ps.edu.israaNow.models.LoginResponse;
import ps.edu.israaNow.models.StatusResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface Api {


    @FormUrlEncoded
    @POST("register")
    Call<ResponseBody> SendDataRegister(
            @Field("type") String type,
            @Field("name") String name,
            @Field("user_no") String user_no,
            @Field("password") String password,
            @Field("password_confirmation") String password_confirmation
    );

    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> userLogin(
            @Field("user_no") String user_no,
            @Field("password") String password
    );

    @GET("profile")
    Call<LoginResponse> getUser(@Header("Authorization") String authorization);

    @Multipart
    @POST("update")
    Call<LoginResponse> updateProfile(@Header("Authorization") String authorization,
                                      @QueryMap Map<String, String> params,
                                      @Part MultipartBody.Part avatar);

    @FormUrlEncoded
    @POST("password")
    Call<DefaultResponse> updatePassword(
            @Header("Authorization") String authorization,
            @Field("password") String password,
            @Field("new_password") String new_password
    );


    @GET("colleges")
    Call<CollegeResponse> getCollege(
            @Header("Authorization") String authorization
    );

    @GET("aboutus")
    Call<AboutUsResponse> getDescription();


    @GET("events")
    Call<EventResponse> getEvent(
            @Header("Authorization") String authorization
    );

    @GET("commingevents")
    Call<EventResponse> getCommingEvent(
            @Header("Authorization") String authorization
    );

    @GET("nowevents")
    Call<EventResponse> getRunningEvent(
            @Header("Authorization") String authorization
    );

    @GET("finishedevents")
    Call<EventResponse> getFinishedEvent(
            @Header("Authorization") String authorization
    );

    @FormUrlEncoded
    @POST("addfavourite")
    Call<AddtoFavResponse> addFav(
            @Header("Authorization") String authorization,
            @Field("event_id") String event_id
    );

    @FormUrlEncoded
    @POST("search")
    Call<EventResponse> search(
            @Header("Authorization") String authorization,
            @Field("name") String name
    );

    @FormUrlEncoded
    @POST("joinevent")
    Call<JoinEventResponse> joinEvent(
            @Header("Authorization") String authorization,
            @Field("event_id") String event_id
    );

    @FormUrlEncoded
    @POST("disjoinevent")
    Call<JoinEventResponse> disjoinevent(
            @Header("Authorization") String authorization,
            @Field("event_id") String event_id
    );

    @GET("favourites")
    Call<EventResponse> getEventFav(
            @Header("Authorization") String authorization

    );

    @GET("viewevent/{id}")
    Call<Event> getEventDetails(
            @Header("Authorization") String authorization,
            @Path("id") String id
    );

    @POST("fcm_token")
    Call<StatusResponse> changeFCM(@Header("Authorization") String authorization,
                                   @QueryMap Map<String, String> map);


}
