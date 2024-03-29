/**
 * Copyright 2006 OCLC Online Computer Library Center Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under the
 * License.
 */
package com.cognitivabrasil.feb.oai;

import ORG.oclc.oai.server.catalog.AbstractServiceOaiCatalog;
import ORG.oclc.oai.server.catalog.OaiDocumentService;

import com.cognitivabrasil.feb.data.entities.Repositorio;
import com.cognitivabrasil.feb.data.repositories.RepositoryRepository;
import com.cognitivabrasil.feb.spring.ApplicationContextProvider2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.context.ApplicationContext;

/**
 * DummyOAICatalog is an example of how to implement the AbstractCatalog interface. Pattern an implementation of the
 * AbstractCatalog interface after this class to have OAICat work with your database. Your effort may be minimized by
 * confining your changes to areas identified by "YOUR CODE GOES HERE" comments. In truth, though, you can do things
 * however you want, as long as the non-private methods return what they're supposed to.
 *
 * @author Jeffrey A. Young, OCLC Online Computer Library Center
 */
public class DataServiceOaiCatalog extends AbstractServiceOaiCatalog {

    private static ApplicationContext appContext = null;

    public static ApplicationContext getCtx() {
        if (appContext == null) {
            appContext = ApplicationContextProvider2.getApplicationContext();
        }
        return appContext;
    }

    public static void setCtx(ApplicationContext ctx) {
        appContext = ctx;
    }

    public DataServiceOaiCatalog(Properties properties) {
        super(properties);
    }

    /**
     * Retrieve a list of sets that satisfy the specified criteria
     *
     * @return a Map object containing "sets" Iterator object (contains <setSpec/> XML Strings) as well as an optional
     * resumptionMap Map.
     * @exception OAIBadRequestException signals an http status code 400 problem
     */
    @Override
    public Map<String, Iterator<String>> listSets() {
        RepositoryRepository rep = getCtx().getBean(RepositoryRepository.class);

        List<Repositorio> repositorios = rep.findAll();

        // clean out old resumptionTokens
        purge();
        Map<String, Iterator<String>> listSetsMap = new HashMap<>();

        List<String> sets = new ArrayList<>();

        for (Repositorio r : repositorios) {
            StringBuilder s = new StringBuilder(200);
            s.append("<set>\n");
            s.append("<setSpec>");
            s.append(r.getName());
            s.append("</setSpec>\n");
            s.append("<setName>");
            s.append(r.getName());
            s.append("</setName>\n");
            s.append("</set>\n");
            sets.add(s.toString());
        }

        listSetsMap.put("sets", sets.iterator());
        return listSetsMap;
    }

    @Override
    public OaiDocumentService getDocumentService() {
        return getCtx().getBean(OaiDocumentService.class);
    }
}
