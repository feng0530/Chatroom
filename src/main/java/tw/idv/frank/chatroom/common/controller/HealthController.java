package tw.idv.frank.chatroom.common.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.idv.frank.chatroom.common.constant.CommonCode;

@Tag(name = "Health API")
@CrossOrigin("*")
@RestController
@Slf4j
public class HealthController {

    @Operation(summary = "Check container health", description = "啟動容器後，健康檢查所呼叫的 API")
    @ApiResponse(responseCode = "200", description = "容器狀態為健康", content = { @Content })
    @GetMapping("/health")
    public CommonCode healthCheck() {
        log.info("Check container health...");
        return CommonCode.SUCCESS;
    }
}
