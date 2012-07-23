package feb.data.entities;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

import feb.data.interfaces.FebDomainObject;
import feb.spring.ApplicationContextProvider;

/**
 *
 *  PERMISSIONS ALLOWED
 *  
 *  For security implementation, a user may have many roles, here is a list of suggested roles and what they mean.
 *  
 *  PERM_MANAGE_USERS - This user has permission to create other users, delete them or change them, but he may
 *  not give them more permissions than he already has.
 *  PERM_UPDATE - Permission to update repositories or sub-federations, or recalculate the index
 *  PERM_MANAGE_REP - Permission to add, change and delete existing repositories or subfederations
 *  PERM_MANAGE_METADATA - Permission to add, change and delete metadata standards
 *  PERM_MANAGE_MAPPINGS - Permission do add, change or update mappings
 *  PERM_CHANGE_DATABASE - Permission to change the database config
 *  PERM_VIEW_STATISTICS - Permission to view the statistics
 *  
 *  The main ADMIN user should have all the permissions.
 *
 * @author paulo
 */
public class Usuario implements UserDetails, FebDomainObject {
    private static final long serialVersionUID = -2896658180312977640L;
    private Integer id;
    private String login;
    private String passwordMd5;
    private String description;
    /* Internal representation of permission, as a string separated bu commas */
    private String permissionsInternal;
    private String role;

    private static Logger log = Logger.getLogger(Usuario.class);
    
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
     * @deprecated use {@link #getUsername()} instead.
     */
    @Deprecated
    public String getLogin() {
    	return getUsername();
    }

    /**
     * @param login the login to set
     * @deprecated use {@link #setUsername(String)} instead.
     */
    @Deprecated
    public void setLogin(String login) {
        this.login = login;
    }
    
    /**
     * Sets the username.
     *
     * @param login the new username
     */
    public void setUsername(String login) {
        this.login = login;
    }


    /**
     * @return the passwordMd5
     */
    public String getPasswordMd5() {
        return passwordMd5;
    }

    /**
     * @param passwordMd5 the passwordMd5 to set
     */
    public void setPasswordMd5(String passwordMd5) {
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
    	assert(getPasswordEncoder() != null);
    	setPasswordMd5(getPasswordEncoder().encodePassword(password, null));
    }
    
    /**
     * Authenticates this user
     * @param password Cleartext password
     * @return true if password matches the user, false otherwise
     * @deprecated authentication should be done by feb.spring security
     */
    @Deprecated
    public boolean authenticate(String password) {
        if(password == null) {
            return false;
        }
        return getPasswordMd5().equals(getPasswordEncoder().encodePassword(password, null));
    }

    /*
     * Implemented to satisfy SpringSecurity
     */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities =
				new HashSet<GrantedAuthority>();
		for(String s : getPermissions()) {
				authorities.add(new GrantedAuthorityImpl(s));
		}
				return authorities;

	}

	@Override
	public String getPassword() {
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

	/**
	 * @return the passwordEncoder
	 */
	// TODO: Use autowired here, probalby needs AOP
	private PasswordEncoder getPasswordEncoder() {
        ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
		return ctx.getBean(PasswordEncoder.class);
	}

	/**
	 * @return the permissions
	 */
	public Set<String> getPermissions() {
		return new HashSet<String>(Arrays.asList(StringUtils.split(getPermissionsInternal(), ',')));
	}

	/**
	 * @param perm the permissions to set
	 */
	public void setPermissions(Set<String> perm) {
		setPermissionsInternal(StringUtils.join(perm, ','));
	}

	/**
	 * @return the permissionsInternal
	 */
	private String getPermissionsInternal() {
		return permissionsInternal;
	}

	/**
	 * @param permissionsInternal the permissionsInternal to set
	 */
	private void setPermissionsInternal(String permissionsInternal) {
		this.permissionsInternal = permissionsInternal;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}
	
    @Override
    public String toString() {
    	return getUsername() + "(" + getId() + ")";
    }

	@Override
	public String toStringDetailed() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE, false);
	}

}
