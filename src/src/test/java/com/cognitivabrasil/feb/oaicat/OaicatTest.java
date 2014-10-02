package com.cognitivabrasil.feb.oaicat;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.intThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ORG.oclc.oai.models.OaiDocument;
import ORG.oclc.oai.server.OAIHandler;
import ORG.oclc.oai.server.catalog.OaiDocumentService;

import com.cognitivabrasil.feb.AppConfig;
import com.cognitivabrasil.feb.data.repositories.RepositoryRepository;
import com.cognitivabrasil.feb.oai.DataServiceOaiCatalog;

// TODO: portar testes para rodar no módulo oaicat

class FakeOaiDocument implements OaiDocument {
    int identifier;

    public FakeOaiDocument(int b) {
        identifier = b;
    }

    @Override
    public boolean isDeleted() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String getXml() {
        // TODO Auto-generated method stub
        return "<xml>";
    }

    @Override
    public Date getTimestamp() {
        // TODO Auto-generated method stub
        return new Date();
    }

    @Override
    public Collection<String> getSets() {
        // TODO Auto-generated method stub
        return new HashSet<>();
    }

    @Override
    public String getOaiIdentifier() {
        // TODO Auto-generated method stub
        return "oai:" + identifier;
    }
};

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class })
@ActiveProfiles("test")
@DirtiesContext
public class OaicatTest {
    private OAIHandler servlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @Mock
    ApplicationContext ctx;

    @Mock
    RepositoryRepository rep;
    @Mock
    OaiDocumentService service;

    @Before
    public void setUp() throws ServletException {
        MockitoAnnotations.initMocks(this);

        when(ctx.getBean(RepositoryRepository.class)).thenReturn(rep);
        when(ctx.getBean(OaiDocumentService.class)).thenReturn(service);

        DataServiceOaiCatalog.setCtx(ctx);

        servlet = new OAIHandler();
        response = new MockHttpServletResponse();

        MockServletConfig c = new MockServletConfig();
        c.addInitParameter("properties", "dummy.properties");

        servlet.init(c);

    }
    
    @Test
    public void listRecordsSemDataFunciona() throws ServletException, IOException {
        List<OaiDocument> documents = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            OaiDocument d = new FakeOaiDocument(i);
            documents.add(d);
        }

        when(service.count(any(), any())).thenReturn(documents.size() + 100);
        when(service.find(any(), any(), anyInt(), anyInt())).thenReturn(documents.iterator());

        request = new MockHttpServletRequest();
        request.addParameter("verb", "ListRecords");
        request.addParameter("metadataPrefix", "obaa");

        servlet.doGet(request, response);
        
        System.out.println("======================================");
        System.out.println(response.getContentAsString());

        String resumptionToken = getResumptionToken(response.getContentAsString());

        String[] parts = resumptionToken.split("\\|", 4);

        assertThat(Integer.valueOf(parts[0]), equalTo(10));
        assertThat(parts[1], equalTo("obaa"));
        assertThat(parts[2], equalTo(""));
        assertThat(parts[3], equalTo(""));
    }

    @Test
    public void resumptionTokenMantemFromEUntil() throws ServletException, IOException {
        List<OaiDocument> documents = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            OaiDocument d = new FakeOaiDocument(i);
            documents.add(d);
        }

        when(service.count(any(), any())).thenReturn(documents.size() + 100);
        when(service.find(any(), any(), anyInt(), anyInt())).thenReturn(documents.iterator());

        request = new MockHttpServletRequest();
        request.addParameter("verb", "ListRecords");
        request.addParameter("metadataPrefix", "obaa");
        request.addParameter("from", "2014-09-18T16:16:01Z");
        request.addParameter("until", "2014-10-18T16:16:01Z");

        servlet.doGet(request, response);

        String resumptionToken = getResumptionToken(response.getContentAsString());

        String[] parts = resumptionToken.split("\\|");

        assertThat(Integer.valueOf(parts[0]), equalTo(10));
        assertThat(parts[1], equalTo("obaa"));
        assertThat(parts[2], equalTo("2014-09-18T16:16:01Z"));
        assertThat(parts[3], equalTo("2014-10-18T16:16:01Z"));
    }

    @Test
    public void resumptionTokenSemData() throws ServletException, IOException {
        List<OaiDocument> documents = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            OaiDocument d = new FakeOaiDocument(i);
            documents.add(d);
        }

        when(service.count(any(), any())).thenReturn(documents.size() + 100);
        when(service.find(any(), any(), anyInt(), anyInt())).thenReturn(documents.iterator());

        request = new MockHttpServletRequest();
        request.addParameter("verb", "ListRecords");
        request.addParameter("resumptionToken", "10|obaa||");

        servlet.doGet(request, response);

        verify(service).find(argThat(nullValue(Date.class)), argThat(nullValue(Date.class)), intThat(is(10)), anyInt());
    }

    @Test
    public void resumptionTokenSemDataPropagaCorretamente() throws ServletException, IOException {
        List<OaiDocument> documents = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            OaiDocument d = new FakeOaiDocument(i);
            documents.add(d);
        }

        when(service.count(any(), any())).thenReturn(documents.size() + 100);
        when(service.find(any(), any(), anyInt(), anyInt())).thenReturn(documents.iterator());

        request = new MockHttpServletRequest();
        request.addParameter("verb", "ListRecords");
        request.addParameter("resumptionToken", "10|obaa||");

        servlet.doGet(request, response);

        String resumptionToken = getResumptionToken(response.getContentAsString());

        String[] parts = resumptionToken.split("\\|", 4);

        assertThat(Integer.valueOf(parts[0]), equalTo(20));
        assertThat(parts[1], equalTo("obaa"));
        assertThat(parts[2], equalTo(""));
        assertThat(parts[3], equalTo(""));
    }
    
    @Test
    public void resumptionComDataPropagaCorretamente() throws ServletException, IOException {
        List<OaiDocument> documents = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            OaiDocument d = new FakeOaiDocument(i);
            documents.add(d);
        }

        when(service.count(any(), any())).thenReturn(documents.size() + 100);
        when(service.find(any(), any(), anyInt(), anyInt())).thenReturn(documents.iterator());

        request = new MockHttpServletRequest();
        request.addParameter("verb", "ListRecords");
        request.addParameter("resumptionToken", "10|obaa|2014-09-18T16:16:01Z|2014-10-18T16:16:01Z");

        servlet.doGet(request, response);

        String resumptionToken = getResumptionToken(response.getContentAsString());

        String[] parts = resumptionToken.split("\\|", 4);

        assertThat(Integer.valueOf(parts[0]), equalTo(20));
        assertThat(parts[1], equalTo("obaa"));
        assertThat(parts[2], equalTo("2014-09-18T16:16:01Z"));
        assertThat(parts[3], equalTo("2014-10-18T16:16:01Z"));
    }

    @Test
    public void splitResumptionToken() {
        String[] split = "10|obaa||".split("\\|", 4);

        assertThat(split.length, equalTo(4));
    }

    private String getResumptionToken(String contentAsString) {
        Pattern p = Pattern.compile(".*resumptionToken[^>]*>([^<]+)<.*", Pattern.DOTALL);// );
        Matcher m = p.matcher(new String(contentAsString.getBytes()));
        m.matches();
        return m.group(1);

    }
}