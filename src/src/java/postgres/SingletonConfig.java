/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package postgres;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletContext;

/**
 *
 * @author paulo
 */
public class SingletonConfig {

    private static SingletonConfig singletonObject;
    static ServletContext servletContext;
    private String usuario;
    private String senha;
    private int porta;
    private String IP;
    private String base;
    String senhaCriptografada;
    Cipher cipher;
    SecretKeySpec skeySpec;
    String key = "6143b50e03de7b3789693ff65b108565"; // chave de criptografia
    Writer writer;
    File file;
    byte[] encrypted;
    private static final String hexDigits = "0123456789abcdef";

    // Note that the constructor is private
    private SingletonConfig() throws NoSuchAlgorithmException, NoSuchPaddingException {

        byte[] hexByte = asByte(key);

        // Generate the secret key specs.
        this.skeySpec = new SecretKeySpec(hexByte, "AES");

        // Instantiate the cipher
        this.cipher = Cipher.getInstance("AES");

        writer = null;
    }

    public static SingletonConfig getConfig() {
        if (singletonObject == null) {

            try {
                singletonObject = new SingletonConfig();
            } catch (NoSuchAlgorithmException e) {
                System.err.println("FEB ERRO: Erro ao iniciar o cifrador. O algoritmo informado nao existe. Mensagem: " + e);
            } catch (NoSuchPaddingException e) {
                System.err.println("FEB ERRO: Erro ao iniciar o cifrador: " + e);
            }

            try {

                ServletContext context = servletContext;
                Properties properties = (Properties) context.getAttribute("febproperties");

                if (properties == null) {

                    String fileName = servletContext.getInitParameter("febproperties");
                    InputStream in;
                    try {
                        //                  System.out.println("fileName=" + fileName);
                        in = new FileInputStream(fileName);
                    } catch (FileNotFoundException e2) {
                        //                  System.out.println("file not found. Try the classpath: " + fileName);
                        in = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
                    }
                    if (in != null) {
                        System.out.println("file was found: Load the properties");
                        properties = new Properties();
                        properties.load(in);
                        //attributes = getAttributes(properties);
                        System.out.println("OAIHandler.init: fileName=" + fileName);
                    }
                } else {
                    //              System.out.println("Load context properties");
                }

                SingletonConfig c = singletonObject;

                //         System.out.println("Store global properties");

                //           System.out.println("Usuario: " + properties.getProperty("Postgres.user"));
                c.setUsuario(properties.getProperty("Postgres.user"));
                //          System.out.println("Senha: " + properties.getProperty("Postgres.password"));
                c.setSenha(properties.getProperty("Postgres.password"));
                //           System.out.println("Porta: " + properties.getProperty("Postgres.port"));
                c.setPorta(properties.getProperty("Postgres.port"));
//            System.out.println("Base: " + properties.getProperty("Postgres.base"));
                c.setBase(properties.getProperty("Postgres.base"));
                //           System.out.println("IP: " + properties.getProperty("Postgres.IP"));
                c.setIP(properties.getProperty("Postgres.IP"));

            } catch (FileNotFoundException e2) {
                e2.printStackTrace();
                //  throw new ServletException(e2.getMessage());
            } catch (IllegalArgumentException e2) {
                e2.printStackTrace();
                //  throw new ServletException(e2.getMessage());
            } catch (IOException e2) {
                e2.printStackTrace();
                //  throw new ServletException(e2.getMessage());
            } catch (Throwable e2) {
                e2.printStackTrace();
                // throw new ServletException(e2.getMessage());
            }
           // singletonObject.print(); //imprime o que foi lido do arquivo
        }


    
        return singletonObject;
    }

    /**
     * Converte uma String hexa no array de bytes correspondente.
     *
     * @param hexa
     *            - A String hexa
     * @return O vetor de bytes
     * @throws IllegalArgumentException
     *             - Caso a String não sej auma representação haxadecimal válida
     */
    private static byte[] asByte(String hexa) throws IllegalArgumentException {

        // verifica se a String possui uma quantidade par de elementos
        if (hexa.length() % 2 != 0) {
            throw new IllegalArgumentException("String hexa inválida");
        }

        byte[] b = new byte[hexa.length() / 2];

        for (int i = 0; i < hexa.length(); i += 2) {
            b[i / 2] = (byte) ((hexDigits.indexOf(hexa.charAt(i)) << 4) | (hexDigits.indexOf(hexa.charAt(i + 1))));
        }
        return b;
    }

