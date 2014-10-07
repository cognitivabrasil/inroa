#!/bin/sh
echo "Dropando base local..."
sudo -u postgres dropdb federacao > /dev/null
echo "Creating user"
sudo -u postgres psql -c "CREATE USER feb WITH PASSWORD 'feb@RNP'" > /dev/null
echo "Criando base local..."
sudo -u postgres createdb -O feb -E 'UTF-8' federacao
sudo -u postgres psql federacao < ./src/main/resources/sql/postgres_schema.sql > /dev/null
echo "Populando base com dados iniciais..."
sudo -u postgres psql federacao < ./src/main/resources/sql/data.sql > /dev/null
echo "Criando diretorio de log..."
sudo mkdir /var/log/feb > /dev/null
echo "ATENÇÃO!!!"
echo "  Dar permissão de escrita no diretório: /var/log/feb para o usuário que executa o servidor web."
echo "  Ex: sudo chown tomcat7:tomcat7 /var/log/feb" > /dev/null
