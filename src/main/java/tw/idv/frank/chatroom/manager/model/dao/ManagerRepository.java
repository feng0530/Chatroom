package tw.idv.frank.chatroom.manager.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.idv.frank.chatroom.manager.model.entity.Manager;

import java.util.List;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer> {

    Manager findByAccount(String email);

    List<Manager> findByOrderByManagerIdAsc();
}
