package feb.data.interfaces;

import java.util.Collection;

import feb.data.entities.Token;

/**
 * Data Access Object for FEB Tokens
 * @author Marcos Nunes <marcosn@gmail.com>
 */
public interface TokensDao {
    public void save(Collection<Token> tokens);

    /**
     * Delete all tokens
     */
        public void clearTokens();

}
