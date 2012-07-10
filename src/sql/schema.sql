--criar o usuario feb
CREATE USER feb WITH PASSWORD 'feb@RNP';

--
-- PostgreSQL database dump
--

-- Dumped from database version 9.1.4
-- Dumped by pg_dump version 9.1.4
-- Started on 2012-06-19 15:30:51 BRT

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 2095 (class 1262 OID 16385)
-- Name: federacao; Type: DATABASE; Schema: -; Owner: feb
--

CREATE DATABASE federacao WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'pt_BR.UTF-8' LC_CTYPE = 'pt_BR.UTF-8';


ALTER DATABASE federacao OWNER TO feb;

\connect federacao

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

CREATE PROCEDURAL LANGUAGE plpgsql;


ALTER PROCEDURAL LANGUAGE plpgsql OWNER TO postgres;

--
-- TOC entry 189 (class 3079 OID 11685)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--
-- Este comando é para a versão do postgres 9.1
--CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2098 (class 0 OID 0)
-- Dependencies: 189
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

--COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;


-- Function: qgram(text, text)

CREATE OR REPLACE FUNCTION qgram(text, text)
  RETURNS real AS
'$libdir/pg_similarity', 'qgram'
  LANGUAGE c IMMUTABLE STRICT
  COST 1;
ALTER FUNCTION qgram(text, text)
  OWNER TO feb;

-- Function: cosine(text, text)

CREATE OR REPLACE FUNCTION cosine(text, text)
  RETURNS real AS
'$libdir/pg_similarity', 'cosine'
  LANGUAGE c IMMUTABLE STRICT
  COST 1;
ALTER FUNCTION cosine(text, text)
  OWNER TO feb;

-- Function: cosine_op(text, text)

CREATE OR REPLACE FUNCTION cosine_op(text, text)
  RETURNS boolean AS
'$libdir/pg_similarity', 'cosine_op'
  LANGUAGE c STABLE STRICT
  COST 1;
ALTER FUNCTION cosine_op(text, text)
  OWNER TO feb;

CREATE OR REPLACE FUNCTION cosine_op (text, text) RETURNS bool
AS '$libdir/pg_similarity', 'cosine_op'
LANGUAGE C STABLE STRICT;

CREATE OPERATOR ~## (
	LEFTARG = text,
	RIGHTARG = text,
	PROCEDURE = cosine_op,
	COMMUTATOR = '~##',
	JOIN = contjoinsel
);

--
-- TOC entry 188 (class 1259 OID 20315)
-- Dependencies: 6
-- Name: autores_id_seq; Type: SEQUENCE; Schema: public; Owner: feb
--

CREATE SEQUENCE autores_id_seq
    START WITH 100
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.autores_id_seq OWNER TO feb;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 187 (class 1259 OID 20196)
-- Dependencies: 2050 6
-- Name: autores; Type: TABLE; Schema: public; Owner: feb; Tablespace: 
--

CREATE TABLE autores (
    nome character varying NOT NULL,
    documento integer NOT NULL,
    id integer DEFAULT nextval('autores_id_seq'::regclass) NOT NULL
);


ALTER TABLE public.autores OWNER TO feb;

--
-- TOC entry 161 (class 1259 OID 16393)
-- Dependencies: 6
-- Name: consultas; Type: TABLE; Schema: public; Owner: feb; Tablespace: 
--

CREATE TABLE consultas (
    consulta character varying NOT NULL,
    ids text
);


ALTER TABLE public.consultas OWNER TO feb;

--
-- TOC entry 162 (class 1259 OID 16399)
-- Dependencies: 6
-- Name: dados_subfederacoes; Type: TABLE; Schema: public; Owner: feb; Tablespace: 
--

CREATE TABLE dados_subfederacoes (
    nome character varying NOT NULL,
    descricao character varying,
    id integer NOT NULL,
    data_ultima_atualizacao timestamp without time zone,
    url character varying(200) NOT NULL,
    data_xml character varying,
    version character varying(20)
);


