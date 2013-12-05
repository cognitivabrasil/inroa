/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feb.solr.bd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author daepstein
 */
public class ConfiguracaoBancoDados {

    protected BufferedReader configuracoes;
    protected String end;
    protected String username;
    protected String password;
    protected String host;
    protected int port;
    protected String database;
    protected List<String> dadosBanco;

    public ConfiguracaoBancoDados() {
        end = "./src/main/resources/feb.properties";
        try {
            configuracoes = new BufferedReader(new FileReader(end));

            dadosBanco = new ArrayList<String>();
            String line = null;
            while ((line = configuracoes.readLine()) != null) {
                dadosBanco.add(line.substring(line.indexOf("=")+1) );
            }
        } catch (IOException ex) {
            Logger.getLogger(ConfiguracaoBancoDados.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ConfiguracaoBancoDados(String endereco) {
        try {
            configuracoes = new BufferedReader(new FileReader(endereco));

            dadosBanco = new ArrayList<String>();
            String line = null;
            while ((line = configuracoes.readLine()) != null) {
                dadosBanco.add(line.substring(line.indexOf("=") + 1));
            }

        } catch (IOException ex) {
            Logger.getLogger(ConfiguracaoBancoDados.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public String getUsername ()
    {
        return dadosBanco.get(0);
    }
    
    
    public String getPassword ()
    {
        return dadosBanco.get(1);
    }
    
    public String getHost ()
    {
        return dadosBanco.get(2);
    }
    
    public String getDatabase ()
    {
        return dadosBanco.get(3);
    }
    
    public int getPort ()
    {
        return Integer.parseInt(dadosBanco.get(4));
    }
    
    
}
