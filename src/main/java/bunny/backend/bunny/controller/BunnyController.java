package bunny.backend.bunny.controller;

import bunny.backend.bunny.dto.request.MonthTargetRequest;
import bunny.backend.bunny.dto.response.MonthTargetResponse;
import bunny.backend.bunny.dto.response.TodayResponse;
import bunny.backend.bunny.service.BunnyService;
import bunny.backend.common.ApiResponse;
import org.springframework.web.bind.annotation.*;

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
    // 오늘의 버니 조회 : 현재 퇴근시간만 보이도록 설정해놓음
    @GetMapping("/bunny")
    public ApiResponse<TodayResponse> todayBunny(
            @RequestHeader("member-no") Long memberId) {
        return bunnyService.todayBunny(memberId);
    }
}
