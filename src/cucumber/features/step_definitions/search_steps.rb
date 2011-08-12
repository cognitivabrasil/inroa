Given /^que eu abri a página "([^\"]*)"$/ do |url|
  visit url
end

When /^eu pesquisar por "([^"]*)"$/ do |query|
  fill_in "key", :with => query
  click_button "Consultar"
end

Then /^deve aparecer um link para "([^"]*)" contendo o texto "([^"]*)"$/ do |url, text|
  response_body.should have_selector("a[href='#{ url }']") do |element|
     element.should contain(text)
  end
end
