package sk.fri.uniza;

import retrofit2.Call;
import retrofit2.Response;
import sk.fri.uniza.model.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Hello IoT!
 */
public class App {

    static SimpleDateFormat dtFormatShort = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    static SimpleDateFormat dtFormatLong = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss,SSS");

    public static void main(String[] args) throws InterruptedException {
        IotNode iotNode = new IotNode();
        HouseHold houseHold = new HouseHold();

        // Ziska token
        Token token = new Token();
        token.setToken(token.createToken(iotNode));

        // Ziska a zobrazi historicke data
        System.out.println("\nAverage history temp from 10/12/2019 20:30 to 11/12/2019 20:30 is: "
                + iotNode.getAverageTemperature(token.getToken(),"station_1",
                "10/12/2019 20:30", "11/12/2019 20:30") + "°C\n");

        // Skontroluje existujuce fieldy v tabulke a prida pozadovane chybajuce
        String[] reqFields = {"airTemp", "windSpeed", "stationName"};
        HouseHoldField.checkExistingFields(houseHold, reqFields, dtFormatLong);

        // Posiela na HouseHold vybrane udaje o pocasi kazdu minutu
        while(true) {
            Call<WeatherData> currentWeatherCall = iotNode.getWeatherStationService()
                    .getCurrentWeatherAuth(token.getToken(), "station_1");

            try {
                Response<WeatherData> weatherDataResponse = currentWeatherCall.execute();

                if(weatherDataResponse.isSuccessful()) {
                    WeatherData currentWeather = weatherDataResponse.body();
                    String currDateTime = dtFormatShort.format(new Date());

                    assert currentWeather != null;
                    HouseHoldData airTemp = new HouseHoldData(currDateTime,
                            currentWeather.getAirTemperature().toString(), "double");
                    HouseHoldData windSpeed = new HouseHoldData(currDateTime,
                            currentWeather.getWindSpeed().toString(), "double");
                    HouseHoldData stationName = new HouseHoldData(currDateTime,
                            currentWeather.getStationName(), "string");

                    checkSendSuccess((houseHold.getHouseHoldService().sendHouseHoldData("1", reqFields[0],
                            airTemp)).execute(), reqFields[0]);
                    checkSendSuccess((houseHold.getHouseHoldService().sendHouseHoldData("1", reqFields[1],
                            windSpeed)).execute(), reqFields[1]);
                    checkSendSuccess((houseHold.getHouseHoldService().sendHouseHoldData("1", reqFields[2],
                            stationName)).execute(), reqFields[2]);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            TimeUnit.MINUTES.sleep(1);
        }
    }

    private static void checkSendSuccess(Response response, String valueName) {
        if(response.isSuccessful())
            System.out.println("INFO  [" + dtFormatLong.format(new Date())
                    + "] Value \"" + valueName + "\" sent successfully");
        else System.out.println("ERROR  [" + dtFormatLong.format(new Date())
                + "] Failed to send value \"" + valueName + "\"");
    }
}