ALTER TABLE public.dados_subfederacoes OWNER TO feb;

--
-- TOC entry 163 (class 1259 OID 16407)
-- Dependencies: 162 6
-- Name: dados_subfederacoes_id_seq; Type: SEQUENCE; Schema: public; Owner: feb
--

CREATE SEQUENCE dados_subfederacoes_id_seq
    START WITH 100
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.dados_subfederacoes_id_seq OWNER TO feb;

--
-- TOC entry 2099 (class 0 OID 0)
-- Dependencies: 163
-- Name: dados_subfederacoes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: feb
--

ALTER SEQUENCE dados_subfederacoes_id_seq OWNED BY dados_subfederacoes.id;


--
-- TOC entry 164 (class 1259 OID 16409)
-- Dependencies: 2037 2038 2040 6
-- Name: documentos; Type: TABLE; Schema: public; Owner: feb; Tablespace: 
--

CREATE TABLE documentos (
    id integer NOT NULL,
    titulo text,
    obaa_entry character varying(100) NOT NULL,
    resumo text,
    data character varying(255) DEFAULT NULL::character varying,
    localizacao text,
    id_repositorio integer,
    "timestamp" timestamp without time zone DEFAULT now(),
    palavras_chave character varying,
    id_rep_subfed integer,
    deleted boolean DEFAULT false NOT NULL,
    obaaxml character varying
);


ALTER TABLE public.documentos OWNER TO feb;

--
-- TOC entry 165 (class 1259 OID 16417)
-- Dependencies: 6 164
-- Name: documentos_id_seq; Type: SEQUENCE; Schema: public; Owner: feb
--

CREATE SEQUENCE documentos_id_seq
    START WITH 100
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
    CYCLE;


ALTER TABLE public.documentos_id_seq OWNER TO feb;

--
-- TOC entry 2100 (class 0 OID 0)
-- Dependencies: 165
-- Name: documentos_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: feb
--

ALTER SEQUENCE documentos_id_seq OWNED BY documentos.id;


--
-- TOC entry 185 (class 1259 OID 18241)
-- Dependencies: 6
-- Name: mapeamentos_id_seq; Type: SEQUENCE; Schema: public; Owner: feb
--

CREATE SEQUENCE mapeamentos_id_seq
    START WITH 100
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.mapeamentos_id_seq OWNER TO feb;

--
-- TOC entry 186 (class 1259 OID 18259)
-- Dependencies: 2049 6
-- Name: mapeamentos; Type: TABLE; Schema: public; Owner: feb; Tablespace: 
--

CREATE TABLE mapeamentos (
    id integer DEFAULT nextval('mapeamentos_id_seq'::regclass) NOT NULL,
    nome character varying(45) NOT NULL,
    descricao character varying(200) NOT NULL,
    xslt text NOT NULL,
    padrao_id integer NOT NULL
);


ALTER TABLE public.mapeamentos OWNER TO feb;

--
-- TOC entry 167 (class 1259 OID 16447)
-- Dependencies: 6
-- Name: objetos; Type: TABLE; Schema: public; Owner: feb; Tablespace: 
--

CREATE TABLE objetos (
    id integer NOT NULL,
    atributo character varying NOT NULL,
    valor character varying,
    documento integer NOT NULL
);


ALTER TABLE public.objetos OWNER TO feb;

--
-- TOC entry 168 (class 1259 OID 16453)
-- Dependencies: 6 167
-- Name: objetos_id_seq; Type: SEQUENCE; Schema: public; Owner: feb
--

CREATE SEQUENCE objetos_id_seq
    START WITH 100
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.objetos_id_seq OWNER TO feb;

--
-- TOC entry 2101 (class 0 OID 0)
-- Dependencies: 168
-- Name: objetos_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: feb
--

