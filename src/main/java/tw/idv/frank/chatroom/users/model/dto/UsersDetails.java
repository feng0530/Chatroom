package tw.idv.frank.chatroom.users.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tw.idv.frank.chatroom.users.model.entity.Users;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersDetails implements UserDetails {

    private Users users;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return users.getPassword();
    }

    @Override
    public String getUsername() {
        return users.getName();
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
        if ("0".equals(users.getUserStatus())) {
            return false;
        }
        return true;
    }
}
