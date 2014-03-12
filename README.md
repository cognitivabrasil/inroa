FEB
====================================================

Dependências externas
----------------------------------------------------
* Java 7 
* Tomcat 7 
* PostgreSQL a partir de 8.4
* Solr

Criação da base de dados PostgreSQL
----------------------------------------------------

É preciso criar o usuário, importar o esquema da base
e popular o banco de dados com os dados iniciais, e fazer 
mesmo com a base de teste:

'''
cd src/sql
sh create.sh
'''

Configuração do Solr
----------------------------------------------------
TODO:


Criação de diretórios e permissões
----------------------------------------------------
Esse passo não é estritamente necessário para começar
a desenvolver.

'''
	sudo mkdir /var/log/feb/
	sudo chown tomcat7 /var/log/feb/
'''

Rodar o feb
----------------------------------------------------

'''
mvn jetty:run
'''

Verificação de que está rodando certo
-----------------------------------------------------------

Acesse o feb em [http://localhost:9091](http://localhost:9091).

Adicione o repositório BLA BLA BLA TODO: e atualize e faça uma
atualização e uma busca teste.


Rodar os testes
---------------------------------------------------
'''
mvn verify
'''



PARA DESENVOLVEDORES
====================================================

Modificações na base de dados
---------------------------------------------------

REGRA 1: NÃO FAÇA!

REGRA 2: Se necessário for
	* garantir que o arquivo src/sql/create.sh gere a
base necessária
	* atualizar os arquivos debian/postinst para atualizar
a base em caso de upgrade via instalador, caso possível


Instalador .deb
---------------------------------------------------
### Como gerar

#### Atualizar o Change log do .deb

Edite o arquivo src/debian/changelog e
adicione um log com as mudanças realizadas nesta versão.

#### Criar uma release

Certifique-se de que atualizou a versão do FEB
no pom.xml e crie um tag remoto no repositoria
marcando essa versão exata.

IMPORTANTE! Uma lançada uma versão para clientes,
seus arquivos não devem mudar.

Para criar o tag no git, execute:

'''
git tag -a v1.4 -m 'RESUMO DA RELEASE'
git push origin --tags
'''



Para gerar os debs você pode ir passo a passo:

'''
groovy CreateDeb.groovy
sh create-deb.sh
sh deploy.sh
'''

ou executar 

'''
sh publish\_ppa.sh
'''

### Como desenvolver

O script *CreateDeb.groovy* contém código que gera os scripts sh
e arquivos de configuração a partir de arquivos *.template*.
Olhe a variável *binding* no script groovy ou qualquer
arquivo template para ver quais variáveis estão disponíveis.

Altere os arquivos de dentro do diretório src/debian ou o 
src/Makefile
