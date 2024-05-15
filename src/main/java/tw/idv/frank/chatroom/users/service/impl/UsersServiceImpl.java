package tw.idv.frank.chatroom.users.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tw.idv.frank.chatroom.common.constant.CommonCode;
import tw.idv.frank.chatroom.common.dto.LoginReq;
import tw.idv.frank.chatroom.common.dto.LoginRes;
import tw.idv.frank.chatroom.common.exception.BaseException;
import tw.idv.frank.chatroom.common.provider.UsersProvider;
import tw.idv.frank.chatroom.common.service.TokenService;
import tw.idv.frank.chatroom.users.model.dao.UsersRepository;
import tw.idv.frank.chatroom.users.model.dto.*;
import tw.idv.frank.chatroom.users.model.entity.Users;
import tw.idv.frank.chatroom.users.service.UsersService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsersProvider provider;

    @Override
    public UsersRes addUsers(AddUsersReq addUsersReq) throws BaseException {
        validEmailExist(addUsersReq);
        return addFunc(addUsersReq);
    }

    @Override
    public void deleteUsersById(String id) {
        usersRepository.deleteById(id);
    }

    @Override
    public UsersRes updateUsers(UpdateUsersReq updateUsersReq) throws BaseException {
        Users users = getUsers(updateUsersReq.getUserId());
        return updateFunc(users, updateUsersReq);
    }

    @Override
    public List<UsersRes> getUsersList() {
        return usersRepository.findAllByOrderByCreateTimeAsc()
                                .stream()
                                .map(users -> modelMapper.map(users, UsersRes.class))
                                .collect(Collectors.toList());
    }

    @Override
    public UsersRes getUsersById(String id) throws BaseException {
        return modelMapper.map(getUsers(id), UsersRes.class);
    }

    @Override
    public UsersRes enableUsers(EnableUsersReq enableUsersReq) throws BaseException {
        return enableUserFunc(enableUsersReq);
    }

    @Override
    public LoginRes login(LoginReq loginReq) {
        UsersRes usersRes = getUsersRes(loginReq);
        return tokenService.generalToken(usersRes);
    }

    private UsersRes getUsersRes(LoginReq loginReq) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginReq.getAccount(), loginReq.getPassword());
        authentication = provider.authenticate(authentication);
        UsersDetails usersDetails = (UsersDetails) authentication.getPrincipal();
        UsersRes usersRes = modelMapper.map(usersDetails.getUsers(), UsersRes.class);
        return usersRes;
    }

    private void validEmailExist(AddUsersReq addUsersReq) throws BaseException {
        Users users = usersRepository.findByEmail(addUsersReq.getEmail());

        if (users != null) {
            log.error("新增失敗: {}", CommonCode.U901.getMes());
            throw new BaseException(CommonCode.U901);
        }
    }

    private UsersRes addFunc(AddUsersReq addUsersReq) {
        addUsersReq.setPassword(passwordEncoder.encode(addUsersReq.getPassword()));
        Users users = modelMapper.map(addUsersReq, Users.class);
        return modelMapper.map(usersRepository.save(users), UsersRes.class);
    }


    private Users getUsers(String id) throws BaseException {
        Users users = usersRepository.findById(id).orElse(null);

        if (users == null) {
            log.error(CommonCode.U902.getMes());
            throw new BaseException(CommonCode.U902);
        }
        return users;
    }

    private UsersRes updateFunc(Users users, UpdateUsersReq updateUsersReq) {
        users.setName(updateUsersReq.getName());
        users.setPassword(updateUsersReq.getPassword());
        users.setGender(updateUsersReq.getGender());
        users.setPhone(updateUsersReq.getPhone());
        users.setBirthday(updateUsersReq.getBirthday());
        users.setPhoto(updateUsersReq.getPhoto());
        return modelMapper.map(usersRepository.save(users), UsersRes.class);
    }

    private UsersRes enableUserFunc(EnableUsersReq enableUsersReq) throws BaseException {
        Users users = getUsers(enableUsersReq.getUserId());
        users.setUserStatus(enableUsersReq.getUserStatus());
        return modelMapper.map(usersRepository.save(users), UsersRes.class);
    }
}
