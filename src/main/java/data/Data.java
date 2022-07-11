package data;

import java.time.ZoneId;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

@lombok.Data
public class Data {
    @JsonProperty("id") private int code;
    private String name;
    private int numberOfMoves;


}
