/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import org.springframework.util.DigestUtils;

/**
 *
 * @author paulo
 */
public class Usuario {
    private Integer id;
    private String login;
    private String passwordMd5;
    private String description;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the passwordMd5
     */
    protected String getPasswordMd5() {
        return passwordMd5;
    }

    /**
     * @param passwordMd5 the passwordMd5 to set
     */
    protected void setPasswordMd5(String passwordMd5) {
        this.passwordMd5 = passwordMd5;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Sets the user password
     * @param password New password
     */
    public void setPassword(String password) {
        setPasswordMd5(DigestUtils.md5DigestAsHex(password.getBytes()));
    }
    
    /**
     * Authenticates this user
     * @param password Cleartext password
     * @return true if password matches the user, false otherwise
     */
    public boolean authenticate(String password) {
        if(password == null) {
            return false;
        }
        return getPasswordMd5().equals(DigestUtils.md5DigestAsHex(password.getBytes()));
    }
}
