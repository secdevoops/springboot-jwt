package es.secdevoops.springboot.jwt.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="user_account")
public class UserAccount implements UserDetails {
    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private boolean enabled;

    @NotNull
    @Column(name = "account_non_expired")
    private boolean accountNonExpired;

    @NotNull
    @Column(name = "credentials_non_expired")
    private boolean credentialsNonExpired;

    @NotNull
    @Column(name = "account_non_locked")
    private boolean accountNonLocked;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_account_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    public UserAccount(String username, String password) {
        this.username = username;
        this.password = password;
        this.enabled = true;
        this.accountNonExpired = true;
        this.credentialsNonExpired = true;
        this.accountNonLocked = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        roles.forEach(role ->grantedAuthorities.add(new SimpleGrantedAuthority(role.getRolename())));
        return grantedAuthorities;
    }
}