ALTER SEQUENCE objetos_id_seq OWNED BY objetos.id;


--
-- TOC entry 169 (class 1259 OID 16455)
-- Dependencies: 6
-- Name: padraometadados; Type: TABLE; Schema: public; Owner: feb; Tablespace: 
--

CREATE TABLE padraometadados (
    id integer NOT NULL,
    nome character varying(45) NOT NULL,
    metadata_prefix character varying(45),
    name_space character varying(45),
    atributos text
);


ALTER TABLE public.padraometadados OWNER TO feb;

--
-- TOC entry 170 (class 1259 OID 16458)
-- Dependencies: 6 169
-- Name: padraometadados_id_seq; Type: SEQUENCE; Schema: public; Owner: feb
--

CREATE SEQUENCE padraometadados_id_seq
    START WITH 100
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.padraometadados_id_seq OWNER TO feb;

--
-- TOC entry 2102 (class 0 OID 0)
-- Dependencies: 170
-- Name: padraometadados_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: feb
--

ALTER SEQUENCE padraometadados_id_seq OWNED BY padraometadados.id;


--
-- TOC entry 171 (class 1259 OID 16460)
-- Dependencies: 6
-- Name: r1idf; Type: TABLE; Schema: public; Owner: feb; Tablespace: 
--

CREATE TABLE r1idf (
    token character varying(255) NOT NULL,
    idf double precision NOT NULL
);


ALTER TABLE public.r1idf OWNER TO feb;

--
-- TOC entry 172 (class 1259 OID 16463)
-- Dependencies: 6
-- Name: r1length; Type: TABLE; Schema: public; Owner: feb; Tablespace: 
--

CREATE TABLE r1length (
    documento_id integer NOT NULL,
    len double precision
);


ALTER TABLE public.r1length OWNER TO feb;

--
-- TOC entry 173 (class 1259 OID 16466)
-- Dependencies: 6
-- Name: r1size; Type: TABLE; Schema: public; Owner: feb; Tablespace: 
--

CREATE TABLE r1size (
    size integer
);


ALTER TABLE public.r1size OWNER TO feb;



--
-- TOC entry 175 (class 1259 OID 16475)
-- Dependencies: 6
-- Name: r1tf; Type: TABLE; Schema: public; Owner: feb; Tablespace: 
--

CREATE TABLE r1tf (
    documento_id integer NOT NULL,
    token text NOT NULL,
    tf integer
);


ALTER TABLE public.r1tf OWNER TO feb;

--
-- TOC entry 176 (class 1259 OID 16481)
-- Dependencies: 6
-- Name: r1tokens; Type: TABLE; Schema: public; Owner: feb; Tablespace: 
--

CREATE TABLE r1tokens (
    documento_id integer NOT NULL,
    token text NOT NULL,
    field integer
);


ALTER TABLE public.r1tokens OWNER TO feb;

--
-- TOC entry 177 (class 1259 OID 16487)
-- Dependencies: 6
-- Name: r1weights; Type: TABLE; Schema: public; Owner: feb; Tablespace: 
--

CREATE TABLE r1weights (
    documento_id integer NOT NULL,
    token text NOT NULL,
    weight double precision
);


ALTER TABLE public.r1weights OWNER TO feb;

--
-- TOC entry 166 (class 1259 OID 16441)
-- Dependencies: 6
-- Name: repositorios_id_seq; Type: SEQUENCE; Schema: public; Owner: feb
--

CREATE SEQUENCE repositorios_id_seq
    START WITH 100
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.repositorios_id_seq OWNER TO feb;

--
-- TOC entry 184 (class 1259 OID 18169)
-- Dependencies: 2046 2047 2048 6
-- Name: repositorios; Type: TABLE; Schema: public; Owner: feb; Tablespace: 
--

