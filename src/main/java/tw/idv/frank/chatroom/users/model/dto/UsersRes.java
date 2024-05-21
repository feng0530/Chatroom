package tw.idv.frank.chatroom.users.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Date;

@Data
public class UsersRes {

    @Id
    @Schema(description = "使用者編號，預設為自動生成")
    private String userId;

    @Schema(description = "使用者姓名")
    @Size(max = 30)
    @NotBlank
    private String name;

    @Schema(description = "使用者信箱")
    @Size(max = 50)
    @NotBlank
    @Email
    private String email;

    @Schema(description = "使用者性別，0代表女生 ; 1代表男生", example = "0")
    @NotNull
    private String gender;

    @Schema(description = "使用者電話", example = "0912345678")
    @Pattern(regexp = "^$|\\d{10}")
    private String phone;

    @Schema(description = "使用者生日", example = "1999-05-30")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthday;

    @Schema(description = "使用者照片")
    private String photo;

    @Schema(description = "使用者登錄狀態（0: 未登錄，1: 已登錄）", defaultValue = "0")
    private String loginStatus;

    @Schema(description = "使用者狀態（0: 未驗證，1: 已驗證，2: 第三方，3: 驗證失敗）", defaultValue = "0")
    private String userStatus;
}

