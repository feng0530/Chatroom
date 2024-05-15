package tw.idv.frank.chatroom.manager.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tw.idv.frank.chatroom.manager.model.entity.Manager;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManagerDetails implements UserDetails {

    private Manager manager;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(manager.getRole());
    }

    @Override
    public String getPassword() {
        return manager.getPassword();
    }

    @Override
    public String getUsername() {
        return manager.getName();
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
