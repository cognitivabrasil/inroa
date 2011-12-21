ALTER TABLE info_repositorios ADD COLUMN data_xml timestamp without time zone NOT NULL DEFAULT '0001-01-01 00:00:00';

ALTER TABLE dados_subfederacoes
   ADD COLUMN data_xml timestamp without time zone NOT NULL DEFAULT '0001-01-01 00:00:00';

