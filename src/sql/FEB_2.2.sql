CREATE TABLE consultas(
   consulta character varying NOT NULL,
   ids text,
   CONSTRAINT consultas_pkey PRIMARY KEY (consulta)
) WITH (OIDS = FALSE);
ALTER TABLE consultas OWNER TO feb;
