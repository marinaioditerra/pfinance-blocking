package it.christianb.pfinanceblocking.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "USERS", uniqueConstraints = @UniqueConstraint(name = "user_username", columnNames = "username"))
public class User extends AbstractBaseEntity implements UserDetails {

    @Getter @Setter
    @Column(name = "username", nullable = false)
    @NotNull
    private String username;

    @Getter @Setter
    @Column(name = "password", nullable = false)
    @NotNull
    private String password;

    @Getter @Setter
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private Set<Deposit> deposits;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
