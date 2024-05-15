package tw.idv.frank.chatroom.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRes {

    @Schema(description = "認證用的Token")
    private String accessToken;

    @Schema(description = "刷新 access的Token")
    private String refreshToken;
}
