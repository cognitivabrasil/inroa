Given /^que eu abri a página "([^\"]*)"$/ do |url|
  visit url
end

Given /^que eu abri a página do FEB$/ do
  visit $feb_url
end


When /^eu pesquisar por "([^"]*)"$/ do |query|
  fill_in "key", :with => query
  click_button "Consultar"
end

Then /^deve aparecer um link para "([^"]*)" contendo o texto "([^"]*)"$/ do |url, text|
	unless(has_link? text, :href => url)
		raise "Página:\n\n #{html}\n===============================\n\n"
	end
end

Quando /^eu clicar no link "([^"]*)"$/ do |texto|
	click_link texto
   # express the regexp above with the code you wish you had
end

Then /^a página deve conter "([^"]*)"$/ do |arg1|
  unless(has_content? arg1)
		raise "Página:\n\n #{html}\n===============================\n\n"
	end
end

Dado /^que eu estou na tela de login$/ do
	steps %Q{
		Given que eu abri a página do FEB
		When eu clicar no link "Admin"
		Then a página deve conter "Digite seu usuário e sua senha"
	}
end

Quando /^eu tento me logar com usuário "([^"]*)" e senha "([^"]*)"$/ do |usuario, senha|
	fill_in "user", :with => usuario
	fill_in "passwd", :with => senha
	click_button "Entrar"
end
