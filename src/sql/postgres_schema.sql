ALTER TABLE public.consultas OWNER TO feb;
ALTER TABLE public.dados_subfederacoes OWNER TO feb;
ALTER TABLE public.documentos OWNER TO feb;
ALTER TABLE public.mapeamentos OWNER TO feb;
ALTER TABLE public.padraometadados OWNER TO feb;
ALTER TABLE public.repositorios OWNER TO feb;
ALTER TABLE public.repositorios_subfed OWNER TO feb;
ALTER TABLE public.usuarios OWNER TO feb;
ALTER TABLE searches OWNER TO feb;
ALTER TABLE visitas OWNER TO feb;
ALTER TABLE public.searches OWNER TO feb;

COMMENT ON TABLE visitas IS 'contador de visitas do FEB';
COMMENT ON TABLE documentos_visitas IS 'tabela n x n de documentos por visitas';

CREATE INDEX fki_documento ON documentos_visitas USING btree (documento);
CREATE INDEX fki_visitas ON documentos_visitas USING btree (visita);
CREATE INDEX fki_mapeamento ON repositorios USING btree (mapeamento_id);
CREATE INDEX fki_repositorio ON documentos USING btree (id_repositorio);
CREATE INDEX fki_padraometadados ON repositorios USING btree (padrao_metadados);

SELECT setval('public.visitas_id_seq', 100, true);
SELECT setval('public.dados_subfederacoes_id_seq', 100, true);
SELECT setval('public.documentos_id_seq', 100, true);
SELECT setval('public.mapeamentos_id_seq', 100, true);
SELECT setval('public.padraometadados_id_seq', 100, true);
SELECT setval('public.repositorios_id_seq', 100, true);
SELECT setval('public.repositorios_subfed_id_seq', 100, true);
SELECT setval('public.usuarios_id_seq', 100, true);
SELECT setval('public.searches_id_seq', 100, true);
SELECT setval('public.searches_id_seq', 100, true);