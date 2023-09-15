package restassuredclass;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.is;;
import org.apache.http.HttpStatus;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class Usuario {

    private String nome;
    private String email;
    private String password;
    private String administrador;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAdministrador() {
        return administrador;
    }

    public void setAdministrador(String administrador) {
        this.administrador = administrador;
    }

    public Usuario(String nome, String email, String password, String administrador) {
        this.nome = nome;
        this.email = email;
        this.password = password;
        this.administrador = administrador;
    }

    public void listarUsuarios() {
       Response response = when()
                .get("http://localhost:3000/usuarios")
               .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract().response(); //Extrai a resposta HTTP
        String responseBody = response.getBody().asString(); // Obtém o corpo da resposta como uma string

        // Imprime a lista de usuários
        System.out.println("Lista de usuários: " + responseBody);

    }

    public void pesquisarUsuarioPorId(String userID) {

        Response response = given()
                .pathParam("_id", userID)
                .when()
                        .get("http://localhost:3000/usuarios/{_id}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("_id", is(userID))
                .extract().response(); //Estrai a resposta HTTP

        String responseBody = response.getBody().asString();

        // Verifica se a resposta está em formato JSON válido
        JsonPath jsonPath = new JsonPath(responseBody);

        // Verifica se a chave "_id" existe na resposta e se seu valor é igual ao userID
        String idFromResponse = jsonPath.getString("_id");
        assertThat(idFromResponse, is(userID));

        // Adicione verificações adicionais aqui, se necessário, para outros campos de dados

        // Imprime a lista de usuários
        System.out.println("Resposta do servidor: " + responseBody);
    }

    public String cadastrarUsuario(Usuario usuario) {
        Response response = given()
                .body("{\n" +
                        "  \"nome\": \"" + usuario.nome + "\",\n" +
                        "  \"email\": \"" + usuario.email + "\",\n" +
                        "  \"password\": \"" + usuario.password + "\",\n" +
                        "  \"administrador\": \"" + usuario.administrador + "\"\n" +
                        "}")
                .contentType("application/json")
        .when()
                .post("http://localhost:3000/usuarios")
        .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("message", is("Cadastro realizado com sucesso"))
                .extract().response(); // Extrai a resposta HTTP

        String responseBody = response.getBody().asString(); // Obtém o corpo da resposta como uma string

        // Use JsonPath para obter o ID diretamente do JSON da resposta
        JsonPath jsonPath = new JsonPath(responseBody);
        String userID = jsonPath.getString("_id");

        System.out.println("Usuário cadastrado com ID: " + userID);
        System.out.println("Resposta do servidor: " + responseBody);

        return userID;
    }

    public String validarCadastroDuplicado(Usuario usuarioDuplicado) {
        Response response = given()
                .body("{\n" +
                        "  \"nome\": \"" + usuarioDuplicado.getNome() + "\",\n" +
                        "  \"email\": \"" + usuarioDuplicado.getEmail() + "\",\n" +
                        "  \"password\": \"" + usuarioDuplicado.getPassword() + "\",\n" +
                        "  \"administrador\": \"" + usuarioDuplicado.getAdministrador() + "\"\n" +
                        "}")
                .contentType("application/json")
                .when()
                .post("http://localhost:3000/usuarios")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", is("Este email já está sendo usado"))
                .extract().response(); // Extrai a resposta HTTP

        String responseBody = response.getBody().asString(); // Obtém o corpo da resposta como uma string

        // Use JsonPath para obter o ID diretamente do JSON da resposta
        JsonPath jsonPath = new JsonPath(responseBody);
        String userID = jsonPath.getString("_id");

        System.out.println("Resposta do servidor para cadastro duplicado: " + responseBody);

        return responseBody;
    }

    public void validarExcluirUsuarioComCarrinho(String userID){
        given()
                .pathParam("_id", userID)
        .when()
                .delete("http://localhost:3000/usuarios/{_id}")
        .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", is("Não é permitido excluir usuário com carrinho cadastrado"));
    }

    public void excluirUsuarioPorID(String userID) {
        Response response = given()
                .pathParam("_id", userID)
        .when()
                .delete("http://localhost:3000/usuarios/{_id}")
        .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().response(); // Extrai a resposta HTTP

        String responseBody = response.getBody().asString(); // Obtém o corpo da resposta como uma string

        System.out.println("Resposta do servidor: " + responseBody);
    }


    public void editarUsuarioPorID(String userId, Usuario usuario, Boolean exists) {

        String message;
        Integer statusCode;

        if (exists) {
            statusCode = HttpStatus.SC_OK;
            message = "Registro Alterado com sucesso";
        } else {
            statusCode = HttpStatus.SC_CREATED;
            message = "Cadastro realizado com sucesso";
        }

        given()
                .pathParam("_id", userId)
                .body("{\n" +
                        "  \"nome\": \"" + usuario.nome + "\",\n" +
                        "  \"email\": \"" + usuario.email + "\",\n" +
                        "  \"password\": \"" + usuario.password + "\",\n" +
                        "  \"administrador\": \"" + usuario.administrador + "\"\n" +
                        "}")
                .contentType("application/json")
        .when()
                .put("http://localhost:3000/usuarios/{_id}")
        .then()
                .statusCode(statusCode)
                .body("message", is(message));
    }
}
