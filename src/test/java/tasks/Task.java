package tasks;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Task {

    @Test
    public void test1() {
        /** Task 1
         * create a request to https://httpstat.us/203
         * expect status 203
         * expect content type TEXT
         */

        given().

                when().
                get("https://httpstat.us/203")

                .then().contentType(ContentType.TEXT).statusCode(203)
                .log().body();
    }

    @Test
    public void test2() {
        /** Task 2
         * create a request to https://httpstat.us/203
         * expect status 203
         * expect content type TEXT
         * expect BODY to be equal to "203 Non-Authoritative Information"
         */

        given().

                when().
                get("https://httpstat.us/203")

                .then()
                .contentType(ContentType.TEXT)
                .statusCode(203)
                .log().body()
                .body(equalTo("203 Non-Authoritative Information"))
        ;
    }

    @Test
    public void test3() {
/** Task 3
 *  create a request to https://jsonplaceholder.typicode.com/todos/2
 *  expect status 200
 *  expect content type JSON
 *  expect title in response body to be "quis ut nam facilis et officia qui"
 */

        given().

                when().
                get("https://jsonplaceholder.typicode.com/todos/2")

                .then()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .log().body()
                .body("title", equalTo("quis ut nam facilis et officia qui"))
        ;
    }

    @Test
    public void test4() {
        /** Task 4
         * create a request to https://jsonplaceholder.typicode.com/todos/2
         *  expect status 200
         *  expect content type JSON
         *  expect response completed status to be false
         */

        given().

                when().
                get("https://jsonplaceholder.typicode.com/todos/2")

                .then()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .log().body()
                .body("completed", equalTo(false))

        ;
    }

    @Test
    public void test5() {
        /** Task 5
         * create a request to https://jsonplaceholder.typicode.com/todos
         * expect status 200
         * expect content type JSON
         * expect third item have:
         *      title = "fugiat veniam minus"
         *      userId = 1
         */

        given().

                when().
                get("https://jsonplaceholder.typicode.com/todos")

                .then()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .log().body()
                .body("title[2]", equalTo("fugiat veniam minus"))
                .body("userId[2]", equalTo(1))

        ;
    }

    @Test
    public void test6() {
        /** Task 6
         * create a request to https://jsonplaceholder.typicode.com/todos/2
         * expect status 200
         * Converting Into POJO
         */
    PojoTask pojoTask=
        given()

                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")

                .then()
                .statusCode(200)
                .log().body()
                .extract().as(PojoTask.class)
        ;
        System.out.println("pojoTask.getTitle() = " + pojoTask.getTitle());
        System.out.println("pojoTask.getUserId() = " + pojoTask.getUserId());
        System.out.println("pojoTask.getId() = " + pojoTask.getId());
        System.out.println("pojoTask.getComplete() = " + pojoTask.getCompleted());
    
    }

    @Test
    public void test7() {
        /** Task 7
         * create a request to https://jsonplaceholder.typicode.com/todos
         * expect status 200
         * Converting Array Into Array of POJOs
         */

       PojoTask[] pojoTasks=
        given().

                when().
                get("https://jsonplaceholder.typicode.com/todos")

                .then()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .extract().as(PojoTask[].class)
        ;

        System.out.println("Arrays.toString(pojoTasks) = " + Arrays.toString(pojoTasks));
    }

    @Test
    public void test8() {
        /** Task 8
         * create a request to https://jsonplaceholder.typicode.com/todos
         * expect status 200
         * Converting Array Into List of POJOs
         */

        List<PojoTask> pojoTasks=Arrays.asList(
                given().

                        when().
                        get("https://jsonplaceholder.typicode.com/todos")

                        .then()
                        .contentType(ContentType.JSON)
                        .statusCode(200)
                        .extract().as(PojoTask[].class))
                ;

        System.out.println("pojoTasks = " + pojoTasks);
    }
}
