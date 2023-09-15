package aulaRA;

import org.apache.http.HttpStatus;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;
import io.restassured.response.Response;

public class Login {
    private String email;
    private String password;

    /* construtor*/
   public Login(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String efetuarLogin(Login login) {

       Response response = given()
               .body("{\n" +
                       "  \"email\": \"" + login.email + "\",\n" +
                       "  \"password\": \"" + login.password +"\"\n" +
                       "}")
               .contentType("application/json")
       .when()
               .post("http://localhost:3000/login")
       .then()
               .statusCode(HttpStatus.SC_OK) /* 200 */
               .body("message", is("Login realizado com sucesso"))
               .extract().response();
       String userToken = response.path("authorization");

       return userToken;
    }
}
