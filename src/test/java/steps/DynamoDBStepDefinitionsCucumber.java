package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import services.DynamoDBServices;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class DynamoDBStepDefinitionsCucumber {
    DynamoDbClient ddb;

    private String tableName;
    private String attributeId;
    private String attributeName;

    @Dado("que eu criar uma nova tabela no DynamoDB")
    public void que_eu_criar_uma_nova_tabela_no_dynamo_db(DataTable dataTable) {
        ddb = DynamoDBServices.createClientDataBase();
        tableName = String.valueOf(dataTable.column(0).get(1));

        DynamoDBServices.createTableDataBase(ddb, tableName);
    }

    @Quando("cadastrar um novo registro")
    public void cadastrar_um_novo_registro(DataTable dataTable) {
        attributeId = String.valueOf(dataTable.column(0).get(1));
        attributeName = String.valueOf(dataTable.column(1).get(1));

        DynamoDBServices.insertItemTable(ddb, tableName,
                attributeId, attributeName);
    }

    @Então("valido registro cadastrado")
    public void valido_registro_cadastrado() {
        DynamoDBServices.consultItemTableDataBase(ddb,tableName);
        DynamoDBServices.validatedItemTableDataBase(ddb,tableName, attributeId, attributeName);
        DynamoDBServices.deleteTableDataBase(ddb,tableName);
        DynamoDBServices.closeDataBaseTable(ddb);
    }

}
