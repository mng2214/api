package post;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import pojo.PetPojo;
import utils.PayloadUtils;

public class PetStore {
    @Test
    public void createPetTest() {
        RestAssured.baseURI = "https://petstore.swagger.io";
        RestAssured.basePath = "v2/pet";

        int pedID = 789123;
        String petName = "Sobaka", status = "waiting";

        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(PayloadUtils.getPetPayload(pedID, petName, status))
                .when().post()
                .then().statusCode(200)
                .extract().response();

        PetPojo parsedResponse = response.as(PetPojo.class);

        Assert.assertEquals(petName, parsedResponse.getName());
        Assert.assertEquals(pedID, parsedResponse.getId());
        Assert.assertEquals(status, parsedResponse.getStatus());

// Validation with a get

       RestAssured.basePath = "v2/pet/" + pedID;
       Response response1 = RestAssured.given()
                .accept(ContentType.JSON)
                .when().get()
                .then().statusCode(200)
                .extract().response();

        PetPojo petPojo = response1.as(PetPojo.class);
        Assert.assertEquals(petName, petPojo.getName());
    }
}
