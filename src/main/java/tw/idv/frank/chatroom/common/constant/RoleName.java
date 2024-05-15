package tw.idv.frank.chatroom.common.constant;

import org.springframework.security.core.GrantedAuthority;

public enum RoleName implements GrantedAuthority {

    ADMIN,

    MANAGER;

    @Override
    public String getAuthority() {
        return name();
    }
}
