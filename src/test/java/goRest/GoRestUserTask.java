package goRest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import tasks.User;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

    @Test
    public void commonTest() {

        Response response =
                given()


                        .when()
                        .get("/comments")

                        .then()
                        .extract().response();

        List<Data> dataList = response.jsonPath().getList("data", Data.class);
        for (Data data : dataList)
            System.out.println(data);

        List<String> mails = response.jsonPath().getList("data.email");

        Assert.assertTrue(mails.contains("embranthiri_swarnalata@kuhic.info"));


    }

    @Test
    public void commonTest2() {

        Comment comments =
                given()
                        .when()
                        .get("/comments")

                        .then()
                        .extract().as(Comment.class);



        System.out.println("comments = " + comments);

    }

    @Test
    public void createComment() {

        Data data=new Data();
        data.setName("Mehmet");
        data.setEmail(getRandomEmail());
        data.setBody("Hello World");


                given()
                        .header("Authorization", "Bearer c2c25a97ad7a65b80c7a7f94a8b485a34b7b94831d6bb37f88b818a2c27a13cd")
                        .contentType(ContentType.JSON)
                        .body(data)

                        .when()
                        .post("/posts/123/comments")

                        .then()
                        .log().body()
        ;
    }



}