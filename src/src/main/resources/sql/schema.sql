SET MODE Oracle;


CREATE SEQUENCE DADOS_SUBFEDERACOES_id_seq INCREMENT BY 1 START WITH 100;
CREATE TABLE dados_subfederacoes (
    id INT NOT NULL,
    nome VARCHAR(400) NOT NULL,
    descricao VARCHAR(400),
    data_ultima_atualizacao timestamp,
    url character varying(200) NOT NULL,
    data_xml CLOB,
    version character varying(20),
    PRIMARY KEY (id)
);


CREATE SEQUENCE padraometadados_id_seq INCREMENT BY 1 START WITH 100;
CREATE TABLE padraometadados (
    id INT NOT NULL,
    nome character varying(45) NOT NULL,
    metadata_prefix character varying(45),
    name_space character varying(45),
    atributos VARCHAR(200),
    PRIMARY KEY (id)
);

CREATE SEQUENCE mapeamentos_id_seq INCREMENT BY 1 START WITH 100;
CREATE TABLE mapeamentos (
    id INT NOT NULL,
    nome character varying(45) NOT NULL,
    descricao character varying(200) NOT NULL,
    xslt CLOB NOT NULL,
    xml_exemplo CLOB,
    padrao_id integer NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (padrao_id) REFERENCES padraometadados(id) ON DELETE CASCADE
);


CREATE SEQUENCE repositorios_id_seq INCREMENT BY 1 START WITH 100;

CREATE TABLE repositorios (
    id INT NOT NULL,
    nome character varying(100) NOT NULL,
    descricao VARCHAR(300),
    data_ultima_atualizacao timestamp,
    url character varying(200) NOT NULL,
    padrao_metadados integer,
    mapeamento_id integer NOT NULL,
    metadata_prefix character varying(45),
    name_space character varying(45),
    internal_set character varying(200),
    data_xml CLOB,
    PRIMARY KEY (id),
    UNIQUE (nome),
    FOREIGN KEY (mapeamento_id) REFERENCES mapeamentos(id)
);

CREATE SEQUENCE repositorios_subfed_id_seq INCREMENT BY 1 START WITH 100;

CREATE TABLE repositorios_subfed (
    id INT NOT NULL,
    id_subfed integer NOT NULL,
    nome character varying(100) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_subfed) REFERENCES dados_subfederacoes(id) ON DELETE CASCADE
);

CREATE SEQUENCE documentos_id_seq INCREMENT BY 1 START WITH 100;
CREATE TABLE documentos (
    id INT NOT NULL,
    titulo VARCHAR(300),
    obaa_entry character varying(100) NOT NULL,
    resumo VARCHAR(2000),
    id_repositorio integer,
    created timestamp,
    palavras_chave VARCHAR(1000),
    id_rep_subfed integer,
    deleted NUMBER(1) DEFAULT 0 NOT NULL,
    obaaxml CLOB,
    PRIMARY KEY (id),
    FOREIGN KEY (id_repositorio) REFERENCES repositorios(id) ON DELETE CASCADE,
    FOREIGN KEY (id_rep_subfed) REFERENCES repositorios_subfed(id) ON DELETE CASCADE
);

CREATE SEQUENCE usuarios_id_seq INCREMENT BY 1 START WITH 100;

CREATE TABLE usuarios (
    id INT NOT NULL,
    login character varying(45) NOT NULL,
    senha character varying(45) NOT NULL,
    nome VARCHAR(300),
    permissions VARCHAR(400),
    role character varying(20),
    PRIMARY KEY (id)
);

CREATE SEQUENCE searches_id_seq INCREMENT BY 1 START WITH 100;
CREATE TABLE searches (
    id INT NOT NULL,
    text VARCHAR(400),
    created timestamp,
    PRIMARY KEY (id)
); 
