package serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Test;
import pojo.PetPojo;

import java.io.File;
import java.io.IOException;

public class Serialization {
    @Test
    public void serialization() throws IOException {
        PetPojo pet = new PetPojo();
        pet.setName("Hutch");
        pet.setStatus("Serving");
        pet.setId(741852);
        File jsonfile = new File("src/test/resources/pet.json");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(jsonfile, pet);
    }

    @Test
    public void serializationTest2() {
        RestAssured.baseURI = "https://petstore.swagger.io";
        RestAssured.basePath = "v2/pet";
        File jasonFile = new File("src/test/resources/pet.json");
        RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(jasonFile)
                .when().post()
                .then().statusCode(200)
                .and()
                .body("name", Matchers.is("Hutch"))
                .log().body();
    }

    @Test
    public void serializationTest3() {
        RestAssured.baseURI = "https://petstore.swagger.io";
        RestAssured.basePath = "v2/pet";
        PetPojo pet = new PetPojo();
        pet.setId(76543456);
        pet.setName("Zeus");
        pet.setStatus("playing");

        RestAssured.given().accept(ContentType.JSON).contentType(ContentType.JSON)
                .body(pet)
                .when().post()
                .then().statusCode(200)
                .body("name",Matchers.equalTo("Zeus"))
                .and()
                .body("status",Matchers.is("playing"))
                .extract().response();


    }
}
