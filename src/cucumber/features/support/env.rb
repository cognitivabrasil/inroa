require "capybara"
require "capybara/dsl"
require "capybara-webkit"


Capybara.run_server = false
Capybara.current_driver = :webkit
Capybara.app_host = '143.54.95.20:8080/feb/'
$feb_url = "http://143.54.95.20:8080/feb/"


#Webrat.configure do | c |
#	c.mode = :selenium
#	c.application_framework = :external
#end

#World(Webrat::Methods)
#World(Webrat::Matchers)
#World(Webrat::Selenium::Matchers)
World(Capybara)
World(Capybara::DSL)