CREATE TABLE repositorios (
    id integer DEFAULT nextval('repositorios_id_seq'::regclass) NOT NULL,
    nome character varying(45) NOT NULL,
    descricao character varying(455) DEFAULT NULL::character varying,
    data_ultima_atualizacao timestamp without time zone,
    periodicidade_horas integer,
    url_or_ip character varying(200) NOT NULL,
    padrao_metadados integer,
    mapeamento_id integer DEFAULT 1 NOT NULL,
    metadata_prefix character varying(45),
    name_space character varying(45),
    set character varying(45),
    data_xml character varying
);


ALTER TABLE public.repositorios OWNER TO feb;

--
-- TOC entry 178 (class 1259 OID 16501)
-- Dependencies: 6
-- Name: repositorios_subfed_id_seq; Type: SEQUENCE; Schema: public; Owner: feb
--

CREATE SEQUENCE repositorios_subfed_id_seq
    START WITH 100
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.repositorios_subfed_id_seq OWNER TO feb;

--
-- TOC entry 179 (class 1259 OID 16503)
-- Dependencies: 2043 6
-- Name: repositorios_subfed; Type: TABLE; Schema: public; Owner: feb; Tablespace: 
--

CREATE TABLE repositorios_subfed (
    id integer DEFAULT nextval('repositorios_subfed_id_seq'::regclass) NOT NULL,
    id_subfed integer NOT NULL,
    nome character varying NOT NULL
);


ALTER TABLE public.repositorios_subfed OWNER TO feb;

--
-- TOC entry 180 (class 1259 OID 16514)
-- Dependencies: 6
-- Name: stopwords; Type: TABLE; Schema: public; Owner: feb; Tablespace: 
--

CREATE TABLE stopwords (
    id integer NOT NULL,
    stopword character varying(45) NOT NULL
);


ALTER TABLE public.stopwords OWNER TO feb;

--
-- TOC entry 181 (class 1259 OID 16517)
-- Dependencies: 6 180
-- Name: stopwords_id_seq; Type: SEQUENCE; Schema: public; Owner: feb
--

CREATE SEQUENCE stopwords_id_seq
    START WITH 100
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.stopwords_id_seq OWNER TO feb;

--
-- TOC entry 2103 (class 0 OID 0)
-- Dependencies: 181
-- Name: stopwords_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: feb
--

ALTER SEQUENCE stopwords_id_seq OWNED BY stopwords.id;


--
-- TOC entry 182 (class 1259 OID 16525)
-- Dependencies: 6
-- Name: usuarios; Type: TABLE; Schema: public; Owner: feb; Tablespace: 
--

CREATE TABLE usuarios (
    id integer NOT NULL,
    login character varying(45) NOT NULL,
    senha character varying(45) NOT NULL,
    nome character varying(45) NOT NULL,
    permissions character varying(200),
    role character varying(20)
);


ALTER TABLE public.usuarios OWNER TO feb;


-- Table: visitas

CREATE TABLE visitas (
  id integer NOT NULL,
  horario timestamp without time zone
);

ALTER TABLE visitas
  OWNER TO feb;
COMMENT ON TABLE visitas IS 'contador de visitas do FEB';

-- Sequence: visitas_id_seq

CREATE SEQUENCE visitas_id_seq
    START WITH 100
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE public.visitas_id_seq OWNER TO feb;

--
-- TOC entry 183 (class 1259 OID 16528)
-- Dependencies: 6 182
-- Name: usuarios_id_seq; Type: SEQUENCE; Schema: public; Owner: feb
--

CREATE SEQUENCE usuarios_id_seq
    START WITH 100
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.usuarios_id_seq OWNER TO feb;

--
-- TOC entry 2104 (class 0 OID 0)
-- Dependencies: 183
-- Name: usuarios_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: feb
--

ALTER SEQUENCE usuarios_id_seq OWNED BY usuarios.id;


--
-- TOC entry 2036 (class 2604 OID 16531)
-- Dependencies: 163 162
-- Name: id; Type: DEFAULT; Schema: public; Owner: feb
--

