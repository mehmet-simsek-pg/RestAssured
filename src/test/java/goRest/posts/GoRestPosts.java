package goRest.posts;

import goRest.baseSetup.BaseSetup;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.util.List;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GoRestPosts extends BaseSetup {

    @Test(enabled = false)
    public void getListAllPosts() {
        // Task 1 : https://gorest.co.in/public/v1/posts  API sinden dönen data bilgisini bir class yardımıyla
        // List ini alınız.

        List<Posts> posts =
                given()

                        .when()
                        .get("/posts")

                        .then()
                        .extract().jsonPath().getList("data", Posts.class);

        for (Posts p:posts
        ) {
            System.out.println("p = " + p);

        }

    }
    @Test(enabled = false)
    public void getListPosts() {
        // Task 2 : https://gorest.co.in/public/v1/posts  API sinden sadece 1 kişiye ait postları listeletiniz.
        //  https://gorest.co.in/public/v1/users/87/posts

        List<Posts> posts =
                given()

                        .when()
                        .get("/users/87/posts")

                        .then()
                        .extract().jsonPath().getList("data", Posts.class);

        for (Posts p:posts
        ) {
            System.out.println("p = " + p);

        }

    }

    @Test(enabled = false)
    public void getAllPosts() {
        // Task 3 : https://gorest.co.in/public/v1/posts  API sinden dönen bütün bilgileri tek bir nesneye atınız

        PostsBody posts =
                given()

                        .when()
                        .get("/posts")

                        .then()
                        .extract().as(PostsBody.class);


        System.out.println("posts.getPosts().get(1).getBody() = " + posts.getData().get(1).getBody());
        System.out.println("posts.getMeta().getPagination().getLimit() = " + posts.getMeta().getPagination().getLimit());
        System.out.println("posts.getPosts().get(2).getTitle() = " + posts.getData().get(2).getTitle());
        System.out.println("posts = " + posts);

    }

    int postID;
    @Test
    public void createPost() {

        Posts posts=new Posts();

        posts.setTitle("Rest Assured");
        posts.setBody("Hello World");
        posts.setUser_id(87);

        postID=
                given()
                        .header("Authorization",
                                "Bearer c2c25a97ad7a65b80c7a7f94a8b485a34b7b94831d6bb37f88b818a2c27a13cd")
                        .contentType(ContentType.JSON)
                        .body(posts)

                        .when()
                        .post("/posts")

                        .then()
                        .log().body()
                        .extract().jsonPath().getInt("data.id")
        ;

        System.out.println("postID = " + postID);
    }

    @Test(dependsOnMethods = "createPost", priority = 1)
    public void getPostById() {
        given()
                .pathParam("postID", postID)

                .when()
                .get("/posts/{postID}")

                .then()
                .log().body()
                .body("data.id", equalTo(postID))
        ;
    }

    @Test(dependsOnMethods = "createPost", priority = 2)
    public void updatePostById() {

        String body = "Welcome Rest Assured";

        given()
                .header("Authorization", "Bearer c2c25a97ad7a65b80c7a7f94a8b485a34b7b94831d6bb37f88b818a2c27a13cd")
                .contentType(ContentType.JSON)
                .body("{\"body\":\"" + body + "\"}")
                .pathParam("postID", postID)

                .when()
                .put("/posts/{postID}")

                .then()
                .statusCode(200)
                .body("data.body", equalTo(body))
                .log().body()
        ;
    }

    @Test(dependsOnMethods = "createPost", priority = 3)
    public void deletePostById() {
        given()
                .header("Authorization", "Bearer c2c25a97ad7a65b80c7a7f94a8b485a34b7b94831d6bb37f88b818a2c27a13cd")
                .pathParam("postID", postID)

                .when()
                .delete("/posts/{postID}")

                .then()
                .statusCode(204)
                .log().body()

        ;
    }

    @Test(dependsOnMethods = "deletePostById")
    public void deletePostByIdNegative() {
        given()
                .header("Authorization", "Bearer c2c25a97ad7a65b80c7a7f94a8b485a34b7b94831d6bb37f88b818a2c27a13cd")
                .pathParam("postID", postID)

                .when()
                .delete("/posts/{postID}")

                .then()
                .log().body()
                .statusCode(404)

        ;
    }
}
