package feb.solr.query;

import java.util.ArrayList;
import java.util.List;

public class QueryResposta {
 
    private List<Integer> idsDocumentos;
    
    public QueryResposta() {
        
        idsDocumentos = new ArrayList<Integer>();
       
    }
    
    public List<Integer> getIDs ()
    { return idsDocumentos; }

    public void setIDs (List<Integer> ids)
    { idsDocumentos = ids; }
    
    
}
