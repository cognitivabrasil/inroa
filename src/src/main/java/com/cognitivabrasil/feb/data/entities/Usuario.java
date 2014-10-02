package com.cognitivabrasil.feb.data.entities;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

import com.cognitivabrasil.feb.data.interfaces.FebDomainObject;
import com.cognitivabrasil.feb.spring.ApplicationContextProvider2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * PERMISSIONS ALLOWED
 *
 * For security implementation, a user may have many roles, here is a list of
 * suggested roles and what they mean.
 *
 * ROLE_MANAGE_USERS - This user has permission to create other users, delete
 * them or change them, but he may not give them more permissions than he
 * already has. PERM_UPDATE - Permission to update repositories or
 * sub-federations, or recalculate the index ROLE_MANAGE_REP - Permission to
 * add, change and delete existing repositories or subfederations
 * ROLE_MANAGE_METADATA - Permission to add, change and delete metadata
 * standards PERM_MANAGE_MAPPINGS - Permission do add, change or update mappings
 * ROLE_CHANGE_DATABASE - Permission to change the database config
 * ROLE_MANAGE_STATISTICS - Permission to delete from statistics' data
 *
 * The main ADMIN user should have all the permissions.
 *
 * @author Paulo Schreiner
 */
@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails, FebDomainObject {

    private static final long serialVersionUID = -2896658180312977640L;
    private Integer id;
    private String userName;
    private String passwordMd5;
    private String description;
    /* Internal representation of permission, as a string separated bu commas */
    private String permissionsInternal;
    private String role;
    private static PasswordEncoder passEncoder;

    /**
     * @return the id
     */
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USUARIOS_SEQ")
    @SequenceGenerator(name="USUARIOS_SEQ", sequenceName="USUARIOS_SEQ")
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
     * @return the userName
     * @deprecated use {@link #getUsername()} instead.
     */
    @Deprecated
    @Transient
    public String getLogin() {
        return getUsername();
    }

    /**
     * @param login the userName to set
     * @deprecated use {@link #setUsername(String)} instead.
     */
    @Deprecated
    public void setLogin(String login) {
        this.userName = login;
    }
    
    @Override
    @Column(name = "login")
    public String getUsername() {
        return userName;

    }

    /**
     * Sets the username.
     *
     * @param userName the new username
     */
    public void setUsername(String userName) {
        this.userName = userName;
    }

    /**
     * @return the passwordMd5
     */
    @Column(name = "senha")
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
    @Column(name = "nome")
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
     *
     * @param password New password
     */
    public void setPassword(String password) {
        assert (getPasswordEncoder() != null);
        setPasswordMd5(getPasswordEncoder().encodePassword(password, null));
    }

    /**
     * Authenticates this user
     *
     * @param password Cleartext password
     * @return true if password matches the user, false otherwise
     * @deprecated authentication should be done by feb.spring security
     */
    @Deprecated
    public boolean authenticate(String password) {
        if (password == null) {
            return false;
        }
        return getPasswordMd5().equals(getPasswordEncoder().encodePassword(password, null));
    }

    /*
     * Implemented to satisfy SpringSecurity
     */
    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (String s : getPermissions()) {
            authorities.add(new GrantedAuthorityImpl(s));
        }
        return authorities;

    }

    @Override
    @Transient
    public String getPassword() {
        return getPasswordMd5();
    }

    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    @Transient
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }

    /**
     * @return the passwordEncoder
     */
    // TODO: Use autowired here, probalby needs AOP
    @Transient
    private static PasswordEncoder getPasswordEncoder() {
        if (passEncoder == null) {
            ApplicationContext ac = ApplicationContextProvider2.getApplicationContext();
            passEncoder = ac.getBean(PasswordEncoder.class);
        }
        return passEncoder;
    }

    /**
     * Retorna os Roles (permissões) do usuário.
     * 
     * Utilizado pelo SpringSecurity. Traduz formato antigo (FEB 2, PERM_XXX) para formato novo
     * (ROLE_XXX).
     * 
     * @return the permissions
     */
    @Transient
    public Set<String> getPermissions() {
        return Arrays.asList(StringUtils.split(getPermissionsInternal(), ',')).stream()
            .map(s -> s.replaceAll("PERM_", "ROLE_"))
            .collect(Collectors.toSet());      
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
    @Column(name = "permissions")
    protected String getPermissionsInternal() {
        return permissionsInternal;
    }

    /**
     * @param permissionsInternal the permissionsInternal to set
     */
    protected void setPermissionsInternal(String permissionsInternal) {
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

    @Override
    @Transient
    public String getName() {
        return getUsername();
    }
}