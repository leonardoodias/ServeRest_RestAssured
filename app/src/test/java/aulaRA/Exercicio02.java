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
        // Criar uma instância de Usuario com dados fictícios para teste
        String userName = faker.name().firstName();
        String userEmail = userName + "@qa.com.br";
        usuario = new Usuario(userName, userEmail, "senha", "true");
    }

    @Test
    public void testCadastroUsuarioDuplicado() {
        // A. Teste de cadastro de usuário duplicado

        // Primeiro, cadastrar um usuário com o e-mail gerado aleatoriamente
        String userID = usuario.cadastrarUsuario(usuario);
        assertNotNull(userID); // Verificar se o cadastro foi bem-sucedido

        // Pesquisar o usuário pelo ID
        usuario.pesquisarUsuarioPorId(userID);

        // Pegar o e-mail do usuário cadastrado
        String userEmail = usuario.getEmail();

        // Visualizar o email usando o método getEmail
        System.out.println("Email do usuário cadastrado: " + userEmail);

        // Tentar cadastrar um novo usuário com o mesmo e-mail
        Usuario usuarioDuplicado = new Usuario("Nome Duplicado", userEmail, "senha123", "false");
        String errorResponse = usuario.validarCadastroDuplicado(usuarioDuplicado);

        // Verificar se o servidor retornou um erro indicando que o e-mail já está em uso
        assertNotNull(errorResponse);
        assertTrue(errorResponse.contains("Este email já está sendo usado"));
    }

    @Test
    public void testRealizaLoginComUsuarioCadastrado() {
        // B. Utilizar informações de um usuário para fazer autenticação

        // Primeiro, cadastrar um usuário com o e-mail gerado aleatoriamente
        String userID = usuario.cadastrarUsuario(usuario);
        assertNotNull(userID); // Verificar se o cadastro foi bem-sucedido

        // Agora, realizar o login com o mesmo usuário
        Login login = new Login(usuario.getEmail(), "senha"); // Use a senha correta aqui
        String userToken = login.efetuarLogin(login); // Chama o método efetuaLogin
        assertNotNull(userToken); // Verificar se o login foi bem-sucedido

        // Imprimir o token do usuário logado
        System.out.println("Token do usuário logado: " + userToken);
    }
}
