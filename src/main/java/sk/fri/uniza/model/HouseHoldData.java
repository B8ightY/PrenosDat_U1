package sk.fri.uniza.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HouseHoldData {
    @JsonProperty("dateTime")
    private String dateTime;
    @JsonProperty("value")
    private String value;
    @JsonProperty("type")
    private String type;

    public HouseHoldData(String dateTime, String value, String type) {
        this.dateTime = dateTime;
        this.value = value;
        this.type = type;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "dateTime='" + dateTime + '\'' +
                ", value='" + value + '\'' +
                ", type='" + type + '\'';
    }
}
