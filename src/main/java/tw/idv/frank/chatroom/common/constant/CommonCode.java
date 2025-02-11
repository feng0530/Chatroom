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

    N903(400, "Role: 僅能為 'ADMIN' 或 'MANAGER'"),

    N904(400, "查無相關資訊!"),

    PARAMETER_ERROR(400,"請求的參數未通過檢查規則!"),

    PARAMETER_TYPE_ERROR(400,"請求的參數型別有誤!"),

    Required_Parameter_Missing(400, "缺少必填的參數"),

    LOGIN_ERROR(400, "帳號或密碼錯誤!"),

    U901(400, "信箱已註冊過!"),

    U902(400, "使用者不存在!"),

    U903(400, "帳號尚未經過驗證"),

    F901(400, "已提出好友申請!"),

    F902(400, "此好友關係已不存在!");

    private Integer code;
    private String mes;
}
