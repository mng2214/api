package pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.cucumber.java.eo.Se;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FootBallPojo {
    private List<CompetitionsPojo> competitions;
}
