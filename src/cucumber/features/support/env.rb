require "webrat"

Webrat.configure do | c |
	c.mode = :mechanize
end

World(Webrat::Methods)
World(Webrat::Matchers)
