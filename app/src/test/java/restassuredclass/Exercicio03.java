package restassuredclass;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.github.javafaker.Faker;

public class Exercicio03 {

    private Usuario usuario;
    private Login login;
    private Produto produto;
    private Carrinho carrinho;
    private Faker faker;


    @Before
    public void preCondition(){
        faker = new Faker();
        // Genera um usua?rio, email e senha ficticios
        String userName = faker.name().firstName();
        String email = userName + "@qa.com.br";
        String password = faker.internet().password();
        // Cria um usua?rio
        usuario = new Usuario(userName, email, password, "true");
        // Cria um login
        login = new Login(email, password);
        // Cria um produto
        String productName = faker.pokemon().name();
        produto = new Produto(productName, 1000, "Pokemon", 100);
        // Cria um objetio de carrinho
        carrinho = new Carrinho();
    }

    // A. Validar que o estoque de um produto é diminuído quando o produto é adicionado em um carrinho
    // B. Validar que o estoque de um produto é aumentado quando um carrinho é cancelado

    @Test
    public void testVerificarEstoque(){
        // Cadastra um usuario e obtém o ID do usuario
        String userID = usuario.cadastrarUsuario(usuario);
        // Efetua login com o usuário cadastrado e obtem o token
        String userToken = login.efetuarLogin(login);
        // Cadastra um produto com o token de autenticac?a?o
        String productID = produto.cadastrarProduto(produto, userToken);
        // Cadastra um carrinho com o token de autenticac?a?o
        carrinho.cadastrarCarrinho(productID, 5, userToken);
        // Verifica o estoque do produto
        produto.listarProdutoPorID(productID, 95);
        // Cancela o carrinho
        carrinho.cancelarCompra(userToken);
        // Verifica o estoque do produto
        produto.listarProdutoPorID(productID, 100);

    }
}
