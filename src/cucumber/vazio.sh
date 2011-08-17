#/bin/bash

sudo service postgresql restart

user=$(sudo -u postgres psql -c "SELECT * FROM pg_catalog.pg_user WHERE usename = 'feb'" | grep feb); #user sera "" se não houver um usuario feb

sudo -u postgres dropdb federacao >> instalacao_feb.log;
if [ "$user" != "" ]; then	#testa se existe o usuario feb
	sudo -u postgres psql -c "ALTER USER feb WITH ENCRYPTED PASSWORD 'feb@RNP'" >> instalacao_feb.log;
else
	sudo -u postgres psql -c "CREATE USER feb WITH PASSWORD 'feb@RNP'" >> instalacao_feb.log;
fi

sudo -u postgres createdb -O feb federacao >> instalacao_feb.log;
sudo -u postgres psql federacao -f /usr/share/FEB/FEB_estrutura.sql >> instalacao_feb.log;
sudo -u postgres psql federacao -f /usr/share/FEB/FEB_dados.sql >> instalacao_feb.log;
