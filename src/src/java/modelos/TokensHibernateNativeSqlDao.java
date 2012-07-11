/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Marcos Nunes <marcosn@gmail.com>
 */
public class TokensHibernateNativeSqlDao implements TokensDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void delete(DocumentoReal doc) {
        String sql = "DELETE FROM r1tokens where documento_id=" + doc.getId();
        Session s = this.sessionFactory.getCurrentSession();
        s.createSQLQuery(sql).executeUpdate();

    }

    @Override
    public void saveTokens(DocumentoReal doc) {
        if (!doc.generateTokens().isEmpty()) {
            Session s = this.sessionFactory.getCurrentSession();

            String insert = "INSERT INTO r1tokens (token, documento_id, field) VALUES";


            //for para preencher as interrogacoes dos titulos
            for (int i = 0; i < doc.generateTokens().size(); i++) {
                if (i == 0) {
                    insert += " (?,?,?)";
                } else {
                    insert += ", (?,?,?)";
                }
            }

            SQLQuery query = s.createSQLQuery(insert);

            int id = doc.getId(); //recebe o id do documento que foi inserido na tabela documentos
            int cont = 0;
            //1 titulo
            int atributo = 1;
            //for para preencher os values do titulo
            for (int i = 0; i < doc.getTitlesTokenized().size(); i++) {
                int i2 = cont * 3; //variavel para contar o numero da interrogacao do values. A cada iteracao do for ele increnta 3 vezes
                String token = doc.getTitlesTokenized().get(i);

                query.setString(i2, token);
                query.setInteger(i2 + 1, id);
                query.setInteger(i2 + 2, atributo);
                cont++;
            }
            //for para preencher os values das palavras chaves
            //2 palavras chave
            atributo = 2;
            for (int i = 0; i < doc.getKeywordsTokenized().size(); i++) {
                int i2 = cont * 3; //variavel para contar o numero da interrogacao do values. A cada iteracao do for ele increnta 3 vezes
                String token = doc.getKeywordsTokenized().get(i);
                query.setString(i2, token);
                query.setInteger(i2 + 1, id);
                query.setInteger(i2 + 2, atributo);
                cont++;
            }

            //for para preencher os values do xx
            //3 descricao
            atributo = 3;
            for (int i = 0; i < doc.getDescriptionsTokenized().size(); i++) {
                int i2 = cont * 3; //variavel para contar o numero da interrogacao do values. A cada iteracao do for ele increnta 3 vezes
                String token = doc.getDescriptionsTokenized().get(i);
                query.setString(i2, token);
                query.setInteger(i2 + 1, id);
                query.setInteger(i2 + 2, atributo);

                cont++;
            }

            query.executeUpdate();
        }
    }

	public void setSessionFactory(SessionFactory sessionFactory2) {
		sessionFactory = sessionFactory2;		
	}
}
