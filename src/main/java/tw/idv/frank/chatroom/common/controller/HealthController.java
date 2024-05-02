package tw.idv.frank.chatroom.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Target;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.idv.frank.chatroom.common.constant.CommonCode;

@CrossOrigin("*")
@RestController
@Slf4j
public class HealthController {

    @GetMapping("/health")
    public CommonCode healthCheck() {
        log.info("Check container health...");
        return CommonCode.SUCCESS;
    }
}
