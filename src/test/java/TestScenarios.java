
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ResponseBody;
import io.restassured.response.ValidatableResponse;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import io.restassured.response.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;
import java.util.*;
import java.lang.*;

public class TestScenarios {

/*
test_01 To check if we can access the users end point
 */
    @Test
    void test_01() {
        Response response = RestAssured.get("https://jsonplaceholder.typicode.com/users");
        int statusCode = response.getStatusCode();
        response.getBody();
        System.out.println(response.getBody().asString());
        System.out.println(response.asString());
        System.out.println( response.getStatusCode());
        Assert.assertEquals(statusCode,200);
    }
    /*
    test_02 To check if we can get the user with username='Delphine' from 'users' end point
     */
    @Test
    void test_02()
    {
         given()
                .accept(ContentType.JSON)
                .when()
                .get("https://jsonplaceholder.typicode.com/users?username=Delphine")
                 .then()
                 .assertThat()
                 .statusCode(200);
    }

    /*
    test_03 To check if we can get the posts of the user 'Delphine' with userid 9 from 'Posts' end point
     */
    @Test
    void test_03()
    {
    List<Integer> idOfAllposts =
            given()
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                    .when()
                    .get("https://jsonplaceholder.typicode.com/users/9/posts")
                    .then()
                    .extract()
                    .response()
                    .path("id");

    System.out.println(idOfAllposts.toString());
    System.out.println(idOfAllposts.size());
}
    /*
        test_04 To check if we fetch the comments and validate if the emails in
        comment section are in the proper format validate in 'Comments' end point
         */
    @Test
    void test_04()
    {
        given()
                .accept(ContentType.JSON)
                .when()
                .get("https://jsonplaceholder.typicode.com/comments?postId=81")
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .body(matchesJsonSchemaInClasspath("commentsSchema.json"));
    }
}
