# language: pt
Funcionalidade: Buscar objeto
  Para satisfazer meu ego
  Como um mestre em computação
  Eu quero garantir que o FEB encontre minha dissertação

  Cenário: Achar tese do Marcos
    Dado que eu abri a página "http://localhost:8080"
    Quando eu pesquisar por "avaliação experimental de uma técnica de padronização de escores de similaridade"
    Então deve aparecer um link para "infoDetalhada.jsp?id=oai:www.lume.ufrgs.br:10183/25494&idBase=0&repositorio=3" contendo o texto "Avaliação"
