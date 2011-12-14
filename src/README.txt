INSTALAÇÃO DO FEB 2.0.1
------------------------------------------------------------

PASSO 1 - Dependências

Primeiramente, é necessário instalar as dependências
do FEB:

 * Java 6
 * Tomcat 6 (ou outro Container de preferencia)
 * PostgreSQL

É importante que estes componentes estejam 
funcionando corretamente antes de prosseguir com
a instalação do FEB.

------------------------------------------------------------

PASSO 2a - Criação da base de dados para 1a instalação do FEB

sudo -u postgres psql -c "CREATE USER feb WITH PASSWORD 'feb@RNP'"
sudo -u postgres createdb -O feb federacao
sudo -u postgres psql federacao -f FEB_estrutura.sql
sudo -u postgres psql federacao -f FEB_dados.sql

A senha pode ser diferente, e a base de dados não precisa estar na
mesma máquina do FEB. Neste caso, você tera que executar o passo 4.


PASSO 2b - Atualização da versão 1.2 para 2.0.1

Caso já tenha a versão 1.2 do FEB instalada, rodar o script
de atualização da base:

sudo -u postgres psql federacao -f /usr/share/FEB/FEB_1.9.sql

---------------------------------------------------------------

PASSO 3 - Instalação dos arquivos

feb.war -> fazer o deploy do arquivo no servidor.

---------------------------------------------------------------

PASSO 4 - Configuração da base

Somente para instalação não-padrão (senha, usuário, ip, etc 
diferente)

Encontre o diretório onde o feb está instalado. Vamos chamá-lo
de ${FEB}. Neste caso:

cd ${FEB}/WEB-INF/classes
java -jar feb_config.jar

Preencha as informações corretamente, e reinicie o servidor
de Servlet onde o FEB está rodando.

PASSO 5 - Testar

Acesse a porta 8080 do servidor onde o FEB foi instalado,
logue-se na interface de admin e adiciona algum repositório.
Verifique que tudo funciona.

