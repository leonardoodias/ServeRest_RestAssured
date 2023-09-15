package aulaRA;

import org.junit.Before;
import org.junit.Test;
import com.github.javafaker.Faker;

import static org.junit.Assert.*;

public class Exercicio02 {
    private Usuario usuario;
    private Faker faker;

    String userToken;


    @Before
    public void preCondition() {
        faker = new Faker();
        // Criar uma inst�ncia de Usuario com dados fict�cios para teste
        String userName = faker.name().firstName();
        String userEmail = userName + "@qa.com.br";
        usuario = new Usuario(userName, userEmail, "senha", "true");
    }

    @Test
    public void testCadastroUsuarioDuplicado() {
        // A. Teste de cadastro de usu�rio duplicado

        // Primeiro, cadastrar um usu�rio com o e-mail gerado aleatoriamente
        String userID = usuario.cadastrarUsuario(usuario);
        assertNotNull(userID); // Verificar se o cadastro foi bem-sucedido

        // Pesquisar o usu�rio pelo ID
        usuario.pesquisarUsuarioPorId(userID);

        // Pegar o e-mail do usu�rio cadastrado
        String userEmail = usuario.getEmail();

        // Visualizar o email usando o m�todo getEmail
        System.out.println("Email do usu�rio cadastrado: " + userEmail);

        // Tentar cadastrar um novo usu�rio com o mesmo e-mail
        Usuario usuarioDuplicado = new Usuario("Nome Duplicado", userEmail, "senha123", "false");
        String errorResponse = usuario.validarCadastroDuplicado(usuarioDuplicado);

        // Verificar se o servidor retornou um erro indicando que o e-mail j� est� em uso
        assertNotNull(errorResponse);
        assertTrue(errorResponse.contains("Este email j� est� sendo usado"));
    }

    @Test
    public void testRealizaLoginComUsuarioCadastrado() {
        // B. Utilizar informa��es de um usu�rio para fazer autentica��o

        // Primeiro, cadastrar um usu�rio com o e-mail gerado aleatoriamente
        String userID = usuario.cadastrarUsuario(usuario);
        assertNotNull(userID); // Verificar se o cadastro foi bem-sucedido

        // Agora, realizar o login com o mesmo usu�rio
        Login login = new Login(usuario.getEmail(), "senha"); // Use a senha correta aqui
        String userToken = login.efetuarLogin(login); // Chama o m�todo efetuaLogin
        assertNotNull(userToken); // Verificar se o login foi bem-sucedido

        // Imprimir o token do usu�rio logado
        System.out.println("Token do usu�rio logado: " + userToken);
    }
}
