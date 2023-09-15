package restassuredclass;

import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;

public class Exercicio04 {

    private Usuario usuario;

    private Produto produto;

    private Carrinho carrinho;

    private Login login;

    private Faker faker;

    public Exercicio04() {
    }

    @Before
    public void preCondition(){
        faker = new Faker();
        String userName = faker.name().fullName();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        usuario = new Usuario(userName, email, password, "true");
        login = new Login(email, password);
        String productName = faker.book().title();
        produto = new Produto(productName, 50, "Livro", 100);
        carrinho = new Carrinho();
    }

    @Test
    public void testVerificarExcluirUsuarioComCarrinho(){
        // Cadastra um usuario e obtém o ID do usuario
        String userID = usuario.cadastrarUsuario(usuario);
        // Efetua login com o usuário cadastrado e obtem o token
        String userToken = login.efetuarLogin(login);
        // Cadastra um produto com o token de autenticação
        String productID = produto.cadastrarProduto(produto, userToken);
        // Cadastra um carrinho com o token de autenticação
        carrinho.cadastrarCarrinho(productID, 5, userToken);

        //Tenta exluir o usuario com carrinho
        usuario.validarExcluirUsuarioComCarrinho(userID);

        //Cancela a compra/carrinho
        carrinho.cancelarCompra(userToken);

        //Exclui o produto e Usuario após cancelar o carrinho.
        produto.excluirProdutoPorID(productID, userToken);
        usuario.excluirUsuarioPorID(userID);
    }
}