    /**
     * Turns array of bytes into string
     *
     * @param buf	Array of bytes to convert to hex string
     * @return	Generated hex string
     */
    private static String asHex(byte buf[]) {
        StringBuffer strbuf = new StringBuffer(buf.length * 2);
        int i;

        for (i = 0; i < buf.length; i++) {
            if (((int) buf[i] & 0xff) < 0x10) {
                strbuf.append("0");
            }

            strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
        }

        return strbuf.toString();
    }

    public static void initConfig(ServletContext c) {
        servletContext = c;
    }

    public void print() {
        System.out.println("Usuario: " + this.getUsuario());
        System.out.println("Senha: " + this.getSenha());
        System.out.println("Base: " + this.getBase());
        System.out.println("IP: " + this.getIP());
        System.out.println("Porta: " + this.getPorta());
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the senha
     */
    public String getSenha() {
        return senha;
    }

    /**
     * Gets a cryptographed password as input and updates
     * the singleton object with the cleartext password.
     * 
     * @param senhaCriptografada a senha criptografada
     */
    public void setSenha(String senhaCriptografada) {
        try {
            this.senha = descriptografa(senhaCriptografada);
            this.senhaCriptografada = senhaCriptografada;
        } catch (Exception e) {
            System.out.println("Erro ao descriptografar a senha: " + e);
        }

    }

    public int getPorta() {
        return porta;
    }

    public void setPorta(String porta) {

        this.porta = (int) new Integer(porta);
        //     System.out.println("porta: "+this.porta);
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    /**
     *
     * @param senha a ser criptografada
     * @return senha criptografada em hexa
     * @throws Exception
     */
    public String criptografa(String senha) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException {

        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        encrypted = cipher.doFinal(senha.getBytes());

        return (asHex(encrypted));

    }

    /**
     *
     * @param encPass senha criptografada
     * @return senha descriptografada em bytes
     * @throws BadPaddingException
     * @throws IllegalArgumentException
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException
     */
    public String descriptografa(String encPass) throws BadPaddingException, IllegalArgumentException, IllegalBlockSizeException, InvalidKeyException {

        cipher.init(Cipher.DECRYPT_MODE, skeySpec);

        byte[] original = cipher.doFinal(asByte(encPass));
        return (new String(original));

    }

    /**
     * M&eacute;todo para gerar nova senha criptografada
     * @param senhaDescriptografada Senha que ser&aacute; criptografada
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException Erro interno de classe. Chave de criptografia inv&aacute;lida
     */
    public void setSenhaCriptografada(String senhaDescriptografada) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        this.senhaCriptografada = criptografa(senhaDescriptografada);
    }

    /**
     * Cria o arquivo feb.proprieties.
     */
    public boolean criaArquivo() {
        boolean resultado = false;
        try {
            Properties properties = new Properties();
            String fileName = servletContext.getInitParameter("febproperties");
            writer = new BufferedWriter(new FileWriter(fileName));
            
            properties.setProperty("Postgres.user", usuario);
            properties.setProperty("Postgres.password", senhaCriptografada);
            properties.setProperty("Postgres.IP", IP);
            properties.setProperty("Postgres.base", base);
            properties.setProperty("Postgres.port", String.valueOf(porta));

            properties.store(writer, "Dados para a conexão no banco PostgreSQL do FEB");
            resultado = true;
        } catch (FileNotFoundException e) {
            System.out.println("FEB ERRO: Probemas ao criar o arquivo de senha " + e);
        } catch (IOException e) {
            System.out.println("FEB ERRO: Probemas ao escrever no arquivo de senha, verifique se o arquivo 'pass' foi criado, apague e tente novamente " + e);
        } finally {
            try {
                writer.close();

            } catch (IOException e) {
                System.out.println("FEB ERRO: Erro ao fechar arquivo:" + e);
            }
            return resultado;
        }
    }
}