package tw.idv.frank.chatroom.manager.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.idv.frank.chatroom.common.constant.CommonCode;
import tw.idv.frank.chatroom.common.exception.BaseException;
import tw.idv.frank.chatroom.manager.model.dao.ManagerRepository;
import tw.idv.frank.chatroom.manager.model.dto.ManagerReq;
import tw.idv.frank.chatroom.manager.model.dto.ManagerRes;
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

    @Override
    public ManagerRes addManager(Manager manager) throws BaseException {
        validAccountExist(manager);
        return modelMapper.map(managerRepository.save(manager), ManagerRes.class);
    }

    @Override
    public void deleteManagerById(Integer id) {
        managerRepository.deleteById(id);
    }

    @Override
    public Manager updateManager(ManagerReq managerReq) throws BaseException {
        Manager manager = validManagerExist(managerReq.getManagerId());
        updateManagerDetail(manager, managerReq);

        return managerRepository.save(manager);
    }

    @Override
    public Manager getManagerById(Integer id) {
        return managerRepository.findById(id).orElse(null);
    }

    @Override
    public List<ManagerRes> getManagerList() {
        return managerRepository.findAllByOrderByManagerIdAsc()
                                .stream()
                                .map(manager -> modelMapper.map(manager, ManagerRes.class))
                                .collect(Collectors.toList());
    }

    private void validAccountExist(Manager manager) throws BaseException {
        Manager check = managerRepository.getByAccount(manager.getAccount());

        if (check != null) {
            log.error("新增失敗: {}", CommonCode.N901.getMes());
            throw new BaseException(CommonCode.N901);
        }
    }

    private Manager validManagerExist(Integer managerId) throws BaseException {
        Manager manager = managerRepository.findById(managerId).orElse(null);

        if (manager == null){
            log.error("修改失敗: {}", CommonCode.N902.getMes());
            throw new BaseException(CommonCode.N902);
        }

        return manager;
    }

    private void updateManagerDetail(Manager manager, ManagerReq managerReq) {
        manager.setName(managerReq.getName());
        manager.setPassword(managerReq.getPassword());
        manager.setRole(managerReq.getRole());
    }
}
