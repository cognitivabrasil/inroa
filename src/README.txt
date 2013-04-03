INSTALAÇÃO DO FEB 2.9-SNAPSHOT
------------------------------------------------------------

PASSO 1 - Dependências

Primeiramente, é necessário instalar as dependências
do FEB:

 * Java 6 ou superior.
 * Tomcat 6 (ou superior) ou outro container
 * PostgreSQL versão >= 8.4
 * postgresql-server-dev-9.1 ou superior
 * pacote make 

É importante que estes componentes estejam 
funcionando corretamente antes de prosseguir com
a instalação do FEB.

------------------------------------------------------------

PASSO 2

1 - Baixe o arquivo "feb-2.9-SNAPSHOT.tar.gz" e descompacte-o. Abra um terminal e entre
na pasta em que os arquivos foram descompactados.

2 - Instalação da biblioteca postgres PGSimilarity

    Localizar o arquivo pg_similarity-0.0.19.tgz na pasta em que os arquivos foram descompactados 
    Extrair o arquivo com o comando
     tar -xzf pg_similarity-0.0.19.tgz

    Ir para a pasta pg_similarity (extraida do arquivo tgz) e executar os seguintes comandos:
     USE_PGXS=1 make
     sudo USE_PGXS=1 make install

3 - Criação da base de dados para 1a instalação do FEB
    Certifique-se que não existe a base de dados "federacao". Se já existir, remova.

    Após execute os seguintes comandos:
        sudo -u postgres psql -c "CREATE USER feb WITH PASSWORD 'feb@RNP'"
        sudo -u postgres createdb -O feb federacao
        sudo -u postgres psql federacao -f schema.sql
        sudo -u postgres psql federacao -f data.sql
	sudo -u postgres psql federacao -f feb_similarity.sql ((( COLOCAR ESSE ARQUIVO JUNTO NO PACOTE e o pg_similarity-0.0.19.tgz )))


4 - Crie o diretório para armazenamento dos logs e altere o "dono" da pasta para o servidor
	sudo mkdir /var/log/feb/
	sudo chown tomcat6 /var/log/feb/


---------------------------------------------------------------

PASSO 3 - Instalação dos arquivos

feb.war -> fazer o deploy do arquivo no servidor.
    Copiar o feb.war para a pasta webapps do tomcat
    Reiniciar o serviço: sudo service tomcat6 restart

---------------------------------------------------------------

PASSO 4 - Configuração da base

Esse passo deve ser executado caso se queira alterar a senha, o servidor
de banco de dados ou a base de dados.

Encontre o diretório onde o feb está instalado. Vamos chamá-lo
de ${FEB}. Neste caso:

cd ${FEB}/WEB-INF/classes
java -jar feb_config.jar

Preencha as informações corretamente, e reinicie o servidor
de Servlet onde o FEB está rodando. Ex: sudo service tomcat6 restart

---------------------------------------------------------------

PASSO 5 - Testar

Acesse a porta 8080 do servidor onde o FEB foi instalado,
logue-se na interface de admin e adiciona algum repositório.
Verifique que tudo funciona.
