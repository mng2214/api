package put;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.Test;
import pojo.PetPojo;
import utils.PayloadUtils;

public class PetStore {
    /*
    1 post call to create a pet
    2 deserialize and validate post response
    3 put call to update existing pet
    4 deserialize and validate put response
    5 get call to search for a pet
    6 deserialize and validate get response
     */

    @Test
    public void updatePetTest() {
        RestAssured.baseURI = "https://petstore.swagger.io";
        RestAssured.basePath = "v2/pet";
        int petID = 789444;
        String petName = "Sobaka";
        String petStatus = "Playing";

        // 1 post call to create a pet

        RequestSpecification reqSpec = RestAssured.given().accept(ContentType.JSON).contentType(ContentType.JSON);
        Response response = reqSpec
                .body(PayloadUtils.getPetPayload(petID, petName, petStatus))
                .when().post()
                .then().statusCode(200)
                .extract().response();

        //    2 deserialize and validate post response

        PetPojo parsedResponse = response.as(PetPojo.class);
        Assert.assertEquals(petID, parsedResponse.getId());
        Assert.assertEquals(petName, parsedResponse.getName());
        Assert.assertEquals(petStatus, parsedResponse.getStatus());

        //3 put call to update existing pet

        String newStatus = "sleeping";

        response = reqSpec
                .body(PayloadUtils.getPetPayload(petID, petName, newStatus))
                .put().then()
                .statusCode(200).extract().response();

        parsedResponse = response.as(PetPojo.class);

        // 4 deserialize and validate put response

        Assert.assertEquals(petID, parsedResponse.getId());
        Assert.assertEquals(petName, parsedResponse.getName());
        Assert.assertEquals(newStatus, parsedResponse.getStatus());

        //  5 get call to search for a pet

        response = RestAssured.given().accept(ContentType.JSON)
                .get(String.valueOf(petID)).then().statusCode(200)
                .extract().response();

        //6 deserialize and validate get response

        parsedResponse = response.as(PetPojo.class);
        Assert.assertEquals(petID, parsedResponse.getId());
        Assert.assertEquals(petName, parsedResponse.getName());
        Assert.assertEquals(newStatus, parsedResponse.getStatus());

    }

}
