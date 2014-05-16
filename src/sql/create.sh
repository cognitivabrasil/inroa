#!/bin/sh
echo "Dropando base local..."
sudo -u postgres dropdb federacao > /dev/null
echo "Creating user"
sudo -u postgres psql -c "CREATE USER feb WITH PASSWORD 'feb@RNP'" > /dev/null
echo "Criando base local..."
sudo -u postgres createdb -O feb -E 'UTF-8' federacao
sudo -u postgres psql < schema.sql > /dev/null
sudo -u postgres psql < postgres_schema.sql > /dev/null
echo "Populando base com dados iniciais..."
sudo -u postgres psql federacao < data.sql > /dev/null
echo "Apagando base de testes..."
sudo -u postgres dropdb federacao_teste > /dev/null
echo "Copiando base local para base de testes..."
echo "create DATABASE federacao_teste with TEMPLATE federacao; ALTER DATABASE federacao_teste OWNER TO feb; " | sudo -u postgres psql > /dev/null
