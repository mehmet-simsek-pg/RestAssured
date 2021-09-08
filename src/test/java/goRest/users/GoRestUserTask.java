package goRest.users;

import goRest.baseSetup.BaseSetup;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class GoRestUserTask extends BaseSetup {

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

    @Test(dependsOnMethods = "createUser", priority = 1)
    public void getUserById() {
        given()
                .pathParam("userId", userId)

                .when()
                .get("/users/{userId}")

                .then()
                .statusCode(200)
                .log().body()
                .body("data.id", equalTo(userId))
        ;
    }

    @Test(dependsOnMethods = "createUser", priority = 2)
    public void updateUserById() {

        String newName = "necdet";

        given()
                .header("Authorization", "Bearer c2c25a97ad7a65b80c7a7f94a8b485a34b7b94831d6bb37f88b818a2c27a13cd")
                .contentType(ContentType.JSON)
                .body("{\"name\":\"" + newName + "\"}")
                .pathParam("userId", userId)

                .when()
                .put("/users/{userId}")

                .then()
                .statusCode(200)
                .body("data.name", equalTo(newName))
                .log().body()
        ;
    }

    @Test(dependsOnMethods = "createUser", priority = 3)
    public void deleteUserById() {
        given()
                .header("Authorization", "Bearer c2c25a97ad7a65b80c7a7f94a8b485a34b7b94831d6bb37f88b818a2c27a13cd")
                .pathParam("userId", userId)

                .when()
                .delete("/users/{userId}")

                .then()
                .statusCode(204)
                .log().body()

        ;
    }

    @Test(dependsOnMethods = "deleteUserById")
    public void deleteUserByIdNegative() {
        given()
                .header("Authorization", "Bearer c2c25a97ad7a65b80c7a7f94a8b485a34b7b94831d6bb37f88b818a2c27a13cd")
                .pathParam("userId", userId)

                .when()
                .delete("/users/{userId}")

                .then()
                .log().body()
                .statusCode(404)

        ;
    }

    @Test
    public void responseSample() {
        Response response =
                given()

                        .when()
                        .get("/users")

                        .then()
                        .statusCode(200)
                        .contentType(ContentType.JSON)
                        .extract().response();


        List<User> userList = response.jsonPath().getList("data", User.class);
        int total = response.jsonPath().getInt("meta.pagination.total");
        int limit = response.jsonPath().getInt("meta.pagination.limit");
        User user = response.jsonPath().getObject("data[0]", User.class);


        System.out.println("users size=" + userList.size());
        System.out.println("total=" + total);
        System.out.println("limit = " + limit);
        System.out.println("user=" + user);
    }

    @Test
    public void createUserWithMap() {

        Map<String, String> user = new HashMap<>();

        user.put("name", "mehmet");
        user.put("gender", "male");
        user.put("email", getRandomEmail());
        user.put("status", "active");

        userId =
                given()
                        .header("Authorization", "Bearer c2c25a97ad7a65b80c7a7f94a8b485a34b7b94831d6bb37f88b818a2c27a13cd")
                        .contentType(ContentType.JSON)
                        .body(user)

                        .when()
                        .post("/users")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().jsonPath().getInt("data.id")

        ;
        System.out.println("userID = " + userId);
    }

    @Test
    public void createUserWithObject() {

        User user = new User();
        user.setName("mehmet");
        ;
        user.setGender("male");
        ;
        user.setEmail(getRandomEmail());
        user.setStatus("active");

        userId =
                given()
                        .header("Authorization", "Bearer c2c25a97ad7a65b80c7a7f94a8b485a34b7b94831d6bb37f88b818a2c27a13cd")
                        .contentType(ContentType.JSON)
                        .body(user)

                        .when()
                        .post("/users")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().jsonPath().getInt("data.id")

        ;
        System.out.println("userID = " + userId);
    }


}
