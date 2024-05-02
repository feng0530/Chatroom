package tw.idv.frank.chatroom.manager.model.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import tw.idv.frank.chatroom.common.constant.RoleName;

import java.util.Date;

@Data
public class ManagerRes {

    @Schema(description = "管理員的編號，預設為自動生成", example = "1")
    private Integer managerId;

    @Schema(description = "管理員姓名", example = "Frank")
    private String name;

    @Schema(description = "管理員帳號")
    private String account;

    @Schema(description = "管理員權限", example = "ADMIN")
    private RoleName role;

    @Schema(description = "管理員權建立時間，預設為自動生成")
    private Date createTime;

    @Schema(description = "管理員權最後修改時間，預設為自動生成")
    private Date updateTime;
}
