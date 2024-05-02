package tw.idv.frank.chatroom.manager.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import tw.idv.frank.chatroom.common.constant.RoleName;

@Data
public class ManagerReq {

    @Schema(description = "管理員的編號，預設為自動生成", example = "1")
    private Integer managerId;

    @Schema(description = "管理員姓名", example = "Frank")
    private String name;

    @Schema(description = "管理員密碼")
    private String password;

    @Schema(description = "管理員權限", example = "ADMIN")
    private RoleName role;
}
