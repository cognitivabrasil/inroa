package feb.data.daos;

import feb.data.entities.PalavrasOfensivas;
import feb.data.interfaces.PalavrasOfensivasDAO;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author luiz
 */
public class PalavrasOfensivasHibernateDAO extends AbstractHibernateDAO<PalavrasOfensivas> implements PalavrasOfensivasDAO {

//    @Autowired
//    private SessionFactory sessionFactory;
    public PalavrasOfensivasHibernateDAO() {
        this.setClazz(PalavrasOfensivas.class);
    }

    @Override
    public PalavrasOfensivas get(int id) {

        Session s = this.sessionFactory.getCurrentSession();
        return (PalavrasOfensivas) s.get(PalavrasOfensivas.class, id);
    }

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
