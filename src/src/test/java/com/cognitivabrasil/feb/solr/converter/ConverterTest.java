package com.cognitivabrasil.feb.solr.converter;

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertThat;

import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import com.cognitivabrasil.feb.data.entities.Document;
import com.cognitivabrasil.feb.data.entities.Repositorio;
import com.cognitivabrasil.feb.data.entities.RepositorioSubFed;
import com.cognitivabrasil.feb.data.entities.SubFederacao;

import cognitivabrasil.obaa.OBAA;
import cognitivabrasil.obaa.builder.ObaaBuilder;

public class ConverterTest {

    @Test 
    public void obaaToSolr() {
        OBAA o = ObaaBuilder.buildObaa()
                    .general()
                        .title("Título 1")
                        .title("Bla")
                        .language("pt-BR")
                        .build();
        
        Document d = new Document();
        d.setId(2);
        d.setMetadata(o);
        d.setObaaEntry("myEntry");
        
        
        RepositorioSubFed repSubFed = new RepositorioSubFed();
        repSubFed.setId(2);
        repSubFed.setName("Meu Rep");
        SubFederacao subFederacao = new SubFederacao();
        subFederacao.setId(1);
        repSubFed.setSubFederacao(subFederacao);
        d.setRepositorioSubFed(repSubFed);
        
        SolrInputDocument doc = Converter.obaaToSolr(d);
      
        System.out.println(doc.getFieldValues("obaa.general.title"));
        
        assertThat(doc.getFieldValues("obaa.general.title"), hasItems("Título 1", "Bla"));
        assertThat(doc.getFieldValues("obaa.general.language"), hasItem("pt-BR"));

        assertThat(doc.getFieldValue("obaa.idBaseDados"), equalTo(2));
        assertThat(doc.getFieldValue("obaa.general.identifier.entry"), equalTo("myEntry"));
        assertThat(doc.getFieldValue("obaa.repositorio"), equalTo(-1));
        assertThat(doc.getFieldValue("obaa.subFederacao"), equalTo(2));
        assertThat(doc.getFieldValue("obaa.federacao"), equalTo(1));
        assertThat((String)doc.getFieldValue("obaa.repName"), endsWith("Meu Rep"));

    }
}
