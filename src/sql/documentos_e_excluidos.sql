/* cria uma view que junta documentos ainda existentes e excluidos */

CREATE VIEW documentos_e_excluidos AS
SELECT id + (SELECT max(id) from documentos) as id,obaa_entry,id_repositorio,data AS timestamp, TRUE as excluido FROM excluidos
UNION
SELECT id as id,obaa_entry,id_repositorio,timestamp, FALSE as excluido FROM documentos
;
