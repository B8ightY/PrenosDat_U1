package sk.fri.uniza.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import retrofit2.Call;
import retrofit2.Response;
import sk.fri.uniza.IotNode;

import java.io.IOException;
import java.util.List;

public class Token {
    @JsonProperty("token")
    private String token;

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String createToken(IotNode iotNode) {
        Call<Token> tokenCall = iotNode.getWeatherStationService().getToken("Basic YWRtaW46aGVzbG8=", List.of("all"));
        String token = null;

        try {
            Response<Token> response = tokenCall.execute();

            if(response.isSuccessful()) {
                Token body = response.body();

                assert body != null;
                token = body.getToken();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return token;
    }
}
