import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import goRest.users.User;

import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class GoRest {


    @Test
    public void pathParamTest2() {
        String country = "us";

        for (int zipCode = 90210; zipCode < 90214; zipCode++) {

            given()
                    .pathParam("country", country)
                    .pathParam("zipKod", zipCode)
                    .log().uri()
                    .when()
                    .get("http://api.zippopotam.us/{country}/{zipKod}")
                    .then()
                    .log().body()
                    .body("places", hasSize(1));
        }
    }

    @Test
    public void pathParamTest() {
        String country = "us";
        String zipKod = "90210";
        given().pathParam("country", country)
                .pathParam("zipKod", zipKod)
                .log().uri()
                .when()
                .get("http://api.zippopotam.us/{country}/{zipKod}")
                .then()
                .log().body()
                .body("places", hasSize(1))
        ;
    }

    @Test
    public void queryParamTest() {

        given()
                .param("page", 1)

                .log().uri()

                .when()
                .get("https://gorest.co.in/public/v1/users")

                .then()
                .log().body()
                .body("meta.pagination.page", equalTo(1))
        ;
    }

    @Test
    public void queryParamTest2() {

        for (int page = 1; page <= 10; page++) {
            given()
                    .param("page", page)

                    .log().uri()

                    .when()
                    .get("https://gorest.co.in/public/v1/users")

                    .then()
                    .log().body()
                    .body("meta.pagination.page", equalTo(page))
            ;
        }
    }

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

    @Test
    public void extractingJsonPathInt() {

        int limit =
                given()
                        .param("page", 1)

                        .when()
                        .get("/users")

                        .then()
                        .spec(responseSpecification)
                        .extract().path("meta.pagination.limit");
        System.out.println("limit = " + limit);
    }

    @Test
    public void extractingJsonPathIntList() {

        List<Integer> id =
                given()
                        .param("page", 1)

                        .when()
                        .get("/users")

                        .then()
                        .extract().path("data.id");
        System.out.println("id = " + id);
    }

    @Test
    public void extractingJsonPathStringList() {

        List<String> name =
                given()
                        .param("page", 1)

                        .when()
                        .get("/users")

                        .then()
                        .extract().path("data.name");
        System.out.println("name = " + name);
        Assert.assertTrue(name.contains("Helene"));
    }

    @Test
    public void goRestUsers() {

        List<User> users =
                given()


                        .when()
                        .get("/users")


                        .then()
                        .statusCode(200)
                        .contentType(ContentType.JSON)
                        .log().body()
                        .extract().jsonPath().getList("data", User.class);


        for (User user: users){
            System.out.println("user = " + user);
        }
    }



}
