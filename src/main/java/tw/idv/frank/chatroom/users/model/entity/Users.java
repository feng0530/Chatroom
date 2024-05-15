package tw.idv.frank.chatroom.users.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users")
public class Users {

    @Id
    @Schema(description = "使用者編號，預設為自動生成")
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
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

    @Schema(description = "使用者密碼", minLength = 6)
    @Size(min = 6, max = 60)
    @NotBlank
    private String password;

    @Schema(description = "使用者性別（0: 女，1: 男）", example = "0")
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
    @Column(name = "login_status")
    private String loginStatus;

    @Schema(description = "使用者狀態（0: 未驗證，1: 已驗證，2: 第三方，3: 驗證失敗）", defaultValue = "0")
    @Column(name = "user_status")
    private String userStatus;

    @Schema(description = "使用者建立時間，預設為自動生成")
    @CreatedDate
    @Column(name = "create_time")
    private Date createTime;

    @Schema(description = "使用者資訊最後修改時間，預設為自動生成")
    @LastModifiedDate
    @Column(name = "update_time")
    private Date updateTime;

    @PrePersist
    public void generateStatus() {
        if (loginStatus == null) {
            loginStatus = "0";
        }

        if (userStatus == null) {
            userStatus = "0";
        }
    }
}
