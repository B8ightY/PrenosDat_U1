package sk.fri.uniza.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;
import sk.fri.uniza.model.HouseHoldData;
import sk.fri.uniza.model.HouseHoldField;

import java.util.List;

public interface HouseHoldService {

    @GET("/field")
    Call<List<HouseHoldField>> getAllFields();

    @POST("/field")
    Call<ResponseBody> createField(@Body HouseHoldField field);

    @POST("/household/{householdID}/{fieldID}")
    Call<ResponseBody> sendHouseHoldData(@Path("householdID") String householdID,
                                         @Path("fieldID") String fieldID,
                                         @Body HouseHoldData data);
}
