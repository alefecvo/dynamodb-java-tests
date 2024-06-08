package steps;

import com.amazonaws.services.dynamodbv2.document.Item;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import services.DynamoDBServices;

import static org.junit.Assert.*;

public class DynamoDBStepDefinitionsCucumber {
    private DynamoDBServices dynamoDBClient;
    private String tableName;

    @Before
    public void criarConexao() {
        dynamoDBClient = new DynamoDBServices();
    }

    @After
    public void encerrarConexao() {
        dynamoDBClient.close();
    }

    @Dado("que eu desejo criar uma tabela {string}")
    public void criarTabela(String tableName) {
        this.tableName = tableName;
        dynamoDBClient.createTable(tableName);
        System.out.println("Criando tabela -> Tabela: " + tableName);
    }

    @Quando("eu insiro um item com ID {string}, título {string} e descrição {string}")
    public void inserirRegistro(String id, String title, String description) {
        dynamoDBClient.insertItem(tableName, id, title, description);
        System.out.println("Inserido registro -> Tabela: " + tableName + " ,Id: " + id + " ,Título: " + title + " ,Descrição: " + description);
    }

    @Então("eu devo ser capaz de recuperar o item com ID {string}")
    public void verificarRegistroInserido(String id) {
        Item item = dynamoDBClient.getItem(tableName, id);
        assertNotNull(item);
        System.out.println("Verificando registro inserido -> Tabela: " + tableName + " ,registro: " + item);
    }

    @E("o item deve ter o título {string} e a descrição {string}")
    public void validarRegistroInserido(String title, String description) {
        Item item = dynamoDBClient.getItem(tableName, "1");
        assertEquals(title, item.getString("Title"));
        assertEquals(description, item.getString("Description"));
        System.out.println("Validando registro inserido -> Tabela: " + tableName + " ,registro: " + item);
    }

    @Quando("eu deleto o item com ID {string}")
    public void deletarRegistro(String id) {
        dynamoDBClient.deleteItem(tableName, id);
        System.out.println("Deletando registro por ID -> Tabela: " + tableName + " ,Id: " + id);
    }

    @Então("o item com ID {string} não deve existir")
    public void verificarRegistroRemovido(String id) {
        Item item = dynamoDBClient.getItem(tableName, id);
        assertNull(item);
        System.out.println("Verificando registro removido por ID -> Tabela: " + tableName + " ,Id: " + id + " ,registro: " + item);

    }

}