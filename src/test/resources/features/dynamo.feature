#language: pt
Funcionalidade: Operações no DynamoDB

  Cenário: Criar, inserir, obter e deletar item no DynamoDB
    Dado que eu desejo criar uma tabela "Filme"
    Quando eu insiro um item com ID "1", título "Harry Potter" e descrição "Harry Potter é uma coleção de filmes de magia"
    Então eu devo ser capaz de recuperar o item com ID "1"
    E o item deve ter o título "Harry Potter" e a descrição "Harry Potter é uma coleção de filmes de magia"
    Quando eu deleto o item com ID "1"
    Então o item com ID "1" não deve existir