ALTER TABLE ONLY dados_subfederacoes ALTER COLUMN id SET DEFAULT nextval('dados_subfederacoes_id_seq'::regclass);


--
-- TOC entry 2039 (class 2604 OID 16532)
-- Dependencies: 165 164
-- Name: id; Type: DEFAULT; Schema: public; Owner: feb
--

ALTER TABLE ONLY documentos ALTER COLUMN id SET DEFAULT nextval('documentos_id_seq'::regclass);


--
-- TOC entry 2041 (class 2604 OID 16534)
-- Dependencies: 168 167
-- Name: id; Type: DEFAULT; Schema: public; Owner: feb
--

ALTER TABLE ONLY objetos ALTER COLUMN id SET DEFAULT nextval('objetos_id_seq'::regclass);


--
-- TOC entry 2042 (class 2604 OID 16535)
-- Dependencies: 170 169
-- Name: id; Type: DEFAULT; Schema: public; Owner: feb
--

ALTER TABLE ONLY padraometadados ALTER COLUMN id SET DEFAULT nextval('padraometadados_id_seq'::regclass);


--
-- TOC entry 2044 (class 2604 OID 16536)
-- Dependencies: 181 180
-- Name: id; Type: DEFAULT; Schema: public; Owner: feb
--

ALTER TABLE ONLY stopwords ALTER COLUMN id SET DEFAULT nextval('stopwords_id_seq'::regclass);


--
-- TOC entry 2045 (class 2604 OID 16538)
-- Dependencies: 183 182
-- Name: id; Type: DEFAULT; Schema: public; Owner: feb
--

ALTER TABLE ONLY usuarios ALTER COLUMN id SET DEFAULT nextval('usuarios_id_seq'::regclass);


--
-- TOC entry 2052 (class 2606 OID 17985)
-- Dependencies: 161 161
-- Name: consultas_pkey; Type: CONSTRAINT; Schema: public; Owner: feb; Tablespace: 
--

ALTER TABLE ONLY consultas
    ADD CONSTRAINT consultas_pkey PRIMARY KEY (consulta);


--
-- TOC entry 2056 (class 2606 OID 17987)
-- Dependencies: 164 164
-- Name: documentos_pkey; Type: CONSTRAINT; Schema: public; Owner: feb; Tablespace: 
--

ALTER TABLE ONLY documentos
    ADD CONSTRAINT documentos_pkey PRIMARY KEY (id);


--
-- TOC entry 2068 (class 2606 OID 17991)
-- Dependencies: 179 179
-- Name: id; Type: CONSTRAINT; Schema: public; Owner: feb; Tablespace: 
--

ALTER TABLE ONLY repositorios_subfed
    ADD CONSTRAINT id PRIMARY KEY (id);


--
-- TOC entry 2080 (class 2606 OID 18267)
-- Dependencies: 186 186
-- Name: mapeamento_pkey; Type: CONSTRAINT; Schema: public; Owner: feb; Tablespace: 
--

ALTER TABLE ONLY mapeamentos
    ADD CONSTRAINT mapeamento_pkey PRIMARY KEY (id);


--
-- TOC entry 2083 (class 2606 OID 20203)
-- Dependencies: 187 187 187
-- Name: pki_autores; Type: CONSTRAINT; Schema: public; Owner: feb; Tablespace: 
--

ALTER TABLE ONLY autores
    ADD CONSTRAINT pki_autores PRIMARY KEY (id);


--
-- TOC entry 2054 (class 2606 OID 17999)
-- Dependencies: 162 162
-- Name: pki_dadossubf; Type: CONSTRAINT; Schema: public; Owner: feb; Tablespace: 
--

ALTER TABLE ONLY dados_subfederacoes
    ADD CONSTRAINT pki_dadossubf PRIMARY KEY (id);


