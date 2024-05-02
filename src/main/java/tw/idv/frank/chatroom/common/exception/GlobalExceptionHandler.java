package tw.idv.frank.chatroom.common.exception;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tw.idv.frank.chatroom.common.constant.CommonCode;
import tw.idv.frank.chatroom.common.dto.CommonResult;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public CommonResult baseExceptionHandler(BaseException e) {
        return new CommonResult(e.getCode());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public CommonResult httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e) {
        return new CommonResult(CommonCode.N903);
    }
}
