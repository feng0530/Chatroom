package tw.idv.frank.chatroom.users.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tw.idv.frank.chatroom.users.model.entity.Users;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users, String> {

    Users findByEmail(String email);

    List<Users> findAllByOrderByCreateTimeAsc();

    @Modifying
    @Query(value = "UPDATE users SET login_status = :loginStatus WHERE user_id = :userId", nativeQuery = true)
    Users updateLoginStatus(@Param("userId") String userId, @Param("loginStatus") String loginStatus);
}
