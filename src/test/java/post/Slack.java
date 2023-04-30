package post;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.Test;
import pojo.SlackPojo;
import utils.PayloadUtils;

public class Slack {
    @Test
    public void sendSlackMessage() {
        //https://slack.com/api/chat.postMessage
        RestAssured.baseURI = "https://slack.com";
        RestAssured.basePath = "api/chat.postMessage";

        String slackMsg = "Artur: msg - from IntelliJ";
        RequestSpecification requestSpecification = RestAssured.given().accept(ContentType.JSON).contentType(ContentType.JSON);

        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer xoxb-4349924244708-5140059154064-Gmd3isPGRJhfpYdpOnwL7MZm")
                .body(PayloadUtils.getSlackMessagePayload(slackMsg))
                .when().post()
                .then().statusCode(200)
                .extract().response();

        SlackPojo parsedResponse = response.as(SlackPojo.class);
        Assert.assertTrue("Failed to send Slack Msg", parsedResponse.isOk());
        Assert.assertEquals("Failed to validate Slack Msg", slackMsg, parsedResponse.getMessage().getText());

        JsonPath jsonPath = response.jsonPath();
        boolean msg = jsonPath.getBoolean("ok");
        Assert.assertTrue(msg);

        String msg1 = jsonPath.getString("message.text");
        Assert.assertEquals(slackMsg,msg1);

        String msg2 = jsonPath.getString("message.blocks[0].type");
        Assert.assertEquals("rich_text",msg2);
        System.out.println("git");
    }
}
