package com.cognitivabrasil.feb.solr.camposObaa;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import cognitivabrasil.obaa.OBAA;
import cognitivabrasil.obaa.Educational.Educational;
import cognitivabrasil.obaa.Educational.TypicalAgeRange;


public class EducationalFieldsTest {
    @Test
    public void simpleAgeRange() {
        OBAA obaa = obaaWithAgeRange("9-10");
               
        int min = EducationalFields.getMintypicalagerange(obaa);
        int max = EducationalFields.getMaxtypicalagerange(obaa);
        
        assertThat(min, equalTo(9));
        assertThat(max, equalTo(10));
    }
    
    @Test
    public void ageRangeAsInDatabase() {
        OBAA obaa = obaaWithAgeRange("5 - 19 anos");
        
        int min = EducationalFields.getMintypicalagerange(obaa);
        int max = EducationalFields.getMaxtypicalagerange(obaa);
        
        assertThat(min, equalTo(5));
        assertThat(max, equalTo(19));
    }

    
    @Test
    public void ageRangeInt() {
        OBAA obaa = obaaWithAgeRange("5 - 6 anos");
        
        List<String> l = EducationalFields.getTypicalAgeRangeInt(obaa);
        
        assertThat(l, hasItem("obaa.educational.typicalagerangeint"));
        assertThat(l, hasItem("5"));
        assertThat(l, hasItem("6"));
    }
    
    @Test
    public void testNoAgesLargerThan20() {
        OBAA obaa = obaaWithAgeRange("9-200");
        
        int max = EducationalFields.getMaxtypicalagerange(obaa);

        assertThat(max, equalTo(20));
    }
    
    @Test
    public void testEmptyEducational() {
        OBAA obaa = new OBAA();
        
        List<String> l = EducationalFields.getTypicalAgeRangeInt(obaa);

        assertThat(l, hasSize(1));
    }
    
    
    
    // Test non-number
    
    
    private OBAA obaaWithAgeRange(String r) {
        OBAA obaa = new OBAA();
        
        obaa.setEducational(new Educational());
        
        TypicalAgeRange ageRange = new TypicalAgeRange(r);
        
        obaa.getEducational().setTypicalAgeRanges(Arrays.asList(ageRange));
        
        return obaa;
    }
}
