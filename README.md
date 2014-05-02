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

```
cd src/sql
sh create.sh
```

Isso irá criar uma base de dados *federacao* com senha
*feb@RNP* e um usuário *feb*.

**IMPORTANTE! MUDE A SENHA USAR A INSTALAÇÃO PARA QUALQUER
COISA ALÉM DE DESENVOLVIMENTO**

Configuração do Solr
----------------------------------------------------
Os arquivos da coleção FEB, com o Schema correto,
estão em src/solr.

Se você estiver desenvolvendo, o mais fácil é rodar
o Solr embedded incluido com o FEB, que já vem configurado
corretamente:

```
cd src/solr
java -jar start.jar
```

Para deploy, o melhor é usar o pacote solr.

Caso você tenha instalado o SOLR no tomcat com o
instalador (.deb) da Cognitiva, basta copiar 
o diretório:

```
sudo cp -R -f src/solr/feb /var/lib/tomcat7/solr/
sudo chown tomcat7:tomcat7 /var/lib/tomcat7/solr/ -R
```

Criação de diretórios e permissões
----------------------------------------------------
Esse passo não é estritamente necessário para começar
a desenvolver.

```
	sudo mkdir /var/log/feb/
	sudo chown tomcat7 /var/log/feb/
```

Rodar o feb
----------------------------------------------------

```
mvn jetty:run
```

Verificação de que está rodando certo
-----------------------------------------------------------

Acesse o feb em http://localhost:9091.
A senha padrão de administrador é *teste*.



Adicionar Repositório Cognix como repositório de testes
--------------------------------------------------------

Entre na ferramenta administrativa e adicione um novo repositório.
Para o LUME os dados são:

Nome: Cognix
Descrição: Repositório Cognix

URL: http://feb.ufrgs.br/repositorio/oai
Comunidades: (vazio)
Padrão metadados: OBAA
Mapeamento: OBAA Padrão
MetadataPrefix e Namespace: obaa
Periodicidade: 1

Após adicionar, rode a atualização manual.


Rodar os testes
---------------------------------------------------
```
mvn verify
```



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
	* obrigatório fazer um BUMP da versão minor ou major.


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

```
git tag -a v1.4 -m 'RESUMO DA RELEASE'
git push origin --tags
```



Para gerar os debs você pode ir passo a passo:

```
groovy CreateDeb.groovy
sh create-deb.sh
sh deploy.sh
```

ou executar 

```
sh publish\_ppa.sh
```

### Como desenvolver

O script *CreateDeb.groovy* contém código que gera os scripts sh
e arquivos de configuração a partir de arquivos *.template*.
Olhe a variável *binding* no script groovy ou qualquer
arquivo template para ver quais variáveis estão disponíveis.

Altere os arquivos de dentro do diretório src/debian ou o 
src/Makefile
