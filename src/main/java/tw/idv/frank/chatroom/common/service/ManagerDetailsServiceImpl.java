package tw.idv.frank.chatroom.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tw.idv.frank.chatroom.common.exception.UserNotFoundException;
import tw.idv.frank.chatroom.manager.model.dao.ManagerRepository;
import tw.idv.frank.chatroom.manager.model.dto.ManagerDetails;
import tw.idv.frank.chatroom.manager.model.entity.Manager;

@Service
public class ManagerDetailsServiceImpl implements UserDetailsService {

    @Autowired
    ManagerRepository managerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Manager manager = managerRepository.findByAccount(username);

        if (manager == null) {
            throw new UserNotFoundException("帳號不存在!");
        }
        return new ManagerDetails(manager);
    }
}
