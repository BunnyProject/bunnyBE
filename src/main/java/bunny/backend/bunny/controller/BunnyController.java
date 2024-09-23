package bunny.backend.bunny.controller;

import bunny.backend.bunny.dto.request.MonthTargetRequest;
import bunny.backend.bunny.dto.response.MonthTargetResponse;
import bunny.backend.bunny.service.BunnyService;
import bunny.backend.common.ApiResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BunnyController {
    private final BunnyService bunnyService;

    public BunnyController(BunnyService bunnyService) {
        this.bunnyService = bunnyService;
    }
    // 한달 목표 설정
    @PostMapping("/bunny/target")
    public ApiResponse<MonthTargetResponse> monthTarget(
            @RequestHeader("member-no") Long memberId, @RequestBody MonthTargetRequest monthTargetRequest) {
        return bunnyService.monthTarget(memberId,monthTargetRequest);
    }
}
