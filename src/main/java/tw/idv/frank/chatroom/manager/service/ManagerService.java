package tw.idv.frank.chatroom.manager.service;

import tw.idv.frank.chatroom.common.dto.LoginReq;
import tw.idv.frank.chatroom.common.dto.LoginRes;
import tw.idv.frank.chatroom.common.exception.BaseException;
import tw.idv.frank.chatroom.manager.model.dto.AddManagerReq;
import tw.idv.frank.chatroom.manager.model.dto.ManagerRes;
import tw.idv.frank.chatroom.manager.model.dto.UpdateManagerReq;
import tw.idv.frank.chatroom.manager.model.entity.Manager;

import java.util.List;

public interface ManagerService {

    ManagerRes addManager(AddManagerReq addManagerReq) throws BaseException;

    void deleteManagerById(Integer id);

    Manager updateManager(UpdateManagerReq updateManagerReq) throws BaseException;

    List<ManagerRes> getManagerList();

    Manager getManagerById(Integer id) throws BaseException;

    LoginRes login(LoginReq loginReq);
}
