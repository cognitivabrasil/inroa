def ir_para_login
  visit $feb_url
	click_link "Admin"
end

def tentar_login(usuario, senha)
	fill_in "user", :with => usuario
	fill_in "passwd", :with => senha
	click_button "Entrar no sistema"
end

$db_state = ""
def db_state(state)
	unless state == $db_state
		system("./#{state}")
		$db_state = state
	end
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
	begin	
		find(:xpath, "//a[@title='#{texto}']").click
	rescue Capybara::ElementNotFound
		click_link(texto)
	end
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

Quando /^eu confirmo o OK( na nova janela)?$/ do | n |
	if n == "na nova janela "	
		within_window(page.driver.browser.window_handles.last) do 
			a = page.driver.browser.switch_to.alert
			if a.text == 'OK'
			  a.dismiss
			else
			  a.accept
			end
		end
	else
		a = page.driver.browser.switch_to.alert
		if a.text == 'OK'
		  a.dismiss
		else
		  a.accept
		end
	end
	
    #page.evaluate_script('window.alert = function() { return true; }')
end

Then /^a página deve conter link para "([^"]*)"$/ do |arg1|
	assert(has_link? arg1)
end

Dado /^que o repositorio está (.*)$/ do | estado |
  db_state(estado) # express the regexp above with the code you wish you had
end

Then /^deve abrir nova janela contendo "([^"]*)"$/ do |text|
	within_window(page.driver.browser.window_handles.last) do 
      assert page.has_content?(text) 
	end 

end

Quando /^eu preencher (na nova janela )?"([^"]*)" com "([^"]*)"$/ do |novajanela, campo, valor|
	if novajanela == "na nova janela "	
		within_window(page.driver.browser.window_handles.last) do 
			fill_in(campo, :with => valor)
		end
	else
			fill_in(campo, :with => valor)
	end
end

Quando /^eu selecionar (na nova janela )?"([^"]*)" de "([^"]*)"$/ do |n, valor, campo|
	if n == "na nova janela "	
		within_window(page.driver.browser.window_handles.last) do 
			select(valor, :from => campo)
		end
	else
			select(valor, :from => campo)
	end
	
end

Quando /^eu clicar (na nova janela )?no botão "([^"]*)"$/ do |n, nome|
	if n == "na nova janela "	
		within_window(page.driver.browser.window_handles.last) do 
			click_button nome
		end
	else
			click_button nome
	end
end

	
