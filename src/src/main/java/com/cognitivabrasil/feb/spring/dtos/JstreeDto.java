/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cognitivabrasil.feb.spring.dtos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marcos Freitas Nunes <marcos@cognitivabrasil.com.br>
 */
public class JstreeDto {
 
    
    String label;
    List<JstreeDto> children;
    String value;
        
    public JstreeDto(String label, List<JstreeDto> children) {
        this.label = label;
        this.children = children;
    }
    
    public JstreeDto(String label, JstreeDto children) {
        this.label = label;
        this.children = new ArrayList<JstreeDto>();
        this.children.add(children);
    }

    public JstreeDto(String label, String value) {
        this.label = label;
        this.value = value;
    }
        
    public JstreeDto(String label) {
        this.label = label;
    }
    
    public String getLabel() {
        return label;
    }

    public void setLabel(String text) {
        this.label = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<JstreeDto> getChildren() {
        return children;
    }

    public void setChildren(List<JstreeDto> children) {
        this.children = children;
    }
    
    public void addChildren(JstreeDto children){
        if(this.children ==null){
            this.children = new ArrayList<JstreeDto>();
        }
        this.children.add(children);
    }   
    
}
