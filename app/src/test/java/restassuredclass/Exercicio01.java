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
        // Criar uma inst�ncia de Usuario com dados fict�cios para teste
        String userName = faker.name().firstName();
        String userEmail = userName + "@qa.com.br";
        usuario = new Usuario(userName, userEmail, "senha", "true");
    }

    /*  A. GET usuarios;
        B. POST usuarios; - Usar scripts para validar status code, mensagem retornada e salvar id em uma vari�vel.
        C. GET usuarios/{{id}} utilizando a vari�vel com o valor do id. - Usar scripts para validar o status code e que as informa��es do usu�rio retornadas est�o
        corretas.
        D. DELETE usuarios/{{id}} utilizando a vari�vel com o valor do id */

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

        // Cadastrar o usu�rio e obter o ID dele
        String userID = usuario.cadastrarUsuario(usuario);

        // Verificar se o ID n�o est� nulo
        assertNotNull(userID);

        // Pesquisar o usu�rio pelo ID
        usuario.pesquisarUsuarioPorId(userID);
    }

    @Test
    public void testDeletarUsuarioPorIDcomSucesso() {
        // D. Deletar Usuarios

        // Cadastrar o usu�rio e obter o ID dele
        String userID = usuario.cadastrarUsuario(usuario);

        // Verificar se o ID n�o est� nulo
        assertNotNull(userID);

        // Deletar o usu�rio pelo ID
        usuario.excluirUsuarioPorID(userID);

    }

}