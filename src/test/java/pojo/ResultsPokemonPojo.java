package pojo;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

public class ResultsPokemonPojo {
    private String name;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static class Football {

        @Before
        public void setup() {
            RestAssured.baseURI = "http://api.football-data.org";
            RestAssured.basePath = "v2/competitions";
        }

        @Test
        public void competitionsTest() {
            Response response = RestAssured.given().accept(ContentType.JSON)
                    .when().get().then()
                    .statusCode(200)
                    .contentType(ContentType.JSON)  //validate response is in json
                    .body("count", Matchers.equalTo(168))
                    .and()
                    .body("competitions[0].name", Matchers.equalTo("AFC Champions League"))
                    .extract().response();
        }


        @Test
        public void competitionSearchTest(){
            Response response = RestAssured.given().accept(ContentType.JSON)
                    .when().get().then()
                    .statusCode(200)
                    .contentType(ContentType.JSON)  //validate response is in json
                    .body("count", Matchers.equalTo(168))
                    .extract().response();


        }
    }
}
