package tw.idv.frank.chatroom.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonCode {

    SUCCESS(200, "Success!"),

    CREATE(200,"新增成功!"),

    READ(200,"查詢成功!"),

    UPDATE(200,"修改成功!"),

    DELETE(200,"刪除成功!"),

    ERROR(400, "發生錯誤!"),

    INSUFFICIENT_PERMISSIONS(403, "權限不足!"),

    TOKEN_ERROR(403, "Token 解析失敗!"),

    RE_LOGIN(403,"請重新登入!"),

    N901(400, "帳號已存在!"),

    N902(400, "Manager不存在!"),

    N903(400, "Role: 僅能為 'ADMIN' 或 'MANAGER'");

    private Integer code;
    private String mes;
}
