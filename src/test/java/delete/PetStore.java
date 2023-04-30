package delete;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.Test;
import pojo.PetPojo;
import utils.PayloadUtils;

public class PetStore {

    @Test
    public void deletePetTest() {

        put.PetStore petStore = new put.PetStore();
        petStore.updatePetTest();


        RestAssured.baseURI = "https://petstore.swagger.io";
        RestAssured.basePath = "v2/pet/789444";
        Response response = RestAssured.given().accept(ContentType.JSON)
                .when().delete()
                .then().statusCode(200)
                .extract().response();

        JsonPath jsonPath = response.jsonPath();
        String responseMessage = jsonPath.getString("message");
        Assert.assertEquals("789444", responseMessage);
    }
}
