
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pojo.Location;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ZippoTest {

    @Test
    public void test() {
        given()
                .when()

                .then()

        ;
    }

    @Test
    public void statusCodeTest() {
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .statusCode(200)
        ;
    }

    @Test
    public void contentTypeTest() {
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .contentType(ContentType.JSON)
        ;
    }

    @Test
    public void logTest() {
        given()
                .log().all()
                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .contentType(ContentType.JSON)

        ;
    }

    @Test
    public void checkStateInResponse() {
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .body("country", equalTo("United States"))
                .statusCode(200)
        ;
    }

    @Test
    public void bodyJsonPathTest() {
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .body("places[0].state", equalTo("California"))
                .statusCode(200)

        ;
    }

    @Test
    public void bodyJsonPathTest2() {
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .body("places[0].'place name'", equalTo("Beverly Hills"))
                .statusCode(200)

        ;
    }

    @Test
    public void hasSizeTest() {
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .body("places", hasSize(1))
                .statusCode(200)

        ;
    }

    @Test
    public void combineTest() {
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .body("places", hasSize(1))
                .body("places.state", hasItem("California"))
                .body("places[0].'place name'", equalTo("Beverly Hills"))
                .statusCode(200)

        ;
    }

    private ResponseSpecification responseSpecification;
    private RequestSpecification requestSpecification;

    @BeforeTest
    public void setup() {
        baseURI = "http://api.zippopotam.us";

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
    public void bodyHasSizeTest() {

        given()
                .log().uri()

                .when()
                .get("/us/90210")

                .then()
                .log().body()
                .body("places", hasSize(1))
        ;
    }

    @Test
    public void bodyHasSizeResponseSpecificationTest() {

        given()
                .spec(requestSpecification)

                .when()
                .get("/us/90210")

                .then()
                .body("places", hasSize(1))
                .spec(responseSpecification)
        ;
    }

    @Test
    public void extractingJsonPath() {

        String placeName =
                given()

                        .spec(requestSpecification)
                        .when()
                        .get("/us/90210")

                        .then()
                        .spec(responseSpecification)
                        .extract().path("places[0].'place name'");
        System.out.println("placeName = " + placeName);
    }

    @Test
    public void extractingJsonPojo() {

        Location location =
                given()

                        .when()
                        .get("/us/90210")

                        .then()
                        .extract().as(Location.class);

        System.out.println("location = " + location);
        System.out.println("location.getCountry() = " + location.getCountry());
        System.out.println("location.getPlaces() = " + location.getPlaces());
        System.out.println("location.getPlaces().get(0).getPlaceName() = " + location.getPlaces().get(0).getPlaceName());
    }


}
