/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.DigestUtils;

/**
 *
 * @author paulo
 */
public class Usuario implements UserDetails {
    private Integer id;
    private String login;
    private String passwordMd5;
    private String description;

    static Logger log = Logger.getLogger(Usuario.class.getName());
    
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
    @Deprecated
    public String getLogin() {
    	return getUsername();
    }

    /**
     * @param login the login to set
     */
    @Deprecated
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

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities =
				new HashSet<GrantedAuthority>();
				authorities.add(new GrantedAuthorityImpl("ADMIN"));
				return authorities;

	}

	@Override
	public String getPassword() {
		log.debug("Got password: " + getPasswordMd5());
		return getPasswordMd5();
	}

	@Override
	public String getUsername() {
        return login;

	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
}
