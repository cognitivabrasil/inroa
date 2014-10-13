/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitivabrasil.feb.spring.dtos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Marcos Freitas Nunes <marcos@cognitivabrasil.com.br>
 */
public class PaginationDto {

    public static final int ITENS_TO_PRESENT = 10;
    public static final int ITENS_PAGINATION = 5;
    private int size;
    private int currentPage;

    public boolean hasPreviousPage() {
        return getSize()>1;
    }

    public boolean hasNextPage() {
        return (getSize()/ITENS_TO_PRESENT)>1;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getNextPage() {
        return currentPage + 1;
    }

    public int getPreviousPage() {
        if (currentPage > 1) {
            return currentPage - 1;
        }
        return currentPage;
    }
    
    public List getPages(){
        int divisor = ITENS_PAGINATION / 2;

        
        int sobraDePaginasDireita = 0;
        int sobraDePaginasEsquerda = 0;
        List<Integer> pagesAvaliable = new ArrayList<>();
        int page = getCurrentPage();
        pagesAvaliable.add(page);
        for (int i = 1; i <= divisor; i++) {
            //Teste de sobras na esquerda
            if ((page - i) >= 0) {
                pagesAvaliable.add(page - i);
            } else {
                sobraDePaginasEsquerda++;
            }
            //Teste de sobras na direita
            if ((page + i) < getSize()) {
                pagesAvaliable.add(page + i);
            } else {
                sobraDePaginasDireita++;
            }
        }
        if (sobraDePaginasEsquerda == 0 || sobraDePaginasDireita == 0) {
            int i;
            for (i = 1; i <= sobraDePaginasDireita; i++) {
                if (page - divisor - i >= 0) {
                    pagesAvaliable.add(page - divisor - i);
                }
            }
            sobraDePaginasDireita = sobraDePaginasDireita - i + 1;
            for (i = 1; i <= sobraDePaginasEsquerda; i++) {
                if (page + divisor + i < getSize()) {
                    pagesAvaliable.add(page + divisor + i);
                }
            }
            sobraDePaginasEsquerda = sobraDePaginasEsquerda - i + 1;
        }
        Collections.sort(pagesAvaliable);
        return pagesAvaliable;
    }

}
