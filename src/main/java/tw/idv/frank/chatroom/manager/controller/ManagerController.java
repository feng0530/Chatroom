package tw.idv.frank.chatroom.manager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tw.idv.frank.chatroom.common.constant.CommonCode;
import tw.idv.frank.chatroom.common.dto.CommonResult;
import tw.idv.frank.chatroom.common.exception.BaseException;
import tw.idv.frank.chatroom.manager.model.dto.ManagerRes;
import tw.idv.frank.chatroom.manager.model.dto.ManagerReq;
import tw.idv.frank.chatroom.manager.model.entity.Manager;
import tw.idv.frank.chatroom.manager.service.ManagerService;

import java.util.List;

@Tag(name = "Manager 相關API")
@CrossOrigin("*")
@RestController
@RequestMapping("/manager")
@Slf4j
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @PostMapping
    public CommonResult<ManagerRes> addManager(@Valid @RequestBody Manager manager) throws BaseException{
        return new CommonResult<ManagerRes>(CommonCode.CREATE, managerService.addManager(manager));
    }

    @DeleteMapping("/{id}")
    public CommonResult deleteManagerById(@PathVariable Integer id) {
        managerService.deleteManagerById(id);
        return new CommonResult(CommonCode.DELETE);
    }

    @PutMapping
    public CommonResult<Manager> updateManagerRole(@RequestBody @Valid ManagerReq managerReq) throws BaseException {
        return new CommonResult<Manager>(CommonCode.UPDATE, managerService.updateManager(managerReq));
    }

    @Operation(summary = "取得 Manager列表")
    @GetMapping("/list")
    public CommonResult<List<ManagerRes>> getManagerList() {
        return new CommonResult<List<ManagerRes>>(CommonCode.SUCCESS, managerService.getManagerList());
    }

    @GetMapping("/list/{id}")
    public CommonResult<Manager> getManagerById(@PathVariable Integer id) {
        return new CommonResult<Manager>(CommonCode.SUCCESS, managerService.getManagerById(id));
    }
}
