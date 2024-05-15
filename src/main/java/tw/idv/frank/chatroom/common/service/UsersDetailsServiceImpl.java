package tw.idv.frank.chatroom.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tw.idv.frank.chatroom.common.exception.UserNotFoundException;
import tw.idv.frank.chatroom.users.model.dao.UsersRepository;
import tw.idv.frank.chatroom.users.model.dto.UsersDetails;
import tw.idv.frank.chatroom.users.model.entity.Users;

@Service
public class UsersDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = usersRepository.findByEmail(username);

        if (users == null) {
            throw new UserNotFoundException("帳號不存在!");
        }
        return new UsersDetails(users);
    }
}
