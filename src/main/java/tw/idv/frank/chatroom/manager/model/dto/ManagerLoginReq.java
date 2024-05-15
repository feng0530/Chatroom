package tw.idv.frank.chatroom.manager.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ManagerLoginReq {

    @Schema(description = "管理員帳號")
    private String account;

    @Schema(description = "管理員密碼")
    private String password;
}
