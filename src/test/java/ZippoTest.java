
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

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
}
