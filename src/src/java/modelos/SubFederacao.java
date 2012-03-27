/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import org.springframework.core.style.ToStringCreator;

/**
 *
 * @author Marcos Nunes
 */
public class SubFederacao implements java.io.Serializable {

    private int id;
    private String nome;
    private String descricao;
    private String url;
    private Date ultimaAtualizacao;
    private Date dataXML;
    private Set<RepositorioSubFed> repositorios;

    public SubFederacao() {
    }

    public Date getDataXML() {
        return dataXML;
    }

    public void setDataXML(Date dataXML) {
        this.dataXML = dataXML;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    public void setUltimaAtualizacao(Date ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<RepositorioSubFed> getRepositorios() {
        return repositorios;
    }

    public void setRepositorios(Set<RepositorioSubFed> repositorios) {
        this.repositorios = repositorios;
    }

    public Date getProximaAtualizacao() {
        return new Date(this.ultimaAtualizacao.getTime() + 24 * 60 * 60 * 1000); // soma a periodicidade em horas
    }

    @Override
    public String toString() {
        return new ToStringCreator(this).append("id", this.getId()).append("nome", this.getNome()).append("descrição", this.getDescricao()).append("url", this.getUrl()).append("última atualização", this.getUltimaAtualizacao()).toString();
    }
    
    /**
     * Retorna o n&uacute;mero de documentos que a subfedera&ccedil;&atilde;o possui.
     * @return int com o n&uacute;mero de documentos
     */
    public int getSizeDoc(){
        int size = 0;

        Iterator it = getRepositorios().iterator();
        while(it.hasNext()){
            RepositorioSubFed repSub = (RepositorioSubFed) it.next();
            size += repSub.getSize();
        }
        
        return size;
    }
}
