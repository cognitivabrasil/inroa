FEB
====================================================

Dependências externas
----------------------------------------------------
* Java 8 
* SQL
 - PostgreSQL a partir de 8.4
 ou
 - Oracle XE 11g
* Solr

Instalação e configuração do Oracle XE
-----------------------------------------------------------
Instruções completas em:

https://registry.hub.docker.com/u/alexeiled/docker-oracle-xe-11g/

Modo rápido:

```
 sudo docker run -d -p 49160:22 -p 49161:1521 -p 49162:8080 alexeiled/docker-oracle-xe-11g
```

### Instalar Oracle SQL Developer ###
http://www.oracle.com/technetwork/developer-tools/sql-developer/downloads/index.html

Baixar a última versão: "Other Platforms"
Obs: Tem que aceitar a licença e logar no sistema para poder efetuar o download do instalador.

Instalar do diretório /opt:

```
sudo unzip sqldeveloper-*-no-jre.zip -d /opt/
sudo chmod +x /opt/sqldeveloper/sqldeveloper.sh
sudo ln -s /opt/sqldeveloper/sqldeveloper.sh /usr/bin/sqldeveloper
sudo chmod +x /opt/sqldeveloper/sqldeveloper/bin/sqldeveloper
```

Editar /opt/sqldeveloper/sqldeveloper.sh:

```
#!/bin/bash
cd /opt/sqldeveloper/sqldeveloper/bin
./sqldeveloper "$@"
```

Editar o product.conf e definir o JavaHome:
Ex:

```
sudo vim ~/.sqldeveloper/4.0.0/product.conf

SetJavaHome /usr/lib/jvm/java-7-oracle
```

Rodar:
```
sudo sqldeveloper
```

### Criar base Oracle ###

De dentro do sqldeveloper, conecte na base _xe_ no _localhost_
na porta _49161_ com username _system_ e senha _oracle_.

Importe, nessa ordem, os arquivos:
  1. schema.sql
  2. data.sql
  3. oracle\_schema.sql


### Erros comuns no Oracle ###

#### ORA-01653: unable to extend table SYSTEM.DOCUMENTOS by 1024 in tablespace SYSTEM ####

Se o sistema falhar com a exceção java.sql.SQLException: _ORA-01653: unable to extend table SYSTEM.DOCUMENTOS by 1024 in tablespace SYSTEM_ provavelmente está faltando espaço no banco de dados.

Para listar as bases por ordem de ocupação:

```
select * from dba_tablespace_usage_metrics order by used_percent desc;
```

Se a base SYSTEM aparecer com quase 100%, aumente o tamanho dela, por exemplo:

```
ALTER DATABASE
  DATAFILE '/u01/app/oracle/oradata/XE/system.dbf'
  RESIZE 50000M
```


Criação da base de dados PostgreSQL
----------------------------------------------------

É preciso criar o usuário, importar o esquema da base
e popular o banco de dados com os dados iniciais, e fazer 
mesmo com a base de teste:

```
cd src/
sh create.sh
```

Isso irá criar uma base de dados *federacao* com senha *feb@RNP* e um usuário *feb*.

**IMPORTANTE! MUDE A SENHA USAR A INSTALAÇÃO PARA QUALQUER
COISA ALÉM DE DESENVOLVIMENTO**

Escolhendo base de dados
-----------------------------------------------------------------

A base de dados será escolhida da seguinte forma:

1. Se houver uma variavel de ambiente FEB\_DATABASE\_TYPE o feb
vai utilizar as informações das variáveis de ambiente para tentar
conectar na base e ignorar outras.
2. Se não houve variáveis de ambiente, mas houver um arquivo 
/etc/feb/feb.properties o Feb vai utilizar informações deste arquivo.
3. Se não houver nem variáveis de ambiente nem o arquivo, o Feb vai
tentar se conectar na base padrão.

### Variáveis de ambiente ###

FEB\_DATABASE\_TYPE - tipo da base: "Oracle" ou "Postgres"
FEB\_DATABASE\_HOST - host da base
FEB\_DATABASE\_PORT - porta da base
FEB\_DATABASE\_DATABASE - nome da base
FEB\_DATABASE\_USERNAME - nome de usuário para se conectar
FEB\_DATABASE\_PASSWORD - senha para se conectar



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

Você pode configurar o Solr pelo arquivo de configuração ou com a veriável de ambiente
FEB\_SOLR\_URL, que deverá conter uma url de acesso ao Solr, por exemplo, 
http://localhost:8983/solr/

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

Você também pode especificar o diretório dos logs do FEB com
a variável de ambiente FEB\_LOG\_HOME ou no arquivo feb.properties.

Google Analytics
----------------------------------------------------
Você pode controlar as estatísticas de uso do sistema com o Google analytics, para isso é necessário
definir o id no Google Analytics na variável de ambiente *FEB\_ANALYTICS\_ID* ou no arquivo feb.properties com a variável *Analytics.id*.

Exemplo do feb.properties

```
Analytics.id=UA-4253018-30
```

Se não for definido o Analytics id na variável de ambiente e nem no feb.properties, nenhum script do Google Analytics será inserido no código.

Rodar o feb
----------------------------------------------------

```
mvn spring-boot:run
```

Verificação de que está rodando certo
-----------------------------------------------------------

Acesse o feb em http://localhost:8080.
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
	* Manter o schema.sql e o postgres\_schema.sql sincronizados.
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


