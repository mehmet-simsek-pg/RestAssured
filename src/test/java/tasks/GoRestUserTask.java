package tasks;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Locale;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class GoRestUserTask {

    private ResponseSpecification responseSpecification;
    private RequestSpecification requestSpecification;

    @BeforeTest
    public void setup() {
        baseURI = "https://gorest.co.in/public/v1";

        requestSpecification = new RequestSpecBuilder()
                .log(LogDetail.URI)
                .setAccept(ContentType.JSON)
                .build();

        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .log(LogDetail.BODY)
                .build();
    }

    int userId;

    @Test
    public void createUser() {

        userId =
                given()
                        .header("Authorization", "Bearer c2c25a97ad7a65b80c7a7f94a8b485a34b7b94831d6bb37f88b818a2c27a13cd")
                        .contentType(ContentType.JSON)
                        .body("{\"name\":\"mehmet\", \"gender\":\"male\", \"email\":\"+" + getRandomEmail() + "\", \"status\":\"active\"}")

                        .when()
                        .post("/users")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().jsonPath().getInt("data.id")

        ;
        System.out.println("userID = " + userId);
    }

    public String getRandomEmail() {

        String mail = RandomStringUtils.randomAlphabetic(8).toLowerCase(Locale.ROOT);
        return mail + "@gmail.com";
    }

    @Test(dependsOnMethods = "createUser")
    public void getUserById() {
        given()
                .pathParam("userId", userId)

                .when()
                .get("https://gorest.co.in/public-api/users/{userId}")

                .then()
                .statusCode(200)
                .log().body()
                .body("data.id", equalTo(userId))
        ;
    }

    @Test(dependsOnMethods = "createUser")
    public void updateUserById() {

        String newName="necdet";

        given()
                .header("Authorization","Bearer c2c25a97ad7a65b80c7a7f94a8b485a34b7b94831d6bb37f88b818a2c27a13cd")
                .contentType(ContentType.JSON)
                .body("{\"name\":\""+newName+"\"}")
                .pathParam("userId",userId)

                .when()
                .put("https://gorest.co.in/public-api/users/{userId}")

                .then()
                .statusCode(200)
                .body("data.name", equalTo(newName))
                .log().body()
        ;
    }


}
