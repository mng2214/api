package get;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import pojo.ResultsPojo;
import pojo.StarWarsPojo;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StarWars {
    int characterCounter = 0;

    /*
    1. Defined/determined the end point
    2. Added query string params as needed
    3. Defined HTTP Method
    4. Send
    5. Validate status code
     */
    @Test
    public void getStarWarsCharacters() {
        //https://swapi.dev/api/people
        RestAssured.given()
                .when()
                .get("https://swapi.dev/api/people")
                .then()
                .statusCode(200).log().body();
    }

    @Test
    public void getSWCharsDeserialized() {
        Response response = RestAssured.given()
                .header("Accept", "application/json")
                .when()
                .get("https://swapi.dev/api/people")
                .then()
                .statusCode(200).extract().response();

        Map<String, Object> deserializedResponse = response.as(new TypeRef<Map<String, Object>>() {
        });

        Object count = deserializedResponse.get("count");
        Assert.assertEquals(82, count);
        //Array of Json Object [{},{},{}]
        List<Map<String, Object>> results = (List<Map<String, Object>>) deserializedResponse.get("results");
        /*
        HW:
        -validate that SW API Count value is correct,
        we have total of 82 characters.
        -get list of all SW characters name
        -LVL100: Find only characters gender is female: Map<String, List<String>> -> female
         */

        for (Map<String, Object> name : results) {
            System.out.println(name.get("name"));
        }
    }

    @Test
    public void homework() {
        int count = 0;
        List<Object> allCharacters = new ArrayList<>();
        List<String> femaleCharacter = new ArrayList<>();
        for (int i = 1; i > 0; i++) {
            Response response = RestAssured.given()
                    .header("Accept", "application/json")
                    .when()
                    .get("https://swapi.dev/api/people/?page=" + i)
                    .then().statusCode(200).extract().response();
            Map<String, Object> deserializedResponse = response.as(new TypeRef<Map<String, Object>>() {
            });
            count = (int) deserializedResponse.get("count");
            List<Map<String, Object>> results = (List<Map<String, Object>>) deserializedResponse.get("results");
            for (Map<String, Object> result : results) {
                allCharacters.add(result.get("name"));
                if (result.get("gender").toString().equals("female")) {
                    femaleCharacter.add(result.get("name").toString());
                }
            }
            if (deserializedResponse.get("next") == null) {
                break;
            }
        }
        Assert.assertEquals(count, allCharacters.size());
        System.out.println("all names: " + allCharacters);
        System.out.println(allCharacters.size());
        System.out.println("all female names: " + femaleCharacter);
        System.out.println(femaleCharacter.size());
    }

    @Test
    public void swapiGetWithPojo() {
        Response response = RestAssured.given().header("Accept", "application/json")
                .when().get("https://swapi.dev/api/people").then()
                .statusCode(200).extract().response();
        StarWarsPojo deserializedResp = response.as(StarWarsPojo.class);
        int actualCount = deserializedResp.getCount();
        int expectedCount = 82;
        Assert.assertEquals(expectedCount, actualCount);
        List<ResultsPojo> results = deserializedResp.getResults();
        for (ResultsPojo result : results) {
            System.out.println(result.getName());
        }
    }

    @Test
    public void homeworkSW() {
        RestAssured.baseURI = "https://swapi.dev";
        RestAssured.basePath = "/api/people/";

        Response response = RestAssured.given().accept(ContentType.JSON) //prints only request
                .when().get()
                .then().statusCode(200)
                .extract().response();  //prints only resp

        StarWarsPojo parsedResponse = response.as(StarWarsPojo.class);
        int actualTotalCharactersCount = parsedResponse.getResults().size(); // counting chars on page
        String nextUrl = parsedResponse.getNext(); //gets value of next field from Json response
        while (nextUrl != null) {
            // make a get call to next url
            // counts chars from response
            // get next page url
            response = RestAssured.given().accept(ContentType.JSON)
                    .when().get(nextUrl)
                    .then().statusCode(200)
                    .contentType(ContentType.JSON) // validation if response in json format  OR .contentType("application/json")
                    .extract().response();

            parsedResponse = response.as(StarWarsPojo.class);
            actualTotalCharactersCount += parsedResponse.getResults().size();
            nextUrl = parsedResponse.getNext();

        }
        Assert.assertEquals(parsedResponse.getCount(), actualTotalCharactersCount); //validating count


    }


}
