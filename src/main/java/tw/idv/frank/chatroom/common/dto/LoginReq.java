package tw.idv.frank.chatroom.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginReq {

    @Schema(description = "管理員帳號")
    private String account;

    @Schema(description = "管理員密碼")
    private String password;
}
