package tw.idv.frank.chatroom.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.idv.frank.chatroom.common.constant.CommonCode;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {

    private Integer code;

    private String msg;

    private T result;

    public CommonResult(CommonCode commonCode) {
        this.code = commonCode.getCode();
        this.msg = commonCode.getMes();
    }

    public CommonResult(CommonCode commonCode, T result) {
        this.code = commonCode.getCode();
        this.msg = commonCode.getMes();
        this.result = result;
    }

    public CommonResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
