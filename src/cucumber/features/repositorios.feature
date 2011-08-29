# language: pt
Funcionalidade: Repositórios
	Para adicionar e remover repositórios
	Como administrador de uma federação
	Quero conseguir adicionar e remover repositórios

	Cenário: Repositório Vazio não retorna nada
		No estado inicial, o repositório não retorna nada com uma busca

		Dado que o repositorio está vazio
		E que eu abri a página do FEB
		Quando eu pesquisar por "Jorjão"
		Então a página deve conter "Nenhum objeto encontrado"
	
	Cenário: Ao clicar para adicionar repositório, deve aparecer janela de adicionar repositório.
		
		Dado que o repositorio está vazio
		E que eu estou na tela de login
		Quando eu tento me logar com usuário "admin" e senha "teste"
		E eu clicar no link "Adicionar um novo repositório"
		Então deve abrir nova janela contendo "Entre com as informações para cadastrar um novo repositório"

	Cenário: Adicionar repositorio
		
		Dado que o repositorio está vazio
		E que eu estou na tela de login
		Quando eu tento me logar com usuário "admin" e senha "teste"
		E eu clicar no link "Adicionar um novo repositório"
		E eu preencher na nova janela "nomeRep" com "teste1"
		E eu preencher na nova janela "descricao" com "Repositório de teste"
		E eu selecionar na nova janela "OBAA" de "padraoMet"
		E eu preencher na nova janela "periodicidade" com "1"
		E eu preencher na nova janela "url" com "http://localhost:8080/feb/teste/rep1.xml"
		E eu clicar na nova janela no botão "Avançar >"
		E eu confirmo o OK na nova janela
		Então a página deve conter "Repositório de teste"
		E o repositório está "teste1"

	Cenário: Recarregar os dados do repositório e buscar por Jorjão

		Dado que o repositorio está teste1
		E que eu abri a página do FEB
		E que eu estou na tela de login
		Quando eu tento me logar com usuário "admin" e senha "teste"
		E eu clicar no link "Atualizar todos"
		Então a página deve conter "Atualizado!"
		E o repositório está "teste1-atualizado"

