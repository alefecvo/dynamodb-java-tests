package steps;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import services.DynamoDBServices;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class DynamoDBStepDefinitionsCucumber {
    DynamoDbClient ddb;

    @Dado("que acesso banco de dados DynamoDB")
    public void que_acesso_banco_de_dados_dynamo_db() {
        ddb = DynamoDBServices.createClientDataBase();
    }

    @Quando("criar tabela para adicionar item")
    public void criar_tabela_para_adicionar_item() {

        DynamoDBServices.createTableDataBase(ddb, "Carros");
        DynamoDBServices.insertItemTable(ddb, "Carros",
                "1", "Fusca");
        DynamoDBServices.deleteTableDataBase(ddb,"Carros");

    }

    @Então("será adicionado item")
    public void será_adicionado_item() {
        DynamoDBServices.closeDataBaseTable(ddb);
    }


}
