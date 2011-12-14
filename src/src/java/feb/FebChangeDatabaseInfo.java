/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feb;

import java.io.Console;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author paulo
 */
public class FebChangeDatabaseInfo {

    static Cipher cipher;
    static SecretKeySpec skeySpec;
    static String key = "6143b50e03de7b3789693ff65b108565"; // chave de criptografia
    static byte[] encrypted;
    static byte[] hexByte;
    private static final String hexDigits = "0123456789abcdef";
    /**
     * @param args the command line arguments
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
    
    public static String criptografa(String senha) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException {

        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        encrypted = cipher.doFinal(senha.getBytes());

        return (asHex(encrypted));

    }
    
    
    public static void main(String[] args) throws BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException {
       hexByte = asByte(key);

        // Generate the secret key specs.
        skeySpec = new SecretKeySpec(hexByte, "AES");

        // Instantiate the cipher
        cipher = Cipher.getInstance("AES");
        
        Properties   properties = new Properties();
       
	String filename = "feb.properties";
       try {
         FileInputStream in = new FileInputStream(filename);
        properties.load(in);
        in.close();

        FileOutputStream out = new FileOutputStream(filename);
        
        Console cons;
        char[] passwd;
        if ((cons = System.console()) == null) {
               System.out.println("Erro!");
        }
     //(passwd = cons.readPassword("[%s]", "Password:")) != null) {
     //...
     //java.util.Arrays.fill(passwd, ' ');
        
        System.out.println("Digite o nome de usuário (" + properties.getProperty("Postgres.user") + "): ");
        String usuario = cons.readLine();
        if(!usuario.equals("")) {
            properties.setProperty("Postgres.user", usuario);
        }
          
        System.out.println("Digite o IP do PostgreSQL (" + properties.getProperty("Postgres.IP") + "): ");
        String IP = cons.readLine();
        if(!IP.equals("")) {
            properties.setProperty("Postgres.IP", IP);
        }
        
        System.out.println("Digite o nome da base de dados (" + properties.getProperty("Postgres.base") + "): ");
        String base = cons.readLine();
        if(!(base.equals(""))) {
            properties.setProperty("Postgres.base", base);
        }
        
        System.out.println("Digite a porta do Postgresql (" + properties.getProperty("Postgres.port") + "): ");
        String port = cons.readLine();
        if(!(port.equals(""))) {
            properties.setProperty("Postgres.port", port);
        }
        System.out.println("Digite a senha: ");
        char passwd1[] = cons.readPassword();
        String pass1 = new String(passwd1);
        
        if(!pass1.equals("")) {
        
            System.out.println("Confirme a senha: ");
            char passwd2[] = cons.readPassword();
        
        
            String pass2 = new String(passwd2);
        
            if(pass1.equals(pass2)) {
                     properties.setProperty("Postgres.password", criptografa(pass1));
            }
            else {
                System.out.println("Senhas não conferem, abortando...");
                System.exit(1);
            }
        
//        properties.setProperty("Postgres.password", senhaCriptografada);
//        properties.setProperty("Postgres.IP", IP);
//        properties.setProperty("Postgres.base", base);
//        properties.setProperty("Postgres.port", String.valueOf(porta));
        
        }
        
        properties.store(out, "Postgres written from console");
        out.close();
        
       }
       catch(FileNotFoundException f) {
           System.out.println("File " + filename + " not found. Please verify that the file exists and that you have permission to alter the file.");
       }
       catch (IOException e) {
           System.out.println("IO/Error");
       }
        
    }
}
