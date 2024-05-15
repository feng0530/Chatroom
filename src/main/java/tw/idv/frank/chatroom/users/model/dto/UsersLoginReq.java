package tw.idv.frank.chatroom.users.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersLoginReq {

    @Schema(description = "管理員帳號")
    private String account;

    @Schema(description = "管理員密碼")
    private String password;
}
