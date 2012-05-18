--Cria backup das informações da repositório
ALTER TABLE repositorios RENAME TO repositorios_2;

-- dropa as restrições
ALTER TABLE info_repositorios DROP CONSTRAINT padraometa;
ALTER TABLE info_repositorios DROP CONSTRAINT repositorios;
ALTER TABLE info_repositorios DROP CONSTRAINT tipomap;
DROP INDEX IF EXISTS fki_padraometadados;
DROP INDEX IF EXISTS fki_tipomap;


--dropa as tabelas
DROP TABLE IF EXISTS atributos,    
    dados_subfederacoes,
    documentos,
    estatistica,
    excluidos,
    mapeamentocomposto,
    mapeamentos,
    objetos,
    padraometadados,
    r1idf,
    r1length,
    r1size,
    r1sum,
    r1tf,
    r1tokens,
    r1weights,    
    repositorios_subfed,
    stopwords,
    tipomapeamento,
    usuarios CASCADE;

--dropa as sequencias
DROP SEQUENCE excluidos_id_seq;
DROP SEQUENCE repositorios_subfed_id_seq;

--dropa os triggers
DROP FUNCTION IF EXISTS fn_insert_excluidos();
DROP FUNCTION IF EXISTS fn_remove_tipomapeamento();

--dropa a view
DROP VIEW IF EXISTS documentos_e_excluidos;


--RECRIA A NOVA BASE

--
-- PostgreSQL database dump
--

-- Dumped from database version 9.1.3
-- Dumped by pg_dump version 9.1.3
-- Started on 2012-04-10 14:48:09 BRT

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 187 (class 3079 OID 11685)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


SET search_path = public, pg_catalog;
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
-- Dependencies: 2029 2030 6
-- Name: dados_subfederacoes; Type: TABLE; Schema: public; Owner: feb; Tablespace: 
--

CREATE TABLE dados_subfederacoes (
    nome character varying NOT NULL,
    descricao character varying,
    id integer NOT NULL,
    data_ultima_atualizacao timestamp without time zone DEFAULT '0001-01-01 00:00:00'::timestamp without time zone,
    url character varying(200) NOT NULL,
    data_xml character varying
);


ALTER TABLE public.dados_subfederacoes OWNER TO feb;

--
-- TOC entry 163 (class 1259 OID 16407)
-- Dependencies: 6 162
-- Name: dados_subfederacoes_id_seq; Type: SEQUENCE; Schema: public; Owner: feb
--

CREATE SEQUENCE dados_subfederacoes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.dados_subfederacoes_id_seq OWNER TO feb;

--
-- TOC entry 2088 (class 0 OID 0)
-- Dependencies: 163
-- Name: dados_subfederacoes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: feb
--

ALTER SEQUENCE dados_subfederacoes_id_seq OWNED BY dados_subfederacoes.id;


--
-- TOC entry 164 (class 1259 OID 16409)
-- Dependencies: 2032 2033 6
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
    deleted boolean NOT NULL DEFAULT false,
    obaaxml character varying
);


ALTER TABLE public.documentos OWNER TO feb;

--
-- TOC entry 165 (class 1259 OID 16417)
-- Dependencies: 164 6
-- Name: documentos_id_seq; Type: SEQUENCE; Schema: public; Owner: feb
--

CREATE SEQUENCE documentos_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
    CYCLE;


ALTER TABLE public.documentos_id_seq OWNER TO feb;

--
-- TOC entry 2089 (class 0 OID 0)
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
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.mapeamentos_id_seq OWNER TO feb;

--
-- TOC entry 186 (class 1259 OID 18259)
-- Dependencies: 2045 6
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
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.objetos_id_seq OWNER TO feb;

--
-- TOC entry 2090 (class 0 OID 0)
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
    START WITH 4
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.padraometadados_id_seq OWNER TO feb;

--
-- TOC entry 2091 (class 0 OID 0)
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
    tid integer NOT NULL,
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
-- TOC entry 174 (class 1259 OID 16469)
-- Dependencies: 6
-- Name: r1sum; Type: TABLE; Schema: public; Owner: feb; Tablespace: 
--

CREATE TABLE r1sum (
    token text NOT NULL,
    total double precision
);


ALTER TABLE public.r1sum OWNER TO feb;

--
-- TOC entry 175 (class 1259 OID 16475)
-- Dependencies: 6
-- Name: r1tf; Type: TABLE; Schema: public; Owner: feb; Tablespace: 
--

