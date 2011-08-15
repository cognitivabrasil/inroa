require "capybara"
require "capybara/dsl"
require "capybara-webkit"

$feb_url = "http://localhost:8080/feb"
#$feb_url = "http://143.54.95.20:8080/feb/"

Capybara.run_server = false
Capybara.current_driver = :webkit
Capybara.app_host = $feb_url


#Webrat.configure do | c |
#	c.mode = :selenium
#	c.application_framework = :external
#end

#World(Webrat::Methods)
#World(Webrat::Matchers)
#World(Webrat::Selenium::Matchers)
World(Capybara)
World(Capybara::DSL)
World(Test::Unit::Assertions)

module AssertionHelper
	def assert(value)
	  unless(value)
		raise "Página:\n\n #{html}\n===============================\n\n"
	  end
	end
end

World(AssertionHelper)

