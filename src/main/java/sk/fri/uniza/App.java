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
        HouseHoldField.checkExistingFields(houseHold, reqFields);

        // Posiela na HouseHold vybrane udaje o pocasi kazdu minutu
        while(true) {
            Call<WeatherData> currentWeatherCall = iotNode.getWeatherStationService().getCurrentWeatherAuth(token.getToken(), "station_1");

            try {
                Response<WeatherData> weatherDataResponse = currentWeatherCall.execute();

                if(weatherDataResponse.isSuccessful()) {
                    WeatherData currentWeather = weatherDataResponse.body();
                    String dateTime = (new SimpleDateFormat("dd/MM/yyyy HH:mm")).format(new Date());

                    assert currentWeather != null;
                    HouseHoldData airTemp = new HouseHoldData(dateTime, currentWeather.getAirTemperature().toString(), "double");
                    HouseHoldData windSpeed = new HouseHoldData(dateTime, currentWeather.getWindSpeed().toString(), "double");
                    HouseHoldData stationName = new HouseHoldData(dateTime, currentWeather.getStationName(), "string");

                    checkSendSuccess((houseHold.getHouseHoldService().sendHouseHoldData("1", reqFields[0], airTemp)).execute(), reqFields[0]);
                    checkSendSuccess((houseHold.getHouseHoldService().sendHouseHoldData("1", reqFields[1], windSpeed)).execute(), reqFields[1]);
                    checkSendSuccess((houseHold.getHouseHoldService().sendHouseHoldData("1", reqFields[2], stationName)).execute(), reqFields[2]);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            TimeUnit.MINUTES.sleep(1);
        }
    }

    private static void checkSendSuccess(Response response, String valueName) {
        if(response.isSuccessful())
            System.out.println("INFO  [" + (new SimpleDateFormat("dd/MM/yyyy HH:mm:ss,SSS"))
                    .format(new Date()) + "] Value \"" + valueName + "\" sent successfully");
        else System.out.println("ERROR  [" + (new SimpleDateFormat("dd/MM/yyyy HH:mm:ss,SSS"))
                .format(new Date()) + "] Failed to send value: \"" + valueName + "\"");
    }
}
