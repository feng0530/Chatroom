package tw.idv.frank.chatroom.users.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tw.idv.frank.chatroom.common.constant.CommonCode;
import tw.idv.frank.chatroom.common.dto.CommonResult;
import tw.idv.frank.chatroom.common.dto.LoginReq;
import tw.idv.frank.chatroom.common.dto.LoginRes;
import tw.idv.frank.chatroom.common.exception.BaseException;
import tw.idv.frank.chatroom.users.model.dto.AddUsersReq;
import tw.idv.frank.chatroom.users.model.dto.EnableUsersReq;
import tw.idv.frank.chatroom.users.model.dto.UpdateUsersReq;
import tw.idv.frank.chatroom.users.model.dto.UsersRes;
import tw.idv.frank.chatroom.users.service.UsersService;

import java.util.List;

@Tag(name = "Users 相關API")
@CrossOrigin("*")
@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    /**
     * 註冊使用者
     *
     * @param addUsersReq
     * @return
     * @throws BaseException
     */
    @Operation(summary = "Create users")
    @ApiResponse(responseCode = "200", description = "Create success!")
    @PostMapping
    public CommonResult<UsersRes> addUsers(
            @Valid @RequestBody AddUsersReq addUsersReq
    ) throws BaseException {
        return new CommonResult<UsersRes>(CommonCode.CREATE, usersService.addUsers(addUsersReq));
    }

    /**
     * 刪除使用者
     * @param id
     * @return
     */
    @Operation(summary = "Delete users")
    @ApiResponse(responseCode = "200", description = "Delete success!", content = {@Content})
    @DeleteMapping("/{id}")
    public CommonResult deleteUsers(@PathVariable String id) {
        usersService.deleteUsersById(id);
        return new CommonResult(CommonCode.DELETE);
    }

    /**
     * 修改使用者資訊
     * @param updateUsersReq
     * @return
     * @throws BaseException
     */
    @Operation(summary = "Update users")
    @ApiResponse(responseCode = "200", description = "Update success!")
    @PutMapping
    public CommonResult<UsersRes> updateUsers(
            @Valid @RequestBody UpdateUsersReq updateUsersReq
    ) throws BaseException {
        return new CommonResult<UsersRes>(CommonCode.UPDATE, usersService.updateUsers(updateUsersReq));
    }

    /**
     * 查詢使用者列表
     * @return
     */
    @Operation(summary = "Get users list")
    @ApiResponse(responseCode = "200", description = "Get users list")
    @GetMapping
    public CommonResult<List<UsersRes>> getUsersList() {
        return new CommonResult<List<UsersRes>>(CommonCode.SUCCESS, usersService.getUsersList());
    }

    /**
     * 查詢使用者詳情
     * @param id
     * @return
     * @throws BaseException
     */
    @Operation(summary = "Get users by Id")
    @ApiResponse(responseCode = "200", description = "Get users detail")
    @GetMapping("/{id}")
    public CommonResult<UsersRes> getUsersById(@PathVariable String id) throws BaseException {
        return new CommonResult<UsersRes>(CommonCode.SUCCESS, usersService.getUsersById(id));
    }

    /**
     * 審核使用者
     * @param enableUsersReq
     * @return
     * @throws BaseException
     */
    @Operation(summary = "Enable users")
    @ApiResponse(responseCode = "200", description = "Enable user!")
    @PutMapping("/enable")
    public CommonResult<UsersRes> enableUsers(@RequestBody EnableUsersReq enableUsersReq) throws BaseException {
        return new CommonResult<UsersRes>(CommonCode.SUCCESS, usersService.enableUsers(enableUsersReq));
    }

    /**
     *
     * @param loginReq
     * @return
     */
    @Operation(summary = "Users login")
    @ApiResponse(responseCode = "200", description = "Login success!")
    @PostMapping("/login")
    public CommonResult<LoginRes> userLogin(@RequestBody LoginReq loginReq) {
        return new CommonResult<LoginRes>(CommonCode.SUCCESS, usersService.login(loginReq));
    }

    @Operation(summary = "Users logout")
    @ApiResponse(responseCode = "200", description = "Logout success!")
    @PostMapping("/logout")
    public CommonResult userLogout(HttpServletRequest request) {
        return usersService.logout(request);
    }
}
