/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

/**
 *
 * @author marcos
 */
public class Teste {

    public static void gravaSubFed(SubFederacao subfed) {
        System.out.println("Nome: " + subfed.getNome());
        System.out.println("Descricao: " + subfed.getDescricao());
        System.out.println("URL: " + subfed.getUrl());
    }

    public static void imprimeRep(Repositorio rep) {
        System.out.println("id: " + rep.getId());
        System.out.println("nome: " + rep.getNome());
        System.out.println("descricao: " + rep.getDescricao());
        System.out.println("url: " + rep.getUrl());
        System.out.println("metadataPrefix: " + rep.getMetadataPrefix());
        System.out.println("ultimaAtualizacao: " + rep.getUltimaAtualizacao());
        System.out.println("namespace: " + rep.getNamespace());
        System.out.println("periodicidadeAtualizacao: " + rep.getPeriodicidadeAtualizacao());
        System.out.println("colecoes: " + rep.getColecoes());
        System.out.println("Padrao Id: " + rep.getPadraoMetadados().getId());
        System.out.println("Padrao Nome: " + rep.getPadraoMetadados().getNome());
        System.out.println("Mapeamento id: " + rep.getMapeamento().getId());

    }
}
