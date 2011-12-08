--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- Name: federacao; Type: DATABASE; Schema: -; Owner: feb
--

CREATE DATABASE federacao WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'pt_BR.utf8' LC_CTYPE = 'pt_BR.utf8';


ALTER DATABASE federacao OWNER TO feb;

\connect federacao

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: -; Owner: postgres
--

CREATE PROCEDURAL LANGUAGE plpgsql;


ALTER PROCEDURAL LANGUAGE plpgsql OWNER TO postgres;

SET search_path = public, pg_catalog;

--
-- Name: block(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION block(text, text) RETURNS real
    LANGUAGE c IMMUTABLE STRICT
    AS '$libdir/pg_similarity', 'block';


ALTER FUNCTION public.block(text, text) OWNER TO postgres;

--
-- Name: block_op(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION block_op(text, text) RETURNS boolean
    LANGUAGE c STABLE STRICT
    AS '$libdir/pg_similarity', 'block_op';


ALTER FUNCTION public.block_op(text, text) OWNER TO postgres;

--
-- Name: cosine(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION cosine(text, text) RETURNS real
    LANGUAGE c IMMUTABLE STRICT
    AS '$libdir/pg_similarity', 'cosine';


ALTER FUNCTION public.cosine(text, text) OWNER TO postgres;

--
-- Name: cosine_op(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION cosine_op(text, text) RETURNS boolean
    LANGUAGE c STABLE STRICT
    AS '$libdir/pg_similarity', 'cosine_op';


ALTER FUNCTION public.cosine_op(text, text) OWNER TO postgres;

--
-- Name: dice(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dice(text, text) RETURNS real
    LANGUAGE c IMMUTABLE STRICT
    AS '$libdir/pg_similarity', 'dice';


ALTER FUNCTION public.dice(text, text) OWNER TO postgres;

--
-- Name: dice_op(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dice_op(text, text) RETURNS boolean
    LANGUAGE c STABLE STRICT
    AS '$libdir/pg_similarity', 'dice_op';


ALTER FUNCTION public.dice_op(text, text) OWNER TO postgres;

--
-- Name: euclidean(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION euclidean(text, text) RETURNS real
    LANGUAGE c IMMUTABLE STRICT
    AS '$libdir/pg_similarity', 'euclidean';


ALTER FUNCTION public.euclidean(text, text) OWNER TO postgres;

--
-- Name: euclidean_op(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION euclidean_op(text, text) RETURNS boolean
    LANGUAGE c STABLE STRICT
    AS '$libdir/pg_similarity', 'euclidean_op';


ALTER FUNCTION public.euclidean_op(text, text) OWNER TO postgres;

--
-- Name: fn_insert_excluidos(); Type: FUNCTION; Schema: public; Owner: feb
--

CREATE FUNCTION fn_insert_excluidos() RETURNS trigger
    LANGUAGE plpgsql
    AS $$begin

	INSERT INTO excluidos(obaa_entry, id_repositorio, id_rep_subfed)
	values (old.obaa_entry, old.id_repositorio, old.id_rep_subfed);
	return old;
end; $$;


ALTER FUNCTION public.fn_insert_excluidos() OWNER TO feb;

--
-- Name: fn_remove_tipomapeamento(); Type: FUNCTION; Schema: public; Owner: feb
--

CREATE FUNCTION fn_remove_tipomapeamento() RETURNS trigger
    LANGUAGE plpgsql
    AS $$begin
delete from tipomapeamento where id IN (select t.id from tipomapeamento t left join mapeamentos m on m.tipo_mapeamento_id = t.id where m.tipo_mapeamento_id IS NULL);
return old;
end;$$;


ALTER FUNCTION public.fn_remove_tipomapeamento() OWNER TO feb;

--
-- Name: get_isnormalized(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION get_isnormalized(text) RETURNS boolean
    LANGUAGE c STABLE STRICT
    AS '$libdir/pg_similarity', 'get_isnormalized';


ALTER FUNCTION public.get_isnormalized(text) OWNER TO postgres;

--
-- Name: get_threshold(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION get_threshold(text) RETURNS real
    LANGUAGE c STABLE STRICT
    AS '$libdir/pg_similarity', 'get_threshold';


ALTER FUNCTION public.get_threshold(text) OWNER TO postgres;

--
-- Name: get_tokenizer(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION get_tokenizer(text) RETURNS integer
    LANGUAGE c STABLE STRICT
    AS '$libdir/pg_similarity', 'get_tokenizer';


ALTER FUNCTION public.get_tokenizer(text) OWNER TO postgres;

--
-- Name: hamming(bit varying, bit varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION hamming(bit varying, bit varying) RETURNS real
    LANGUAGE c IMMUTABLE STRICT
    AS '$libdir/pg_similarity', 'hamming';


ALTER FUNCTION public.hamming(bit varying, bit varying) OWNER TO postgres;

--
-- Name: hamming_op(bit varying, bit varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION hamming_op(bit varying, bit varying) RETURNS boolean
    LANGUAGE c STABLE STRICT
    AS '$libdir/pg_similarity', 'hamming_op';


ALTER FUNCTION public.hamming_op(bit varying, bit varying) OWNER TO postgres;

--
-- Name: jaccard(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION jaccard(text, text) RETURNS real
    LANGUAGE c IMMUTABLE STRICT
    AS '$libdir/pg_similarity', 'jaccard';


ALTER FUNCTION public.jaccard(text, text) OWNER TO postgres;

--
-- Name: jaccard_op(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION jaccard_op(text, text) RETURNS boolean
    LANGUAGE c STABLE STRICT
    AS '$libdir/pg_similarity', 'jaccard_op';


ALTER FUNCTION public.jaccard_op(text, text) OWNER TO postgres;

--
-- Name: jaro(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION jaro(text, text) RETURNS real
    LANGUAGE c IMMUTABLE STRICT
    AS '$libdir/pg_similarity', 'jaro';


ALTER FUNCTION public.jaro(text, text) OWNER TO postgres;

--
-- Name: jaro_op(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION jaro_op(text, text) RETURNS boolean
    LANGUAGE c STABLE STRICT
    AS '$libdir/pg_similarity', 'jaro_op';


ALTER FUNCTION public.jaro_op(text, text) OWNER TO postgres;

--
-- Name: jarowinkler(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION jarowinkler(text, text) RETURNS real
    LANGUAGE c IMMUTABLE STRICT
    AS '$libdir/pg_similarity', 'jarowinkler';


ALTER FUNCTION public.jarowinkler(text, text) OWNER TO postgres;

--
-- Name: jarowinkler_op(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION jarowinkler_op(text, text) RETURNS boolean
    LANGUAGE c STABLE STRICT
    AS '$libdir/pg_similarity', 'jarowinkler_op';


ALTER FUNCTION public.jarowinkler_op(text, text) OWNER TO postgres;

--
-- Name: lev(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION lev(text, text) RETURNS real
    LANGUAGE c IMMUTABLE STRICT
    AS '$libdir/pg_similarity', 'lev';


ALTER FUNCTION public.lev(text, text) OWNER TO postgres;

--
-- Name: lev_op(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION lev_op(text, text) RETURNS boolean
    LANGUAGE c STABLE STRICT
    AS '$libdir/pg_similarity', 'lev_op';


ALTER FUNCTION public.lev_op(text, text) OWNER TO postgres;

--
-- Name: levslow(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION levslow(text, text) RETURNS real
    LANGUAGE c IMMUTABLE STRICT
    AS '$libdir/pg_similarity', 'levslow';


ALTER FUNCTION public.levslow(text, text) OWNER TO postgres;

--
-- Name: levslow_op(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION levslow_op(text, text) RETURNS boolean
    LANGUAGE c STABLE STRICT
    AS '$libdir/pg_similarity', 'levslow_op';


ALTER FUNCTION public.levslow_op(text, text) OWNER TO postgres;

--
-- Name: matchingcoefficient(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION matchingcoefficient(text, text) RETURNS real
    LANGUAGE c IMMUTABLE STRICT
    AS '$libdir/pg_similarity', 'matchingcoefficient';


ALTER FUNCTION public.matchingcoefficient(text, text) OWNER TO postgres;

--
-- Name: matchingcoefficient_op(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION matchingcoefficient_op(text, text) RETURNS boolean
    LANGUAGE c STABLE STRICT
    AS '$libdir/pg_similarity', 'matchingcoefficient_op';


ALTER FUNCTION public.matchingcoefficient_op(text, text) OWNER TO postgres;

--
-- Name: mongeelkan(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION mongeelkan(text, text) RETURNS real
    LANGUAGE c IMMUTABLE STRICT
    AS '$libdir/pg_similarity', 'mongeelkan';


ALTER FUNCTION public.mongeelkan(text, text) OWNER TO postgres;

--
-- Name: mongeelkan_op(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION mongeelkan_op(text, text) RETURNS boolean
    LANGUAGE c STABLE STRICT
    AS '$libdir/pg_similarity', 'mongeelkan_op';


ALTER FUNCTION public.mongeelkan_op(text, text) OWNER TO postgres;

--
-- Name: needlemanwunsch(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION needlemanwunsch(text, text) RETURNS real
    LANGUAGE c IMMUTABLE STRICT
    AS '$libdir/pg_similarity', 'needlemanwunsch';


ALTER FUNCTION public.needlemanwunsch(text, text) OWNER TO postgres;

--
-- Name: needlemanwunsch_op(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION needlemanwunsch_op(text, text) RETURNS boolean
    LANGUAGE c STABLE STRICT
    AS '$libdir/pg_similarity', 'needlemanwunsch_op';


ALTER FUNCTION public.needlemanwunsch_op(text, text) OWNER TO postgres;

--
-- Name: overlapcoefficient(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION overlapcoefficient(text, text) RETURNS real
    LANGUAGE c IMMUTABLE STRICT
    AS '$libdir/pg_similarity', 'overlapcoefficient';


ALTER FUNCTION public.overlapcoefficient(text, text) OWNER TO postgres;

--
-- Name: overlapcoefficient_op(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION overlapcoefficient_op(text, text) RETURNS boolean
    LANGUAGE c STABLE STRICT
    AS '$libdir/pg_similarity', 'overlapcoefficient_op';


ALTER FUNCTION public.overlapcoefficient_op(text, text) OWNER TO postgres;

--
-- Name: qgram(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION qgram(text, text) RETURNS real
    LANGUAGE c IMMUTABLE STRICT
    AS '$libdir/pg_similarity', 'qgram';


ALTER FUNCTION public.qgram(text, text) OWNER TO postgres;

--
-- Name: qgram_op(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION qgram_op(text, text) RETURNS boolean
    LANGUAGE c STABLE STRICT
    AS '$libdir/pg_similarity', 'qgram_op';


ALTER FUNCTION public.qgram_op(text, text) OWNER TO postgres;

--
-- Name: set_isnormalized(text, boolean); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION set_isnormalized(text, boolean) RETURNS boolean
    LANGUAGE c STRICT
    AS '$libdir/pg_similarity', 'set_isnormalized';


ALTER FUNCTION public.set_isnormalized(text, boolean) OWNER TO postgres;

--
-- Name: set_threshold(text, real); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION set_threshold(text, real) RETURNS real
    LANGUAGE c STRICT
    AS '$libdir/pg_similarity', 'set_threshold';


ALTER FUNCTION public.set_threshold(text, real) OWNER TO postgres;

--
-- Name: set_tokenizer(text, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION set_tokenizer(text, integer) RETURNS integer
    LANGUAGE c STRICT
    AS '$libdir/pg_similarity', 'set_tokenizer';


ALTER FUNCTION public.set_tokenizer(text, integer) OWNER TO postgres;

--
-- Name: smithwaterman(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION smithwaterman(text, text) RETURNS real
    LANGUAGE c IMMUTABLE STRICT
    AS '$libdir/pg_similarity', 'smithwaterman';


ALTER FUNCTION public.smithwaterman(text, text) OWNER TO postgres;

--
-- Name: smithwaterman_op(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION smithwaterman_op(text, text) RETURNS boolean
    LANGUAGE c STABLE STRICT
    AS '$libdir/pg_similarity', 'smithwaterman_op';


ALTER FUNCTION public.smithwaterman_op(text, text) OWNER TO postgres;

--
-- Name: smithwatermangotoh(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION smithwatermangotoh(text, text) RETURNS real
    LANGUAGE c IMMUTABLE STRICT
    AS '$libdir/pg_similarity', 'smithwatermangotoh';


ALTER FUNCTION public.smithwatermangotoh(text, text) OWNER TO postgres;

--
-- Name: smithwatermangotoh_op(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION smithwatermangotoh_op(text, text) RETURNS boolean
    LANGUAGE c STABLE STRICT
    AS '$libdir/pg_similarity', 'smithwatermangotoh_op';


ALTER FUNCTION public.smithwatermangotoh_op(text, text) OWNER TO postgres;

--
-- Name: ~##; Type: OPERATOR; Schema: public; Owner: postgres
--

CREATE OPERATOR ~## (
    PROCEDURE = cosine_op,
    LEFTARG = text,
    RIGHTARG = text,
    COMMUTATOR = ~##,
    JOIN = contjoinsel
);


ALTER OPERATOR public.~## (text, text) OWNER TO postgres;

--
-- Name: ~%%; Type: OPERATOR; Schema: public; Owner: postgres
--

CREATE OPERATOR ~%% (
    PROCEDURE = jaro_op,
    LEFTARG = text,
    RIGHTARG = text,
    COMMUTATOR = ~%%,
    JOIN = contjoinsel
);


ALTER OPERATOR public.~%% (text, text) OWNER TO postgres;

--
-- Name: ~==; Type: OPERATOR; Schema: public; Owner: postgres
--

CREATE OPERATOR ~== (
    PROCEDURE = lev_op,
    LEFTARG = text,
    RIGHTARG = text,
    COMMUTATOR = ~==,
    JOIN = contjoinsel
);


ALTER OPERATOR public.~== (text, text) OWNER TO postgres;

--
-- Name: ~@@; Type: OPERATOR; Schema: public; Owner: postgres
--

CREATE OPERATOR ~@@ (
    PROCEDURE = jarowinkler_op,
    LEFTARG = text,
    RIGHTARG = text,
    COMMUTATOR = ~@@,
    JOIN = contjoinsel
);


ALTER OPERATOR public.~@@ (text, text) OWNER TO postgres;

--
-- Name: ~~~; Type: OPERATOR; Schema: public; Owner: postgres
--

CREATE OPERATOR ~~~ (
    PROCEDURE = qgram_op,
    LEFTARG = text,
    RIGHTARG = text,
    COMMUTATOR = ~~~,
    JOIN = contjoinsel
);


ALTER OPERATOR public.~~~ (text, text) OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: atributos; Type: TABLE; Schema: public; Owner: feb; Tablespace: 
--

CREATE TABLE atributos (
    id integer NOT NULL,
    id_padrao integer NOT NULL,
    atributo character varying(45) NOT NULL
);


ALTER TABLE public.atributos OWNER TO feb;

--
-- Name: atributos_id_seq; Type: SEQUENCE; Schema: public; Owner: feb
--

CREATE SEQUENCE atributos_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.atributos_id_seq OWNER TO feb;

--
-- Name: atributos_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: feb
--

ALTER SEQUENCE atributos_id_seq OWNED BY atributos.id;


--
-- Name: dados_subfederacoes; Type: TABLE; Schema: public; Owner: feb; Tablespace: 
--

CREATE TABLE dados_subfederacoes (
    nome character varying NOT NULL,
    descricao character varying,
    id integer NOT NULL,
    data_ultima_atualizacao timestamp without time zone DEFAULT '0001-01-01 00:00:00'::timestamp without time zone,
    url character varying(200) NOT NULL
);


ALTER TABLE public.dados_subfederacoes OWNER TO feb;

--
-- Name: dados_subfederacoes_id_seq; Type: SEQUENCE; Schema: public; Owner: feb
--

CREATE SEQUENCE dados_subfederacoes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.dados_subfederacoes_id_seq OWNER TO feb;

--
-- Name: dados_subfederacoes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: feb
--

ALTER SEQUENCE dados_subfederacoes_id_seq OWNED BY dados_subfederacoes.id;


--
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
    id_rep_subfed integer
);


ALTER TABLE public.documentos OWNER TO feb;

--
-- Name: excluidos_id_seq; Type: SEQUENCE; Schema: public; Owner: feb
--

CREATE SEQUENCE excluidos_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.excluidos_id_seq OWNER TO feb;

--
-- Name: excluidos; Type: TABLE; Schema: public; Owner: feb; Tablespace: 
--

CREATE TABLE excluidos (
    obaa_entry character varying NOT NULL,
    data timestamp with time zone DEFAULT now() NOT NULL,
    id integer DEFAULT nextval('excluidos_id_seq'::regclass) NOT NULL,
    id_repositorio integer,
    id_rep_subfed integer
);


ALTER TABLE public.excluidos OWNER TO feb;

--
-- Name: documentos_e_excluidos; Type: VIEW; Schema: public; Owner: feb
--

CREATE VIEW documentos_e_excluidos AS
    SELECT (excluidos.id + (SELECT max(documentos.id) AS max FROM documentos)) AS id, excluidos.obaa_entry, excluidos.id_repositorio, excluidos.data AS "timestamp", true AS excluido FROM excluidos UNION SELECT documentos.id, documentos.obaa_entry, documentos.id_repositorio, documentos."timestamp", false AS excluido FROM documentos;


ALTER TABLE public.documentos_e_excluidos OWNER TO feb;

--
-- Name: documentos_id_seq; Type: SEQUENCE; Schema: public; Owner: feb
--

CREATE SEQUENCE documentos_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1
    CYCLE;


ALTER TABLE public.documentos_id_seq OWNER TO feb;

--
-- Name: documentos_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: feb
--

ALTER SEQUENCE documentos_id_seq OWNED BY documentos.id;


--
-- Name: info_repositorios; Type: TABLE; Schema: public; Owner: feb; Tablespace: 
--

CREATE TABLE info_repositorios (
    id_repositorio integer NOT NULL,
    data_ultima_atualizacao timestamp without time zone DEFAULT '0001-01-01 00:00:00'::timestamp without time zone,
    periodicidade_horas integer,
    nome_na_federacao character varying(45) NOT NULL,
    url_or_ip character varying(200) NOT NULL,
    tipo_sincronizacao character varying(45) DEFAULT 'OAI'::character varying,
    padrao_metadados integer,
    tipo_mapeamento_id integer DEFAULT 1 NOT NULL,
    metadata_prefix character varying(45),
    name_space character varying(45),
    set character varying(45)
);


ALTER TABLE public.info_repositorios OWNER TO feb;

--
-- Name: mapeamentocomposto; Type: TABLE; Schema: public; Owner: feb; Tablespace: 
--

CREATE TABLE mapeamentocomposto (
    id integer NOT NULL,
    valor character varying(45) NOT NULL,
    id_origem integer NOT NULL
);


ALTER TABLE public.mapeamentocomposto OWNER TO feb;

--
-- Name: mapeamentocomposto_id_seq; Type: SEQUENCE; Schema: public; Owner: feb
--

CREATE SEQUENCE mapeamentocomposto_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.mapeamentocomposto_id_seq OWNER TO feb;

--
-- Name: mapeamentocomposto_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: feb
--

ALTER SEQUENCE mapeamentocomposto_id_seq OWNED BY mapeamentocomposto.id;


--
-- Name: repositorios_id_seq; Type: SEQUENCE; Schema: public; Owner: feb
--

CREATE SEQUENCE repositorios_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.repositorios_id_seq OWNER TO feb;

--
-- Name: mapeamentos; Type: TABLE; Schema: public; Owner: feb; Tablespace: 
--

CREATE TABLE mapeamentos (
    id integer DEFAULT nextval('repositorios_id_seq'::regclass) NOT NULL,
    padraometadados_id integer NOT NULL,
    origem_id integer NOT NULL,
    destino_id integer NOT NULL,
    tipo_mapeamento_id integer NOT NULL,
    mapeamento_composto_id integer
);


ALTER TABLE public.mapeamentos OWNER TO feb;

--
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
-- Name: objetos_id_seq; Type: SEQUENCE; Schema: public; Owner: feb
--

CREATE SEQUENCE objetos_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.objetos_id_seq OWNER TO feb;

--
-- Name: objetos_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: feb
--

ALTER SEQUENCE objetos_id_seq OWNED BY objetos.id;


--
-- Name: padraometadados; Type: TABLE; Schema: public; Owner: feb; Tablespace: 
--

CREATE TABLE padraometadados (
    id integer NOT NULL,
    nome character varying(45) NOT NULL,
    metadata_prefix character varying(45),
    name_space character varying(45)
);


ALTER TABLE public.padraometadados OWNER TO feb;

--
-- Name: padraometadados_id_seq; Type: SEQUENCE; Schema: public; Owner: feb
--

CREATE SEQUENCE padraometadados_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.padraometadados_id_seq OWNER TO feb;

--
-- Name: padraometadados_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: feb
--

ALTER SEQUENCE padraometadados_id_seq OWNED BY padraometadados.id;


--
-- Name: r1idf; Type: TABLE; Schema: public; Owner: feb; Tablespace: 
--

CREATE TABLE r1idf (
    token character varying(255) NOT NULL,
    idf double precision NOT NULL
);


ALTER TABLE public.r1idf OWNER TO feb;

--
-- Name: r1length; Type: TABLE; Schema: public; Owner: feb; Tablespace: 
--

CREATE TABLE r1length (
    tid integer NOT NULL,
    len double precision
);


ALTER TABLE public.r1length OWNER TO feb;

--
-- Name: r1size; Type: TABLE; Schema: public; Owner: feb; Tablespace: 
--

CREATE TABLE r1size (
    size integer
);


ALTER TABLE public.r1size OWNER TO feb;

--
-- Name: r1sum; Type: TABLE; Schema: public; Owner: feb; Tablespace: 
--

CREATE TABLE r1sum (
    token text NOT NULL,
    total double precision
);


ALTER TABLE public.r1sum OWNER TO feb;

--
-- Name: r1tf; Type: TABLE; Schema: public; Owner: feb; Tablespace: 
--

CREATE TABLE r1tf (
    tid integer NOT NULL,
    token text NOT NULL,
    tf integer
);


ALTER TABLE public.r1tf OWNER TO feb;

--
-- Name: r1tokens; Type: TABLE; Schema: public; Owner: feb; Tablespace: 
--

CREATE TABLE r1tokens (
    id integer NOT NULL,
    token text NOT NULL,
    field integer
);


ALTER TABLE public.r1tokens OWNER TO feb;

--
-- Name: r1weights; Type: TABLE; Schema: public; Owner: feb; Tablespace: 
--

CREATE TABLE r1weights (
    tid integer NOT NULL,
    token text NOT NULL,
    weight double precision
);


ALTER TABLE public.r1weights OWNER TO feb;

--
-- Name: repositorios; Type: TABLE; Schema: public; Owner: feb; Tablespace: 
--

CREATE TABLE repositorios (
    id integer DEFAULT nextval('repositorios_id_seq'::regclass) NOT NULL,
    nome character varying(45) NOT NULL,
    descricao character varying(455) DEFAULT NULL::character varying
);


ALTER TABLE public.repositorios OWNER TO feb;

--
-- Name: repositorios_subfed_id_seq; Type: SEQUENCE; Schema: public; Owner: feb
--

CREATE SEQUENCE repositorios_subfed_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    MINVALUE 0
    CACHE 1;


ALTER TABLE public.repositorios_subfed_id_seq OWNER TO feb;

--
-- Name: repositorios_subfed; Type: TABLE; Schema: public; Owner: feb; Tablespace: 
--

CREATE TABLE repositorios_subfed (
    id integer DEFAULT nextval('repositorios_subfed_id_seq'::regclass) NOT NULL,
    id_subfed integer NOT NULL,
    nome character varying NOT NULL
);


ALTER TABLE public.repositorios_subfed OWNER TO feb;

--
-- Name: stopwords; Type: TABLE; Schema: public; Owner: feb; Tablespace: 
--

CREATE TABLE stopwords (
    id integer NOT NULL,
    stopword character varying(45) NOT NULL
);


ALTER TABLE public.stopwords OWNER TO feb;

--
-- Name: stopwords_id_seq; Type: SEQUENCE; Schema: public; Owner: feb
--

CREATE SEQUENCE stopwords_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.stopwords_id_seq OWNER TO feb;

--
-- Name: stopwords_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: feb
--

ALTER SEQUENCE stopwords_id_seq OWNED BY stopwords.id;


--
-- Name: tipomapeamento; Type: TABLE; Schema: public; Owner: feb; Tablespace: 
--

CREATE TABLE tipomapeamento (
    id integer NOT NULL,
    nome character varying(45) NOT NULL,
    descricao character varying(200) DEFAULT NULL::character varying
);


ALTER TABLE public.tipomapeamento OWNER TO feb;

--
-- Name: tipomapeamento_id_seq; Type: SEQUENCE; Schema: public; Owner: feb
--

CREATE SEQUENCE tipomapeamento_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.tipomapeamento_id_seq OWNER TO feb;

--
-- Name: tipomapeamento_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: feb
--

ALTER SEQUENCE tipomapeamento_id_seq OWNED BY tipomapeamento.id;


--
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
-- Name: usuarios_id_seq; Type: SEQUENCE; Schema: public; Owner: feb
--

CREATE SEQUENCE usuarios_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.usuarios_id_seq OWNER TO feb;

--
-- Name: usuarios_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: feb
--

ALTER SEQUENCE usuarios_id_seq OWNED BY usuarios.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: feb
--

ALTER TABLE atributos ALTER COLUMN id SET DEFAULT nextval('atributos_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: feb
--

ALTER TABLE dados_subfederacoes ALTER COLUMN id SET DEFAULT nextval('dados_subfederacoes_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: feb
--

ALTER TABLE documentos ALTER COLUMN id SET DEFAULT nextval('documentos_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: feb
--

ALTER TABLE mapeamentocomposto ALTER COLUMN id SET DEFAULT nextval('mapeamentocomposto_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: feb
--

ALTER TABLE objetos ALTER COLUMN id SET DEFAULT nextval('objetos_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: feb
--

ALTER TABLE padraometadados ALTER COLUMN id SET DEFAULT nextval('padraometadados_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: feb
--

ALTER TABLE stopwords ALTER COLUMN id SET DEFAULT nextval('stopwords_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: feb
--

ALTER TABLE tipomapeamento ALTER COLUMN id SET DEFAULT nextval('tipomapeamento_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: feb
--

ALTER TABLE usuarios ALTER COLUMN id SET DEFAULT nextval('usuarios_id_seq'::regclass);


--
-- Name: documentos_pkey; Type: CONSTRAINT; Schema: public; Owner: feb; Tablespace: 
--

ALTER TABLE ONLY documentos
    ADD CONSTRAINT documentos_pkey PRIMARY KEY (id);


--
-- Name: excluidos_pkey; Type: CONSTRAINT; Schema: public; Owner: feb; Tablespace: 
--

ALTER TABLE ONLY excluidos
    ADD CONSTRAINT excluidos_pkey PRIMARY KEY (id);


--
-- Name: id; Type: CONSTRAINT; Schema: public; Owner: feb; Tablespace: 
--

ALTER TABLE ONLY repositorios_subfed
    ADD CONSTRAINT id PRIMARY KEY (id);


--
-- Name: info_repositorios_pkey; Type: CONSTRAINT; Schema: public; Owner: feb; Tablespace: 
--

ALTER TABLE ONLY info_repositorios
    ADD CONSTRAINT info_repositorios_pkey PRIMARY KEY (id_repositorio);


--
-- Name: mapeamentos_padraometadados_id_key; Type: CONSTRAINT; Schema: public; Owner: feb; Tablespace: 
--

ALTER TABLE ONLY mapeamentos
    ADD CONSTRAINT mapeamentos_padraometadados_id_key UNIQUE (padraometadados_id, origem_id, destino_id, tipo_mapeamento_id);


--
-- Name: mapeamentos_pkey; Type: CONSTRAINT; Schema: public; Owner: feb; Tablespace: 
--

ALTER TABLE ONLY mapeamentos
    ADD CONSTRAINT mapeamentos_pkey PRIMARY KEY (tipo_mapeamento_id, padraometadados_id, origem_id, destino_id, id);


--
-- Name: pki_dadossubf; Type: CONSTRAINT; Schema: public; Owner: feb; Tablespace: 
--

ALTER TABLE ONLY dados_subfederacoes
    ADD CONSTRAINT pki_dadossubf PRIMARY KEY (id);


--
-- Name: pki_id_atrbuto; Type: CONSTRAINT; Schema: public; Owner: feb; Tablespace: 
--

ALTER TABLE ONLY atributos
    ADD CONSTRAINT pki_id_atrbuto PRIMARY KEY (id);


--
-- Name: pki_mapeamentocomp; Type: CONSTRAINT; Schema: public; Owner: feb; Tablespace: 
--

ALTER TABLE ONLY mapeamentocomposto
    ADD CONSTRAINT pki_mapeamentocomp PRIMARY KEY (id);


--
-- Name: pki_metadados; Type: CONSTRAINT; Schema: public; Owner: feb; Tablespace: 
--

ALTER TABLE ONLY objetos
    ADD CONSTRAINT pki_metadados PRIMARY KEY (id);


--
-- Name: pki_rep; Type: CONSTRAINT; Schema: public; Owner: feb; Tablespace: 
--

ALTER TABLE ONLY repositorios
    ADD CONSTRAINT pki_rep PRIMARY KEY (id);


--
-- Name: pki_tipomap; Type: CONSTRAINT; Schema: public; Owner: feb; Tablespace: 
--

ALTER TABLE ONLY padraometadados
    ADD CONSTRAINT pki_tipomap PRIMARY KEY (id);


--
-- Name: pki_tipomapeamento; Type: CONSTRAINT; Schema: public; Owner: feb; Tablespace: 
--

ALTER TABLE ONLY tipomapeamento
    ADD CONSTRAINT pki_tipomapeamento PRIMARY KEY (id);


--
-- Name: r1idf_pkey; Type: CONSTRAINT; Schema: public; Owner: feb; Tablespace: 
--

ALTER TABLE ONLY r1idf
    ADD CONSTRAINT r1idf_pkey PRIMARY KEY (token);


--
-- Name: stopwords_pkey; Type: CONSTRAINT; Schema: public; Owner: feb; Tablespace: 
--

ALTER TABLE ONLY stopwords
    ADD CONSTRAINT stopwords_pkey PRIMARY KEY (id);


--
-- Name: usuarios_pkey; Type: CONSTRAINT; Schema: public; Owner: feb; Tablespace: 
--

ALTER TABLE ONLY usuarios
    ADD CONSTRAINT usuarios_pkey PRIMARY KEY (id);


--
-- Name: fki_; Type: INDEX; Schema: public; Owner: feb; Tablespace: 
--

CREATE INDEX fki_ ON r1length USING btree (tid);


--
-- Name: fki_atributosOrigem; Type: INDEX; Schema: public; Owner: feb; Tablespace: 
--

CREATE INDEX "fki_atributosOrigem" ON mapeamentos USING btree (origem_id);


--
-- Name: fki_atributos_padraometadados; Type: INDEX; Schema: public; Owner: feb; Tablespace: 
--

CREATE INDEX fki_atributos_padraometadados ON atributos USING btree (id_padrao);


--
-- Name: fki_destino; Type: INDEX; Schema: public; Owner: feb; Tablespace: 
--

CREATE INDEX fki_destino ON mapeamentos USING btree (destino_id);


--
-- Name: fki_documentos; Type: INDEX; Schema: public; Owner: feb; Tablespace: 
--

CREATE INDEX fki_documentos ON r1tokens USING btree (id);


--
-- Name: fki_mapeamentocomp; Type: INDEX; Schema: public; Owner: feb; Tablespace: 
--

CREATE INDEX fki_mapeamentocomp ON mapeamentos USING btree (mapeamento_composto_id);


--
-- Name: fki_mapemanetoscomposto; Type: INDEX; Schema: public; Owner: feb; Tablespace: 
--

CREATE INDEX fki_mapemanetoscomposto ON mapeamentocomposto USING btree (id_origem);


--
-- Name: fki_obj_doc; Type: INDEX; Schema: public; Owner: feb; Tablespace: 
--

CREATE INDEX fki_obj_doc ON objetos USING btree (documento);


--
-- Name: fki_padraometadados; Type: INDEX; Schema: public; Owner: feb; Tablespace: 
--

CREATE INDEX fki_padraometadados ON info_repositorios USING btree (padrao_metadados);


--
-- Name: fki_padraometadadosmap; Type: INDEX; Schema: public; Owner: feb; Tablespace: 
--

CREATE INDEX fki_padraometadadosmap ON mapeamentos USING btree (padraometadados_id);


--
-- Name: fki_repositorio; Type: INDEX; Schema: public; Owner: feb; Tablespace: 
--

CREATE INDEX fki_repositorio ON documentos USING btree (id_repositorio);


--
-- Name: fki_tipomap; Type: INDEX; Schema: public; Owner: feb; Tablespace: 
--

CREATE INDEX fki_tipomap ON info_repositorios USING btree (tipo_mapeamento_id);


--
-- Name: fki_tipomapeamento; Type: INDEX; Schema: public; Owner: feb; Tablespace: 
--

CREATE INDEX fki_tipomapeamento ON mapeamentos USING btree (tipo_mapeamento_id);


--
-- Name: tg_insert_exluidos; Type: TRIGGER; Schema: public; Owner: feb
--

CREATE TRIGGER tg_insert_exluidos
    AFTER DELETE ON documentos
    FOR EACH ROW
    EXECUTE PROCEDURE fn_insert_excluidos();


--
-- Name: tg_remove_tipomapeamento; Type: TRIGGER; Schema: public; Owner: feb
--

CREATE TRIGGER tg_remove_tipomapeamento
    AFTER DELETE ON mapeamentos
    FOR EACH ROW
    EXECUTE PROCEDURE fn_remove_tipomapeamento();


--
-- Name: atr; Type: FK CONSTRAINT; Schema: public; Owner: feb
--

ALTER TABLE ONLY mapeamentocomposto
    ADD CONSTRAINT atr FOREIGN KEY (id_origem) REFERENCES atributos(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: atr_padrao; Type: FK CONSTRAINT; Schema: public; Owner: feb
--

ALTER TABLE ONLY atributos
    ADD CONSTRAINT atr_padrao FOREIGN KEY (id_padrao) REFERENCES padraometadados(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: destino; Type: FK CONSTRAINT; Schema: public; Owner: feb
--

ALTER TABLE ONLY mapeamentos
    ADD CONSTRAINT destino FOREIGN KEY (destino_id) REFERENCES atributos(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: documentos; Type: FK CONSTRAINT; Schema: public; Owner: feb
--

ALTER TABLE ONLY r1tokens
    ADD CONSTRAINT documentos FOREIGN KEY (id) REFERENCES documentos(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: excluidos; Type: FK CONSTRAINT; Schema: public; Owner: feb
--

ALTER TABLE ONLY documentos
    ADD CONSTRAINT excluidos FOREIGN KEY (id_repositorio) REFERENCES repositorios(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: mapeamentocomp; Type: FK CONSTRAINT; Schema: public; Owner: feb
--

ALTER TABLE ONLY mapeamentos
    ADD CONSTRAINT mapeamentocomp FOREIGN KEY (mapeamento_composto_id) REFERENCES mapeamentocomposto(id) ON UPDATE CASCADE ON DELETE SET NULL;


--
-- Name: obj_doc; Type: FK CONSTRAINT; Schema: public; Owner: feb
--

ALTER TABLE ONLY objetos
    ADD CONSTRAINT obj_doc FOREIGN KEY (documento) REFERENCES documentos(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: origem; Type: FK CONSTRAINT; Schema: public; Owner: feb
--

ALTER TABLE ONLY mapeamentos
    ADD CONSTRAINT origem FOREIGN KEY (origem_id) REFERENCES atributos(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: padraometa; Type: FK CONSTRAINT; Schema: public; Owner: feb
--

ALTER TABLE ONLY info_repositorios
    ADD CONSTRAINT padraometa FOREIGN KEY (padrao_metadados) REFERENCES padraometadados(id) ON UPDATE CASCADE;


--
-- Name: padraometa; Type: FK CONSTRAINT; Schema: public; Owner: feb
--

ALTER TABLE ONLY mapeamentos
    ADD CONSTRAINT padraometa FOREIGN KEY (padraometadados_id) REFERENCES padraometadados(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: repositorio; Type: FK CONSTRAINT; Schema: public; Owner: feb
--

ALTER TABLE ONLY documentos
    ADD CONSTRAINT repositorio FOREIGN KEY (id_repositorio) REFERENCES repositorios(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: repositorio_subfed; Type: FK CONSTRAINT; Schema: public; Owner: feb
--

ALTER TABLE ONLY documentos
    ADD CONSTRAINT repositorio_subfed FOREIGN KEY (id_rep_subfed) REFERENCES repositorios_subfed(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: repositorios; Type: FK CONSTRAINT; Schema: public; Owner: feb
--

ALTER TABLE ONLY info_repositorios
    ADD CONSTRAINT repositorios FOREIGN KEY (id_repositorio) REFERENCES repositorios(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: subfed; Type: FK CONSTRAINT; Schema: public; Owner: feb
--

ALTER TABLE ONLY repositorios_subfed
    ADD CONSTRAINT subfed FOREIGN KEY (id_subfed) REFERENCES dados_subfederacoes(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: tipomap; Type: FK CONSTRAINT; Schema: public; Owner: feb
--

ALTER TABLE ONLY info_repositorios
    ADD CONSTRAINT tipomap FOREIGN KEY (tipo_mapeamento_id) REFERENCES tipomapeamento(id);


--
-- Name: tipomapeamento; Type: FK CONSTRAINT; Schema: public; Owner: feb
--

ALTER TABLE ONLY mapeamentos
    ADD CONSTRAINT tipomapeamento FOREIGN KEY (tipo_mapeamento_id) REFERENCES tipomapeamento(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

