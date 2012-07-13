#!/bin/sh
echo "Dropando base local..."
sudo -u postgres dropdb federacao
echo "Criando base local..."
sudo -u postgres psql < schema.sql
echo "Populando base com dados iniciais..."
sudo -u postgres psql federacao < data.sql
echo "Apagando base de testes..."
sudo -u postgres dropdb federacao_teste;
echo "Copiando base local para base de testes..."
echo "create DATABASE federacao_teste with TEMPLATE federacao; ALTER DATABASE federacao_teste OWNER TO feb; " | sudo -u postgres psql