CREATE TABLE r1tf (
    tid integer NOT NULL,
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
    id integer NOT NULL,
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
    tid integer NOT NULL,
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
     START WITH 1
     INCREMENT BY 1
     NO MINVALUE
     NO MAXVALUE
     CACHE 1;


ALTER TABLE public.repositorios_id_seq OWNER TO feb;

--
-- TOC entry 184 (class 1259 OID 18169)
-- Dependencies: 2040 2041 2042 2043 2044 6
-- Name: repositorios; Type: TABLE; Schema: public; Owner: feb; Tablespace: 
--

CREATE TABLE repositorios (
    id integer DEFAULT nextval('repositorios_id_seq'::regclass) NOT NULL,
    nome character varying(45) NOT NULL,
    descricao character varying(455) DEFAULT NULL::character varying,
    data_ultima_atualizacao timestamp without time zone DEFAULT '0001-01-01 00:00:00'::timestamp without time zone,
    periodicidade_horas integer,
    url_or_ip character varying(200) NOT NULL,
    padrao_metadados integer,
    mapeamento_id integer DEFAULT 1 NOT NULL,
    metadata_prefix character varying(45),
    name_space character varying(45),
    set character varying(45),
    data_xml timestamp without time zone DEFAULT '0001-01-01 00:00:00'::timestamp without time zone
);


ALTER TABLE public.repositorios OWNER TO feb;

--
-- TOC entry 178 (class 1259 OID 16501)
-- Dependencies: 6
-- Name: repositorios_subfed_id_seq; Type: SEQUENCE; Schema: public; Owner: feb
--

CREATE SEQUENCE repositorios_subfed_id_seq
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.repositorios_subfed_id_seq OWNER TO feb;

--
-- TOC entry 179 (class 1259 OID 16503)
-- Dependencies: 2037 6
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
    START WITH 180
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.stopwords_id_seq OWNER TO feb;

--
-- TOC entry 2092 (class 0 OID 0)
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
    nome character varying(45) NOT NULL
);


ALTER TABLE public.usuarios OWNER TO feb;

--
-- TOC entry 183 (class 1259 OID 16528)
-- Dependencies: 182 6
-- Name: usuarios_id_seq; Type: SEQUENCE; Schema: public; Owner: feb
--

CREATE SEQUENCE usuarios_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.usuarios_id_seq OWNER TO feb;

--
-- TOC entry 2093 (class 0 OID 0)
-- Dependencies: 183
-- Name: usuarios_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: feb
--

ALTER SEQUENCE usuarios_id_seq OWNED BY usuarios.id;


--
-- TOC entry 2031 (class 2604 OID 16531)
-- Dependencies: 163 162
-- Name: id; Type: DEFAULT; Schema: public; Owner: feb
--

ALTER TABLE ONLY dados_subfederacoes ALTER COLUMN id SET DEFAULT nextval('dados_subfederacoes_id_seq'::regclass);


--
-- TOC entry 2034 (class 2604 OID 16532)
-- Dependencies: 165 164
-- Name: id; Type: DEFAULT; Schema: public; Owner: feb
--

ALTER TABLE ONLY documentos ALTER COLUMN id SET DEFAULT nextval('documentos_id_seq'::regclass);


--
-- TOC entry 2035 (class 2604 OID 16534)
-- Dependencies: 168 167
-- Name: id; Type: DEFAULT; Schema: public; Owner: feb
--

ALTER TABLE ONLY objetos ALTER COLUMN id SET DEFAULT nextval('objetos_id_seq'::regclass);


--
-- TOC entry 2036 (class 2604 OID 16535)
-- Dependencies: 170 169
-- Name: id; Type: DEFAULT; Schema: public; Owner: feb
--

ALTER TABLE ONLY padraometadados ALTER COLUMN id SET DEFAULT nextval('padraometadados_id_seq'::regclass);


--
-- TOC entry 2038 (class 2604 OID 16536)
-- Dependencies: 181 180
-- Name: id; Type: DEFAULT; Schema: public; Owner: feb
--

ALTER TABLE ONLY stopwords ALTER COLUMN id SET DEFAULT nextval('stopwords_id_seq'::regclass);


--
-- TOC entry 2039 (class 2604 OID 16538)
-- Dependencies: 183 182
-- Name: id; Type: DEFAULT; Schema: public; Owner: feb
--

ALTER TABLE ONLY usuarios ALTER COLUMN id SET DEFAULT nextval('usuarios_id_seq'::regclass);


--
-- TOC entry 2047 (class 2606 OID 17985)
-- Dependencies: 161 161
-- Name: consultas_pkey; Type: CONSTRAINT; Schema: public; Owner: feb; Tablespace: 
--

ALTER TABLE ONLY consultas
    ADD CONSTRAINT consultas_pkey PRIMARY KEY (consulta);


--
-- TOC entry 2051 (class 2606 OID 17987)
-- Dependencies: 164 164
-- Name: documentos_pkey; Type: CONSTRAINT; Schema: public; Owner: feb; Tablespace: 
--

ALTER TABLE ONLY documentos
    ADD CONSTRAINT documentos_pkey PRIMARY KEY (id);


--
-- TOC entry 2063 (class 2606 OID 17991)
-- Dependencies: 179 179
-- Name: id; Type: CONSTRAINT; Schema: public; Owner: feb; Tablespace: 
--

ALTER TABLE ONLY repositorios_subfed
    ADD CONSTRAINT id PRIMARY KEY (id);


--
-- TOC entry 2073 (class 2606 OID 18267)
-- Dependencies: 186 186
-- Name: mapeamento_pkey; Type: CONSTRAINT; Schema: public; Owner: feb; Tablespace: 
--

ALTER TABLE ONLY mapeamentos
    ADD CONSTRAINT mapeamento_pkey PRIMARY KEY (id);


