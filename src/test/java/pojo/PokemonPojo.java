package pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)

public class PokemonPojo {
    private int count;
    private String next;
    private String previous;
    private List<Map<String,Object>> maps;
    private List<ResultsPokemonPojo> results;


}