--
-- TOC entry 2060 (class 2606 OID 18005)
-- Dependencies: 167 167
-- Name: pki_metadados; Type: CONSTRAINT; Schema: public; Owner: feb; Tablespace: 
--

ALTER TABLE ONLY objetos
    ADD CONSTRAINT pki_metadados PRIMARY KEY (id);


--
-- TOC entry 2076 (class 2606 OID 18181)
-- Dependencies: 184 184
-- Name: pki_rep; Type: CONSTRAINT; Schema: public; Owner: feb; Tablespace: 
--

ALTER TABLE ONLY repositorios
    ADD CONSTRAINT pki_rep PRIMARY KEY (id);


--
-- TOC entry 2062 (class 2606 OID 18009)
-- Dependencies: 169 169
-- Name: pki_tipomap; Type: CONSTRAINT; Schema: public; Owner: feb; Tablespace: 
--

ALTER TABLE ONLY padraometadados
    ADD CONSTRAINT pki_tipomap PRIMARY KEY (id);


--
-- TOC entry 2064 (class 2606 OID 18013)
-- Dependencies: 171 171
-- Name: r1idf_pkey; Type: CONSTRAINT; Schema: public; Owner: feb; Tablespace: 
--

ALTER TABLE ONLY r1idf
    ADD CONSTRAINT r1idf_pkey PRIMARY KEY (token);


--
-- TOC entry 2070 (class 2606 OID 18015)
-- Dependencies: 180 180
-- Name: stopwords_pkey; Type: CONSTRAINT; Schema: public; Owner: feb; Tablespace: 
--

ALTER TABLE ONLY stopwords
    ADD CONSTRAINT stopwords_pkey PRIMARY KEY (id);


--
-- TOC entry 2078 (class 2606 OID 20195)
-- Dependencies: 184 184
-- Name: uni_nome; Type: CONSTRAINT; Schema: public; Owner: feb; Tablespace: 
--

ALTER TABLE ONLY repositorios
    ADD CONSTRAINT uni_nome UNIQUE (nome);


--
-- TOC entry 2072 (class 2606 OID 18017)
-- Dependencies: 182 182
-- Name: usuarios_pkey; Type: CONSTRAINT; Schema: public; Owner: feb; Tablespace: 
--

ALTER TABLE ONLY usuarios
    ADD CONSTRAINT usuarios_pkey PRIMARY KEY (id);


-- Constraint: visitas_pki

-- ALTER TABLE visitas DROP CONSTRAINT visitas_pki;

ALTER TABLE visitas
  ADD CONSTRAINT visitas_pki PRIMARY KEY(id);

--
-- TOC entry 2065 (class 1259 OID 18018)
-- Dependencies: 172
-- Name: fki_; Type: INDEX; Schema: public; Owner: feb; Tablespace: 
--

CREATE INDEX fki_ ON r1length USING btree (documento_id);


--
-- TOC entry 2066 (class 1259 OID 18022)
-- Dependencies: 176
-- Name: fki_documentos; Type: INDEX; Schema: public; Owner: feb; Tablespace: 
--

CREATE INDEX fki_documentos ON r1tokens USING btree (documento_id);


--
-- TOC entry 2081 (class 1259 OID 20209)
-- Dependencies: 187
-- Name: fki_fki_documentos; Type: INDEX; Schema: public; Owner: feb; Tablespace: 
--

CREATE INDEX fki_fki_documentos ON autores USING btree (documento);


--
-- TOC entry 2073 (class 1259 OID 18283)
-- Dependencies: 184
-- Name: fki_mapeamento; Type: INDEX; Schema: public; Owner: feb; Tablespace: 
--

CREATE INDEX fki_mapeamento ON repositorios USING btree (mapeamento_id);


--
-- TOC entry 2058 (class 1259 OID 18025)
-- Dependencies: 167
-- Name: fki_obj_doc; Type: INDEX; Schema: public; Owner: feb; Tablespace: 
--

