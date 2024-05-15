package tw.idv.frank.chatroom.manager.service.impl;

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
import tw.idv.frank.chatroom.common.provider.ManagerProvider;
import tw.idv.frank.chatroom.common.service.TokenService;
import tw.idv.frank.chatroom.manager.model.dao.ManagerRepository;
import tw.idv.frank.chatroom.manager.model.dto.AddManagerReq;
import tw.idv.frank.chatroom.manager.model.dto.ManagerDetails;
import tw.idv.frank.chatroom.manager.model.dto.ManagerRes;
import tw.idv.frank.chatroom.manager.model.dto.UpdateManagerReq;
import tw.idv.frank.chatroom.manager.model.entity.Manager;
import tw.idv.frank.chatroom.manager.service.ManagerService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ManagerProvider provider;

    @Override
    public ManagerRes addManager(AddManagerReq addManagerReq) throws BaseException {
        validAccountExist(addManagerReq);
        return addFunc(addManagerReq);
    }

    @Override
    public void deleteManagerById(Integer id) {
        managerRepository.deleteById(id);
    }

    @Override
    public Manager updateManager(UpdateManagerReq updateManagerReq) throws BaseException {
        Manager manager = getManager(updateManagerReq.getManagerId());
        return updateFunc(manager, updateManagerReq);
    }

    @Override
    public Manager getManagerById(Integer id) throws BaseException {
        return getManager(id);
    }

    @Override
    public List<ManagerRes> getManagerList() {
        return managerRepository.findByOrderByManagerIdAsc()
                                .stream()
                                .map(manager -> modelMapper.map(manager, ManagerRes.class))
                                .collect(Collectors.toList());
    }

    @Override
    public LoginRes login(LoginReq loginReq) {
        ManagerRes managerRes = getManagerRes(loginReq);
        return tokenService.generalToken(managerRes);
    }

    private ManagerRes getManagerRes(LoginReq loginReq) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginReq.getAccount(), loginReq.getPassword());
        authentication = provider.authenticate(authentication);
        ManagerDetails managerDetails = (ManagerDetails) authentication.getPrincipal();
        ManagerRes managerRes = modelMapper.map(managerDetails.getManager(), ManagerRes.class);
        return managerRes;
    }

    private void validAccountExist(AddManagerReq addManagerReq) throws BaseException {
        Manager manager = managerRepository.findByAccount(addManagerReq.getAccount());

        if (manager != null) {
            log.error("新增失敗: {}", CommonCode.N901.getMes());
            throw new BaseException(CommonCode.N901);
        }
    }

    private ManagerRes addFunc(AddManagerReq addManagerReq) {
        addManagerReq.setPassword(passwordEncoder.encode(addManagerReq.getPassword()));
        Manager manager = modelMapper.map(addManagerReq, Manager.class);
        return modelMapper.map(managerRepository.save(manager), ManagerRes.class);
    }

    private Manager getManager(Integer managerId) throws BaseException {
        Manager manager = managerRepository.findById(managerId).orElse(null);

        if (manager == null){
            log.error(CommonCode.N902.getMes());
            throw new BaseException(CommonCode.N902);
        }
        return manager;
    }

    private Manager updateFunc(Manager manager, UpdateManagerReq updateManagerReq) {
        manager.setName(updateManagerReq.getName());
        manager.setPassword(updateManagerReq.getPassword());
        manager.setRole(updateManagerReq.getRole());
        return managerRepository.save(manager);
    }
}
