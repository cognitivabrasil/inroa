/* cria uma view que junta documentos ainda existentes e excluidos */

CREATE VIEW documentos_e_excluidos AS
SELECT id + (SELECT max(id) from documentos) as id,obaa_entry,id_repositorio,data AS timestamp, TRUE as excluido FROM excluidos
UNION
SELECT id as id,obaa_entry,id_repositorio,timestamp, FALSE as excluido FROM documentos
;

ALTER TABLE excluidos ADD COLUMN id_rep_subfed integer;

ALTER TABLE excluidos ALTER COLUMN id_repositorio DROP NOT NULL;

CREATE OR REPLACE FUNCTION fn_insert_excluidos() RETURNS trigger AS
$BODY$begin

	INSERT INTO excluidos(obaa_entry, id_repositorio, id_rep_subfed)
	values (old.obaa_entry, old.id_repositorio, old.id_rep_subfed);
	return old;
end; $BODY$
LANGUAGE plpgsql VOLATILE
COST 100;

CREATE OR REPLACE FUNCTION fn_remove_tipomapeamento() RETURNS trigger AS
$BODY$begin
delete from tipomapeamento where id IN (select t.id from tipomapeamento t left join mapeamentos m on m.tipo_mapeamento_id = t.id where m.tipo_mapeamento_id IS NULL);
return old;
end;$BODY$
LANGUAGE plpgsql VOLATILE
COST 100;

DROP TRIGGER IF EXISTS tg_remove_tipomapeamento ON mapeamentos;
CREATE TRIGGER tg_remove_tipomapeamento
  AFTER DELETE
  ON mapeamentos
  FOR EACH ROW
  EXECUTE PROCEDURE fn_remove_tipomapeamento();

ALTER TABLE info_repositorios DROP COLUMN id_federacao;
ALTER TABLE info_repositorios ADD COLUMN "set" character varying(45);

DELETE FROM dados_subfederacoes WHERE nome='Local';
ALTER TABLE dados_subfederacoes ADD COLUMN url character varying(200) NOT NULL;
ALTER TABLE dados_subfederacoes DROP COLUMN "login";
ALTER TABLE dados_subfederacoes DROP COLUMN senha;
ALTER TABLE dados_subfederacoes DROP COLUMN porta;
ALTER TABLE dados_subfederacoes DROP COLUMN ip;
ALTER TABLE dados_subfederacoes DROP COLUMN base;

CREATE TABLE estatistica ( tempo_atualizacao numeric(64) DEFAULT 0 ) WITH (  OIDS = FALSE );
