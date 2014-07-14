CREATE TABLE consultas (
    consulta character varying NOT NULL,
    ids text,
    PRIMARY KEY (consulta)
);

CREATE TABLE dados_subfederacoes (
    id serial NOT NULL,
    nome text NOT NULL,
    descricao text,
    data_ultima_atualizacao timestamp,
    url character varying(200) NOT NULL,
    data_xml text,
    version character varying(20),
    PRIMARY KEY (id)
);

CREATE TABLE padraometadados (
    id serial NOT NULL,
    nome character varying(45) NOT NULL,
    metadata_prefix character varying(45),
    name_space character varying(45),
    atributos text,
    PRIMARY KEY (id)
);

CREATE TABLE mapeamentos (
    id serial NOT NULL,
    nome character varying(45) NOT NULL,
    descricao character varying(200) NOT NULL,
    xslt text NOT NULL,
    xml_exemplo text,
    padrao_id integer NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (padrao_id) REFERENCES padraometadados(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE repositorios (
    id serial NOT NULL,
    nome character varying NOT NULL,
    descricao text,
    data_ultima_atualizacao timestamp,
    url_or_ip character varying(200) NOT NULL,
    padrao_metadados integer,
    mapeamento_id integer NOT NULL,
    metadata_prefix character varying(45),
    name_space character varying(45),
    internal_set character varying(45),
    data_xml text,
    PRIMARY KEY (id),
    UNIQUE (nome),
    FOREIGN KEY (mapeamento_id) REFERENCES mapeamentos(id)
);

CREATE TABLE repositorios_subfed (
    id serial NOT NULL,
    id_subfed integer NOT NULL,
    nome character varying NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_subfed) REFERENCES dados_subfederacoes(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE documentos (
    id serial NOT NULL,
    titulo text,
    obaa_entry character varying(100) NOT NULL,
    resumo text,
    id_repositorio integer,
    created timestamp DEFAULT now(),
    palavras_chave text,
    id_rep_subfed integer,
    deleted boolean DEFAULT false NOT NULL,
    obaaxml text,
    PRIMARY KEY (id),
    FOREIGN KEY (id_repositorio) REFERENCES repositorios(id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (id_rep_subfed) REFERENCES repositorios_subfed(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE usuarios (
    id serial NOT NULL,
    login character varying(45) NOT NULL,
    senha character varying(45) NOT NULL,
    nome text,
    permissions text,
    role character varying(20),
    PRIMARY KEY (id)
);

CREATE TABLE searches (
    id serial NOT NULL,
    text TEXT,
    time timestamp,PRIMARY KEY (id)
); 