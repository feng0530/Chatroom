package tw.idv.frank.chatroom.common.exception;


import lombok.Getter;
import tw.idv.frank.chatroom.common.constant.CommonCode;

@Getter
public class BaseException extends Exception{

    CommonCode code;

    public BaseException(CommonCode code) {
        this.code = code;
    }

    public BaseException(String mes) {
        super(mes);
    }
}
