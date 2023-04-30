package pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
public class PokemonPage1Pojo {
    private List<AbilitiesPokemonPojo> abilities;
    private int base_experience;
    //private List<Map<String,String>> forms;
    private List<Map<String,Object>> game_indices;
    private int height;
    private List held_items;
    private int id;
    private boolean is_default;
    private String location_area_encounters;
    private String name;
    private int order;
    private List past_types;
    private Map<String,String> species;
    private Map<String, Object> sprites;
    private List<Map<String,Object>> stats;
    private List<Map<String,Object>> types;
    private int weight;
    private List<Map<String,Object>> moves;


}
