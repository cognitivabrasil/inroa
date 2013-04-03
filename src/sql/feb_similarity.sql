-- Function: qgram(text, text)

-- DROP FUNCTION qgram(text, text);

CREATE OR REPLACE FUNCTION qgram(text, text)
  RETURNS real AS
'$libdir/pg_similarity', 'qgram'
  LANGUAGE c IMMUTABLE STRICT
  COST 1;
ALTER FUNCTION qgram(text, text)
  OWNER TO postgres;

-- Function: cosine(text, text)

-- DROP FUNCTION cosine(text, text);

CREATE OR REPLACE FUNCTION cosine(text, text)
  RETURNS real AS
'$libdir/pg_similarity', 'cosine'
  LANGUAGE c IMMUTABLE STRICT
  COST 1;
ALTER FUNCTION cosine(text, text)
  OWNER TO postgres;

-- Function: cosine_op(text, text)

-- DROP FUNCTION cosine_op(text, text);

CREATE OR REPLACE FUNCTION cosine_op(text, text)
  RETURNS boolean AS
'$libdir/pg_similarity', 'cosine_op'
  LANGUAGE c STABLE STRICT
  COST 1;
ALTER FUNCTION cosine_op(text, text)
  OWNER TO postgres;

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