--
-- TOC entry 2049 (class 2606 OID 17999)
-- Dependencies: 162 162
-- Name: pki_dadossubf; Type: CONSTRAINT; Schema: public; Owner: feb; Tablespace: 
--

ALTER TABLE ONLY dados_subfederacoes
    ADD CONSTRAINT pki_dadossubf PRIMARY KEY (id);


--
-- TOC entry 2055 (class 2606 OID 18005)
-- Dependencies: 167 167
-- Name: pki_metadados; Type: CONSTRAINT; Schema: public; Owner: feb; Tablespace: 
--

ALTER TABLE ONLY objetos
    ADD CONSTRAINT pki_metadados PRIMARY KEY (id);


--
-- TOC entry 2071 (class 2606 OID 18181)
-- Dependencies: 184 184
-- Name: pki_rep; Type: CONSTRAINT; Schema: public; Owner: feb; Tablespace: 
--

ALTER TABLE ONLY repositorios
    ADD CONSTRAINT pki_reps PRIMARY KEY (id);


--
-- TOC entry 2057 (class 2606 OID 18009)
-- Dependencies: 169 169
-- Name: pki_tipomap; Type: CONSTRAINT; Schema: public; Owner: feb; Tablespace: 
--

ALTER TABLE ONLY padraometadados
    ADD CONSTRAINT pki_tipomap PRIMARY KEY (id);


--
-- TOC entry 2059 (class 2606 OID 18013)
-- Dependencies: 171 171
-- Name: r1idf_pkey; Type: CONSTRAINT; Schema: public; Owner: feb; Tablespace: 
--

ALTER TABLE ONLY r1idf
    ADD CONSTRAINT r1idf_pkey PRIMARY KEY (token);


--
-- TOC entry 2065 (class 2606 OID 18015)
-- Dependencies: 180 180
-- Name: stopwords_pkey; Type: CONSTRAINT; Schema: public; Owner: feb; Tablespace: 
--

ALTER TABLE ONLY stopwords
    ADD CONSTRAINT stopwords_pkey PRIMARY KEY (id);


--
-- TOC entry 2067 (class 2606 OID 18017)
-- Dependencies: 182 182
-- Name: usuarios_pkey; Type: CONSTRAINT; Schema: public; Owner: feb; Tablespace: 
--

ALTER TABLE ONLY usuarios
    ADD CONSTRAINT usuarios_pkey PRIMARY KEY (id);


--
-- TOC entry 2060 (class 1259 OID 18018)
-- Dependencies: 172
-- Name: fki_; Type: INDEX; Schema: public; Owner: feb; Tablespace: 
--

CREATE INDEX fki_ ON r1length USING btree (tid);


--
-- TOC entry 2061 (class 1259 OID 18022)
-- Dependencies: 176
-- Name: fki_documentos; Type: INDEX; Schema: public; Owner: feb; Tablespace: 
--

CREATE INDEX fki_documentos ON r1tokens USING btree (id);


--
-- TOC entry 2068 (class 1259 OID 18283)
-- Dependencies: 184
-- Name: fki_mapeamento; Type: INDEX; Schema: public; Owner: feb; Tablespace: 
--

CREATE INDEX fki_mapeamento ON repositorios USING btree (mapeamento_id);


--
-- TOC entry 2053 (class 1259 OID 18025)
-- Dependencies: 167
-- Name: fki_obj_doc; Type: INDEX; Schema: public; Owner: feb; Tablespace: 
--

CREATE INDEX fki_obj_doc ON objetos USING btree (documento);


--
-- TOC entry 2069 (class 1259 OID 18211)
-- Dependencies: 184
-- Name: fki_padraometadados; Type: INDEX; Schema: public; Owner: feb; Tablespace: 
--

CREATE INDEX fki_padraometadados ON repositorios USING btree (padrao_metadados);


--
-- TOC entry 2052 (class 1259 OID 18028)
-- Dependencies: 164
-- Name: fki_repositorio; Type: INDEX; Schema: public; Owner: feb; Tablespace: 
--

CREATE INDEX fki_repositorio ON documentos USING btree (id_repositorio);


--
-- TOC entry 2078 (class 2606 OID 18048)
-- Dependencies: 176 164 2050
-- Name: documentos; Type: FK CONSTRAINT; Schema: public; Owner: feb
--

