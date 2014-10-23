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
 * Classe que auxilia a páginação no resultado da busca.
 *
 * @author Marcos Freitas Nunes <marcos@cognitivabrasil.com.br>
 */
public class PaginationDto {

    public static final int ITENS_PAGINATION = 5;
    private final int size;
    private final int currentPage;
    private final int itenToPresent;
    private List<Integer> pagesAvaliable;

    /**
     * Construtor mínimo. Inicializa apenas o número total de resultados e a quantidade que é exibida na tela de
     * resultado da busca.
     *
     * @param itenToPresent Número de resultados exibidos na tela de busca.
     * @param size Número total de resultados para a consulta efetuada.
     */
    public PaginationDto(int itenToPresent, int size) {
        this.itenToPresent = itenToPresent;
        this.size = size;
        this.currentPage = 0;
    }

    /**
     * Construtor que já inicializa todas informações necessárias para funcionar a paginação.
     *
     * @param itenToPresent Número de resultados exibidos na tela de busca.
     * @param size Número total de resultados para a consulta efetuada.
     * @param currentPage Página atual, se for a primeira deve ser zero.
     */
    public PaginationDto(int itenToPresent, int size, int currentPage) {
        this.size = size;
        this.currentPage = currentPage;
        this.itenToPresent = itenToPresent;
    }

    /**
     * Testa se o número total de resultados é maior que um e retorna o resultado do teste.
     *
     * @return boolean informando se tem página anterior.
     */
    public boolean hasPreviousPage() {
        return currentPage > 0;
    }

    /**
     * Informa se tem próxima página. Divite o número total de resultados pelo número de resultados exibidos na tela e
     * testa se esse número é maior que 1.
     *
     * @return boolean informando se tem próxima página de resultados ou não.
     */
    public boolean hasNextPage() {
        float numberOfPages = (float) getSize() / itenToPresent;
        return getNextPage() < numberOfPages;
    }

    /**
     * Retorna o número total de resultados.
     *
     * @return
     */
    public int getSize() {
        return size;
    }

    /**
     * Retorna a página atual.
     *
     * @return
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * Retorna o número da próxima página.
     *
     * @return
     */
    public int getNextPage() {
        return currentPage + 1;
    }

    /**
     * Retorna o número da página anterior.
     *
     * @return
     */
    public int getPreviousPage() {
        if (currentPage >= 1) {
            return currentPage - 1;
        }
        return currentPage;
    }

    /**
     * Retorna uma lista com o número das páginas que serão exibidas na paginação.
     *
     * @return
     */
    public List<Integer> getPages() {
        if (pagesAvaliable != null) {
            return pagesAvaliable;
        }
        
        if (getSize() <= itenToPresent) {
            return new ArrayList();
        }
        int divisor = ITENS_PAGINATION / 2;

        int sobraDePaginasDireita = 0;
        int sobraDePaginasEsquerda = 0;
        pagesAvaliable = new ArrayList<>();
        float numeroPaginas = (float) getSize() / itenToPresent;
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
            float nextPage = page + i;
            if (nextPage < numeroPaginas) {
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

            for (i = 1; i <= sobraDePaginasEsquerda; i++) {
                float nextPage = page + divisor + i;
                if (nextPage < numeroPaginas) {
                    pagesAvaliable.add(page + divisor + i);
                }
            }
        }
        Collections.sort(pagesAvaliable);
        return pagesAvaliable;
    }
}
