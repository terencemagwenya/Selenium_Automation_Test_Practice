package mfstestcases;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.File;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.*;
public class Q2ApiTesting {
    static Response response;
    static final String contentType = "application/json; charset=UTF-8";
    static final String projectFilePath = System.getProperty("user.dir");
    static final String jsonPayloadFilePath = projectFilePath + "\\jsonfiles\\";
    @Test(priority = 1)
    public void CreateResource() {
        baseURI = "https://jsonplaceholder.typicode.com";
        String title = "API_Automation";
        String body = "Terence";
        String userId = "103";
        CreateResourceData jsonPayload = new CreateResourceData(title,body,userId);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = null;
        try {
            jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonPayload);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        response = given().
                header("Content-Type", contentType).
                body(jsonString).log().all().
                when().
                post("/posts/");
        System.out.println("The response code is : " + response.getStatusCode());
        System.out.println("The response body is as below : ");
        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertEquals(response.jsonPath().get("title"), title);
        Assert.assertEquals(response.jsonPath().get("body"), body);
        Assert.assertEquals(response.jsonPath().get("userId"), userId);
    }
}