ALTER TABLE ONLY r1tokens
    ADD CONSTRAINT documentos FOREIGN KEY (id) REFERENCES documentos(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2075 (class 2606 OID 18201)
-- Dependencies: 164 2070 184
-- Name: excluidos; Type: FK CONSTRAINT; Schema: public; Owner: feb
--

ALTER TABLE ONLY documentos
    ADD CONSTRAINT excluidos FOREIGN KEY (id_repositorio) REFERENCES repositorios(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2080 (class 2606 OID 18284)
-- Dependencies: 184 2072 186
-- Name: mapeamento; Type: FK CONSTRAINT; Schema: public; Owner: feb
--

ALTER TABLE ONLY repositorios
    ADD CONSTRAINT mapeamento FOREIGN KEY (mapeamento_id) REFERENCES mapeamentos(id);


--
-- TOC entry 2077 (class 2606 OID 18063)
-- Dependencies: 2050 167 164
-- Name: obj_doc; Type: FK CONSTRAINT; Schema: public; Owner: feb
--

ALTER TABLE ONLY objetos
    ADD CONSTRAINT obj_doc FOREIGN KEY (documento) REFERENCES documentos(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2081 (class 2606 OID 18268)
-- Dependencies: 2056 169 186
-- Name: padraometadados; Type: FK CONSTRAINT; Schema: public; Owner: feb
--

ALTER TABLE ONLY mapeamentos
    ADD CONSTRAINT padraometadados FOREIGN KEY (padrao_id) REFERENCES padraometadados(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2076 (class 2606 OID 18206)
-- Dependencies: 164 2070 184
-- Name: repositorio; Type: FK CONSTRAINT; Schema: public; Owner: feb
--

ALTER TABLE ONLY documentos
    ADD CONSTRAINT repositorio FOREIGN KEY (id_repositorio) REFERENCES repositorios(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2074 (class 2606 OID 18088)
-- Dependencies: 164 179 2062
-- Name: repositorio_subfed; Type: FK CONSTRAINT; Schema: public; Owner: feb
--

ALTER TABLE ONLY documentos
    ADD CONSTRAINT repositorio_subfed FOREIGN KEY (id_rep_subfed) REFERENCES repositorios_subfed(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2079 (class 2606 OID 18098)
-- Dependencies: 2048 162 179
-- Name: subfed; Type: FK CONSTRAINT; Schema: public; Owner: feb
--

ALTER TABLE ONLY repositorios_subfed
    ADD CONSTRAINT subfed FOREIGN KEY (id_subfed) REFERENCES dados_subfederacoes(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2086 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2012-04-10 14:48:09 BRT

--
-- PostgreSQL database dump complete
--


--insere os valores nas tabelas

INSERT INTO repositorios (id, nome, descricao, periodicidade_horas, url_or_ip,
 padrao_metadados, mapeamento_id, metadata_prefix, name_space, set)

	SELECT id_repositorio, nome, descricao, periodicidade_horas, url_or_ip, 
	padrao_metadados, tipo_mapeamento_id, metadata_prefix, name_space, set
	FROM info_repositorios, repositorios_2
;

--Dropa as tabelas
DROP TABLE info_repositorios;
DROP TABLE repositorios_2;


--INSERTS PADRAO

--
-- TOC entry 2018 (class 0 OID 0)
-- Dependencies: 170
-- Name: padraometadados_id_seq; Type: SEQUENCE SET; Schema: public; Owner: feb
--

SELECT pg_catalog.setval('padraometadados_id_seq', 3, true);
--
-- TOC entry 2015 (class 0 OID 16455)
-- Dependencies: 169
-- Data for Name: padraometadados; Type: TABLE DATA; Schema: public; Owner: feb
--

INSERT INTO padraometadados (id, nome, metadata_prefix, name_space, atributos) VALUES (2, 'lom', 'oai_lom', 'lom', 'Identifier;Catalog;Entry;Title;Language;Description;Keyword;Coverage;Structure;AggregationLevel;Version;Status;Role;Entity;Date;MetaMetadataCatalog;MetaMetadataEntry;MetaMetadataRole;MetaMetadataEntity;MetaMetadataDate;MetadataSchema;MetaMetadataLanguage;Format;Size;Location;Type;Name;MinimumVersion;MaximumVersion ;InstallationRemarks;OtherPlatformRequirements;Duration;InteractivityType;LearningResourceType;InteractivityLevel;SemanticDensity;IntendedEndUserRole;Context;TypicalAgeRange;Difficulty;TypicalLearningTime;EducationalDescription;EducationalLanguage;Cost;CopyrightAndOtherRestrictions;RightsDescription;Kind;ResourceCatalog;ResourceEntry;ResourceDescription;AnnotationEntity;AnnotationDate;AnnotationDescription;Purpose;Source;TaxonId;TaxonEntry;ClassificationDescription;ClassificationKeyword');
INSERT INTO padraometadados (id, nome, metadata_prefix, name_space, atributos) VALUES (3, 'obaa', 'oai_obaa', 'obaa', 'Entry;Identifier;Catalog;Title;Language;Description;Keyword;Coverage;Structure;AggregationLevel;Version;Status;Role;Entity;Date;MetaMetadataEntry;MetaMetadataRole;MetaMetadataEntity;MetaMetadataDate;MetadataSchema;MetaMetadataLanguage;Format;Size;Location;Type;Name;MinimumVersion;MaximumVersion ;InstallationRemarks;OtherPlatformRequirements;Duration;SupportedPlatforms;PlatformType;SpecificFormat;SpecificSize;SpecificLocation;SpecificType;SpecificName;SpecificMinimumVersion;SpecificMaximumVersion;SpecificInstallationRemarks;SpecificOtherPlatformRequirements;ServiceName;ServiceType;Provides;Essential;Protocol;OntologyLanguage;OntologyLocation;ServiceLanguage;ServiceLocation;InteractivityType;LearningResourceType;InteractivityLevel;SemanticDensity;IntendedEndUserRole;Context;TypicalAgeRange;Difficulty;TypicalLearningTime;EducationalDescription;EducationalLanguage;LearningContentType;Perception;Synchronism;CoPresence;Reciprocity;DidacticStrategy;Cost;CopyrightAndOtherRestrictions;RightsDescription;Kind;ResourceCatalog;ResourceEntry;ResourceDescription;AnnotationEntity;AnnotationDate;AnnotationDescription;Purpose;Source;TaxonId;TaxonEntry;ClassificationDescription;ClassificationKeyword;HasVisual;HasAuditory;HasText;HasTactile;DisplayTransformability;ControlFlexibility;EquivalentResource;IsSuplementary;AudioDescription;AudioDescriptionLanguage;AltTextLang;LongDescriptionLang;ColorAvoidance;GraphicAlternative;SignLanguage;CaptionTypeLanguage;CaptionRate;SegmentInformation;SegmentInformationIdentifier;SegmentInformationTitle;SegmentInformationDescription;SegmentInformationKeyword;SegmentMediaType;Start;End;SegmentGroupInformationIdentifier;GroupType;SegmentGroupInformationTitle;SegmentGroupInformationDescription;SegmentGroupInformationKeyword;SegmentsIdentifier');
INSERT INTO padraometadados (id, nome, metadata_prefix, name_space, atributos) VALUES (1, 'Dublin Core', 'oai_dc', 'dc', 'Title;Language;Description;Subject;Coverage;Type;OtherContributor;Publisher;Format;Rights;Relation;Source;Date;Creator;Identifier');




--
-- Data for Name: stopwords; Type: TABLE DATA; Schema: public; Owner: feb
--

INSERT INTO stopwords VALUES (1, 'a');
INSERT INTO stopwords VALUES (2, 'à');
INSERT INTO stopwords VALUES (3, 'agora');
INSERT INTO stopwords VALUES (4, 'ainda');
INSERT INTO stopwords VALUES (5, 'além');
INSERT INTO stopwords VALUES (6, 'alguns');
INSERT INTO stopwords VALUES (7, 'ano');
INSERT INTO stopwords VALUES (8, 'anos');
INSERT INTO stopwords VALUES (9, 'antes');
INSERT INTO stopwords VALUES (10, 'ao');
INSERT INTO stopwords VALUES (11, 'aos');
INSERT INTO stopwords VALUES (12, 'apenas');
INSERT INTO stopwords VALUES (13, 'após');
INSERT INTO stopwords VALUES (14, 'aqui');
INSERT INTO stopwords VALUES (15, 'as');
INSERT INTO stopwords VALUES (16, 'às');
INSERT INTO stopwords VALUES (17, 'assim');
INSERT INTO stopwords VALUES (18, 'até');
INSERT INTO stopwords VALUES (19, 'bem');
INSERT INTO stopwords VALUES (20, 'bom');
INSERT INTO stopwords VALUES (21, 'cada');
INSERT INTO stopwords VALUES (22, 'caso');
INSERT INTO stopwords VALUES (23, 'coisa');
INSERT INTO stopwords VALUES (24, 'com');
INSERT INTO stopwords VALUES (25, 'como');
INSERT INTO stopwords VALUES (26, 'conta');
INSERT INTO stopwords VALUES (27, 'contra');
INSERT INTO stopwords VALUES (28, 'da');
INSERT INTO stopwords VALUES (29, 'dar');
INSERT INTO stopwords VALUES (30, 'das');
INSERT INTO stopwords VALUES (31, 'de');
INSERT INTO stopwords VALUES (32, 'depois');
INSERT INTO stopwords VALUES (33, 'desde');
INSERT INTO stopwords VALUES (34, 'deve');
INSERT INTO stopwords VALUES (35, 'dia');
INSERT INTO stopwords VALUES (36, 'dias');
INSERT INTO stopwords VALUES (37, 'disse');
INSERT INTO stopwords VALUES (38, 'diz');
INSERT INTO stopwords VALUES (39, 'do');
INSERT INTO stopwords VALUES (40, 'dois');
INSERT INTO stopwords VALUES (41, 'dos');
INSERT INTO stopwords VALUES (42, 'duas');
INSERT INTO stopwords VALUES (43, 'durante');
INSERT INTO stopwords VALUES (44, 'e');
INSERT INTO stopwords VALUES (45, 'é');
INSERT INTO stopwords VALUES (46, 'ela');
INSERT INTO stopwords VALUES (47, 'ele');
INSERT INTO stopwords VALUES (48, 'eles');
INSERT INTO stopwords VALUES (49, 'em');
INSERT INTO stopwords VALUES (50, 'enquanto');
INSERT INTO stopwords VALUES (51, 'então');
INSERT INTO stopwords VALUES (52, 'entre');
INSERT INTO stopwords VALUES (53, 'era');
INSERT INTO stopwords VALUES (54, 'essa');
INSERT INTO stopwords VALUES (55, 'esse');
INSERT INTO stopwords VALUES (56, 'esta');
INSERT INTO stopwords VALUES (57, 'está');
INSERT INTO stopwords VALUES (58, 'estão');
INSERT INTO stopwords VALUES (59, 'estava');
INSERT INTO stopwords VALUES (60, 'este');
INSERT INTO stopwords VALUES (61, 'eu');
INSERT INTO stopwords VALUES (62, 'eua');
INSERT INTO stopwords VALUES (63, 'fato');
INSERT INTO stopwords VALUES (64, 'faz');
INSERT INTO stopwords VALUES (65, 'fazer');
INSERT INTO stopwords VALUES (66, 'fez');
INSERT INTO stopwords VALUES (67, 'ficou');
INSERT INTO stopwords VALUES (68, 'foi');
INSERT INTO stopwords VALUES (69, 'fora');
INSERT INTO stopwords VALUES (70, 'foram');
INSERT INTO stopwords VALUES (71, 'forma');
INSERT INTO stopwords VALUES (72, 'há');
INSERT INTO stopwords VALUES (73, 'havia');
INSERT INTO stopwords VALUES (74, 'hoje');
INSERT INTO stopwords VALUES (75, 'isso');
INSERT INTO stopwords VALUES (76, 'já');
INSERT INTO stopwords VALUES (77, 'lado');
INSERT INTO stopwords VALUES (78, 'maior');
INSERT INTO stopwords VALUES (79, 'mais');
INSERT INTO stopwords VALUES (80, 'mas');
INSERT INTO stopwords VALUES (81, 'me');
INSERT INTO stopwords VALUES (82, 'meio');
INSERT INTO stopwords VALUES (83, 'melhor');
INSERT INTO stopwords VALUES (84, 'menos');
INSERT INTO stopwords VALUES (85, 'meses');
INSERT INTO stopwords VALUES (86, 'mesma');
INSERT INTO stopwords VALUES (87, 'mesmo');
INSERT INTO stopwords VALUES (88, 'meu');
INSERT INTO stopwords VALUES (89, 'minha');
INSERT INTO stopwords VALUES (90, 'muito');
INSERT INTO stopwords VALUES (91, 'na');
INSERT INTO stopwords VALUES (92, 'nada');
INSERT INTO stopwords VALUES (93, 'não');
INSERT INTO stopwords VALUES (94, 'nas');
INSERT INTO stopwords VALUES (95, 'nem');
INSERT INTO stopwords VALUES (96, 'neste');
INSERT INTO stopwords VALUES (97, 'no');
INSERT INTO stopwords VALUES (98, 'nome');
INSERT INTO stopwords VALUES (99, 'nos');
INSERT INTO stopwords VALUES (100, 'nós');
INSERT INTO stopwords VALUES (101, 'nova');
INSERT INTO stopwords VALUES (102, 'novo');
INSERT INTO stopwords VALUES (103, 'num');
INSERT INTO stopwords VALUES (104, 'numa');
INSERT INTO stopwords VALUES (105, 'o');
INSERT INTO stopwords VALUES (106, 'onde');
INSERT INTO stopwords VALUES (107, 'ontem');
INSERT INTO stopwords VALUES (108, 'os');
INSERT INTO stopwords VALUES (109, 'ou');
INSERT INTO stopwords VALUES (110, 'outra');
INSERT INTO stopwords VALUES (111, 'outras');
INSERT INTO stopwords VALUES (112, 'outro');
INSERT INTO stopwords VALUES (113, 'outros');
INSERT INTO stopwords VALUES (114, 'para');
INSERT INTO stopwords VALUES (115, 'parte');
INSERT INTO stopwords VALUES (116, 'partir');
INSERT INTO stopwords VALUES (117, 'pela');
INSERT INTO stopwords VALUES (118, 'pelo');
INSERT INTO stopwords VALUES (119, 'pelos');
INSERT INTO stopwords VALUES (120, 'pode');
INSERT INTO stopwords VALUES (121, 'podem');
INSERT INTO stopwords VALUES (122, 'poder');
INSERT INTO stopwords VALUES (123, 'pontos');
INSERT INTO stopwords VALUES (124, 'por');
INSERT INTO stopwords VALUES (125, 'porque');
INSERT INTO stopwords VALUES (126, 'pouco');
INSERT INTO stopwords VALUES (127, 'qual');
INSERT INTO stopwords VALUES (128, 'qualquer');
INSERT INTO stopwords VALUES (129, 'quando');
INSERT INTO stopwords VALUES (130, 'quanto');
INSERT INTO stopwords VALUES (131, 'quase');
INSERT INTO stopwords VALUES (132, 'quatro');
INSERT INTO stopwords VALUES (133, 'que');
INSERT INTO stopwords VALUES (134, 'quem');
INSERT INTO stopwords VALUES (135, 'quer');
INSERT INTO stopwords VALUES (136, 'r');
INSERT INTO stopwords VALUES (137, 'são');
INSERT INTO stopwords VALUES (138, 'se');
INSERT INTO stopwords VALUES (139, 'segundo');
INSERT INTO stopwords VALUES (140, 'seja');
INSERT INTO stopwords VALUES (141, 'sem');
INSERT INTO stopwords VALUES (142, 'sempre');
INSERT INTO stopwords VALUES (143, 'sendo');
INSERT INTO stopwords VALUES (144, 'ser');
INSERT INTO stopwords VALUES (145, 'será');
INSERT INTO stopwords VALUES (146, 'serão');
INSERT INTO stopwords VALUES (147, 'seria');
INSERT INTO stopwords VALUES (148, 'setor');
INSERT INTO stopwords VALUES (149, 'seu');
INSERT INTO stopwords VALUES (150, 'seus');
INSERT INTO stopwords VALUES (151, 'sido');
INSERT INTO stopwords VALUES (152, 'só');
INSERT INTO stopwords VALUES (153, 'sobre');
INSERT INTO stopwords VALUES (154, 'sua');
INSERT INTO stopwords VALUES (155, 'suas');
INSERT INTO stopwords VALUES (156, 'sul');
INSERT INTO stopwords VALUES (157, 'também');
INSERT INTO stopwords VALUES (158, 'tão');
INSERT INTO stopwords VALUES (159, 'tel');
INSERT INTO stopwords VALUES (160, 'tem');
INSERT INTO stopwords VALUES (161, 'têm');
INSERT INTO stopwords VALUES (162, 'ter');
INSERT INTO stopwords VALUES (163, 'teve');
INSERT INTO stopwords VALUES (164, 'tinha');
INSERT INTO stopwords VALUES (165, 'toda');
INSERT INTO stopwords VALUES (166, 'todas');
INSERT INTO stopwords VALUES (167, 'todo');
INSERT INTO stopwords VALUES (168, 'todos');
INSERT INTO stopwords VALUES (169, 'três');
INSERT INTO stopwords VALUES (170, 'tudo');
INSERT INTO stopwords VALUES (171, 'um');
INSERT INTO stopwords VALUES (172, 'uma');
INSERT INTO stopwords VALUES (173, 'us');
INSERT INTO stopwords VALUES (174, 'vai');
INSERT INTO stopwords VALUES (175, 'vão');
INSERT INTO stopwords VALUES (176, 'vem');
INSERT INTO stopwords VALUES (177, 'vez');
INSERT INTO stopwords VALUES (178, 'vezes');
INSERT INTO stopwords VALUES (179, 'você');



--
-- Data for Name: usuarios; Type: TABLE DATA; Schema: public; Owner: feb
--

INSERT INTO usuarios (login,senha,nome) VALUES ('admin', '698dc19d489c4e4db73e28a713eab07b', 'Administrador da federação');

INSERT INTO mapeamentos VALUES (2, 'DC Padrão', 'Mapeamento Dublin Core padrão',
'<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
xmlns:dc="http://purl.org/dc/elements/1.1/"
xmlns:obaa="http://ltsc.ieee.org/xsd/LOM http://ltsc.ieee.org/xsd/lomv1.0/lom.xsd"
xmlns:oai_dc="http://www.openarchives.org/OAI/2.0/oai_dc/"
xmlns:oai_pmh="http://www.openarchives.org/OAI/2.0/"
exclude-result-prefixes="dc"
xpath-default-namespace="http://www.openarchives.org/OAI/2.0/">

<xsl:output method="xml" encoding="UTF-8"/>
<xsl:template match="/">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="OAI-PMH">
	<xsl:copy>
		<xsl:apply-templates/>
	</xsl:copy>
</xsl:template>

<xsl:template match="ListRecords">
	<xsl:copy>
		<xsl:apply-templates/>
	</xsl:copy>
</xsl:template>

<xsl:template match="responseDate">
	<xsl:copy-of select="."/>
</xsl:template>

<xsl:template match="request">
	<xsl:copy-of select="."/>
</xsl:template>

<xsl:template match="record">
	<xsl:copy>
		<xsl:apply-templates/>
	</xsl:copy>
</xsl:template>

<xsl:template match="header">
	<xsl:copy-of select="."/>
</xsl:template>

<xsl:template match="metadata">
	<xsl:copy>
		<xsl:apply-templates/>
	</xsl:copy>
</xsl:template>

<xsl:template match="oai_dc:dc">
<obaa:obaa  xsi:schemaLocation="http://ltsc.ieee.org/xsd/LOM http://ltsc.ieee.org/xsd/obaav1.0/obaa.xsd">
	<obaa:general>
	
	<xsl:for-each select="dc:title">
		<obaa:title>
			<xsl:value-of select="."/>
		</obaa:title>
	</xsl:for-each>

		<obaa:identifier>
			<obaa:catalog>NULL</obaa:catalog>
			<obaa:entry><xsl:value-of select="./dc:identifier[1]"/></obaa:entry>
		</obaa:identifier>

	<xsl:for-each select="dc:description">
		<obaa:description>
			<xsl:value-of select="."/>
		</obaa:description>
	</xsl:for-each>

	<xsl:for-each select="dc:coverage">
		<obaa:coverage>
			<xsl:value-of select="."/>
		</obaa:coverage>
	</xsl:for-each>

	<xsl:for-each select="dc:subject">
		<obaa:keyword>
			<xsl:value-of select="."/>
		</obaa:keyword>
	</xsl:for-each>
	
	</obaa:general>	

	<obaa:lifeCycle>
		<xsl:for-each select="dc:creator">
			<obaa:contribute>
				<obaa:role>author</obaa:role>
				<obaa:entity><xsl:value-of select="."/></obaa:entity>
				<obaa:date><xsl:value-of select="../dc:date[1]"/></obaa:date>
			</obaa:contribute>
		</xsl:for-each>
		<xsl:for-each select="dc:contributor">
			<obaa:contribute>
				<obaa:role>unknown</obaa:role>
				<obaa:entity><xsl:value-of select="."/></obaa:entity>
			</obaa:contribute>
		</xsl:for-each>
		<xsl:for-each select="dc:publisher">
			<obaa:contribute>
				<obaa:role>publisher</obaa:role>
				<obaa:entity><xsl:value-of select="."/></obaa:entity>
			</obaa:contribute>
		</xsl:for-each>
	
	</obaa:lifeCycle>
	
	<obaa:technical>
		<xsl:for-each select="dc:identifier">
			<obaa:location><xsl:value-of select="."/></obaa:location>
		</xsl:for-each>
	</obaa:technical>

	<obaa:rights>
		<obaa:description><xsl:value-of select="dc:right"/></obaa:description>
	</obaa:rights>
</obaa:obaa>
</xsl:template>

</xsl:stylesheet>
'
, 2);

INSERT INTO mapeamentos VALUES (1, 'LOM padrão', 'Mapeamento LOM padrão',
'<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
xmlns:dc="http://purl.org/dc/elements/1.1/"
xmlns:lom="http://ltsc.ieee.org/xsd/LOM"
xmlns:obaa="http://ltsc.ieee.org/xsd/LOM"
xmlns:oai_dc="http://www.openarchives.org/OAI/2.0/oai_dc/"
xmlns:oai_pmh="http://www.openarchives.org/OAI/2.0/"
exclude-result-prefixes="dc"
xpath-default-namespace="http://www.openarchives.org/OAI/2.0/">

<xsl:output method="xml" encoding="UTF-8"/>
<xsl:template match="/">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="OAI-PMH">
	<xsl:copy>
		<xsl:apply-templates/>
	</xsl:copy>
</xsl:template>

<xsl:template match="ListRecords">
	<xsl:copy>
		<xsl:apply-templates/>
	</xsl:copy>
</xsl:template>

<xsl:template match="responseDate">
	<xsl:copy-of select="."/>
</xsl:template>

<xsl:template match="request">
	<xsl:copy-of select="."/>
</xsl:template>

<xsl:template match="record">
	<xsl:copy>
		<xsl:apply-templates/>
	</xsl:copy>
</xsl:template>

<xsl:template match="header">
	<xsl:copy-of select="."/>
</xsl:template>

<xsl:template match="metadata">
	<xsl:copy>
		<xsl:apply-templates/>
	</xsl:copy>
</xsl:template>

<xsl:template match="lom:lom">
<obaa:obaa xmlns:obaa="http://ltsc.ieee.org/xsd/LOM" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://ltsc.ieee.org/xsd/LOM http://ltsc.ieee.org/xsd/obaav1.0/obaa.xsd">
	<xsl:apply-templates/>
</obaa:obaa>

</xsl:template>

<xsl:template match="@*|node()">
  <xsl:copy>
    <xsl:apply-templates select="@*|node()"/>
  </xsl:copy>
</xsl:template>

</xsl:stylesheet>
', 1);


ALTER TABLE dados_subfederacoes DROP COLUMN data_xml;
ALTER TABLE dados_subfederacoes ADD COLUMN data_xml character varying;
ALTER TABLE repositorios DROP COLUMN data_xml;
ALTER TABLE repositorios ADD COLUMN data_xml character varying;

--- 26/04/12
ALTER TABLE dados_subfederacoes
   ALTER COLUMN data_ultima_atualizacao DROP DEFAULT;
ALTER TABLE repositorios
   ALTER COLUMN data_ultima_atualizacao DROP DEFAULT;
ALTER TABLE repositorios
   ALTER COLUMN data_xml DROP DEFAULT;

ALTER TABLE dados_subfederacoes
   ALTER COLUMN data_ultima_atualizacao DROP DEFAULT;


--- 9/5/12
ALTER TABLE usuarios ADD COLUMN permissions VARCHAR(200);
ALTER TABLE usuarios ADD COLUMN role VARCHAR(20);


--- 10/05/12
ALTER TABLE repositorios ADD CONSTRAINT uni_nome UNIQUE (nome);

---13/05/12
UPDATE usuarios SET permissions = 'PERM_MANAGE_USERS,PERM_UPDATE,PERM_MANAGE_REP,PERM_MANAGE_METADATA,PERM_MANAGE_MAPPINGS,PERM_CHANGE_DATABASE,PERM_VIEW_STATISTICS' WHERE login = 'admin';
UPDATE usuarios SET role = 'Administrador' WHERE login = 'admin';


--- 17/05/2012
ALTER TABLE autores ADD CONSTRAINT fki_documentos FOREIGN KEY (documento) REFERENCES documentos (id) ON UPDATE CASCADE ON DELETE CASCADE;
