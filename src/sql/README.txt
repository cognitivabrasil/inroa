pg_dump -h 143.54.95.20 -p 5432 -U feb -F p -C --schema-only -f "FEB_estrutura.sql" federacao
pg_dump -h 143.54.95.20 -p 5432 -U feb  -t usuarios -t padraometadados -t tipomapeamento -t atributos -t mapeamentocomposto -t stopwords -t mapeamentos -F p --inserts -C -f "FEB_dados.sql" federacao
