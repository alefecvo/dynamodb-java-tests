#language: pt

Funcionalidade: DynamoDB Feature

  Cenário: Criar tabela de carros, cadastrar carros e validar carros cadastrados
    Dado que eu criar uma nova tabela no DynamoDB
      | Tabela      |
      | Carro      |
    Quando cadastrar um novo registro
      | Id    | Name    |
      | 1     | Fusca   |
    Então valido registro cadastrado

  Cenário: Criar tabela de clientes, cadastrar clientes e validar clientes cadastrados
    Dado que eu criar uma nova tabela no DynamoDB
      | Tabela      |
      | Cliente      |
    Quando cadastrar um novo registro
      | Id    | Name    |
      | 1     | Lucas   |
    Então valido registro cadastrado