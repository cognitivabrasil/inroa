package com.cognitivabrasil.feb.data.services;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.cognitivabrasil.feb.AppConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class })
@ActiveProfiles("test")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SearchServiceIT {
    @Autowired
    SearchService searchService;
    
    @Autowired
    TagCloudService tagCloudService;
 
    @Test
    public void testIndex(){
        searchService.save("marcos", DateTime.now());
        searchService.save("marcos", DateTime.now());
        searchService.save("marcos", DateTime.now());
        searchService.save("nunes", DateTime.now());
        searchService.save("nunes", DateTime.now());
                
        Map<String, Integer> tagCloud = tagCloudService.getTagCloud();
        assertThat(tagCloud, notNullValue());
        assertThat(tagCloud.size(), equalTo(2));
    }
}
