package feb.data.daos;

import feb.data.entities.PalavrasOfensivas;
import feb.data.interfaces.PalavrasOfensivasDAO;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author luiz
 */
public class PalavrasOfensivasHibernateDAO implements PalavrasOfensivasDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean contains(String word) {

        Session s = sessionFactory.getCurrentSession();

        word = word.replace("@", "a");
        word = word.replace("$", "s");
        word = word.replace("£", "e");
        word = word.replace("3", "e");
        word = word.replace("1", "l");
        word = word.replace("4", "t");
        word = word.replace("0", "o");
        word = word.replace("¢", "c");
        word = feb.util.Operacoes.removeAcentuacao(word);
        
        String[] tokens = word.split(" ");

        boolean containsBadWord = false;
        for (int i = 0; i < tokens.length; i++) {
            
            List<PalavrasOfensivas> p = s.createCriteria(PalavrasOfensivas.class).add(Restrictions.ilike("word", tokens[i])).list();
            
            if (!(p.isEmpty())) {
                containsBadWord = true;
            }
        }
        
        return (containsBadWord);
    }
}
