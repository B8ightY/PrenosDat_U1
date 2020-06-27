package sk.fri.uniza;

import retrofit2.Call;
import retrofit2.Response;
import sk.fri.uniza.model.HouseHoldData;
import sk.fri.uniza.model.HouseHoldField;
import sk.fri.uniza.model.Token;
import sk.fri.uniza.model.WeatherData;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Hello IoT!
 */
public class App {
    public static void main(String[] args) throws InterruptedException {
        IotNode iotNode = new IotNode();
        HouseHold houseHold = new HouseHold();

        // Ziska token
        Token token = new Token();
        token.setToken(token.createToken(iotNode));

        System.out.println("\nPriemerna teplota: " + iotNode.getAverageTemperature(token.getToken(),"station_1",
                "10/12/2019 20:30", "11/12/2019 20:30") + "°C");

        // Prida field
        HouseHoldField stationNameField = new HouseHoldField("stationName", null, "Názov stanice");
        Call stationNameFieldCall = houseHold.getHouseHoldService().createField(stationNameField);
        try {
            stationNameFieldCall.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Posiela data kazdu minutu
        /*while(true) {
            Call<WeatherData> currentWeatherCall = iotNode.getWeatherStationService().getCurrentWeatherAuth(token.getToken(), "station_1");

            try {
                Response<WeatherData> weatherDataResponse = currentWeatherCall.execute();

                if(weatherDataResponse.isSuccessful()) {
                    WeatherData currentWeather = weatherDataResponse.body();
                    String dateTime = (new SimpleDateFormat("dd/MM/yyyy HH:mm")).format(new Date());

                    HouseHoldData airTemp = new HouseHoldData(dateTime, currentWeather.getAirTemperature().toString(), "double");
                    HouseHoldData windSpeed = new HouseHoldData(dateTime, currentWeather.getWindSpeed().toString(), "double");
                    HouseHoldData stationName = new HouseHoldData(dateTime, currentWeather.getStationName(), "string");

                    Call airTempCall = houseHold.getHouseHoldService().sendHouseHoldData("1", "airTemp", airTemp);
                    Call windSpeedCall = houseHold.getHouseHoldService().sendHouseHoldData("1", "windSpeed", windSpeed);
                    Call stationNameCall = houseHold.getHouseHoldService().sendHouseHoldData("1", "stationName", stationName);

                    Response airTempResponse = airTempCall.execute();
                    Response windSpeedResponse = windSpeedCall.execute();
                    Response stationNameResponse = stationNameCall.execute();

                    if(!airTempResponse.isSuccessful()) System.out.println("An error occurred in attempt to send \"airTemp\"");
                    if(!windSpeedResponse.isSuccessful()) System.out.println("An error occurred in attempt to send \"windSpeed\"");
                    if(!stationNameResponse.isSuccessful()) System.out.println("An error occurred in attempt to send \"stationName\"");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            TimeUnit.MINUTES.sleep(1);
        }*/
    }
}
