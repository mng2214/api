package get;

import io.restassured.RestAssured;
import org.junit.Test;

public class coyote {

    @Test
    public void getSWChars() {

        RestAssured.given()
                .when()
                .get("https://api.coyote.com/docs/index.html#tag/Carrier-Available-Load-APIs/paths/~1api~1v1~1AvailableLoads~1{loadId}/get")
                .then().statusCode(200);
    }
}
