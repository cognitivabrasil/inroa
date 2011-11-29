/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
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
	// Note that the constructor is private
        
        String senhaCriptografada;
        Cipher cipher;
        SecretKeySpec skeySpec;
        String key = "6143b50e03de7b3789693ff65b108565"; // chave de criptografia
        Writer writer;
        File file;
        byte[] encrypted;
        private static final String hexDigits = "0123456789abcdef";

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

            try{
            singletonObject = new SingletonConfig();
            } catch (NoSuchAlgorithmException e) {
                System.out.println("Erro ao criptografar chave: "+e);
            } catch (NoSuchPaddingException e){
                System.out.println("Erro ao criptografar chave: "+e);
            }


            System.out.println("Teste 2");
            try {
                HashMap attributes = null;
                ServletContext context = servletContext;
                Properties properties = (Properties) context.getAttribute("febproperties");

                if (properties == null) {

                    String fileName = servletContext.getInitParameter("febproperties");
                    InputStream in;
                    try {
                        System.out.println("fileName=" + fileName);
                        in = new FileInputStream(fileName);
                    } catch (FileNotFoundException e2) {
                        System.out.println("file not found. Try the classpath: " + fileName);
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
                    System.out.println("Load context properties");
                }

                SingletonConfig c = SingletonConfig.getConfig();

                System.out.println("Store global properties");
                System.out.println("Usuario: " + properties.getProperty("Postgres.usuario"));
                c.setUsuario(properties.getProperty("Postgres.usuario"));
                System.out.println("Senha: " + properties.getProperty("Postgres.senha"));
                c.setSenha(properties.getProperty("Postgres.senha"));

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
        }


        singletonObject.print();
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
            if (hexa.length() % 2 != 0)
                throw new IllegalArgumentException("String hexa inválida");

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
             System.out.println("Usuario: " + usuario);
         
            System.out.println("Senha: " + senha);
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
        try{
            senha = descriptografa(senhaCriptografada);
        } catch (Exception e){
            System.out.println("Erro ao descriptografar a senha: "+e);
        }

        return senha;
    }

    /**
     * @param senha the senha to set
     */
    public void setSenha(String senha) {
        try{
        senhaCriptografada = criptografa(senha);
        } catch (Exception e){
            System.out.println("Erro ao criptografar a senha: "+e);
        }
        //this.senha = senha;
    }
        
/**
 * 
 * @param senha a ser criptografada
 * @return senha criptografada em hexa
 * @throws Exception
 */
    public String criptografa (String senha) throws Exception {

        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        encrypted = cipher.doFinal(senha.getBytes());

        return (asHex(encrypted));

    }


    /**
     *
     * @param encPass senha criptografada
     * @return senha dsecriptografada em bytes
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
}