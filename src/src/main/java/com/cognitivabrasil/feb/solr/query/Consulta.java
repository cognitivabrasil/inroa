package com.cognitivabrasil.feb.solr.query;

import java.util.List;

public interface Consulta {

    /**
     * @return A consulta (textual) do usuário, sem filtros
     */
    String getConsulta();

    boolean hasAuthor();

    String getAutor();

    String getIdioma();

    List<String> getAll();

    /**
     * @param name nome do campo de pesquisa
     * @return objetos presentes na consulta referentes a este campo, p.e, o usuário pode filtrar por tipo 
     * de arquivo pdf ou word, neste caso a lista teria as duas strings 'application/pdf' e application-msword.
     */
    List get(String name);

    /**
     * @param name o nome do campo
     * @return o nome completo do campo como armazendo no índice do Solr
     */
    String getFullName(String name);

    /**
     * @param name o nome do campo
     * @return o nome do tag do campo no Solr
     */
    String getTagName(String name);

    boolean isRss();

    int getLimit();

    int getOffset();

    Consulta clone();

    void setConsulta(String text);

    String getUrlEncoded();

    boolean isActive(String fieldName, String name);

    void removeFacetFilter(String fieldName, String name);

    void addFacetFilter(String fieldName, Object name);

    void setSizeResult(int numDocs);

}
