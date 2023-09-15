package aulaRA;

import org.apache.http.HttpStatus;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;

public class Carrinho {
    private String idProduto;
    private Integer quantidade;

    public Carrinho() { }

    public Carrinho(String idProduto, Integer quantidade) {
        this.idProduto = idProduto;
        this.quantidade = quantidade;
    }

    public void cadastrarCarrinho(String idProduto, Integer quantidade, String usertoken) {
        given()
                .header("authorization", usertoken)
                .body("{\n" +
                        "  \"produtos\": [\n" +
                        "    {\n" +
                        "      \"idProduto\": \"" + idProduto + "\",\n" +
                        "      \"quantidade\": " + quantidade + "\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}")
                .contentType("application/json")
        .when()
                .post("http://localhost:3000/carrinhos")
        .then()
                .log().all()
                .statusCode(HttpStatus.SC_CREATED) /* 201 */
                .body("message", is("Cadastro realizado com sucesso"));
    }


    public void cancelarCompra(String usertoken) {
        given()
                .header("authorization", usertoken)
        .when()
                .delete("http://localhost:3000/carrinhos/cancelar-compra")
        .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .body("message", is("Registro excluído com sucesso. Estoque dos produtos reabastecido"));
    }
}
