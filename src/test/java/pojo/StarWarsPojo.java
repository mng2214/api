package pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;


import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true) // if not all key defines no exception thrown
@Getter
@Setter
public class StarWarsPojo {
    private int count;
    private String next;
    private String previous;
    private List<Map<String, Object>> maps;
    private List<ResultsPojo> results;

}
