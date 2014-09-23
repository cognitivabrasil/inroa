/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cognitivabrasil.feb.util;

import static org.hamcrest.Matchers.equalTo;
import org.joda.time.DateTime;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author Marcos Freitas Nunes <marcos@cognitivabrasil.com.br>
 */
public class OperacoesTest {
    
    @Test
    public void testDateDiffZero(){
        assertThat(Operacoes.testarDataDifZero(DateTime.now()), equalTo(false));
        
        assertThat(Operacoes.testarDataDifZero(DateTime.parse("1991-01-01")), equalTo(false));
        
        assertThat(Operacoes.testarDataDifZero(new DateTime(0)), equalTo(true));
    }
    
    @Test
    public void testStringDateDiffZero(){
        assertThat(Operacoes.testarDataDifZero("2014-09-22"), equalTo(false));
        
        assertThat(Operacoes.testarDataDifZero("1991-01-01"), equalTo(false));
        String nulo = null;
        assertThat(Operacoes.testarDataDifZero(nulo), equalTo(true));
        assertThat(Operacoes.testarDataDifZero("0001-01-01"), equalTo(true));
    }
    
    @Test
    public void testDateFormat(){
        String format = Operacoes.ultimaAtualizacaoFrase(DateTime.parse("1984-08-21T01:01:01Z"), "url");
        assertThat(format, equalTo("Dia 21/08/1984 &agrave;s 01:01:01"));
    }
    
    @Test
    public void testDateFormatWithoutURL(){
        String format = Operacoes.ultimaAtualizacaoFrase(DateTime.parse("1984-08-21T01:01:01Z"), null);
        assertThat(format, equalTo("N&atilde;o foi informado um endere&ccedil;o para sincroniza&ccedil;&atilde;o"));
    }
    
    @Test
    public void testDateFormatOutDated(){
        String format = Operacoes.ultimaAtualizacaoFrase(new DateTime(0), "url");
        assertThat(format, equalTo("Ainda n&atilde;o foi atualizado!"));
    }
    
}
