package tw.idv.frank.chatroom.users.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.idv.frank.chatroom.users.model.entity.Users;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users, String> {

    Users findByEmail(String email);

    List<Users> findAllByOrderByCreateTimeAsc();
}