CREATE INDEX fki_obj_doc ON objetos USING btree (documento);


--
-- TOC entry 2074 (class 1259 OID 18211)
-- Dependencies: 184
-- Name: fki_padraometadados; Type: INDEX; Schema: public; Owner: feb; Tablespace: 
--

CREATE INDEX fki_padraometadados ON repositorios USING btree (padrao_metadados);


--
-- TOC entry 2057 (class 1259 OID 18028)
-- Dependencies: 164
-- Name: fki_repositorio; Type: INDEX; Schema: public; Owner: feb; Tablespace: 
--

CREATE INDEX fki_repositorio ON documentos USING btree (id_repositorio);


--
-- TOC entry 2088 (class 2606 OID 18048)
-- Dependencies: 164 176 2055
-- Name: documentos; Type: FK CONSTRAINT; Schema: public; Owner: feb
--

ALTER TABLE ONLY r1tokens
    ADD CONSTRAINT documentos FOREIGN KEY (documento_id) REFERENCES documentos(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2085 (class 2606 OID 18201)
-- Dependencies: 164 2075 184
-- Name: excluidos; Type: FK CONSTRAINT; Schema: public; Owner: feb
--

ALTER TABLE ONLY documentos
    ADD CONSTRAINT excluidos FOREIGN KEY (id_repositorio) REFERENCES repositorios(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2092 (class 2606 OID 20204)
-- Dependencies: 2055 164 187
-- Name: fki_documentos; Type: FK CONSTRAINT; Schema: public; Owner: feb
--

ALTER TABLE ONLY autores
    ADD CONSTRAINT fki_documentos FOREIGN KEY (documento) REFERENCES documentos(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2090 (class 2606 OID 18284)
-- Dependencies: 2079 186 184
-- Name: mapeamento; Type: FK CONSTRAINT; Schema: public; Owner: feb
--

ALTER TABLE ONLY repositorios
    ADD CONSTRAINT mapeamento FOREIGN KEY (mapeamento_id) REFERENCES mapeamentos(id);


--
-- TOC entry 2087 (class 2606 OID 18063)
-- Dependencies: 167 164 2055
-- Name: obj_doc; Type: FK CONSTRAINT; Schema: public; Owner: feb
--

ALTER TABLE ONLY objetos
    ADD CONSTRAINT obj_doc FOREIGN KEY (documento) REFERENCES documentos(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2091 (class 2606 OID 18268)
-- Dependencies: 169 2061 186
-- Name: padraometadados; Type: FK CONSTRAINT; Schema: public; Owner: feb
--

ALTER TABLE ONLY mapeamentos
    ADD CONSTRAINT padraometadados FOREIGN KEY (padrao_id) REFERENCES padraometadados(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2086 (class 2606 OID 18206)
-- Dependencies: 184 2075 164
-- Name: repositorio; Type: FK CONSTRAINT; Schema: public; Owner: feb
--

ALTER TABLE ONLY documentos
    ADD CONSTRAINT repositorio FOREIGN KEY (id_repositorio) REFERENCES repositorios(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2084 (class 2606 OID 18088)
-- Dependencies: 2067 179 164
-- Name: repositorio_subfed; Type: FK CONSTRAINT; Schema: public; Owner: feb
--

ALTER TABLE ONLY documentos
    ADD CONSTRAINT repositorio_subfed FOREIGN KEY (id_rep_subfed) REFERENCES repositorios_subfed(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2089 (class 2606 OID 18098)
-- Dependencies: 2053 162 179
-- Name: subfed; Type: FK CONSTRAINT; Schema: public; Owner: feb
--

ALTER TABLE ONLY repositorios_subfed
    ADD CONSTRAINT subfed FOREIGN KEY (id_subfed) REFERENCES dados_subfederacoes(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2097 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2012-06-19 15:30:52 BRT

--
-- PostgreSQL database dump complete
--

