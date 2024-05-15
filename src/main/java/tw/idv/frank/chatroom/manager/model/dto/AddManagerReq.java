package tw.idv.frank.chatroom.manager.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Data;
import tw.idv.frank.chatroom.common.constant.RoleName;

@Data
public class AddManagerReq {

    @Schema(description = "管理員姓名", example = "Frank")
    @Size(max = 30)
    @NotBlank
    private String name;

    @Schema(description = "管理員帳號")
    @Size(max = 30)
    @NotBlank
    private String account;

    @Schema(description = "管理員密碼", minLength = 6, maxLength = 30)
    @Size(min = 6, max = 60)
    @NotBlank
    private String password;

    @Schema(description = "管理員權限", example = "ADMIN")
    @NotNull
    @Enumerated(EnumType.STRING)
    private RoleName role;
}
