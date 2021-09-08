package goRest.comments;

import goRest.baseSetup.BaseSetup;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;

import org.testng.annotations.Test;

import java.util.List;
import java.util.Locale;

import static io.restassured.RestAssured.given;

public class GoRestComments extends BaseSetup {

    @Test
    public void commonTest() {

        Response response =
                given()


                        .when()
                        .get("/comments")

                        .then()
                        .extract().response();

        List<Comments> dataList = response.jsonPath().getList("data", Comments.class);
        for (Comments data : dataList)
            System.out.println(data);

        List<String> mails = response.jsonPath().getList("data.email");

        Assert.assertTrue(mails.contains("embranthiri_swarnalata@kuhic.info"));


    }

    @Test
    public void commonTest2() {

        CommentsBody comments =
                given()
                        .when()
                        .get("/comments")

                        .then()
                        .extract().as(CommentsBody.class);



        System.out.println("comments = " + comments);

    }

    public String getRandomEmail() {

        String mail = RandomStringUtils.randomAlphabetic(8).toLowerCase(Locale.ROOT);
        return mail + "@gmail.com";
    }

    @Test
    public void createComment() {

        Comments data=new Comments();
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
