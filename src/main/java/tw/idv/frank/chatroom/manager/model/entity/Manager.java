package tw.idv.frank.chatroom.manager.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import tw.idv.frank.chatroom.common.constant.RoleName;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@EntityListeners(AuditingEntityListener.class)
@Table(name = "manager")
public class Manager {

    @Id
    @Schema(description = "管理員的編號，預設為自動生成", example = "1")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manager_id")
    private Integer managerId;

    @Schema(description = "管理員姓名", example = "Frank")
    @NotBlank
    private String name;

    @Schema(description = "管理員帳號")
    @NotBlank
    private String account;

    @Schema(description = "管理員密碼")
    @NotBlank
    private String password;

    @Schema(description = "管理員權限", example = "ADMIN")
    @NotNull
    @Enumerated(EnumType.STRING)
    private RoleName role;

    @Schema(description = "管理員權建立時間，預設為自動生成")
    @CreatedDate
    @Column(name = "create_time")
    private Date createTime;

    @Schema(description = "管理員權最後修改時間，預設為自動生成")
    @LastModifiedDate
    @Column(name = "update_time")
    private Date updateTime;
}
