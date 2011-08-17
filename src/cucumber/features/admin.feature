# language: pt
Funcionalidade: Administração
  Para administrar o FEB
  Como um usuário adminsitrador
  Quero conseguir administrar minha federação

  Cenário: Mostrar tela de login
    Um usuário não logado clica no link para administrar, deve aparecer a tela
    de login.
		Dado que eu abri a página do FEB
		Quando eu clicar no link "Admin"
		Então a página deve conter "Digite seu usuário e sua senha"

	Cenário: Autenticação bem sucedida
		Um usuário com senha correta deve conseguir se logar no FEB.
		Dado que eu estou na tela de login
		Quando eu tento me logar com usuário "admin" e senha "teste"
		Então a página deve conter link para "Sair"

	Cenário: Autenticação mal sucedida
		Um usuário com senha incorreta não deve conseguir se logar no FEB
		Dado que eu estou na tela de login
		Quando eu tento me logar com usuário "admin" e senha "errada"
		E eu confirmo o OK
		Então a página deve conter "Digite seu usuário e sua senha"


