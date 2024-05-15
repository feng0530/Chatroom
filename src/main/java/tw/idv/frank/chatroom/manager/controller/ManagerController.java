package tw.idv.frank.chatroom.manager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tw.idv.frank.chatroom.common.constant.CommonCode;
import tw.idv.frank.chatroom.common.dto.CommonResult;
import tw.idv.frank.chatroom.common.dto.LoginReq;
import tw.idv.frank.chatroom.common.dto.LoginRes;
import tw.idv.frank.chatroom.common.exception.BaseException;
import tw.idv.frank.chatroom.manager.model.dto.*;
import tw.idv.frank.chatroom.manager.model.entity.Manager;
import tw.idv.frank.chatroom.manager.service.ManagerService;

import java.util.List;

@Tag(name = "Manager 相關API")
@CrossOrigin("*")
@RestController
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    /**
     * 新增管理員
     * @param addManagerReq
     * @return
     * @throws BaseException
     */
    @Operation(summary = "Create manager")
    @ApiResponse(responseCode = "200", description = "Create success!")
    @PostMapping
    public CommonResult<ManagerRes> addManager(
            @Valid @RequestBody AddManagerReq addManagerReq
    ) throws BaseException{
        return new CommonResult<ManagerRes>(CommonCode.CREATE, managerService.addManager(addManagerReq));
    }

    /**
     * 刪除管理員
     * @param id
     * @return
     */
    @Operation(summary = "Delete manager by Id")
    @ApiResponse(responseCode = "200", description = "Delete success!", content = {@Content})
    @DeleteMapping("/{id}")
    public CommonResult deleteManagerById(@PathVariable Integer id) {
        managerService.deleteManagerById(id);
        return new CommonResult(CommonCode.DELETE);
    }

    /**
     * 修改管理員資訊
     * @param updateManagerReq
     * @return
     * @throws BaseException
     */
    @Operation(summary = "Update manager role")
    @ApiResponse(responseCode = "200", description = "Update success!")
    @PutMapping
    public CommonResult<Manager> updateManagerRole(
            @Valid @RequestBody UpdateManagerReq updateManagerReq
    ) throws BaseException {
        return new CommonResult<Manager>(CommonCode.UPDATE, managerService.updateManager(updateManagerReq));
    }

    /**
     * 查詢管理員列表
     * @return
     */
    @Operation(summary = "Get manager list")
    @ApiResponse(responseCode = "200", description = "Get manager list")
    @GetMapping
    public CommonResult<List<ManagerRes>> getManagerList() {
        return new CommonResult<List<ManagerRes>>(CommonCode.SUCCESS, managerService.getManagerList());
    }

    /**
     * 查詢單一管理員
     * @param id
     * @return
     */
    @Operation(summary = "Get manager by Id")
    @ApiResponse(responseCode = "200", description = "Get manager detail")
    @GetMapping("/{id}")
    public CommonResult<Manager> getManagerById(
        @Parameter(description = "Input manager_id", example = "1")
        @PathVariable Integer id
    ) throws BaseException {
        return new CommonResult<Manager>(CommonCode.SUCCESS, managerService.getManagerById(id));
    }

    /**
     *
     * @param loginReq
     * @return
     */
    @Operation(summary = "Manager login")
    @ApiResponse(responseCode = "200", description = "Login success!")
    @PostMapping("/login")
    public CommonResult<LoginRes> managerLogin(@RequestBody LoginReq loginReq) {
        return new CommonResult<LoginRes>(CommonCode.SUCCESS, managerService.login(loginReq));
    }
}
