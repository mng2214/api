package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Assert;
import pojo.PetPojo;

import java.util.ArrayList;
import java.util.List;

public class PetstoreStepDefs {
    Response response;

    @Given("User has petstore endpoint")
    public void user_has_petstore_endpoint() {
        RestAssured.baseURI = "https://petstore.swagger.io";
        RestAssured.basePath = "v2/pet";
    }

    @When("User sends get request to list pets")
    public void user_sends_get_request_to_list_pets() {
        response = RestAssured.given()
                .queryParam("status", "sold")
                .accept(ContentType.JSON)
                .when().get("findByStatus"); //invalidId
    }

    @Then("Status code {int}")
    public void status_code(int expectedStatusCode) {
        response.prettyPrint();
        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals(expectedStatusCode, actualStatusCode);
    }

    @Then("Response contains list of pets")
    public void response_contains_list_of_pets() {
        List<PetPojo> petPojoList = new ArrayList<>();
        petPojoList = response.as(petPojoList.getClass());
        Assert.assertTrue(petPojoList.size() > 0);
    }








    @When("^User sends GET request to list (non-existing|invalidId) pet by ID$")
    public void user_sends_get_request_to_list_non_existing_pet_by_id(String petIdType) {
        String pedID;

        if (petIdType.equalsIgnoreCase("non-existing")) {
            pedID = "0987654321234567890";
        } else {
            pedID = "0987654321234567890ABC";
        }
        response = RestAssured.given().accept(ContentType.JSON)
                .when().get(pedID);
    }

    @Then("Error message is {string}")
    public void error_message_is(String errorMsg) {
        response.then().body("message", Matchers.is(errorMsg));
    }


}
