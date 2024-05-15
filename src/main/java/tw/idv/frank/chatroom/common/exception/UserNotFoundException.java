package tw.idv.frank.chatroom.common.exception;

import org.springframework.security.core.AuthenticationException;

public class UserNotFoundException extends AuthenticationException {

    public UserNotFoundException(String msg) {
        super(msg);
    }
}
