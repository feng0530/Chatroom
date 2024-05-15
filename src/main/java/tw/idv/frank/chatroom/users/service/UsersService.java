package tw.idv.frank.chatroom.users.service;

import tw.idv.frank.chatroom.common.dto.LoginReq;
import tw.idv.frank.chatroom.common.dto.LoginRes;
import tw.idv.frank.chatroom.common.exception.BaseException;
import tw.idv.frank.chatroom.users.model.dto.AddUsersReq;
import tw.idv.frank.chatroom.users.model.dto.EnableUsersReq;
import tw.idv.frank.chatroom.users.model.dto.UpdateUsersReq;
import tw.idv.frank.chatroom.users.model.dto.UsersRes;

import java.util.List;

public interface UsersService {

    UsersRes addUsers(AddUsersReq addUsersReq) throws BaseException;

    void deleteUsersById(String id);

    UsersRes updateUsers(UpdateUsersReq updateUsersReq) throws BaseException;

    List<UsersRes> getUsersList();

    UsersRes getUsersById(String id) throws BaseException;

    UsersRes enableUsers(EnableUsersReq enableUsersReq) throws BaseException;

    LoginRes login(LoginReq loginReq);
}
