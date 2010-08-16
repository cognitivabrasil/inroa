/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operacoesLdap;

/**
 * Reinicia o serviço Ldap para que utilize a nova configuração do arquivo slapd conf. É necessário dar permissão de sudo para o usuário tomcat para o serviço "/etc/init.d/ldap" sem solicitar senha.
 * @author Marcos
 */
public class ReiniciarServico {

    boolean resultado = false;

    /**
     * Reinicia o serviço Ldap para que utilize a nova configuração do arquivo slapd conf. É necessário dar permissão de sudo para o usuário tomcat para o serviço "/etc/init.d/ldap" sem solicitar senha.
     */
    public ReiniciarServico() {
        try {

            Process proc = Runtime.getRuntime().exec("sudo /etc/init.d/ldap restart"); //executa o comando de restart no linux
//            BufferedReader leitor = new BufferedReader(new InputStreamReader(proc.getInputStream()));
//
//            String linha = null;
//            while ((linha = leitor.readLine()) != null) {
//                System.out.println(linha);
//            }
            resultado = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Retorna o resultado o processo de reiniciar o serviço ldap no linux
     * @return Retorna true se o comando para reiniciar o serviço foi executado com sucesso, ou false se ocorreu algum erro ao executar o comando.
     */
    public boolean getResultado(){
        return this.resultado;
    }
}
