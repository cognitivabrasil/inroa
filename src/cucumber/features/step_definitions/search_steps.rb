def ir_para_login
  visit $feb_url
	click_link "Admin"
end

def tentar_login(usuario, senha)
	fill_in "user", :with => usuario
	fill_in "passwd", :with => senha
	click_button "Entrar no sistema"
end

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
	assert(has_link? text, :href => url)
end

Quando /^eu clicar no link "([^"]*)"$/ do |texto|
	click_link texto
   # express the regexp above with the code you wish you had
end

Then /^a página deve conter "([^"]*)"$/ do |arg1|
  assert(has_content? arg1)
end

Dado /^que eu estou na tela de login$/ do
	ir_para_login()
end

Quando /^eu tento me logar com usuário "([^"]*)" e senha "([^"]*)"$/ do |usuario, senha|
	tentar_login(usuario, senha)
end

Quando /^eu confirmo o OK$/ do
		a = page.driver.browser.switch_to.alert
		if a.text == 'OK'
		  a.dismiss
		else
		  a.accept
		end
    #page.evaluate_script('window.alert = function() { return true; }')
end

Then /^a página deve conter link para "([^"]*)"$/ do |arg1|
	assert(has_link? arg1)
end


