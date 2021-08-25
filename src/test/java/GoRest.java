import org.testng.annotations.Test;

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
}
