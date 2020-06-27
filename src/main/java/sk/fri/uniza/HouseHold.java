package sk.fri.uniza;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import sk.fri.uniza.api.HouseHoldService;

public class HouseHold {
    private final Retrofit retrofit;
    private final HouseHoldService houseHoldService;

    public HouseHold() {
        retrofit = new Retrofit.Builder()
                // Url adresa kde je umiestnená HouseHold služba
                .baseUrl("http://localhost:8080/")
                // Na konvertovanie JSON objektu na java POJO použijeme
                // Jackson knižnicu
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        // Vytvorenie inštancie komunikačného rozhrania
        houseHoldService = retrofit.create(HouseHoldService.class);
    }

    public HouseHoldService getHouseHoldService() {
        return houseHoldService;
    }
}
