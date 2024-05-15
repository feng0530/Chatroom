package tw.idv.frank.chatroom.users.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
public class UpdateUsersReq {

    @Id
    @Schema(description = "使用者編號，預設為自動生成")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userId;

    @Schema(description = "使用者姓名")
    @Size(max = 30)
    @NotBlank
    private String name;

    @Schema(description = "使用者密碼", minLength = 6)
    @Size(min = 6, max = 60)
    @NotBlank
    private String password;

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
}
