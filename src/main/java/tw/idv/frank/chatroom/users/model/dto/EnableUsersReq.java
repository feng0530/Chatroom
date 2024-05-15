package tw.idv.frank.chatroom.users.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class EnableUsersReq {

    @Schema(description = "使用者編號，預設為自動生成")
    private String userId;

    @Schema(description = "使用者狀態（0: 未驗證，1: 已驗證，2: 第三方，3: 驗證失敗）", defaultValue = "0")
    private String userStatus;
}
