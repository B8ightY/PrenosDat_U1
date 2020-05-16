package sk.fri.uniza;

import sk.fri.uniza.model.Token;

/**
 * Hello IoT!
 */
public class App {
    public static void main(String[] args) {
        IotNode iotNode = new IotNode();

        // Ziska token
        Token token = new Token();
        token.setToken(token.createToken(iotNode));

        System.out.println("\nPriemerna teplota: " + iotNode.getAverageTemperature(token.getToken(),"station_1",
                "10/12/2019 20:30", "11/12/2019 20:30") + "°C");
    }
}
