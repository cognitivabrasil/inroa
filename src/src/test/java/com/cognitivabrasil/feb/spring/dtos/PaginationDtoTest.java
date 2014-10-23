/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitivabrasil.feb.spring.dtos;

import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 * Testa a classe de paginação {@link PaginationDto}.
 * @author Marcos Freitas Nunes <marcos@cognitivabrasil.com.br>
 */
public class PaginationDtoTest {
    
    @Test
    public void testPaginationPage1(){
        PaginationDto pagination = new PaginationDto(5, 20);
        
        assertThat(pagination.hasPreviousPage(), equalTo(false));
        assertThat(pagination.getPreviousPage(), equalTo(0));
        assertThat(pagination.getPages().size(), equalTo(4));
        assertThat(pagination.getCurrentPage(), equalTo(0));
        assertThat(pagination.hasNextPage(), equalTo(true));
        List<Integer> list = new ArrayList();
        list.add(0);
        list.add(1);
        list.add(2);
        list.add(3);
        assertThat(pagination.getPages(), equalTo(list));
        assertThat(pagination.getNextPage(), equalTo(1));
    }
    
    @Test
    public void testPaginationPage2(){
        PaginationDto pagination = new PaginationDto(5, 20, 1);
        
        assertThat(pagination.hasPreviousPage(), equalTo(true));
        assertThat(pagination.getPreviousPage(), equalTo(0));
        assertThat(pagination.getPages().size(), equalTo(4));
        assertThat(pagination.getCurrentPage(), equalTo(1));
        assertThat(pagination.hasNextPage(), equalTo(true));
        List<Integer> list = new ArrayList();
        list.add(0);
        list.add(1);
        list.add(2);
        list.add(3);
        assertThat(pagination.getPages(), equalTo(list));
        assertThat(pagination.getNextPage(), equalTo(2));
    }
    @Test
    public void test21Results(){
        PaginationDto pagination = new PaginationDto(5, 21, 1);
        assertThat(pagination.getPages().size(), equalTo(5));
        List<Integer> list = new ArrayList();
        list.add(0);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        assertThat(pagination.getPages(), equalTo(list));
    }
    
    @Test
    public void testPaginationSobras(){
        PaginationDto pagination = new PaginationDto(5, 100, 4);
        
        assertThat(pagination.hasPreviousPage(), equalTo(true));
        assertThat(pagination.getPreviousPage(), equalTo(3));
        assertThat(pagination.getPages().size(), equalTo(5));
        assertThat(pagination.getCurrentPage(), equalTo(4));
        assertThat(pagination.hasNextPage(), equalTo(true));
        List<Integer> list = new ArrayList();
        
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        assertThat(pagination.getPages(), equalTo(list));
        assertThat(pagination.getNextPage(), equalTo(5));
    }
    
    @Test
    public void testPaginationPage7(){
        PaginationDto pagination = new PaginationDto(5, 100, 6);       
        List<Integer> list = new ArrayList();        
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        list.add(8);
        assertThat(pagination.getPages(), equalTo(list));
    }
    
    @Test
    public void testLastPage(){
        PaginationDto pagination = new PaginationDto(5, 20, 4);
        assertThat(pagination.hasPreviousPage(), equalTo(true));
        assertThat(pagination.hasNextPage(), equalTo(false));
    }
        
    @Test
    public void testEmpty(){
        PaginationDto pagination = new PaginationDto(5, 5);
        assertThat(pagination.getPages(), notNullValue());
        assertThat(pagination.getPages(), hasSize(0));
        assertThat(pagination.hasPreviousPage(), equalTo(false));
        assertThat(pagination.hasNextPage(), equalTo(false));
    }

    
    @Test
    public void testMiddlePage(){
        PaginationDto pagination = new PaginationDto(10, 50, 3);
        assertThat(pagination.hasPreviousPage(), equalTo(true));
        assertThat(pagination.hasNextPage(), equalTo(true));
        assertThat(pagination.getPages(), hasSize(5));
    }
}
