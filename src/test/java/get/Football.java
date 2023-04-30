package get;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pojo.CompetitionsPojo;
import pojo.FootBallPojo;

import java.util.List;
import java.util.Map;

public class Football {
    @Before
    public void setup() {
        RestAssured.baseURI = "http://api.football-data.org/";
        RestAssured.basePath = "v2/competitions";
    }

    @Test
    public void football() {

        Response response = RestAssured.given().accept(ContentType.JSON)
                .header("X-Auth-Token", "c55b7a64e8424d46a52051bce36d1c0a")
                .when().get()
                .then().statusCode(200)
                .extract().response();

        JsonPath jsonPath = response.jsonPath();
        List<Map<String, Object>> responseMessage = jsonPath.get("competitions");
        for (Map<String, Object> name : responseMessage) {
            if (name.get("name").equals("MLS")) {
                Assert.assertEquals(2145, name.get("id"));
                System.out.println(name.get("id"));
            }
        }

//        FootBallPojo parsedResponse = response.as(FootBallPojo.class);
//        List<CompetitionsPojo> competitions = parsedResponse.getCompetitions();
//        for (CompetitionsPojo competition : competitions) {
//            if (competition.getName().equals("MLS")) {
//                Assert.assertEquals(2145, competition.getId());
//            }
//        }

        ///////////////// Advanced Rest Assured way

    }

    @Test
    public void advancedRestAssuredTest() {

        Response response2 = RestAssured.given().accept(ContentType.JSON)
                .header("X-Auth-Token", "c55b7a64e8424d46a52051bce36d1c0a")
                .when().get()
                .then().statusCode(200)
                .body("competitions.find { it.name == 'MLS' }.id", Matchers.equalTo(2145))
                .extract().response();
    }


    @Test
    public void advancedRestAssuredTest2() {
        Response response = RestAssured.given().accept(ContentType.JSON).header("X-Auth-Token", "c55b7a64e8424d46a52051bce36d1c0a")
                .when().get()
                .then()
                .statusCode(200)
                .body("competitions.collect {it.name}", Matchers.containsInRelativeOrder("A League"))
                .extract().response();
        List<String> result = response.path("competitions.collect {it.name}");
        Assert.assertEquals(168, result.size());

        //get all country names where competition id is greater than 2006
        List<String> countryList = response.path("competitions.findAll {it.id > 2006}.area.name");
//        System.out.println(countryList.size());
//        System.out.println(countryList);

        //sum all values from all competitions
        int sum = response.path("competitions.collect {it.id}.sum()");
        System.out.println(sum);
    }


}
