--Cria backup das informações da repositório
ALTER TABLE repositorios RENAME TO repositorios_2;

-- dropa as restrições
ALTER TABLE info_repositorios DROP CONSTRAINT padraometa;
ALTER TABLE info_repositorios DROP CONSTRAINT repositorios;
ALTER TABLE info_repositorios DROP CONSTRAINT tipomap;

--dropa as tabelas
DROP TABLE IF EXISTS atributos,
    consultas,
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
    repositorios,
    repositorios_subfed,
    stopwords,
    tipomapeamento,
    usuarios;

--dropa os triggers
DROP FUNCTION IF EXISTS fn_insert_excluidos(),
    fn_remove_tipomapeamento();

--dropa a view
DROP VIEW IF EXISTS documentos_e_excluidos;

