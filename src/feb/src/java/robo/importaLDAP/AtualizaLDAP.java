/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robo.importaLDAP;

import com.novell.ldap.LDAPConnection;
import ferramentaBusca.indexador.Indexador;
import operacoesLdap.Consultar;
import java.util.ArrayList;
import java.util.HashMap;
import operacoesLdap.Remover;

/**
 * Classe que possibilita sincronizar dois diretórios LDAP.
 * @author Marcos
 */
public class AtualizaLDAP {

    /**
     * Sincroniza dois diretórios Ldap.
     * Primeiramente consulta o Ldap Origem e armazena todos seus objetos em um ArrayList.
     * Após, se o passo anterior tiver sucesso, exclui todos os objetos do Ldap destino.
     * Por fim armazena todos os objetos do Ldap Origem no Ldap destino.
     * Retorna um boolean indicando se foi realizado o sincronismo.
     * 
     * @param ipOrigem ip do LDAP de onde serão importados os metadados.
     * @param dnOrigem dn do LDAP de onde serão importados os metadados. Ex.: ou=lom,ou=pgie,dc=ufrgs,dc=br
     * @param loginOrigem login do LDAP de onde serão importados os metadados.
     * @param senhaOrigem senha do LDAP de onde serão importados os metadados.
     * @param portaOrigem porta do LDAP de onde serão importados os metadados.
     * @param nomeAtribIdentificador Nome do atributo identificador, por exemplo lomIdentifier ou obaaIdentifier.
     * @param dnDestino dn do LDAP onde serão armazenados os metadados importados.
     * @param lc Conex&atilde;o com o ldap. Deve ter conexão e bind realizados.
     * @param indexar variavel do tipo Indexador
     *
     * @return retorna um boolean indicando o status da atualização
     */
    public boolean atualizaLDAP(
            String ipOrigem,
            String dnOrigem,
            String loginOrigem,
            String senhaOrigem,
            int portaOrigem,
            String nomeAtribIdentificador,
            String dnDestino,
            LDAPConnection lc,
            Indexador indexar,
            int idRep) {
        ArrayList<HashMap> resultado = new ArrayList<HashMap>();
        Insere insere = new Insere();
        boolean apaga = false;
        Consultar consulta = new Consultar(ipOrigem, nomeAtribIdentificador + "=*", dnOrigem, loginOrigem, senhaOrigem, portaOrigem, null);

        resultado = consulta.getResultado();

        System.out.println(" numero de resultados: " + resultado.size());

        if (resultado.size() > 0) {
            //deleteLDAP deleta = new deleteLDAP();
            Remover deleta = new Remover();
            //seta o debug como false. Para não imprimir textos na saída
            deleta.setDebugOut(false);
            //seta deburErr com true, para imprimir os erros.
            deleta.setDebugErr(true);
            //apaga toda a base
            System.out.println(" Deletando objetos do LDAP destino...");
            apaga = deleta.apagaTodosObjetos(dnDestino, lc);
            
        }else{
            System.out.println("O ldap de origem não possui nenhum objeto.");
        }

        if (apaga) {
            System.out.println(" Inserindo objetos do LDAP origem para o destino...");
            for (int count = 0; count < resultado.size(); count++) {
                HashMap internoHash = new HashMap();
                internoHash = (HashMap) resultado.get(count);
                //seta o debug como false. Para não imprimir textos na saída
                insere.setDebugOut(false);
                insere.insereLomtoObaa(internoHash, dnDestino, lc, indexar, idRep);
            }
        }
        if (resultado.size() > 0 && apaga==true) {
            return true;
        } else {
            return false;
        }

    }

    /*public static void main(String[] args) {
        AtualizaLDAP run = new AtualizaLDAP();
        run.atualizaLDAP("200.132.0.131", "ou=lom,ou=pgie,dc=ufrgs,dc=br",
                "uid=lom, ou=People, ou=pgie, dc=ufrgs, dc=br", "splom/2005",
                "lomIdentifier", "143.54.95.20", "ou=lom,ou=pgie3,dc=ufrgs,dc=br",
                "cn=Manager,ou=pgie3,dc=ufrgs,dc=br", "secret", 389, 389);
    } */
}
