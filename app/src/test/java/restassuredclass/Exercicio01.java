package restassuredclass;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import com.github.javafaker.Faker;

public class Exercicio01 {
    private Usuario usuario;
    private String userID;
    private Faker faker;

    @Before
    public void preCondition() {
        faker = new Faker();
        // Criar uma instância de Usuario com dados fictícios para teste
        String userName = faker.name().firstName();
        String userEmail = userName + "@qa.com.br";
        usuario = new Usuario(userName, userEmail, "senha", "true");
    }

    /*  A. GET usuarios;
        B. POST usuarios; - Usar scripts para validar status code, mensagem retornada e salvar id em uma variável.
        C. GET usuarios/{{id}} utilizando a variável com o valor do id. - Usar scripts para validar o status code e que as informações do usuário retornadas estão
        corretas.
        D. DELETE usuarios/{{id}} utilizando a variável com o valor do id */

    @Test
    public void testListarUsuarioscomSucesso() {
        // A. GET usuarios
        usuario.listarUsuarios();
    }

    @Test
    public void testCadastroDeUsuarioscomSucesso() {
        // B. POST usuarios
        userID = usuario.cadastrarUsuario(usuario);
    }

    @Test
    public void testListarUsuarioPorIDcomSucesso() {
        // C. GET usuarios por ID

        // Cadastrar o usuário e obter o ID dele
        String userID = usuario.cadastrarUsuario(usuario);

        // Verificar se o ID não está nulo
        assertNotNull(userID);

        // Pesquisar o usuário pelo ID
        usuario.pesquisarUsuarioPorId(userID);
    }

    @Test
    public void testDeletarUsuarioPorIDcomSucesso() {
        // D. Deletar Usuarios

        // Cadastrar o usuário e obter o ID dele
        String userID = usuario.cadastrarUsuario(usuario);

        // Verificar se o ID não está nulo
        assertNotNull(userID);

        // Deletar o usuário pelo ID
        usuario.excluirUsuarioPorID(userID);

    }

}