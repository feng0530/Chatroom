package tw.idv.frank.chatroom.manager.service;

import tw.idv.frank.chatroom.common.exception.BaseException;
import tw.idv.frank.chatroom.manager.model.dto.ManagerRes;
import tw.idv.frank.chatroom.manager.model.dto.ManagerReq;
import tw.idv.frank.chatroom.manager.model.entity.Manager;

import java.util.List;

public interface ManagerService {

    ManagerRes addManager(Manager manager) throws BaseException;

    void deleteManagerById(Integer id);

    Manager updateManager(ManagerReq managerReq) throws BaseException;

    Manager getManagerById(Integer id);

    List<ManagerRes> getManagerList();
}
