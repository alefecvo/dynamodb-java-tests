#language: pt

Funcionalidade: DynamoDB Feature

  Cenário: Testando DynamoDB com Localstack
    Dado que acesso banco de dados DynamoDB
    Quando criar tabela para adicionar item
    Então será adicionado item