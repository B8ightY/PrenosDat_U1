package sk.fri.uniza;

/**
 * Hello IoT!
 */
public class App {
    public static void main(String[] args) {
        IotNode iotNode = new IotNode();

        // Ziska token
        String token = iotNode.getToken();

        System.out.println("\nPriemerna teplota: " + iotNode.getAverageTemperature(token,"station_1",
                "10/12/2019 20:30", "11/12/2019 20:30") + "°C");
    }
}
