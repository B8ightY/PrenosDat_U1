package sk.fri.uniza.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import retrofit2.Call;
import retrofit2.Response;
import sk.fri.uniza.HouseHold;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HouseHoldField {
    @JsonProperty("name")
    private String name;
    @JsonProperty("unit")
    private String unit;
    @JsonProperty("description")
    private String description;

    public HouseHoldField() {}

    public HouseHoldField(String name, String unit, String description) {
        this.name = name;
        this.unit = unit;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", unit='" + unit + '\'' +
                ", description='" + description + '\'';
    }

    public static void checkExistingFields(HouseHold houseHold, String[] reqFields, SimpleDateFormat dtFormatLong) {
        Call<List<HouseHoldField>> allFields = houseHold.getHouseHoldService().getAllFields();
        try {
            Response<List<HouseHoldField>> response = allFields.execute();

            if(response.isSuccessful()) {
                List<HouseHoldField> fieldsData = response.body();
                assert fieldsData != null;

                for(String field : reqFields) {
                    boolean found = false;
                    for(HouseHoldField data: fieldsData) {
                        if (data.getName().equals(field)) {
                            found = true;
                            break;
                        }
                    }
                    if(!found) addMissingField(houseHold, field, dtFormatLong);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addMissingField(HouseHold houseHold, String name, SimpleDateFormat dtFormatLong) {
        HouseHoldField stationNameField;

        switch(name) {
            case "airTemp":
                stationNameField = new HouseHoldField("airTemp", "°C", "Teplota vzduchu");
                break;

            case "windSpeed":
                stationNameField = new HouseHoldField("windSpeed", "m/s", "Rýchlosť vetra");
                break;

            case "stationName":
                stationNameField = new HouseHoldField("stationName", null, "Názov stanice");
                break;

            default:
                throw new IllegalStateException("No rule to add field with name \"" + name + "\"");
        }

        System.out.println("INFO  [" + dtFormatLong.format(new Date()) + "] Adding missing field \"" + name + "\"");

        try {
            if(((houseHold.getHouseHoldService().createField(stationNameField)).execute()).isSuccessful())
                System.out.println("INFO  [" + dtFormatLong.format(new Date())
                        + "] Field \"" + name + "\" added successfully");
            else System.out.println("ERROR  [" + dtFormatLong.format(new Date())
                    + "] Failed to add field \"" + name + "\"");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
